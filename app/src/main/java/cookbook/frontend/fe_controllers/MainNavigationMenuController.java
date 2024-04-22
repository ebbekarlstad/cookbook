package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_objects.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainNavigationMenuController {

    @FXML
    private ListView<Recipe> mainTable; // Assumes ListView is set to hold Recipe objects

    private ObservableList<Recipe> recipeList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Ideally, fetch the recipes from a RecipeController or similar backend class
        try {
            recipeList.addAll(RecipeController.getRecipes());
            mainTable.setItems(recipeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainTable.setOnMouseClicked(this::handleRecipeSelection);
    }

    private void handleRecipeSelection(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double-click to handle recipe selection
            Recipe selectedRecipe = mainTable.getSelectionModel().getSelectedItem();
            if (selectedRecipe != null) {
                System.out.println("Selected Recipe: " + selectedRecipe.getName());
                // Further actions like opening a recipe detail view can be implemented here
            }
        }
    }


    @FXML
public void showDescriptionFromMain(MouseEvent event) {
    // Implementation code
    // For example, open a new window or display details about the selected item
}

}
