package dao;

import java.sql.*;
import java.util.*;
import model.Transaction;
import util.DBConnection;

public class TransactionDAO {

    /* ===============================
       SAVE TRANSACTION
       =============================== */
	public int save(Transaction tx) throws Exception {

	    String sql = """
	        INSERT INTO transactions
	        (user_id, receiver_id, amount, device, location, status)
	        VALUES (?, ?, ?, ?, ?, ?)
	    """;

	    PreparedStatement ps =
	        DBConnection.getConnection()
	        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	    ps.setInt(1, tx.getUserId());        // sender
	    ps.setInt(2, tx.getReceiverId());    // receiver
	    ps.setDouble(3, tx.getAmount());
	    ps.setString(4, tx.getDevice());

	    // 🔥 THIS LINE IS CRITICAL
	    ps.setString(5, tx.getLocation());

	    ps.setString(6, tx.getStatus());

	    ps.executeUpdate();

	    ResultSet rs = ps.getGeneratedKeys();
	    return rs.next() ? rs.getInt(1) : 0;
	}


    /* ===============================
       TRANSACTION HISTORY
       =============================== */
	public List<Transaction> findByUserId(int userId) throws Exception {

	    List<Transaction> list = new ArrayList<>();

	    String sql = """
	        SELECT 
	            t.transaction_id,
	            t.user_id,
	            t.receiver_id,
	            t.amount,
	            t.status,
	            t.location,
	            t.transaction_time,
	            u1.username AS sender_name,
	            u2.username AS receiver_name
	        FROM transactions t
	        JOIN users u1 ON t.user_id = u1.user_id
	        JOIN users u2 ON t.receiver_id = u2.user_id
	        WHERE t.user_id = ? OR t.receiver_id = ?
	        ORDER BY t.transaction_time DESC
	    """;

	    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
	    ps.setInt(1, userId);
	    ps.setInt(2, userId);

	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        Transaction tx = new Transaction();
	        tx.setTransactionId(rs.getInt("transaction_id"));
	        tx.setUserId(rs.getInt("user_id"));
	        tx.setReceiverId(rs.getInt("receiver_id"));
	        tx.setAmount(rs.getDouble("amount"));
	        tx.setStatus(rs.getString("status"));
	        tx.setLocation(rs.getString("location"));   // ✅ CRITICAL
	        tx.setTransactionTime(rs.getTimestamp("transaction_time"));
	        tx.setSenderName(rs.getString("sender_name"));
	        tx.setReceiverName(rs.getString("receiver_name"));
	        list.add(tx);
	    }
	    return list;
	}


    /* ===============================
       FRAUD FEATURE SUPPORT METHODS
       =============================== */

    // 🔥 Feature 1: User Average Amount
    public double getUserAverageAmount(int userId) throws Exception {

        String sql = """
            SELECT AVG(amount) AS avg_amount
            FROM transactions
            WHERE user_id = ?
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getDouble("avg_amount") : 0;
    }

    // 🔥 Feature 2: Velocity (Last 1 Minute)
    public int countTransactionsLast1Min(int userId) throws Exception {

        String sql = """
            SELECT COUNT(*) 
            FROM transactions
            WHERE user_id = ?
            AND transaction_time >= NOW() - INTERVAL 1 MINUTE
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getInt(1) : 0;
    }

    // 🔥 Feature 2: Velocity (Last 5 Minutes)
    public int countTransactionsLast5Min(int userId) throws Exception {

        String sql = """
            SELECT COUNT(*)
            FROM transactions
            WHERE user_id = ?
            AND transaction_time >= NOW() - INTERVAL 5 MINUTE
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getInt(1) : 0;
    }
    
    public Set<String> getUserLocations(int userId) throws Exception {

        Set<String> locations = new HashSet<>();

        String sql = """
            SELECT DISTINCT location
            FROM transactions
            WHERE user_id = ?
        """;

        PreparedStatement ps =
            DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            locations.add(rs.getString("location"));
        }
        return locations;
    }
    
    
    /* Count exact amount today (₹50,000 rule) */
    public int countExactAmountToday(int userId, double amount) throws Exception {

        String sql = """
            SELECT COUNT(*)
            FROM transactions
            WHERE user_id = ?
            AND amount = ?
            AND DATE(transaction_time) = CURDATE()
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setDouble(2, amount);

        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getInt(1) : 0;
    }

    /* Daily total */
    public double getTodayTotalAmount(int userId) throws Exception {

        String sql = """
            SELECT IFNULL(SUM(amount),0)
            FROM transactions
            WHERE user_id = ?
            AND DATE(transaction_time) = CURDATE()
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getDouble(1) : 0;
    }

    /* Velocity in seconds */
    public int countTransactionsLastSeconds(int userId, int seconds)
            throws Exception {

        String sql = """
            SELECT COUNT(*)
            FROM transactions
            WHERE user_id = ?
            AND transaction_time >= NOW() - INTERVAL ? SECOND
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, seconds);

        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getInt(1) : 0;
    }


}
