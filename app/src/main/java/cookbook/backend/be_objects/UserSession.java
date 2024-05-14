package cookbook.backend.be_objects;

public class UserSession {
    private static UserSession instance;
    private Long userId;
    private String username;
    private boolean admin;

    private UserSession() {
        // Privat konstruktor för att förhindra flera instanser
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void login(Long userId, String username, boolean admin) {
        this.userId = userId;
        this.username = username;
        this.admin = admin;
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

    public boolean isAdmin() {
        return admin;
    }
}
