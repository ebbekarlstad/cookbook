package cookbook.frontend.fe_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShoppingListViewController {

    @FXML
    private TextField itemName;

    @FXML
    private TextField quantity;

    @FXML
    private Button addShoppingItem;

    @FXML
    private Button back;

    @FXML
    private ComboBox<String> unit;

    private final String connectionString = "jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false";
    private static final Logger LOGGER = Logger.getLogger(ShoppingListViewController.class.getName());

    @FXML
    private void initialize() {
        // Populate ComboBox with filler values
        unit.getItems().addAll("g", "kg", "oz", "lb");
    }

    @FXML
    private void createShoppingItem(ActionEvent event) {
        String itemNameText = itemName.getText();
        String quantityText = quantity.getText();
        String unitText = unit.getValue();

        if (itemNameText.isEmpty() || quantityText.isEmpty() || unitText == null) {
            // Handle empty fields error
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
             PreparedStatement statement = conn.prepareStatement("INSERT INTO shopping_list (item_name, quantity, unit) VALUES (?, ?, ?)")) {
            statement.setString(1, itemNameText);
            statement.setString(2, quantityText);
            statement.setString(3, unitText);
            int rowsAffected = statement.executeUpdate();
            LOGGER.log(Level.INFO, "Rows affected: " + rowsAffected);

            // Show alert message
            String message;
            if (rowsAffected > 0) {
                message = "Item added successfully!";
            } else {
                message = "Failed to add item.";
            }
            showAlert(Alert.AlertType.INFORMATION, "Add Item", message);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while inserting into database", e);
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the item.");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void backButton(ActionEvent event) {
        // Handle back button action
    }
}
