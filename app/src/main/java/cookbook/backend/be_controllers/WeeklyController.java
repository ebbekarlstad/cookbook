package cookbook.backend.be_controllers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.User;

import java.sql.Date;
import java.util.List;

public class WeeklyController {
  private DatabaseMng dbManager;

  public WeeklyController(DatabaseMng dbManager) {
    this.dbManager = dbManager;
  }

  public void createWeeklyList(int userId, List<String> recipeIds) {
    String insertWeeklyListSQL = "INSERT INTO weekly_dinner_list (UserID, Week) VALUES (?, ?)";
    String insertDinnerListRecipeSQL = "INSET INTO dinner_list_recipes (WeeklyDinnerListID, RecipeID, DayOfWeek) VALUES (?, ?, ?)";

    try (Connection conn = dbManager.getConnection();
        PreparedStatement pstmtWeeklyList = conn.prepareStatement(insertWeeklyListSQL);
        PreparedStatement pstmtWeeklyDinner = conn.prepareStatement(insertDinnerListRecipeSQL)) {
        
        pstmtWeeklyList.setInt(1, userId);
        pstmtWeeklyList.setDate(1, new Date(System.currentTimeMillis()));
        pstmtWeeklyList.executeUpdate();
        
        // ResultSet och Map för veckodagarna
        }

    }
  }


}
