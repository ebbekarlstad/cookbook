package cookbook.backend.be_controllers;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.Recipe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoritesController {
  private DatabaseMng dbManager;

  public FavoritesController(DatabaseMng dbManager) {
    this.dbManager = dbManager;
  }

    public boolean addFavorite(String userId, Recipe recipe) {
      String sql = "INSERT INTO user_favorites (UserID, RecipeID) VALUES (?, ?)";
      try (Connection conn = dbManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1, Integer.parseInt(userId));
          pstmt.setString(2, recipe.getId());  
          int affectedRows = pstmt.executeUpdate();
          return affectedRows > 0;
      } catch (SQLException e) {
        System.err.println("Error adding favorite: " + e.getMessage());
        return false;
      }
    }

    public boolean removeFavorite(String userId, Recipe recipe) {
      String sql = "DELETE FROM user_favorites WHERE UserID = ? AND RecipeID = ?";
      try (Connection conn = dbManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1, Integer.parseInt(userId));
          pstmt.setString(2, recipe.getId());
          int affectedRows = pstmt.executeUpdate();
          return affectedRows > 0;
      } catch (SQLException e) {
          System.err.println("Error removing favorite: " + e.getMessage());
          return false;
      }
    }

    // public List<Recipe> getFavorites(String userId) {
    //   List<Recipe> favorites = new ArrayList<>();
    //   String sql = "SELECT * FROM user_favorites";
    //   try (Connection conn = dbManager.getConnection();
    //         PreparedStatement pstmt = conn.prepareStatement(sql)) {
    //       pstmt.setString(1, userId);
    //       ResultSet rs = pstmt.executeQuery();
    //       while (rs.next()) {
    //         String recipeId = rs.getString("RecipeID");
    //         String ingredients = rs.getString("Ingredients");
    //         String recipeName = rs.getString("RecipeName");
    //         favorites.add(new Recipe(recipeId, ingredients, recipeName, recipeName));
    //       }
    //       return favorites;
    //     } catch (SQLException e) {
    //       System.err.println("Error fetching favorites: " + e.getMessage());
    //       return null;
    //     }
    // }
}
