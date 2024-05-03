package cookbook.backend.be_controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cookbook.backend.DatabaseMng;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


import cookbook.backend.be_objects.Recipe;

/**
 * WeeklyController
 */

public class WeeklyController {
  private DatabaseMng dbManager;

  public WeeklyController(DatabaseMng dbManager) {
    this.dbManager = dbManager;
  }



     public List<Date> getWeeklyList(int userId) {
      List<Date> weeklyLists = new ArrayList<>();
      String sql = "SELECT week FROM weekly_dinner_lists WHERE UserID = ?";
      try (Connection conn = dbManager.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
              weeklyLists.add(rs.getDate("Week"));
            }
          } catch (SQLException e) {
            System.out.println("Error retrieving weekly recipes: " + e.getMessage());
            return null;
          }
          return weeklyLists;
     }


    // public boolean addWeeklyList(int userId, Date weekStartDate) {
    //   String sql = "INSERT INTO weekly_dinner_lists";
    //   try (Connection conn = dbManager.getConnection();
    //       PreparedStatement pstmt = conn.prepareStatement(sql)) {
    //         pstmt.setInt(1, userId);
    //         pstmt.setDate(2, weekStartDate);
    //         int affectedRows = pstmt.executeUpdate();
    //         return affectedRows > 0;
    //       } catch (SQLException e) {
    //         System.out.println("Error adding weekly List: " + e.getMessage());
    //         return false;
    //       }
    // }


    // public boolean deleteWeeklyList(int userId, Date weekStartDate) {
    //   String sql = "DELETE FROM weekly_recipes WHERE user_id = ? AND Week = ?";
    //   try (Connection conn = dbManager.getConnection();
    //     PreparedStatement pstmt = conn.prepareStatement(sql)) {
    //       pstmt.setInt(1, userId);
    //       pstmt.setDate(2, weekStartDate);
    //       int affectedRows = pstmt.executeUpdate();
    //       return affectedRows > 0;
    //   } catch (SQLException e) {
    //     System.out.println("Error removing weekly recipe: " + e.getMessage());
    //     return false;
    //   }
    // }


    public List <Recipe> getRecipesForDay(int userId, Date weekStartDate, String dayOfWeek) {
      List <Recipe> recipes = new ArrayList<>();
      String sql = "SELECT r.recipe_id, r.recipe_name, r.short_desc, r.detailed_desc FROM recipes r INNER JOIN weekly_recipes wr ON r.recipe_id = wr.recipe_id, INNER JOIN weekly_dinner_lists wl ON wr.WeeklyDinnerListID = w1.WeeklyDinnerListID WHERE w1.UserID = ? w1.Week = ? AND wr.day_of_week = ?";
      try (Connection conn = dbManager.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDate(2, weekStartDate);
            pstmt.setString(3, dayOfWeek);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
              Recipe recipe = new Recipe(
                rs.getString("recipe_id"),
                rs.getString("recipe_name"),
                rs.getString("short_desc"),
                rs.getString("detailed_desc")
              );
              recipes.add(recipe);
            }
          } catch (SQLException e) {
            System.out.println("Error retrieving recipes for day: " + e.getMessage());
          }
          return recipes;
    }



    public boolean addRecipeToWeeklyList(int userId, Date weekStartDate, String recipeId, String dayOfWeek) {
      String sql = "INSERT INTO weekly_recipes (user_id, week, recipe_id, day_of_week) VALUE (?, ?, ?, ?)";
      try (Connection conn = dbManager.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDate(2, weekStartDate);
            pstmt.setString(3, recipeId);
            pstmt.setString(4, dayOfWeek);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
          } catch (SQLException e) {
            System.out.println("Erron adding recipe to weekly list: " + e.getMessage());
            return false;
          }
    }

    public boolean removeRecipeFromWeeklyList(int userId, Date weekStartDate, String recipeId, String dayOfWeek) {
      String sql = "DELETE FROM weekly_recipes where user_id = ? AND week = ? AND recipe_id = ? AND day_of_week = ?";
      try (Connection conn = dbManager.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1, userId);
          pstmt.setDate(2, weekStartDate);
          pstmt.setString(3, recipeId);
          pstmt.setString(4, dayOfWeek);
          int affectedRows = pstmt.executeUpdate();
          return affectedRows > 0;
          } catch (SQLException e) {
            System.out.println("Error removing recipe from weekly lists: " + e.getMessage());
            return false;
          }
    }

    public Map<String, List<Recipe>> getWeeklyRecipes(int userId) {
      Map<String, List<Recipe>> recipesForWeek = new HashMap<>();
      String[] daysOfWeek = {"Monady", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
      Date weekStartDate = getCurrentWeekStartDate();
      for (String day : daysOfWeek) {
        recipesForWeek.put(day, getRecipesForDay(userId, weekStartDate, day));
      }
      return recipesForWeek;
    }

    private Date getCurrentWeekStartDate() {
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
      return new Date(cal.getTimeInMillis());
    }

}
  