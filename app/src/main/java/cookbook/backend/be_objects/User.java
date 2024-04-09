package cookbook.backend.be_objects;

public class User {
  private String id;
  private String userName;
  private String passwordHash;
  private String isAdmin;

  public User(String id, String userName, String passwordHash, String isAdmin) {
    setId(id);
    setUserName(userName);
    setPasswordHash(passwordHash);
    setIsAdmin(isAdmin);
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
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

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getIsAdmin() {
    return this.isAdmin;
  }

  public void setIsAdmin(String isAdmin) {
    this.isAdmin = isAdmin;
  }

}
