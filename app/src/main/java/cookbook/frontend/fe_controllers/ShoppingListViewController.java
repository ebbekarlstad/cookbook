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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import javafx.scene.control.ListView;

public class ShoppingListViewController {

  @FXML
  private ListView<String> weeksList; // ListView to display the list of weeks, each representing a time range
  @FXML
  private ListView<String> dishesList; // ListView to display the list of dishes planned for the selected week
  @FXML
  private ListView<String> ingredientsList; // ListView to display the list of ingredients for the selected dishes
  @FXML
  private ListView<String> ShoppingList; // ListView to display the items in the shopping list for the selected week

  /**
   * Establishes a connection to the MySQL database using JDBC.
   * @return A Connection object to interact with the database.
   * @throws SQLException If a database access error occurs or the url is null.
   */
  private Connection connect() throws SQLException {
      return DriverManager.getConnection("jdbc:mysql://localhost:3306/cookbookdb", "root", "root");
  }


    /**
     * Initializes the controller class. This method is automatically called after the FXML fields have been injected.
     * It sets up the initial state of the UI components and registers event listeners.
     */
    @FXML
    public void initialize() {
        loadWeeks(); // Load the list of weeks into the weeksList ListView
        weeksList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadDishes(newValue); // Load dishes based on the selected week
            loadAllIngredients(); // Load all ingredients for the selected dishes
            loadShoppingListItems(newValue); // Load shopping list for the selected week
        });
    }

    /**
     * Loads weeks from the database and populates the weeksList ListView with week ranges.
     * Each week range is represented by its starting and ending dates along with a week number.
     */
    private void loadWeeks() {
        ObservableList<String> weeks = FXCollections.observableArrayList(); // List to hold week ranges for the UI
        String sql = "SELECT MIN(Week) AS StartDate, MAX(Week) AS EndDate FROM weekly_dinner_lists " +
                     "GROUP BY YEAR(Week), WEEK(Week) ORDER BY YEAR(Week) DESC, WEEK(Week) DESC";
        try (Connection conn = connect(); // Establish a database connection
             PreparedStatement pstmt = conn.prepareStatement(sql); // Prepare SQL query
             ResultSet rs = pstmt.executeQuery()) { // Execute query and get the result set
            while (rs.next()) {
                LocalDate startDate = rs.getDate("StartDate").toLocalDate(); // Get the start date of the week
                LocalDate endDate = rs.getDate("EndDate").toLocalDate(); // Get the end date of the week
                int weekNumber = startDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()); // Calculate week number
                String weekDisplay = "Week " + weekNumber + " (" + startDate + " to " + endDate + ")"; // Format display string for the week
                weeks.add(weekDisplay); // Add the formatted week range to the list
            }
        } catch (SQLException e) {
            System.out.println("Error loading weeks: " + e.getMessage()); // Log error if the SQL operation fails
        }
        weeksList.setItems(weeks); // Set the items in the ListView to the loaded week ranges
    }


    /**
     * Loads the dishes associated with a selected week from the database and updates the dishesList ListView.
     * The week range is parsed from the provided weekDisplay string.
     *
     * @param weekDisplay A string representation of the week, typically formatted as "Week <number> (startDate to endDate)"
     */
    private void loadDishes(String weekDisplay) {
      ObservableList<String> dishes = FXCollections.observableArrayList(); // List to hold names of dishes for the UI
      // Split the weekDisplay string to extract the start and end dates
      String[] parts = weekDisplay.split(" to ");
      String startDatePart = parts[0].substring(parts[0].indexOf('(') + 1); // Extract the start date from the week display
      String endDatePart = parts[1].substring(0, parts[1].indexOf(')')); // Extract the end date from the week display
      
      // SQL query to fetch recipe names within the specified week range
      String sql = "SELECT RecipeName FROM recipes " +
                   "JOIN dinner_list_recipes ON recipes.RecipeID = dinner_list_recipes.RecipeID " +
                   "JOIN weekly_dinner_lists ON dinner_list_recipes.WeeklyDinnerListID = weekly_dinner_lists.WeeklyDinnerListID " +
                   "WHERE weekly_dinner_lists.Week BETWEEN ? AND ?";
      try (Connection conn = connect(); // Establish a database connection
           PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepare the SQL query
          pstmt.setDate(1, Date.valueOf(startDatePart)); // Set the start date in the query
          pstmt.setDate(2, Date.valueOf(endDatePart)); // Set the end date in the query
          try (ResultSet rs = pstmt.executeQuery()) { // Execute the query and get the result set
              while (rs.next()) {
                  dishes.add(rs.getString("RecipeName")); // Add each fetched recipe name to the list
              }
          }
      } catch (SQLException e) {
          System.out.println("Error loading dishes: " + e.getMessage()); // Log error if SQL operation fails
      }
      dishesList.setItems(dishes); // Set the items in the ListView to the loaded dishes
  }


     /**
     * Loads all ingredients for the dishes currently displayed in the dishesList ListView.
     * For each dish, it fetches ingredient details from the database and updates the ingredientsList ListView.
     */
    private void loadAllIngredients() {
      ObservableList<String> allIngredients = FXCollections.observableArrayList(); // List to hold ingredient details for the UI
      // Loop through each dish listed in dishesList
      for (String dish : dishesList.getItems()) {
          // SQL query to fetch the name, amount, and unit of each ingredient used in the dish
          String sql = "SELECT i.IngredientName, ri.Amount, ri.Unit " +
                       "FROM ingredients i " +
                       "JOIN recipe_ingredients ri ON i.IngredientID = ri.IngredientID " +
                       "JOIN recipes r ON ri.RecipeID = r.RecipeID " +
                       "WHERE r.RecipeName = ?";
          try (Connection conn = connect(); // Establish a database connection
               PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepare the SQL query
              pstmt.setString(1, dish); // Set the dish name in the query
              try (ResultSet rs = pstmt.executeQuery()) { // Execute the query and get the result set
                  while (rs.next()) {
                      // Format the ingredient detail and add to the list
                      String ingredientDetail = rs.getString("IngredientName") + " - " +
                                                rs.getString("Amount") + " " +
                                                rs.getString("Unit");
                      allIngredients.add(ingredientDetail);
                  }
              }
          } catch (SQLException e) {
              System.out.println("Error loading ingredients: " + e.getMessage()); // Log error if SQL operation fails
          }
      }
      ingredientsList.setItems(allIngredients); // Set the items in the ListView to the loaded ingredients
  }


