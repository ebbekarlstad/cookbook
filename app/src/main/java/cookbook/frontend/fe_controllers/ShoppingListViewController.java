package cookbook.frontend.fe_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.sql.*;

public class ShoppingListViewController {

    @FXML
    private ListView<String> weeksList;
    @FXML
    private ListView<String> dishesList;
    @FXML
    private ListView<String> ingredientsList;

    private Connection connect() throws SQLException {
        // Ensure these credentials match your database configuration
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/cookbookdb", "root", "root");
    }

    @FXML
    public void initialize() {
        loadWeeks();
        weeksList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadDishes(newValue);
            loadAllIngredients();  // Load all ingredients once all dishes are loaded
        });
    }
    

    private void loadWeeks() {
      ObservableList<String> weeks = FXCollections.observableArrayList();
      String sql = "SELECT MIN(Week) AS StartDate, MAX(Week) AS EndDate FROM weekly_dinner_lists " +
                   "GROUP BY YEAR(Week), WEEK(Week) ORDER BY YEAR(Week) DESC, WEEK(Week) DESC";
  
      try (Connection conn = connect();
           PreparedStatement pstmt = conn.prepareStatement(sql);
           ResultSet rs = pstmt.executeQuery()) {
          while (rs.next()) {
              LocalDate startDate = rs.getDate("StartDate").toLocalDate();
              LocalDate endDate = rs.getDate("EndDate").toLocalDate();
              int weekNumber = startDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
              String weekDisplay = "Week " + weekNumber + " (" + startDate + " to " + endDate + ")";
              weeks.add(weekDisplay);
          }
      } catch (SQLException e) {
          System.out.println("Error loading weeks: " + e.getMessage());
      }
      weeksList.setItems(weeks);
  }
  
    
  private void loadDishes(String weekDisplay) {
    ObservableList<String> dishes = FXCollections.observableArrayList();
    // Extract the start and end dates from the week display
    String[] parts = weekDisplay.split(" to ");
    String startDatePart = parts[0].substring(parts[0].indexOf('(') + 1);
    String endDatePart = parts[1].substring(0, parts[1].indexOf(')'));

    String sql = "SELECT RecipeName FROM recipes " +
                 "JOIN dinner_list_recipes ON recipes.RecipeID = dinner_list_recipes.RecipeID " +
                 "JOIN weekly_dinner_lists ON dinner_list_recipes.WeeklyDinnerListID = weekly_dinner_lists.WeeklyDinnerListID " +
                 "WHERE weekly_dinner_lists.Week BETWEEN ? AND ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setDate(1, Date.valueOf(startDatePart));
        pstmt.setDate(2, Date.valueOf(endDatePart));
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                dishes.add(rs.getString("RecipeName"));
            }
        }
    } catch (SQLException e) {
        System.out.println("Error loading dishes: " + e.getMessage());
    }
    dishesList.setItems(dishes);
}



    private void loadAllIngredients() {
      ObservableList<String> allIngredients = FXCollections.observableArrayList();
  
      // Iterating over all dishes in the dishesList
      for (String dish : dishesList.getItems()) {
          String sql = "SELECT i.IngredientName, ri.Amount, ri.Unit " +
                       "FROM ingredients i " +
                       "JOIN recipe_ingredients ri ON i.IngredientID = ri.IngredientID " +
                       "JOIN recipes r ON ri.RecipeID = r.RecipeID " +
                       "WHERE r.RecipeName = ?";
          System.out.println("Executing SQL for dish: " + dish);
          try (Connection conn = connect();
               PreparedStatement pstmt = conn.prepareStatement(sql)) {
              pstmt.setString(1, dish);
              try (ResultSet rs = pstmt.executeQuery()) {
                  if (!rs.isBeforeFirst()) {
                      System.out.println("No data found for dish: " + dish);
                  } else {
                      while (rs.next()) {
                          String ingredientDetail = rs.getString("IngredientName") + " - " +
                                                    rs.getString("Amount") + " " +
                                                    rs.getString("Unit");
                          if (!allIngredients.contains(ingredientDetail)) {
                              allIngredients.add(ingredientDetail);
                          }
                          System.out.println("Loaded ingredient: " + ingredientDetail);
                      }
                  }
              }
          } catch (SQLException e) {
              System.out.println("Error loading ingredients for dish " + dish + ": " + e.getMessage());
          }
      }
      ingredientsList.setItems(allIngredients);
  }
  
    @FXML
    void backButton(ActionEvent event) {
        try {
            Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
            Scene navigationPageScene = new Scene(navigationPageParent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(navigationPageScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
