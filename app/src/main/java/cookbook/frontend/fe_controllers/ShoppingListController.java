package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * Controller for the Shopping List view.
 */
public class ShoppingListController {

    @FXML
    private ListView<String> ingView; // Assuming the ListView is for strings, adjust the type if necessary.

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize your ListView with data or any setup code.
        loadShoppingListItems();
    }

    /**
     * Loads shopping list items into the ListView. This is a placeholder method.
     * You need to adapt this method to load actual data.
     */
    private void loadShoppingListItems() {
        // Here, we just add some dummy data for demonstration purposes.
        ingView.getItems().addAll("Milk", "Eggs", "Flour", "Sugar", "Butter");
    }

    /**
     * Handles additional actions, such as item selection or other buttons if present.
     * This is just a template method to give you an idea.
     */
    @FXML
    private void handleItemAction() {
        String selectedItem = ingView.getSelectionModel().getSelectedItem();
        System.out.println("Selected item: " + selectedItem);
        // Add more handling logic as per your application needs.
    }
}
