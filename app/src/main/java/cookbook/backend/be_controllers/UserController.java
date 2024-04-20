package cookbook.backend.be_controllers;
import cookbook.backend.be_objects.User;
import cookbook.backend.be_objects.CookingOB;
import cookbook.backend.DatabaseMng;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
  private DatabaseMng dbManager;
    private User user;

  public UserController(DatabaseMng dbManager) {
    this.dbManager = dbManager;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean saveToDatabase() {
    String sql = "INSERT INTO users (UserName, DisplayName, Password, IsAdmin) VALUES (?, ?, ?, ?)";
    try (Connection conn = dbManager.getConnection(); // Använder dbManager för att få en Connection
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getDisplayName());
        pstmt.setString(3, user.getPasswordHash());
        pstmt.setBoolean(4, user.getIsAdmin());
            
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

  // add favorite recipe to database
  public boolean addFavoriteRecipe(CookingOB recipe) {
    String sql = "INSERT INTO favorite_recipes (UserName, RecipeName, Ingredients) VALUES (?, ?, ?)";
    try (Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, user.getUserName());
      pstmt.setString(2, recipe.getName());
      pstmt.setString(3, recipe.getIngredients());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        user.addFavoriteRecipe(recipe);
        System.out.println("Favorite recipe added successfully.");
        return true;
      } else {
        System.out.println("No rows affected.");
        return false;
      }
    } catch (SQLException e) {
      System.err.println("Database error during adding favorite recipe: " + e.getMessage());
      return false;
    }
  }

  // remove favorite recipe to database
  public boolean removeFavoriteRecipe(CookingOB recipe) {
    String sql = "INSERT INTO favorite:recipes (UserName, RecipeName, Ingredients) VALUES (?, ?, ?)";
    try (Connection conn = dbManager.getConnection());
      PreparedStatement pstmt = conn.prepareStatement(sql)) {
      
      pstmt.setString(1, user.getUserName());
      pstmt.setString(2, recipe.getName());
      pstmt.setString(3, recipe.getIngredients());
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
      user.removeFavoriteRecipe(recipe);
      System.out.println("Favorite recipe removed successfully.");
      return true;
      } else {
        System.out.println("No rows affected.");
        return false;
      }
    } catch (SQLException e) {
      System.err.println("Database error during removing favorite recipe: " + e.getMessage());
      return false;
    }
  }

  // retrieve all favorite recipes from database
  public List<CookingOB> getFavoriteRecipes() {
    List<CookingOB> recipes = new ArrayList<>();
    String sql = "SELECT RecipeName, Ingredients FROM favorite_recipes WHERE UserName = ?";
    try (Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
      pstmt.setString(1, user.getUserName());
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        String recipeName = rs.getString("RecipeName");
        String ingredients = rs.getString("Ingredients");
        recipes.add(new CookingOB(ingredients, recipeName));
      }
      return recipes;
    } catch (SQLException e) {
      System.err.println("Database error during fetching favorite recipes: " + e.getMessage());
      return null;
    }
  }
}
