package dao;

import java.sql.*;
import util.DBConnection;

public class WalletDAO {
	
	
	
	public void ensureWalletExists(int userId) throws Exception {

	    Connection con = DBConnection.getConnection();

	    String checkSql = "SELECT wallet_id FROM wallet WHERE user_id=?";
	    PreparedStatement checkPs = con.prepareStatement(checkSql);
	    checkPs.setInt(1, userId);
	    ResultSet rs = checkPs.executeQuery();

	    if (!rs.next()) {
	        String insertSql =
	            "INSERT INTO wallet(user_id, balance) VALUES (?, 10000)";
	        PreparedStatement insertPs = con.prepareStatement(insertSql);
	        insertPs.setInt(1, userId);
	        insertPs.executeUpdate();
	    }
	}


    // Create wallet for new user
    public void createWallet(int userId) throws Exception {
        String sql = "INSERT INTO wallet(user_id,balance) VALUES(?,10000)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, userId);
        ps.executeUpdate();
    }

    public double getBalance(int userId) throws Exception {
        String sql = "SELECT balance FROM wallet WHERE user_id=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getDouble("balance") : 0;
    }

    public void addBalance(int userId, double amount) throws Exception {
        String sql = "UPDATE wallet SET balance = balance + ? WHERE user_id=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setDouble(1, amount);
        ps.setInt(2, userId);
        ps.executeUpdate();
    }

    public void deductBalance(int userId, double amount) throws Exception {
        String sql = "UPDATE wallet SET balance = balance - ? WHERE user_id=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setDouble(1, amount);
        ps.setInt(2, userId);
        ps.executeUpdate();
    }

    
    

}
