package cookbook.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseMng {

    private String databaseUrl = "jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl);
    }

    public Optional<String> getPasswordHashForUser(String userName) {
      String sql = "SELECT passwordHash FROM user WHERE userName = ?";
      try (Connection conn = getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
              return Optional.of(rs.getString("passwordHash"));
            }
      } catch (SQLException e) {
        System.err.println("SQL Exception in getPasswordHashForUser: " + e.getMessage());
      }
      return Optional.empty();
    }

}

// package cookbook.backend;

// import java.sql.Statement;
// import java.sql.ResultSet;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;

// public class DatabaseMng {
//     private static String lastErrorMessage;

//     // Method that connects to the database
//     public static boolean connect() {
//         String sqlQuery = "SELECT * FROM Members;";
        
//         // Try catch that tries to connect
//         try {
//             Connection conn = DriverManager
//                     .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
            
//             // Allows for SQL statements to be sent
//             Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery(sqlQuery);
//             rs.next();
//             return true;
        
//         // Catches and sends error information
//         } catch (SQLException e) {
//             lastErrorMessage = e.getMessage();
//             return false;
//         }
//     }

//     // Gets the last error message
//     public static String getLastErrorMessage() {
//         return lastErrorMessage;
//     }
// }