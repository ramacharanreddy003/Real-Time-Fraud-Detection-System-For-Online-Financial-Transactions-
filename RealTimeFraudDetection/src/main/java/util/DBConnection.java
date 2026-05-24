package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection con;

    public static Connection getConnection() {

        try {
            if (con == null || con.isClosed()) {

                // 1️⃣ Load MySQL Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // 2️⃣ Database details (CHANGE PASSWORD ONLY)
                String url = "jdbc:mysql://localhost:3306/fraud_app";
                String username = "root";
                String password = "2004";  // 👈 change this

                // 3️⃣ Create connection
                con = DriverManager.getConnection(url, username, password);

                System.out.println("✅ Database connected successfully");
            }

        } catch (Exception e) {
            System.out.println("❌ Database connection failed");
            e.printStackTrace();
        }

        return con;
    }
}
