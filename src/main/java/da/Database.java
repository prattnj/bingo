package da;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String DB_NAME = "bingo";
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_USER = "pratt";
    private static final String DB_PASSWORD = System.getenv("MYSQL_PASSWORD");

    private static final String[] schema = new String[]{
"""
CREATE TABLE IF NOT EXISTS user (
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    token VARCHAR(255) NOT NULL,
    PRIMARY KEY (username)
)
""", """
CREATE TABLE IF NOT EXISTS board (
    id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    items TEXT NOT NULL,
    created_at BIGINT NOT NULL,
    public BOOL NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (username) REFERENCES user(username)
)
"""
    };

    public static void createDatabase() throws SQLException {
        String query = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.prepareStatement(query).executeUpdate();
            conn.setCatalog(DB_NAME);
            for (String s : schema) {
                conn.prepareStatement(s).executeUpdate();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        conn.setCatalog(DB_NAME);
        return conn;
    }
}
