package cookbook.backend;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMng {
    private static String lastErrorMessage;

    public static boolean connect() {
        String sqlQuery = "SELECT * FROM Members;";
        
        try {
            Connection conn = DriverManager
                    .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            rs.next();
            return true;
        
        } catch (SQLException e) {
            lastErrorMessage = e.getMessage();
            return false;
        }
    }

    public static String getLastErrorMessage() {
        return lastErrorMessage;
    }

}