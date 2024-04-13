package cookbook.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMng {
    private static String lastErrorMessage;
    private static Connection conn = null; // Hold the connection

    // Method that connects to the database
    public static Connection getConnection() {
        
        // Try catch that tries to connect
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookDB?user=root&password=root&useSSL=false");
            return conn;
        
        // Catches and sends error information
        } catch (SQLException e) {
            lastErrorMessage = e.getMessage();
            return null;
        }
    }

    // Gets the last error message
    public static String getLastErrorMessage() {
        return lastErrorMessage;
    }
}