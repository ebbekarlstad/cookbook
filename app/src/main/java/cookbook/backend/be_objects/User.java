package cookbook.backend.be_objects;

import cookbook.backend.DatabaseMng;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
  private String userName;
  private String displayName;
  private String passwordHash;
  private Boolean isAdmin;
  private DatabaseMng dbManager;
  private final LogIn login;

  public User(String userName, String displayName, String password, Boolean isAdmin, DatabaseMng dbManager) {
    setUserName(userName);
    setDisplayName(displayName);
    setIsAdmin(isAdmin);
    this.dbManager = dbManager;
    this.login = new LogIn(dbManager);
    setPassword(password);
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
    String sql = "INSERT INTO users (UserName, DisplayName, Password, IsAdmin) VALUES (?, ?, ?, ?)";
    try (Connection conn = dbManager.getConnection(); // Använder dbManager för att få en Connection
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, this.userName);
        pstmt.setString(2, this.displayName);
        pstmt.setString(3, this.passwordHash);
        pstmt.setBoolean(4, this.isAdmin);
            
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