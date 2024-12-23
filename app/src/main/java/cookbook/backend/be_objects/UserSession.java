package cookbook.backend.be_objects;

public class UserSession {
	private static UserSession instance;
	private Long userId;
	private String username;
	private boolean isAdmin;
	private boolean unreadMessagesChecked; // flag to use later


	private UserSession() {
	}

	public static UserSession getInstance() {
		if (instance == null) {
			instance = new UserSession();
		}
		return instance;
	}

	public void login(Long userId, String username, boolean isAdmin) {
		this.userId = userId;
		this.username = username;
		this.isAdmin = isAdmin;
		this.unreadMessagesChecked = false;

	}

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void logout() {
		userId = null;
		username = null;
		isAdmin = false;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isUnreadMessagesChecked() {
        return unreadMessagesChecked;
    }

    public void setUnreadMessagesChecked(boolean unreadMessagesChecked) {
        this.unreadMessagesChecked = unreadMessagesChecked;
    }

}
