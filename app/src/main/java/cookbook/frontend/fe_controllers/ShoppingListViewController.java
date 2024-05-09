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
            loadAllIngredients();  // Load all ingredients once all dishes are loaded
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

    private void loadAllIngredients() {
        ObservableList<IngredientData> allIngredients = FXCollections.observableArrayList();
        // Use a map to accumulate amounts of ingredients by name and unit
        Map<String, Pair<Float, String>> ingredientMap = new HashMap<>();

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
                }
            } catch (SQLException e) {
                System.out.println("Error loading ingredients for dish " + dish + ": " + e.getMessage());
            }
        }

        // Convert the accumulated map entries into IngredientData objects
        ingredientMap.forEach((name, pair) -> allIngredients.add(new IngredientData(name, pair.getKey(), pair.getValue())));

        ingredientTable.setItems(allIngredients);
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
