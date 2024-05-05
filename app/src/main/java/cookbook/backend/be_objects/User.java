package cookbook.backend.be_objects;
import cookbook.backend.DatabaseMng;

import java.util.ArrayList;
import java.util.List;

public class User {
  private Long userId;
  private String userName;
  private String displayName;
  private String passwordHash;
  private Boolean isAdmin;
  private final LogIn login;
  private List<Recipe> favoriteRecipes = new ArrayList<>();

  public User(Long userId, String userName, String displayName, String password, Boolean isAdmin, DatabaseMng dbManager, String favourites) {
    this.userId = userId != null ? userId : 1L;
    setUserName(userName);
    setDisplayName(displayName);
    setIsAdmin(isAdmin);
    this.login = new LogIn(dbManager);
    setPassword(password);
  }

  public Long getUserId() {
    return userId;
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
    String hashedPassword = login.hashPassword(password);
    if (hashedPassword != null) {
      this.passwordHash = hashedPassword;
    } else {
      System.err.println("Failed to hash password");
      throw new IllegalStateException("Password hashing failed.");
    }
  }

  public Boolean getIsAdmin() {
    return this.isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public void addFavoriteRecipe(Recipe recipe) {
    if(!favoriteRecipes.contains(recipe)) {
      favoriteRecipes.add(recipe);
    }
  }

  public void removeFavoriteRecipe(Recipe recipe) {
    favoriteRecipes.remove(recipe);
  }

  public List<Recipe> getFavoriteRecipes() {
    return new ArrayList<>(favoriteRecipes);
  }
}