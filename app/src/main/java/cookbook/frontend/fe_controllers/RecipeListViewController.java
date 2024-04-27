package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_objects.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class RecipeListViewController {
  @FXML
  private TextField searchByNameField;

  @FXML
  private Button searchByNameButton;

  @FXML
  private TextField searchByIngredientsField;

  @FXML
  private Button searchByIngredientsButton;

  @FXML
  private TextField searchByTagsField;

  @FXML
  private Button searchByTagsButton;

  @FXML
  private ListView<Recipe> mainTable; // Assumes ListView is set to hold Recipe objects

  private ObservableList<Recipe> recipeList = FXCollections.observableArrayList();

  @FXML
  private void initialize() {
  
      // Set a custom cell factory for the ListView
      mainTable.setCellFactory(param -> new ListCell<Recipe>() {
          @Override
          protected void updateItem(Recipe recipe, boolean empty) {
              super.updateItem(recipe, empty);
              if (empty || recipe == null || recipe.getRecipeName() == null) {
                  setText(null);
                  setTooltip(null); // Clear toolTip
              } else {
                  setText(recipe.getRecipeName());
                  Tooltip tooltip = new Tooltip(recipe.getShortDesc()); // Create a tooltip with the short description
                  tooltip.setShowDelay(Duration.millis(100));
                  setTooltip(tooltip);
              }
          }

      });

      // Ideally, fetch the recipes from a RecipeController or similar backend class
      try {
          recipeList.addAll(RecipeController.getRecipes());
          mainTable.setItems(recipeList);
      } catch (Exception e) {
          e.printStackTrace();
      }
      mainTable.setOnMouseClicked(this::handleRecipeSelection);
  }

  public void backButton(ActionEvent event) throws SQLException, IOException {
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

    private void handleRecipeSelection(MouseEvent event) {
      if (event.getClickCount() == 2) { // Double-click to handle recipe selection
          Recipe selectedRecipe = mainTable.getSelectionModel().getSelectedItem();
          if (selectedRecipe != null) {
            try {
              System.out.println("Selected Recipe: " + selectedRecipe.getRecipeName());
              // Load the FXML for the comments view
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecipeDetails.fxml"));
              Parent commentViewParent = loader.load();

              // Get the controller and pass the selected recipe
              RecipeDetailsViewController controller = loader.getController();
              controller.initData(selectedRecipe);

              // Setup and show the new stage (or scene)
              Scene commentViewScene = new Scene(commentViewParent);
              Stage window = new Stage(); // You might want to use another method to handle windows
              window.setScene(commentViewScene);
              window.show();
            } catch (IOException e) {
                e.printStackTrace();
          }
        }
      }
    }
  
  // When user clicks search by name.
  @FXML
  private void searchByName(ActionEvent event) {
    String nameQuery = searchByNameField.getText().trim();
    if (!nameQuery.isEmpty()) {
      try {
        recipeList.clear();
        recipeList.addAll(RecipeController.getRecipesByName(nameQuery));
        mainTable.setItems(recipeList);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  // When user clicks search by ingredients.
  @FXML
  private void searchByIngredients(ActionEvent event) {
    String ingredientQuery = searchByIngredientsField.getText().trim();
    if (!ingredientQuery.isEmpty()) {
      try {

        recipeList.clear(); // Clear the current list
        recipeList.addAll(RecipeController.getRecipesByIngredients(ingredientQuery));
        mainTable.setItems(recipeList);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // When userclicks search by tags.
  @FXML
  private void searchByTags(ActionEvent event) {
    String tagQuery = searchByTagsField.getText().trim();
    if (!tagQuery.isEmpty()) {
      try {
        recipeList.clear(); // Clear the current list
        recipeList.addAll(RecipeController.getRecipesByTags(tagQuery));
        mainTable.setItems(recipeList);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
