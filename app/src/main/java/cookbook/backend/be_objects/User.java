package cookbook.backend.be_objects;

import cookbook.backend.DatabaseMng;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
  private Integer userId;
  private String userName;
  private String passwordHash;
  private Boolean isAdmin;
  private DatabaseMng dbManager;
  private final LogIn login;

  public User(Integer userId, String userName, String password, Boolean isAdmin, DatabaseMng dbManager) {
    setUserId(userId);
    setUserName(userName);
    setPassword(password);
    setIsAdmin(isAdmin);
    this.dbManager = dbManager;
    this.login = new LogIn(dbManager);
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
    String sql = "INSERT INTO users (userName, passwordHash, isAdmin) VALUES (?, ?, ?)";
    try (Connection conn = dbManager.getConnection(); // Använder dbManager för att få en Connection
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
        pstmt.setString(1, this.userName);
        pstmt.setString(2, this.passwordHash);
        pstmt.setBoolean(3, this.isAdmin);
            
        int affectedRows = pstmt.executeUpdate();
          return affectedRows > 0;
    } catch (SQLException e) {
        System.err.println("Database error during user insertion: " + e.getMessage());
        return false;
    }
  }
}