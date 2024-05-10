package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.IngredientInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.scene.control.ListView;

public class ShoppingListViewController {

    @FXML
    private Button DeleteIngredient;

    @FXML
    private Button ModifyIngredient;


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
        Map<String, List<IngredientInfo>> ingredientMap = new HashMap<>(); // Map to store ingredient details

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
                        // Retrieve ingredient details from the result set
                        String ingredientName = rs.getString("IngredientName");
                        float amount = rs.getFloat("Amount");
                        String unit = rs.getString("Unit");

                        // Create an IngredientInfo object with the amount and unit
                        IngredientInfo info = new IngredientInfo(amount, unit);

                        // Check if the ingredient name already exists in the map
                        if (ingredientMap.containsKey(ingredientName)) {
                            // If yes, add the ingredient info to the existing list
                            ingredientMap.get(ingredientName).add(info);
                        } else {
                            // If not, create a new list and add the ingredient info to it
                            List<IngredientInfo> infoList = new ArrayList<>();
                            infoList.add(info);
                            ingredientMap.put(ingredientName, infoList);
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error loading ingredients: " + e.getMessage()); // Log error if SQL operation fails
            }
        }

        // Format the ingredient details and add them to the list
        for (Map.Entry<String, List<IngredientInfo>> entry : ingredientMap.entrySet()) {
            String ingredientName = entry.getKey();
            List<IngredientInfo> infoList = entry.getValue();

            // Calculate the total amount for the ingredient
            float totalAmount = 0;
            for (IngredientInfo info : infoList) {
                totalAmount += info.getAmount();
            }

            // Get the unit from the first ingredient info (assuming all have the same unit)
            String unit = infoList.get(0).getUnit();

            // Format the ingredient detail
            String ingredientDetail = ingredientName + " - " + totalAmount + " " + unit;
            allIngredients.add(ingredientDetail);
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


    /**
     * Handles the action triggered by pressing the "Add Ingredient" button. This method adds selected
     * ingredients to the shopping list for a specified week.
     *
     * @param event The action event triggered by the user interaction.
     */
    @FXML
    void addIngredient(ActionEvent event) {
        // Retrieve the selected ingredients from the ingredients list UI component
        ObservableList<String> selectedIngredients = ingredientsList.getSelectionModel().getSelectedItems();

        // Check if no ingredients are selected and exit the method if true
        if (selectedIngredients.isEmpty()) {
            return;  // No ingredient selected, so do nothing
        }

        // Extract the week identifier from the UI and parse its start date
        String weekDisplay = weeksList.getSelectionModel().getSelectedItem();
        String[] parts = weekDisplay.split(" to ");
        String startDatePart = parts[0].substring(parts[0].indexOf('(') + 1);

        // Retrieve the WeeklyDinnerListID from the database based on the week's start date
        int weeklyDinnerListID = getWeeklyDinnerListID(Date.valueOf(startDatePart));

        // Establish a database connection and prepare to insert new shopping list items
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Shopping_List (ItemName, Amount, Unit, WeeklyDinnerListID) VALUES (?, ?, ?, ?)")) {
            // Loop through each selected ingredient and prepare it for batch insertion
            for (String ingredientDetail : selectedIngredients) {
                String[] ingredientParts = ingredientDetail.split(" - ");
                String itemName = ingredientParts[0];
                String amountUnit = ingredientParts[1];
                String[] amountUnitParts = amountUnit.split(" ");
                String amount = amountUnitParts[0];
                String unit = amountUnitParts[1];

                // Set parameters for the prepared statement
                pstmt.setString(1, itemName);
                pstmt.setFloat(2, Float.parseFloat(amount));
                pstmt.setString(3, unit);
                pstmt.setInt(4, weeklyDinnerListID);
                pstmt.addBatch();
            }
            // Execute the batch of inserts
            pstmt.executeBatch();
        } catch (SQLException e) {
            // Handle SQL exceptions by logging the error
            System.out.println("Error adding ingredient to shopping list: " + e.getMessage());
        }

        // Refresh the shopping list in the UI to reflect the newly added items
        loadShoppingListItems(weekDisplay);
    }


    /**
     * Retrieves the WeeklyDinnerListID for a given week's start date from the database.
     * This ID is used to link shopping list entries with their corresponding week in the weekly_dinner_lists table.
     *
     * @param weekStartDate The start date of the week for which the ID is required.
     * @return The WeeklyDinnerListID if found, otherwise returns -1 as an invalid identifier.
     */
    private int getWeeklyDinnerListID(Date weekStartDate) {
        // SQL query to find the WeeklyDinnerListID for the given week start date
        String sql = "SELECT WeeklyDinnerListID FROM weekly_dinner_lists WHERE Week = ?";

        // Try-with-resources statement to handle the database connection and query execution
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, weekStartDate); // Set the date parameter for the SQL query

            // Execute the query and process the result set
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Return the WeeklyDinnerListID if found
                    return rs.getInt("WeeklyDinnerListID");
                }
            }
        } catch (SQLException e) {
            // Log any SQL errors encountered during the operation
            System.out.println("Error retrieving WeeklyDinnerListID: " + e.getMessage());
        }
        // Return -1 if no valid ID was found
        return -1;
    }



    /**
     * Handles the action triggered by pressing the "Back" button. This method changes the scene
     * to the navigation view, allowing the user to return to the main menu of the application.
     *
     * @param event The action event triggered by the user interaction.
     */
    @FXML
    void backButton(ActionEvent event) {
        // Attempt to load the navigation view FXML and switch scenes
        try {
            // Load the FXML for the navigation page
            Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
            Scene navigationPageScene = new Scene(navigationPageParent);

            // Get the current window from the event source and set the new scene
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(navigationPageScene);
            window.show();
        } catch (Exception e) {
            // Print stack trace if there's an exception during scene change (usually FXML loading issues)
            e.printStackTrace();
        }
        // Execute the batch of inserts
        pstmt.executeBatch();
    } catch (SQLException e) {
        // Handle SQL exceptions by logging the error
        System.out.println("Error adding ingredient to shopping list: " + e.getMessage());
    }

    // Refresh the shopping list in the UI to reflect the newly added items
    loadShoppingListItems(weekDisplay);
}

    
/**
 * Retrieves the WeeklyDinnerListID for a given week's start date from the database.
 * This ID is used to link shopping list entries with their corresponding week in the weekly_dinner_lists table.
 *
 * @param weekStartDate The start date of the week for which the ID is required.
 * @return The WeeklyDinnerListID if found, otherwise returns -1 as an invalid identifier.
 */
