package server;

import da.Database;
import spark.Spark;

import java.sql.SQLException;

public class Server {

    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);

            Spark.port(port);

            Spark.path("/auth", () -> {
                Spark.post("/register", Handler::handleRegister);
                Spark.post("/login", Handler::handleLogin);
            });

            Spark.path("/api", () -> {

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
