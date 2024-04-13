package cookbook.backend.be_objects;

import cookbook.backend.DatabaseMng;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
  private Integer userId;
  private String userName;
  private String displayName;
  private String passwordHash;
  private Boolean isAdmin;
  private DatabaseMng dbManager;
  private final LogIn login;

  public User(Integer userId, String userName, String displayName, String password, Boolean isAdmin, DatabaseMng dbManager) {
    setUserId(userId);
    setUserName(userName);
    setDisplayName(displayName);
    setIsAdmin(isAdmin);
    this.dbManager = dbManager;
    this.login = new LogIn(dbManager);
    setPassword(password);
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getPasswordHash() {
    return this.passwordHash;
  }

  // This hashes the password before storing it.
  public void setPassword(String password) {
    this.passwordHash = login.hashPassword(password);
  }

  public Boolean getIsAdmin() {
    return this.isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public boolean saveToDatabase() {
    String sql = "INSERT INTO users (UserID, UserName, DisplayName, Password, IsAdmin) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = dbManager.getConnection(); // Använder dbManager för att få en Connection
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, this.userId);
        pstmt.setString(2, this.userName);
        pstmt.setString(3, this.passwordHash);
        pstmt.setString(4, this.displayName);
        pstmt.setBoolean(5, this.isAdmin);
            
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