package cookbook.backend.be_objects;

public class UserSession {
  private static UserSession instance;
  private Long userId;
  private String username;
  private String role;

  private UserSession() {}

  public static synchronized UserSession getInstance() {
      if (instance == null) {
          instance = new UserSession();
      }
      return instance;
  }

  public Long getUserId() {
      return userId;
  }

  public void setUserId(Long userId) {
      this.userId = userId;
  }

  public String getUsername() {
      return username;
  }

  public void setUsername(String username) {
      this.username = username;
  }

  public String getRole() {
      return role;
  }

  public void setRole(String role) {
      this.role = role;
  }

  public boolean isAdmin() {
      return "admin".equalsIgnoreCase(role);
  }
}
