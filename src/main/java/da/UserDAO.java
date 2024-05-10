package da;

import model.bean.UserBean;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static void insert(UserBean user) throws SQLException {
        String query = "INSERT INTO user (username, password, email, token) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.username());
            ps.setString(2, BCrypt.hashpw(user.password(), BCrypt.gensalt()));
            ps.setString(3, user.email());
            ps.setString(4, user.token());
            ps.executeUpdate();
        }
    }

    public static void updateToken(String username, String token) throws SQLException {
        String query = "UPDATE user SET token = ? WHERE username = ?";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, token);
            ps.setString(2, username);
            ps.executeUpdate();
        }
    }

    public static UserBean get(String username) throws SQLException {
        String query = "SELECT * FROM user WHERE username = ?";
        return getUserByString(query, username);
    }

    public static UserBean getByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        return getUserByString(query, email);
    }

    public static UserBean getByToken(String token) throws SQLException {
        String query = "SELECT * FROM user WHERE token = ?";
        return getUserByString(query, token);
    }

    private static UserBean getUserByString(String query, String key) throws SQLException {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserBean(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            } else return null;
        }
    }

    public static boolean validatePassword(String username, String password) throws SQLException {
        UserBean user = get(username);
        if (user == null) return false;
        return BCrypt.checkpw(password, user.password());
    }
}
