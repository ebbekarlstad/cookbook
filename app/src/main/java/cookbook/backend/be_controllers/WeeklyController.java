package cookbook.backend.be_controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cookbook.backend.DatabaseMng;

import java.util.ArrayList;
import java.util.List;

import cookbook.backend.be_objects.Recipe;

/**
 * WeeklyController
 */

public class WeeklyController {
  private DatabaseMng dbManager;

  public WeeklyController(DatabaseMng dbManager) {
    this.dbManager = dbManager;
  }

  public boolean addWeeklyRecipe(int userId, String recipeId, String dayOfWeek) {
    String sql = "INSERT INTO weekly_recipes (user_id, recipe_id, day_of_week) Values (?, ?, ?)";
    try (Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1, userId);
          pstmt.setString(2, recipeId);
          pstmt.setString(3, dayOfWeek);
          int affectedRows = pstmt.executeUpdate();
          return affectedRows > 0;
        } catch (SQLException e) {
          System.out.println("Error adding weekly recipe: " + e.getMessage());
          return false;
        }
   }
   public boolean deleteWeeklyRecipe(int userId, String recipeId) {
    String sql = "DELETE FROM weekly_recipes WHERE user_id = ? AND recipe_id = ?";
    try (Connection conn = dbManager.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, userId);
        pstmt.setString(2, recipeId);
        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;
     } catch (SQLException e) {
      System.out.println("Error removing weekly recipe: " + e.getMessage());
      return false;
     }

     public List<Recipe> getWeeklyRecipes(int userId) {
      List<Recipe> recipes = new ArrayList<>();
      String sql = "SELECT r.recipe_id, r.recipe_name, r.short_desc, r.detailed_desc FROM recipes r INNER JOIN weekly_recipes ON r.recipe_id = w.recipe_ide WHERE w.user_id = ?";
      try (Connection conn = dbManager.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
              recipes.add(new Recipe(rs.getString("recipe_id"), rs.getString("recipe_name"), rs.getString("short_desc"), rs.getString("detailed_desc")));
            }
            return recipes;
          } catch (SQLException e) {
            System.out.println("Error retrieving weekly recipes: " + e.getMessage());
            return null;
          }
     }

  }
  