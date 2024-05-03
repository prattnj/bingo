package da;

import model.bean.UserBean;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static void insertUser(UserBean user) throws SQLException {
        String query = "INSERT INTO user (username, password, email, token) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getToken());
            ps.executeUpdate();
        }
    }

    public static void updateUser(UserBean user) throws SQLException {
        insertUser(user);
    }

    public static UserBean getUser(String username) throws SQLException {
        String query = "SELECT * FROM user WHERE username = ?";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserBean(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            } else return null;
        }
    }

    public static UserBean getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserBean(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            } else return null;
        }
    }

    public static boolean validatePassword(String username, String password) throws SQLException {
        UserBean user = getUser(username);
        if (user == null) return false;
        return BCrypt.checkpw(password, user.getPassword());
    }
}
