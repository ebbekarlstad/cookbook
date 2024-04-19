package cookbook.backend.be_controllers;
import cookbook.backend.be_objects.User;
import cookbook.backend.DatabaseMng;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserController {
  private DatabaseMng dbManager;
    private User user;

  public UserController(DatabaseMng dbManager) {
    this.dbManager = dbManager;
  }

  public void setUser(User user) {
    this.user = user;
  }

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
}
