package cookbook.backend.be_controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
      String sql = "SELECT r.recipe_id, r.recipe_name, r.short_desc, r.detailed_desc " +
      "FROM recipes r " +
      "INNER JOIN weekly_recipes wr ON r.recipe_id = wr.recipe_id " +
      "INNER JOIN weekly_dinner_lists wl ON wr.WeeklyDinnerListID = wl.WeeklyDinnerListID " +
      "WHERE wl.UserID = ? AND wl.Week = ? AND wr.day_of_week = ?";
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

    public Map<String, List<Recipe>> getWeeklyRecipes(int userId, Date weekStartDate) {
      Map<String, List<Recipe>> recipesForWeek = new HashMap<>();
      String[] daysOfWeek = {"Monady", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
      for (String day : daysOfWeek) {
        recipesForWeek.put(day, getRecipesForDay(userId, weekStartDate, day));
      }
      return recipesForWeek;
    }

    public int ensureWeeklyDinnerListExists(int userId, Date weekStartDate) {
      try (Connection conn = dbManager.getConnection()) {
        String checkSql = "SELECT WeeklyDinnerListID FROM weekly_dinner_list WHERE UserID = ? AND Week = ?";
        try (PreparedStatement check = conn.prepareStatement(checkSql)) {
          check.setInt(1, userId);
          check.setDate(2, weekStartDate);
          ResultSet rs = check.executeQuery();
          if (rs.next()) {
            return rs.getInt("WeeklyDinnerListID");
          }
        }
        String insertSql = "INSERT INTO weekly_dinner_list (UserID, Week) VALUES (?, ?)";
        try (PreparedStatement insert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
          insert.setInt(1, userId);
          insert.setDate(2, weekStartDate);
          int affectedRows = insert.executeUpdate();
          if (affectedRows > 0) {
            ResultSet rs = insert.getGeneratedKeys();
            if (rs.next()) {
              return rs.getInt(1);
            }
          }
        }
      } catch (SQLException e) {
        System.out.println("Error ensuring weekly dinner lists exist: " + e.getMessage());
      }
      return -1;
    }

    public List<Date> getYearlyWeeks() {
      List<Date> yearlyWeeks = new ArrayList<>();
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

      int year = cal.get(Calendar.YEAR);
      while(cal.get(Calendar.YEAR) == year) {
        yearlyWeeks.add(new Date(cal.getTimeInMillis()));
        cal.add(Calendar.WEEK_OF_YEAR, 1);
      }
      return yearlyWeeks;
    }

    public List<String> getWeekdays() {
      List<String> weekdays = new ArrayList<>();
      List<Date> yearlyWeeks = getYearlyWeeks();
      Calendar cal = Calendar.getInstance();

      for (Date weekStartDate : yearlyWeeks) {
        cal.setTime(weekStartDate);

        for (int i = 0; i < 7; i++) {
          String dayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(cal.getTime());
          weekdays.add(dayOfWeek);
          cal.add(Calendar.DAY_OF_WEEK, 1);
        }
      }
      return weekdays;
    }

}
  