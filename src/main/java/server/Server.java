package server;

import da.Database;
import spark.Spark;

import java.sql.SQLException;

public class Server {

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);

            Spark.port(port);

            Spark.before((_, response) -> {
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Request-Method", "*");
                response.header("Access-Control-Allow-Headers", "*");
            });

            Spark.path("/auth", () -> {
                Spark.post("/user", AuthHandler::register);
                Spark.post("/session", AuthHandler::login);
                // Spark.delete("/session", null); // logout
            });

            Spark.path("/api", () -> {
                Spark.get("/board", BoardHandler::list);

                Spark.before("/*", (req, _) -> AuthHandler.verifyAuthentication(req));

                Spark.post("/board", BoardHandler::create);
                Spark.put("/board", BoardHandler::update);
                Spark.delete("/board", BoardHandler::delete);
            });

            Database.createDatabase();

            Spark.awaitInitialization();

            System.out.println("Server listening on port " + port + "...");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Port must be specified in the command line arguments as an integer.");
        }
    }
}
