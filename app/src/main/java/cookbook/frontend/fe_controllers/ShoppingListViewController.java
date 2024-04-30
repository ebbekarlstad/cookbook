package cookbook.frontend.fe_controllers;

import cookbook.backend.DatabaseMng;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;

public class ShoppingListViewController {

    @FXML
    private TableView<ShoppingIngredient> ingredientTable;

    @FXML
    private TableColumn<ShoppingIngredient, String> ingredientColumn;

    @FXML
    private TableColumn<ShoppingIngredient, String> amountColumn;

    @FXML
    private TextField ingredientName;

    @FXML
    private TextField amount;

    @FXML
    private Button addIngredient;

    private final ObservableList<ShoppingIngredient> ingredientList = FXCollections.observableArrayList();
    private DatabaseMng dbManager = new DatabaseMng();
    private int currentShoppingListId = -1;  // Default to -1, indicating no valid shopping list is selected

    @FXML
    public void initialize() {
        ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        ingredientTable.setItems(ingredientList);
        initShoppingList(); // Initialize a valid shopping list ID
        loadIngredients();    }

    private void initShoppingList() {
      try (Connection conn = dbManager.getConnection();
           Statement stmt = conn.createStatement()) {
          ResultSet rs = stmt.executeQuery("SELECT MAX(ShoppingListID) as ShoppingListID FROM shopping_lists;");
          if (rs.next()) {
              currentShoppingListId = rs.getInt("ShoppingListID");
              // Optionally create a new list if none exists:
              if (currentShoppingListId == 0) { 
              }
          }
      } catch (SQLException ex) {
          System.err.println("Error initializing shopping list: " + ex.getMessage());
      }
  }

  private void loadIngredients() {
    if (currentShoppingListId != -1) {
        String sql = "SELECT IngredientID, Quantity FROM shopping_list_items WHERE ShoppingListID = " + currentShoppingListId;
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String ingName = rs.getString("IngredientID");
                String qty = rs.getString("Quantity");
                ingredientList.add(new ShoppingIngredient(ingName, qty));
                // System.out.println("Loaded: " + ingName + " " + qty); // Comment out or remove this line
            }
        } catch (SQLException ex) {
            System.err.println("Error loading ingredients from database: " + ex.getMessage());
        }
    }
}

  

    @FXML
    void addIngredientToList(ActionEvent event) {
        String name = ingredientName.getText();
        String qty = amount.getText();
        if (!name.isEmpty() && !qty.isEmpty() && currentShoppingListId != -1) {
            if (validateOrAddIngredient(name)) {  // Ensure the ingredient is valid or added
                ShoppingIngredient newIngredient = new ShoppingIngredient(name, qty);
                ingredientList.add(newIngredient);
                saveIngredientToDatabase(newIngredient);
                ingredientName.clear();
                amount.clear();
            }
        }
    }
    
    private boolean validateOrAddIngredient(String ingredientName) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM ingredients WHERE IngredientID = ?")) {
            pstmt.setString(1, ingredientName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;  // Ingredient exists
            } else {
                // Add ingredient if it doesn't exist
                try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO ingredients (IngredientID, IngredientName) VALUES (?, ?)")) {
                    insertStmt.setString(1, ingredientName);
                    insertStmt.setString(2, ingredientName);  // Assuming IngredientName is the same as IngredientID for simplicity
                    insertStmt.executeUpdate();
                    return true;  // Ingredient added
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error validating or adding ingredient: " + ex.getMessage());
            return false;
        }
    }
    

    private void saveIngredientToDatabase(ShoppingIngredient ingredient) {
        String sql = "INSERT INTO shopping_list_items (ShoppingListID, IngredientID, Quantity) VALUES (?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, currentShoppingListId);
            pstmt.setString(2, ingredient.getIngredientName());
            pstmt.setString(3, ingredient.getAmount());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error saving ingredient to database: " + ex.getMessage());
        }
    }


  

    public static class ShoppingIngredient {
        private final String ingredientName;
        private final String amount;

        public ShoppingIngredient(String ingredientName, String amount) {
            this.ingredientName = ingredientName;
            this.amount = amount;
        }

        public String getIngredientName() {
            return ingredientName;
        }

        public String getAmount() {
            return amount;
        }
    }
}
