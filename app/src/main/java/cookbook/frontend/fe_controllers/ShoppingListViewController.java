package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.ShoppingListItem;
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

public class ShoppingListViewController {

    @FXML
    private TableColumn<ShoppingListItem, Integer> IDColumn;

    @FXML
    private TableColumn<ShoppingListItem, String> ItemNameColumn;

    @FXML
    private TableColumn<ShoppingListItem, Float> AmountColumn;

    @FXML
    private TableColumn<ShoppingListItem, String> UnitColumn;

    @FXML
    private TableView<ShoppingListItem> ShoppingColumn;


    @FXML
    private TextField ItemName;

    @FXML
    private TextField Quantity;

    @FXML
    private Button addShoppingItem;

    @FXML
    private Button back;

    @FXML
    private Button deleteShoppingItem;

    @FXML
    private Button editShoppingItem;

    @FXML
    private Button saveShoppingItem;

    @FXML
    private ComboBox<String> unit;

    @FXML
    private void initialize() {
        unit.getItems().addAll("g", "kg", "ml", "L", "mg", "tea spoon", "pinch"); // Add items here
    }

    private void populateTableView() {
        ObservableList<ShoppingListItem> items = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false")) {
            String query = "SELECT * FROM Shopping_List";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ItemID");
                String name = resultSet.getString("ItemName");
                float amount = resultSet.getFloat("Amount");
                String unit = resultSet.getString("Unit");

                items.add(new ShoppingListItem(id, name, amount, unit));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        IDColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        ItemNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        AmountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        UnitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());

        ShoppingColumn.setItems(items);
    }
    @FXML
    void addItem(ActionEvent event) {
        String itemName = ItemName.getText();
        float quantity = Float.parseFloat(Quantity.getText());
        String selectedUnit = unit.getValue();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false")) {
            String query = "INSERT INTO Shopping_List (ItemName, Amount, Unit) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, itemName);
            preparedStatement.setFloat(2, quantity);
            preparedStatement.setString(3, selectedUnit);

            preparedStatement.executeUpdate();
            populateTableView();

        } catch (SQLException e) {
          throw new RuntimeException("The connect is not established ... bruh" + e);
        }
    }

    @FXML
    void DeleteItem(ActionEvent event) {

    }

    @FXML
    void EditItem(ActionEvent event) {

    }

    @FXML
    void SaveItem(ActionEvent event) {

    }

    @FXML
    void backButton(ActionEvent event) {
        try {
            //Load the navigation page FXML
            Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
            Scene navigationPageScene = new Scene(navigationPageParent);

            // Get the current stage and replace it
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(navigationPageScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
