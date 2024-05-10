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
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class ShoppingListViewController {

    @FXML
    private ListView<String> weeksList;
    @FXML
    private ListView<String> dishesList;
    @FXML
    private ListView<String> ingredientsList;
    @FXML
    private ListView<String> ShoppingList; // ListView to display the shopping list items

    private Connection connect() throws SQLException {
        // Ensure these credentials match your database configuration
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/cookbookdb", "root", "root");
    }

    @FXML
    public void initialize() {
        loadWeeks();
        weeksList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadDishes(newValue);
            loadAllIngredients();
            loadShoppingListItems(newValue); // Load shopping list for the selected week
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
        for (String dish : dishesList.getItems()) {
            String sql = "SELECT i.IngredientName, ri.Amount, ri.Unit " +
                         "FROM ingredients i " +
                         "JOIN recipe_ingredients ri ON i.IngredientID = ri.IngredientID " +
                         "JOIN recipes r ON ri.RecipeID = r.RecipeID " +
                         "WHERE r.RecipeName = ?";
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, dish);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String ingredientDetail = rs.getString("IngredientName") + " - " +
                                                  rs.getString("Amount") + " " +
                                                  rs.getString("Unit");
                        allIngredients.add(ingredientDetail);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error loading ingredients: " + e.getMessage());
            }
        }
        ingredientsList.setItems(allIngredients);
    }

    private void loadShoppingListItems(String weekDisplay) {
      ObservableList<String> shoppingItems = FXCollections.observableArrayList();
      String[] parts = weekDisplay.split(" to ");
      String startDatePart = parts[0].substring(parts[0].indexOf('(') + 1);
      String endDatePart = parts[1].substring(0, parts[1].indexOf(')'));
      String sql = "SELECT ItemName, Amount, Unit FROM Shopping_List " +
                   "JOIN weekly_dinner_lists ON Shopping_List.WeeklyDinnerListID = weekly_dinner_lists.WeeklyDinnerListID " +
                   "WHERE weekly_dinner_lists.Week BETWEEN ? AND ?";
      try (Connection conn = connect();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setDate(1, Date.valueOf(startDatePart));
          pstmt.setDate(2, Date.valueOf(endDatePart));
          try (ResultSet rs = pstmt.executeQuery()) {
              while (rs.next()) {
                  String itemDetail = rs.getString("ItemName") + " - " +
                                      rs.getFloat("Amount") + " " +
                                      rs.getString("Unit");
                  shoppingItems.add(itemDetail);
              }
          }
      } catch (SQLException e) {
          System.out.println("Error loading shopping list items: " + e.getMessage());
      }
      ShoppingList.setItems(shoppingItems);
  }

    @FXML
    void addIngredient(ActionEvent event) {
        ObservableList<String> selectedIngredients = ingredientsList.getSelectionModel().getSelectedItems();
        if (selectedIngredients.isEmpty()) {
            return;  // No ingredient selected, so do nothing
        }
        String weekDisplay = weeksList.getSelectionModel().getSelectedItem();
        String[] parts = weekDisplay.split(" to ");
        String startDatePart = parts[0].substring(parts[0].indexOf('(') + 1);
    
        // Get the WeeklyDinnerListID based on the start date of the week
        int weeklyDinnerListID = getWeeklyDinnerListID(Date.valueOf(startDatePart));
    
        // Database connection and insertion logic
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Shopping_List (ItemName, Amount, Unit, WeeklyDinnerListID) VALUES (?, ?, ?, ?)")) {
            for (String ingredientDetail : selectedIngredients) {
                String[] ingredientParts = ingredientDetail.split(" - ");
                String itemName = ingredientParts[0];
                String amountUnit = ingredientParts[1];
                String[] amountUnitParts = amountUnit.split(" ");
                String amount = amountUnitParts[0];
                String unit = amountUnitParts[1];
    
                pstmt.setString(1, itemName);
                pstmt.setFloat(2, Float.parseFloat(amount));
                pstmt.setString(3, unit);
                pstmt.setInt(4, weeklyDinnerListID);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            System.out.println("Error adding ingredient to shopping list: " + e.getMessage());
        }
        
        // Update the Shopping List UI
        loadShoppingListItems(weekDisplay);
    }
    
    private int getWeeklyDinnerListID(Date weekStartDate) {
        String sql = "SELECT WeeklyDinnerListID FROM weekly_dinner_lists WHERE Week = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, weekStartDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("WeeklyDinnerListID");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving WeeklyDinnerListID: " + e.getMessage());
        }
        return -1;  // Return an invalid ID if not found
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
