package cookbook.backend.be_controllers;
import cookbook.backend.be_objects.User;
import cookbook.backend.DatabaseMng;

import java.sql.*;

public class    UserController {
  private DatabaseMng dbManager;
  private User user;


  public UserController(DatabaseMng dbManager) throws SQLException {
    this.dbManager = dbManager;
  }

  public void setUser(User user) {
    this.user = user;
  }

    public static User loggedInUser;

    public boolean saveToDatabase() {
    String sql = "INSERT INTO users (UserName, DisplayName, Password, IsAdmin) VALUES (?, ?, ?, ?)";
    try (Connection conn = dbManager.getConnection(); // Använder dbManager för att få en Connection
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getDisplayName());
        pstmt.setString(3, user.getPasswordHash());
        pstmt.setBoolean(4, user.getIsAdmin());
            
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
          System.out.println("User saved successfully.");
          return true;
        } else {
          System.out.println("No rows affected.");
          return false;
        }
    } catch (SQLException e) {
        System.err.println("Database error during user insertion: " + e.getMessage());
        return false;
    }
  }

  // For later when logged in user has been implemented.

//    public static User searchForUser(String UserName, String Password) throws SQLException {
//
//        String query = "SELECT * FROM user WHERE username=(?) AND password=(?) LIMIT 1;";
//
//        // If theres no user with that information, return null.
//        loggedInUser = null;
//        Connection conn = DriverManager
//                .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
//
//        try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
//            sqlStatement.setString(1, UserName);
//            sqlStatement.setString(2, Password);
//            ResultSet result = sqlStatement.executeQuery();
//            if (result.next()) {
//                loggedInUser = new User(
//                        result.getLong("UserID"),
//                        result.getString("Username"),
//                        result.getString("password"),
//                        result.getBoolean("IsAdmin"));
//            }
//            result.close();
//        } catch (SQLException x) {
//            System.out.println(x);
//        }
//        return loggedInUser;
//    }
}

