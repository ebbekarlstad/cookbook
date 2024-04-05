package cookbook.backend;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMng {
    private static String lastErrorMessage;

    // Method that connects to the database
    public static boolean connect() {
        String sqlQuery = "SELECT * FROM Members;";
        
        // Try catch that tries to connect
        try {
            Connection conn = DriverManager
                    .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
            
            // Allows for SQL statements to be sent
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            rs.next();
            return true;
        
        // Catches and sends error information
        } catch (SQLException e) {
            lastErrorMessage = e.getMessage();
            return false;
        }
    }

    // Gets the last error message
    public static String getLastErrorMessage() {
        return lastErrorMessage;
    }
}