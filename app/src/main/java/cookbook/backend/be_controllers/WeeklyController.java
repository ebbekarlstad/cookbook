package cookbook.backend.be_controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cookbook.backend.DatabaseMng;

import java.sql.Date;
import java.util.Map;

public class WeeklyController {
  private DatabaseMng dbManager;

  public WeeklyController(DatabaseMng dbManager) {
    this.dbManager = dbManager;
  }

  public void createWeeklyList(int userId, Map<String, String> recipeIdAndDayMap) {
    String insertWeeklyListSQL = "INSERT INTO weekly_dinner_list (UserID, Week) VALUES (?, ?)";
    String insertDinnerListRecipeSQL = "INSERT INTO dinner_list_recipes (WeeklyDinnerListID, RecipeID, DayOfWeek) VALUES (?, ?, ?)";

    try (Connection conn = dbManager.getConnection();
        PreparedStatement pstmtWeeklyList = conn.prepareStatement(insertWeeklyListSQL);
        PreparedStatement pstmtWeeklyDinner = conn.prepareStatement(insertDinnerListRecipeSQL)) {
        
        pstmtWeeklyList.setInt(1, userId);
        pstmtWeeklyList.setDate(2, new Date(System.currentTimeMillis()));
        pstmtWeeklyList.executeUpdate();
        
        ResultSet generatedKeys = pstmtWeeklyList.getGeneratedKeys();
        if (generatedKeys.next()) {
          int weeklyDinnerListId = generatedKeys.getInt(1);
        
        for (Map.Entry <String, String> entry : recipeIdAndDayMap.entrySet()) {
          pstmtWeeklyDinner.setInt(1, weeklyDinnerListId);
          pstmtWeeklyDinner.setString(2, entry.getKey());
          pstmtWeeklyDinner.setString(3, entry.getValue());
          pstmtWeeklyDinner.executeUpdate();
        }
      }
    }
    catch (SQLException e) {
      System.out.println("Error creating the weekly dinner list: " + e.getMessage());
    }
   }
  }
  