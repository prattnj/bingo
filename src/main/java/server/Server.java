package server;

import com.google.gson.JsonSyntaxException;
import da.Database;
import model.exception.BadRequestException;
import model.exception.ConflictException;
import model.exception.ServerErrorException;
import model.exception.UnauthorizedException;
import model.request.AuthRequest;
import model.request.WriteBoardRequest;
import service.AuthService;
import service.BoardService;
import spark.Spark;

import java.sql.SQLException;

public class Server {

    private static void run(int port) throws SQLException {
        Spark.port(port);

        Spark.before((_, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "*");
            response.header("Access-Control-Allow-Headers", "*");
        });

        setupEndpoints();
        mapExceptions();

        Database.createDatabase();

        Spark.awaitInitialization();
    }

    private static void setupEndpoints() {
        Spark.path("/auth", () -> {
            Spark.post("/user", (req, res) -> Handler.handle(req, res, new AuthService.Register(), AuthRequest.class));
            Spark.post("/session", (req, res) -> Handler.handle(req, res, new AuthService.Login(), AuthRequest.class));
            // Spark.delete("/session", null); // logout
        });

        Spark.path("/api", () -> {
            Spark.before("/*", (req, _) -> Handler.verifyAuthentication(req));

            Spark.get("/board/all", (req, res) -> Handler.handle(req, res, new BoardService.ListAll(), null));
            Spark.get("/board", (req, res) -> Handler.handle(req, res, new BoardService.ListMine(), null));
            Spark.post("/board", (req, res) -> Handler.handle(req, res, new BoardService.Create(), WriteBoardRequest.class));
            Spark.put("/board", (req, res) -> Handler.handle(req, res, new BoardService.Update(), WriteBoardRequest.class));
            Spark.delete("/board", (req, res) -> Handler.handle(req, res, new BoardService.Delete(), WriteBoardRequest.class));
        });
    }

    private static void mapExceptions() {
        Spark.exception(BadRequestException.class, (e, _, res) -> {
            res.status(400);
            res.body(Handler.newResponse(e.getMessage()));
        });
        Spark.exception(JsonSyntaxException.class, (_, _, res) -> {
            res.status(400);
            res.body(Handler.newResponse("JSON syntax error"));
        });
        Spark.exception(UnauthorizedException.class, (e, _, res) -> {
            res.status(401);
            res.body(Handler.newResponse(e.getMessage()));
        });
        Spark.exception(ConflictException.class, (e, _, res) -> {
            res.status(409);
            res.body(Handler.newResponse(e.getMessage()));
        });
        Spark.exception(ServerErrorException.class, (_, _, res) -> {
            res.status(500);
            res.body(Handler.newResponse("Server error"));
        });
    }

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);

            run(port);

            System.out.println("Server listening on port " + port + "...");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Port must be specified in the command line arguments as an integer.");
        }
    }
}
