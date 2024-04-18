package cookbook.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseMng {

  private String lastErrorMessage = "";

  private String databaseUrl = "jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false";

    public Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(databaseUrl);
            return conn;
        } catch (SQLException e) {
            lastErrorMessage = e.getMessage();
            throw e;
        }
    }

    public Optional<String> getPasswordHashForUser(String userName) {
      String sql = "SELECT Password FROM users WHERE UserName = ?";
      try (Connection conn = getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setString(1, userName);
          ResultSet rs = pstmt.executeQuery();
          if (rs.next()) {
            return Optional.of(rs.getString("Password"));
          } 
        } catch (SQLException e) {
            System.err.println("SQL Exception in getPasswordHashForUser: " + e.getMessage());
            lastErrorMessage = e.getMessage();
        }
        return Optional.empty();
    }

    public boolean connect() {
       try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
        return conn != null;
      } catch (SQLException e) {
        lastErrorMessage = e.getMessage();
        return false;
      }
    }

    public String getLastErrorMessage() {
      return lastErrorMessage;
    }
}
