package cookbook.backend.be_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
  private Integer userId;
  private String userName;
  private String passwordHash;
  private Boolean isAdmin;
  private final LogIn login = new LogIn();

  public User(Integer userId, String userName, String password, Boolean isAdmin) {
    setUserId(userId);
    setUserName(userName);
    setPassword(password);
    setIsAdmin(isAdmin);
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
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
      if (conn == null) {
        System.err.println("Can't connect to the database.");
        return false;
      }
      // Defining SQL query to insert user data into the "users" table
      String insertQuery = "INSERT INTO users (userName, passwordHash, isAdmin) VALUES (?, ?, ?)";
      try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
        pstmt.setString(1, this.userName);
        pstmt.setString(2, this.passwordHash);
        pstmt.setBoolean(3, this.isAdmin);

        // Execute the update query and get the number of affected rows, returns true if at least one is affected.
        // By checking affectedRows we can se if new user added is successful or not, if 0 it means no users were added.
        int affectedRows = pstmt.executeUpdate();
          return affectedRows > 0;
        } catch (SQLException e) {
          System.err.println("SQL exception during user insertion: " + e.getMessage());
          return false;
        }
    } catch (SQLException e) {
        // Handles errors if database connection fails.
        System.err.println("Database connection error: " + e.getMessage());
        return false;
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          System.err.println("Unable to close database connection: " + e.getMessage());
          }
      }
    }
  }
}