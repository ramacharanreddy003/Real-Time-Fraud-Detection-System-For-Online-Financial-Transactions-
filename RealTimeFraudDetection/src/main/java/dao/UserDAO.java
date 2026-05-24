package dao;

import java.sql.*;
import model.User;
import util.DBConnection;

public class UserDAO {

    // Register user
    public int register(User user) throws Exception {
        String sql = "INSERT INTO users(username,password,email,mobile) VALUES(?,?,?,?)";
        PreparedStatement ps = DBConnection.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getMobile());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // user_id
        }
        return 0;
    }

    // Login user
    public User login(String username, String password) throws Exception {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setMobile(rs.getString("mobile"));
            user.setCreatedAt(rs.getTimestamp("created_at"));
            return user;
        }
        return null;
    }
    
    public User validateUser(String username, String password) throws Exception {

        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            return user;
        }
        return null;
    }
    
    public User findByUsernameOrMobile(String input) throws Exception {

        String sql = "SELECT * FROM users WHERE username=? OR mobile=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, input);
        ps.setString(2, input);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User u = new User();
            u.setUserId(rs.getInt("user_id"));
            u.setUsername(rs.getString("username"));
            u.setMobile(rs.getString("mobile"));
            return u;
        }
        return null;
    }
}
