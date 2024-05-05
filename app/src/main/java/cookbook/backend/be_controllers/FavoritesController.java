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

    public boolean addFavorite(Long userId, Recipe recipe) {
      String sql = "INSERT INTO user_favorites (UserID, RecipeID) VALUES (?, ?)";
      try (Connection conn = dbManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setLong(1, userId);
          pstmt.setString(2, recipe.getId());  
          int affectedRows = pstmt.executeUpdate();
          return affectedRows > 0;
      } catch (SQLException e) {
        System.err.println("Error adding favorite: " + e.getMessage());
        return false;
      }
    }

    public boolean removeFavorite(Long userId, Recipe recipe) {
      String sql = "DELETE FROM user_favorites WHERE UserID = ? AND RecipeID = ?";
      try (Connection conn = dbManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setLong(1, userId);
          pstmt.setString(2, recipe.getId());
          int affectedRows = pstmt.executeUpdate();
          return affectedRows > 0;
      } catch (SQLException e) {
          System.err.println("Error removing favorite: " + e.getMessage());
          return false;
      }
    }

    public List<Recipe> getFavorites(Long userId) {
      List<Recipe> favoriteRecipes = new ArrayList<>();
      String sql = "SELECT r.RecipeID, r.RecipeName, r.ShortDesc, r.DetailedDesc FROM user_favorites uf JOIN recipes r ON uf.RecipeID = r.RecipeID WHERE uf.UserID = ?";
      try (Connection conn = dbManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setLong(1, userId);
          ResultSet rs = pstmt.executeQuery();
          while (rs.next()) {
            String recipeId = rs.getString("RecipeID");
            String recipeName = rs.getString("RecipeName");
            String shortDesc = rs.getString("ShortDesc");
            String detailedDesc = rs.getString("DetailedDesc");
            Recipe recipe = new Recipe(recipeId, recipeName, shortDesc, detailedDesc);
            favoriteRecipes.add(recipe);
          }
          return favoriteRecipes;
        } catch (SQLException e) {
          System.err.println("Error fetching favorites: " + e.getMessage());
          return new ArrayList<>();
        }
    }
}
