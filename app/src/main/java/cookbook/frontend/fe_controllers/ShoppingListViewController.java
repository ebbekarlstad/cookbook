package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.IngredientData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Pair;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShoppingListViewController {


    @FXML
    private TableColumn<IngredientData, String> ingredientColumn;

    @FXML
    private TableView<IngredientData> ingredientTable;

    @FXML
    private TableColumn<IngredientData, Float> amountColumn;

    @FXML
    private TableColumn<IngredientData, String> unitColumn;

    @FXML
    private Label amount_text;

    @FXML
    private Label currentIngredient;

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
            // Store ingredients in shopping list for the selected week
            storeIngredientsInShoppingList(newValue);
            // Load ingredients from shopping list for the selected week
            loadAllIngredients(newValue);
        });

        // Listener to update labels when ingredient selection changes
        ingredientTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IngredientData>() {
            @Override
            public void changed(ObservableValue<? extends IngredientData> observable, IngredientData oldValue, IngredientData newValue) {
                if (newValue != null) {
                    currentIngredient.setText(newValue.getName());
                    amount_text.setText(String.valueOf(newValue.getAmount()));
                }
            }
        });

        // Load ingredients for the default week when the view is initialized
        String defaultWeek = weeksList.getItems().isEmpty() ? null : weeksList.getItems().get(0);
        if (defaultWeek != null) {
            loadAllIngredients(defaultWeek);
        }
    }


    private void loadWeeks() {
        ObservableList<String> weeks = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT Week FROM weekly_dinner_lists ORDER BY Week DESC";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                weeks.add(rs.getDate("Week").toString());
            }
        } catch (SQLException e) {
            System.out.println("Error loading weeks: " + e.getMessage());
        }
        weeksList.setItems(weeks);
    }

    private void loadDishes(String week) {
        ObservableList<String> dishes = FXCollections.observableArrayList();
        String sql = "SELECT RecipeName FROM recipes " +
                "JOIN dinner_list_recipes ON recipes.RecipeID = dinner_list_recipes.RecipeID " +
                "JOIN weekly_dinner_lists ON dinner_list_recipes.WeeklyDinnerListID = weekly_dinner_lists.WeeklyDinnerListID " +
                "WHERE weekly_dinner_lists.Week = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(week));
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

    private void loadAllIngredients(String week) {
        ObservableList<IngredientData> allIngredients = FXCollections.observableArrayList();
        // Use a map to accumulate amounts of ingredients by name and unit
        Map<String, Pair<Float, String>> ingredientMap = new HashMap<>();

        // SQL query to select ingredients from all recipes associated with the selected week
        String sql = "SELECT i.IngredientName, ri.Amount, ri.Unit " +
                "FROM recipes r " +
                "JOIN dinner_list_recipes dl ON r.RecipeID = dl.RecipeID " +
                "JOIN weekly_dinner_lists wdl ON dl.WeeklyDinnerListID = wdl.WeeklyDinnerListID " +
                "JOIN recipe_ingredients ri ON r.RecipeID = ri.RecipeID " +
                "JOIN ingredients i ON ri.IngredientID = i.IngredientID " +
                "WHERE wdl.Week = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(week));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("IngredientName");
                    float amount = rs.getFloat("Amount");
                    String unit = rs.getString("Unit");
                    // If ingredient already exists in the map, add the amount to its existing value
                    if (ingredientMap.containsKey(name)) {
                        Pair<Float, String> pair = ingredientMap.get(name);
                        ingredientMap.put(name, new Pair<>(pair.getKey() + amount, unit));
                    } else {
                        ingredientMap.put(name, new Pair<>(amount, unit));
                    }
                    System.out.println("Loaded ingredient: " + name);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading ingredients for week " + week + ": " + e.getMessage());
        }

        // Convert the accumulated map entries into IngredientData objects
        ingredientMap.forEach((name, pair) -> allIngredients.add(new IngredientData(name, pair.getKey(), pair.getValue())));

        ingredientTable.setItems(allIngredients);
    }


    private void storeIngredientsInShoppingList(String week) {
        try {
            // Connect to the database
            Connection connection = connect();

            // Prepare the SQL statement to insert or update ingredients in the Shopping_List table
            String insertOrUpdateSQL = "INSERT INTO Shopping_List (ItemName, Amount, Unit) " +
                    "VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE Amount = Amount + VALUES(Amount)";
            PreparedStatement insertOrUpdateStatement = connection.prepareStatement(insertOrUpdateSQL);

            // Prepare the SQL statement to select ingredients from the recipes for the given week
            String selectIngredientsSQL = "SELECT i.IngredientName, SUM(ri.Amount) AS TotalAmount, ri.Unit " +
                    "FROM recipes r " +
                    "JOIN dinner_list_recipes dl ON r.RecipeID = dl.RecipeID " +
                    "JOIN weekly_dinner_lists wdl ON dl.WeeklyDinnerListID = wdl.WeeklyDinnerListID " +
                    "JOIN recipe_ingredients ri ON r.RecipeID = ri.RecipeID " +
                    "JOIN ingredients i ON ri.IngredientID = i.IngredientID " +
                    "WHERE wdl.Week = ? " +
                    "GROUP BY i.IngredientName, ri.Unit";
            PreparedStatement selectIngredientsStatement = connection.prepareStatement(selectIngredientsSQL);
            selectIngredientsStatement.setDate(1, Date.valueOf(week));
            ResultSet resultSet = selectIngredientsStatement.executeQuery();

            // Iterate over the result set and insert or update ingredients in the shopping list table
            while (resultSet.next()) {
                String itemName = resultSet.getString("IngredientName");
                float totalAmount = resultSet.getFloat("TotalAmount");
                String unit = resultSet.getString("Unit");

                // Set parameters for the insertOrUpdateStatement
                insertOrUpdateStatement.setString(1, itemName);
                insertOrUpdateStatement.setFloat(2, totalAmount);
                insertOrUpdateStatement.setString(3, unit);
                insertOrUpdateStatement.executeUpdate();
            }

            // Close the database connections
            resultSet.close();
            selectIngredientsStatement.close();
            insertOrUpdateStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onDeleteBtn(ActionEvent event) {
        TableView.TableViewSelectionModel<IngredientData> selectionModel = ingredientTable.getSelectionModel();
        if (!selectionModel.isEmpty()) {
            IngredientData selectedIngredient = selectionModel.getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Ingredient");
            alert.setContentText("Are you sure you want to delete the ingredient '" + selectedIngredient.getName() + "'?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ingredientTable.getItems().remove(selectedIngredient);
            }
        } else {
            showAlert("No Ingredient Selected", "Please select an ingredient to delete.");
        }
    }

    @FXML
    void onModifyBtn(ActionEvent event) {
        TableView.TableViewSelectionModel<IngredientData> selectionModel = ingredientTable.getSelectionModel();
        if (!selectionModel.isEmpty()) {
            IngredientData selectedIngredient = selectionModel.getSelectedItem();

            TextInputDialog dialog = new TextInputDialog(String.valueOf(selectedIngredient.getAmount()));
            dialog.setTitle("Modify Ingredient");
            dialog.setHeaderText("Modify Amount");
            dialog.setContentText("Please enter the new amount for '" + selectedIngredient.getName() + "':");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newAmount -> {
                try {
                    float newAmountFloat = Float.parseFloat(newAmount);
                    selectedIngredient.setAmount(newAmountFloat);
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid number for the amount.");
                }
            });
        } else {
            showAlert("No Ingredient Selected", "Please select an ingredient to modify.");
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void onUpButton(ActionEvent event) {
        TableView.TableViewSelectionModel<IngredientData> selectionModel = ingredientTable.getSelectionModel();
        if (!selectionModel.isEmpty()) {
            IngredientData selectedIngredient = selectionModel.getSelectedItem();
            float currentAmount = selectedIngredient.getAmount();
            selectedIngredient.setAmount(currentAmount + 1); // Increment amount
            amount_text.setText(String.valueOf(selectedIngredient.getAmount()));
        }
    }


    @FXML
    void onDownButton(ActionEvent event) {
        TableView.TableViewSelectionModel<IngredientData> selectionModel = ingredientTable.getSelectionModel();
        if (!selectionModel.isEmpty()) {
            IngredientData selectedIngredient = selectionModel.getSelectedItem();
            float currentAmount = selectedIngredient.getAmount();
            if (currentAmount > 0) {
                selectedIngredient.setAmount(currentAmount - 1); // Decrement amount if it's greater than 0
                amount_text.setText(String.valueOf(selectedIngredient.getAmount()));
            }
        }
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