/**
 * Loads the shopping list items for a selected week from the database and updates the ShoppingList ListView.
 * The week range is parsed from the provided weekDisplay string to determine which items to load.
 * 
 * @param weekDisplay A string representation of the week, typically formatted as "Week <number> (startDate to endDate)"
 */
private void loadShoppingListItems(String weekDisplay) {
  // Initialize an observable list to hold the shopping list items for display in the UI
  ObservableList<String> shoppingItems = FXCollections.observableArrayList();

  // Split the weekDisplay string to extract the start and end dates
  String[] parts = weekDisplay.split(" to ");
  String startDatePart = parts[0].substring(parts[0].indexOf('(') + 1);
  String endDatePart = parts[1].substring(0, parts[1].indexOf(')'));

  // SQL query to fetch shopping list items for the specified week range
  String sql = "SELECT ItemName, Amount, Unit FROM Shopping_List " +
               "JOIN weekly_dinner_lists ON Shopping_List.WeeklyDinnerListID = weekly_dinner_lists.WeeklyDinnerListID " +
               "WHERE weekly_dinner_lists.Week BETWEEN ? AND ?";

  // Establish a database connection and prepare the SQL statement
  try (Connection conn = connect();
       PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setDate(1, Date.valueOf(startDatePart)); // Set the start date parameter in the SQL query
      pstmt.setDate(2, Date.valueOf(endDatePart));   // Set the end date parameter in the SQL query

      // Execute the query and process the result set
      try (ResultSet rs = pstmt.executeQuery()) {
          while (rs.next()) {
              // Format each shopping list item detail and add it to the observable list
              String itemDetail = rs.getString("ItemName") + " - " +
                                  rs.getFloat("Amount") + " " +
                                  rs.getString("Unit");
              shoppingItems.add(itemDetail);
          }
      }
  } catch (SQLException e) {
      // Log the SQL error if there is an issue loading the shopping list items
      System.out.println("Error loading shopping list items: " + e.getMessage());
  }

  // Update the ShoppingList ListView with the loaded items
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
    @FXML
    void generateShoppingListFile(ActionEvent event) {
        File file = new File(Paths.get("ShoppingList.txt").toAbsolutePath().toString());
        try (FileWriter writer = new FileWriter(file, false)) { // false to overwrite.
            ObservableList<String> items = ShoppingList.getItems();
            for (String item : items) {
                writer.write(item + System.lineSeparator());
            }
            writer.flush();
            System.out.println("Shopping list saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

