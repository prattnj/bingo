package da;

import com.google.gson.Gson;
import model.bean.BoardBean;
import model.bean.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

public class BoardDAO {

    public static void insert(BoardBean board) throws SQLException {
        String query = "INSERT INTO board (id, name, username, items, created_at, public) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, board.id());
            ps.setString(2, board.username());
            ps.setString(3, new Gson().toJson(board.items()));
            ps.setLong(4, board.createdAt());
            ps.setBoolean(5, board.isPublic());
            ps.executeUpdate();
        }
    }

    public static void update(BoardBean board) throws SQLException {
        String query = "UPDATE board SET name = ?, items = ?, public = ? WHERE id = ?";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, board.name());
            ps.setString(2, new Gson().toJson(board.items()));
            ps.setBoolean(3, board.isPublic());
            ps.setString(4, board.id());
            ps.executeUpdate();
        }
    }

    public static BoardBean get(String id) throws SQLException {
        String query = "SELECT * FROM board WHERE id = ?";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return getBoardFromResultSet(rs);
            else return null;
        }
    }

    public static Collection<BoardBean> getAllUser(String username) throws SQLException {
        String query = "SELECT * FROM board WHERE username = ?";
        return getMultipleBoards(query, username);
    }

    public static Collection<BoardBean> getAllPublic() throws SQLException {
        String query = "SELECT * FROM board WHERE public = ?";
        return getMultipleBoards(query, "1");
    }

    public static void delete(String id) throws SQLException {
        String query = "DELETE FROM board WHERE id = ?";
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    private static Collection<BoardBean> getMultipleBoards(String query, String key) throws SQLException {
        Collection<BoardBean> boards = new HashSet<>();
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) boards.add(getBoardFromResultSet(rs));
        }
        return boards;
    }

    private static BoardBean getBoardFromResultSet(ResultSet rs) throws SQLException {
        String id = rs.getString(1);
        String name = rs.getString(2);
        String username = rs.getString(3);
        Item[][] items = new Gson().fromJson(rs.getString(4), Item[][].class);
        long createdAt = rs.getLong(5);
        boolean isPublic = rs.getBoolean(6);
        return new BoardBean(id, name, username, items, createdAt, isPublic);
    }
}
