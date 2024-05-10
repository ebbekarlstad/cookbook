package cookbook.backend.be_objects;

public class UserSession {
    private static UserSession instance;
    private Long userId;
  
    private UserSession() {}
  
    public static UserSession getInstance() {
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
  }
  