package dao;

import java.sql.*;
import util.DBConnection;

public class FraudAnalysisDAO {

    public void save(int transactionId, int riskScore, String reason) throws Exception {

        String sql = """
            INSERT INTO fraud_analysis(transaction_id,risk_score,fraud_reason)
            VALUES(?,?,?)
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, transactionId);
        ps.setInt(2, riskScore);
        ps.setString(3, reason);
        ps.executeUpdate();
    }
}
