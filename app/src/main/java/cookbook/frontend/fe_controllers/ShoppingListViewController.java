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
    private TableView<Ingredient> IngredientTable;

    @FXML
    private TableColumn<Ingredient, String> ingredientColumn;

    @FXML
    private TableColumn<Ingredient, Integer> amountColumn;

    @FXML
    private TextField ingredientName;

    @FXML
    private TextField amount;

    @FXML
    private Button addIngredient;

    private final ObservableList<Ingredient> ingredientList = FXCollections.observableArrayList();
    private DatabaseMng dbManager = new DatabaseMng();

    @FXML
    public void initialize() {
        ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        IngredientTable.setItems(ingredientList);
        loadIngredients();
    }

    private void loadIngredients() {
        String sql = "SELECT IngredientID, Quantity FROM shopping_list_items";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String ingName = rs.getString("IngredientID");  // Assumes IngredientID stores the name
                int qty = rs.getInt("Quantity");
                ingredientList.add(new Ingredient(ingName, qty));
            }
        } catch (SQLException ex) {
            System.err.println("Error loading ingredients from database: " + ex.getMessage());
        }
    }

    @FXML
    void addIngredientToList(ActionEvent event) {
        String name = ingredientName.getText();
        String qty = amount.getText();
        if (!name.isEmpty() && !qty.isEmpty()) {
            int quantity = Integer.parseInt(qty);
            Ingredient newIngredient = new Ingredient(name, quantity);
            ingredientList.add(newIngredient);
            saveIngredientToDatabase(newIngredient, 1);  // Example list ID, should be dynamic based on user/session
            ingredientName.clear();
            amount.clear();
        }
    }

    private void saveIngredientToDatabase(Ingredient ingredient, int shoppingListId) {
        String sql = "INSERT INTO shopping_list_items (ShoppingListID, IngredientID, Quantity) VALUES (?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shoppingListId);
            pstmt.setString(2, ingredient.getIngredientName());
            pstmt.setInt(3, ingredient.getAmount());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error saving ingredient to database: " + ex.getMessage());
        }
    }

    public static class Ingredient {
        private final String ingredientName;
        private final int amount;

        public Ingredient(String ingredientName, int amount) {
            this.ingredientName = ingredientName;
            this.amount = amount;
        }

        public String getIngredientName() {
            return ingredientName;
        }

        public int getAmount() {
            return amount;
        }
    }
}