private int getWeeklyDinnerListID(Date weekStartDate) {
  // SQL query to find the WeeklyDinnerListID for the given week start date
  String sql = "SELECT WeeklyDinnerListID FROM weekly_dinner_lists WHERE Week = ?";
  
  // Try-with-resources statement to handle the database connection and query execution
  try (Connection conn = connect();
       PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setDate(1, weekStartDate); // Set the date parameter for the SQL query
      
      // Execute the query and process the result set
      try (ResultSet rs = pstmt.executeQuery()) {
          if (rs.next()) {
              // Return the WeeklyDinnerListID if found
              return rs.getInt("WeeklyDinnerListID");
          }
      }
  } catch (SQLException e) {
      // Log any SQL errors encountered during the operation
      System.out.println("Error retrieving WeeklyDinnerListID: " + e.getMessage());
  }
  // Return -1 if no valid ID was found
  return -1;
}

    

/**
 * Handles the action triggered by pressing the "Back" button. This method changes the scene
 * to the navigation view, allowing the user to return to the main menu of the application.
 *
 * @param event The action event triggered by the user interaction.
 */
@FXML
void backButton(ActionEvent event) {
    // Attempt to load the navigation view FXML and switch scenes
    try {
        // Load the FXML for the navigation page
        Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
        Scene navigationPageScene = new Scene(navigationPageParent);

        // Get the current window from the event source and set the new scene
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(navigationPageScene);
        window.show();
    } catch (Exception e) {
        // Print stack trace if there's an exception during scene change (usually FXML loading issues)
        e.printStackTrace();
    }



    /**
     * Handles the action triggered by pressing the "Generate Shopping List" button.
     * This method writes the contents of the shopping list to a text file.
     * Each item from the shopping list UI component is written to a file named "ShoppingList.txt",
     * which is saved in the current working directory.
     *
     * @param event The action event triggered by the user interaction.
     */
    @FXML
    void generateShoppingListFile(ActionEvent event) {
        // Define the path for the file where the shopping list will be saved
        File file = new File(Paths.get("ShoppingList.txt").toAbsolutePath().toString());

        // Attempt to open a FileWriter to write to the file, 'false' to overwrite any existing content
        try (FileWriter writer = new FileWriter(file, false)) {
            // Retrieve the items from the ShoppingList ListView
            ObservableList<String> items = ShoppingList.getItems();

            // Iterate over each item in the shopping list and write it to the file with a newline
            for (String item : items) {
                writer.write(item + System.lineSeparator());
            }

            // Ensure all data is written to the file before closing the FileWriter
            writer.flush();

            // Log the location of the saved file to the console for confirmation
            System.out.println("Shopping list saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            // Log any I/O errors that occur during the file writing process
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    @FXML
    void DeleteIngredient(ActionEvent event) {
        // Retrieve the selected item from the ShoppingList ListView
        String selectedItem = ShoppingList.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedItem != null) {
            // Extract the item name from the selected item string
            String itemName = selectedItem.split(" - ")[0];

            // Construct the SQL DELETE statement
            String sql = "DELETE FROM Shopping_List WHERE ItemName = ?";

            try (Connection conn = connect(); // Establish a database connection
                 PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepare the SQL statement
                // Set the item name parameter in the prepared statement
                pstmt.setString(1, itemName);

                // Execute the SQL DELETE statement
                pstmt.executeUpdate();

                // Refresh the ShoppingList ListView to reflect the updated list after deletion
                loadShoppingListItems(weeksList.getSelectionModel().getSelectedItem());
            } catch (SQLException e) {
                // Handle any SQL exceptions
                System.out.println("Error deleting ingredient from shopping list: " + e.getMessage());
            }
        } else {
            // If no item is selected, display a message to the user
            System.out.println("Please select an item to delete.");
        }
    }

    @FXML
    void ModifyIngredient(ActionEvent event) {
        // Retrieve the selected item from the ShoppingList ListView
        String selectedItem = ShoppingList.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedItem != null) {
            // Extract the item name and amount from the selected item string
            String[] parts = selectedItem.split(" - ");
            String itemName = parts[0];
            String amountString = parts[1].split(" ")[0]; // Extract the amount value
            float currentAmount = Float.parseFloat(amountString); // Parse the amount value

            // Create a TextInputDialog to prompt the user for the new amount
            TextInputDialog dialog = new TextInputDialog(Float.toString(currentAmount));
            dialog.setTitle("Modify Ingredient Amount");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the new amount for " + itemName + ":");

            // Show the dialog and wait for user input
            Optional<String> result = dialog.showAndWait();

            // Process the user input if available
            result.ifPresent(newAmount -> {
                try {
                    // Convert the new amount to float
                    float updatedAmount = Float.parseFloat(newAmount);

                    // Construct the SQL UPDATE statement to modify the amount in the database
                    String sql = "UPDATE Shopping_List SET Amount = ? WHERE ItemName = ?";

                    try (Connection conn = connect(); // Establish a database connection
                         PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepare the SQL statement
                        // Set the parameters in the prepared statement
                        pstmt.setFloat(1, updatedAmount);
                        pstmt.setString(2, itemName);

                        // Execute the SQL UPDATE statement
                        pstmt.executeUpdate();

                        // Refresh the ShoppingList ListView to reflect the updated list after modification
                        loadShoppingListItems(weeksList.getSelectionModel().getSelectedItem());
                    } catch (SQLException e) {
                        // Handle any SQL exceptions
                        System.out.println("Error modifying ingredient amount: " + e.getMessage());
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where the user enters a non-numeric value
                    System.out.println("Invalid amount format. Please enter a numeric value.");
                }
            });
        } else {
            // If no item is selected, display a message to the user
            System.out.println("Please select an item to modify.");
        }
    }

}



/**
 * Handles the action triggered by pressing the "Generate Shopping List" button.
 * This method writes the contents of the shopping list to a text file.
 * Each item from the shopping list UI component is written to a file named "ShoppingList.txt",
 * which is saved in the current working directory.
 *
 * @param event The action event triggered by the user interaction.
 */
@FXML
void generateShoppingListFile(ActionEvent event) {
    // Define the path for the file where the shopping list will be saved
    File file = new File(Paths.get("ShoppingList.txt").toAbsolutePath().toString());

    // Attempt to open a FileWriter to write to the file, 'false' to overwrite any existing content
    try (FileWriter writer = new FileWriter(file, false)) {
        // Retrieve the items from the ShoppingList ListView
        ObservableList<String> items = ShoppingList.getItems();

        // Iterate over each item in the shopping list and write it to the file with a newline
        for (String item : items) {
            writer.write(item + System.lineSeparator());
        }

        // Ensure all data is written to the file before closing the FileWriter
        writer.flush();

        // Log the location of the saved file to the console for confirmation
        System.out.println("Shopping list saved to " + file.getAbsolutePath());
    } catch (IOException e) {
        // Log any I/O errors that occur during the file writing process
        System.out.println("Error writing to file: " + e.getMessage());
    }
}
}
