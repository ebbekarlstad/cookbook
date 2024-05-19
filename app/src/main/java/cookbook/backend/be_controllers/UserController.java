package cookbook.backend.be_controllers;

import cookbook.backend.be_objects.User;
import cookbook.backend.DatabaseMng;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.sql.Statement;
import java.sql.ResultSet;

public class UserController {
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

  // Method to get all users from the database.
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT UserID, DisplayName FROM users"; // Adjust SQL as needed
    try (Connection conn = this.dbManager.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        User user = new User(rs.getLong("UserID"), rs.getString("DisplayName"), sql, sql, null, dbManager, sql);
        users.add(user);
      }
    } catch (SQLException e) {
      System.err.println("Error retrieving users: " + e.getMessage());
      return Collections.emptyList();
    }
    return users;
  }
}
