package cookbook.frontend.fe_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    void createShoppingItem(ActionEvent event) {
        String itemNameText = itemName.getText();
        String quantityText = quantity.getText();
        String unitText = unit.getValue();

        if (itemNameText.isEmpty() || quantityText.isEmpty() || unitText == null) {
            // Handle empty fields error
            return;
        }

        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO shopping_list (item_name, quantity, unit) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, itemNameText);
                statement.setString(2, quantityText);
                statement.setString(3, unitText);
                int rowsAffected = statement.executeUpdate();
                LOGGER.log(Level.INFO, "Rows affected: " + rowsAffected);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while inserting into database", e);
        }
    }

    @FXML
    void backButton(ActionEvent event) {
        // Handle back button action
    }
}
