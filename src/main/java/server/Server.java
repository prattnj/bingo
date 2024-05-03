package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

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

            Spark.awaitInitialization();

            System.out.println("Server listening on port " + port);
        } catch (Exception e) {
            System.out.println("Port must be specified in the command line arguments as an integer.");
        }
    }
}
