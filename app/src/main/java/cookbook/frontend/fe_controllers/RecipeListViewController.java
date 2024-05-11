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

/**
 * Controller class for the recipe list view.
 */
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
  private ListView<Recipe> mainTable;

  private ObservableList<Recipe> recipeList = FXCollections.observableArrayList();

  /**
   * Initializes the controller class.
   */
  @FXML
  private void initialize() {
    mainTable.setCellFactory(param -> new ListCell<Recipe>() {
      @Override
      protected void updateItem(Recipe recipe, boolean empty) {
        super.updateItem(recipe, empty);
        if (empty || recipe == null || recipe.getRecipeName() == null) {
          setText(null);
          setTooltip(null);
        } else {
          setText(recipe.getRecipeName());
          Tooltip tooltip = new Tooltip(recipe.getShortDesc());
          tooltip.setShowDelay(Duration.millis(100));
          setTooltip(tooltip);
        }
      }
    });

    try {
      recipeList.addAll(RecipeController.getRecipes());
      mainTable.setItems(recipeList);
    } catch (Exception e) {
      e.printStackTrace();
    }
    mainTable.setOnMouseClicked(this::handleRecipeSelection);
  }

  /**
   * Handles back button action to navigate to the navigation page.
   * @param event The action event.
   */
  public void backButton(ActionEvent event) throws SQLException, IOException {
    try {
      Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
      Scene navigationPageScene = new Scene(navigationPageParent);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(navigationPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles selection of a recipe from the list.
   * @param event The mouse event.
   */
  private void handleRecipeSelection(MouseEvent event) {
    if (event.getClickCount() == 2) {
      Recipe selectedRecipe = mainTable.getSelectionModel().getSelectedItem();
      if (selectedRecipe != null) {
        try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecipeDetails.fxml"));
          Parent commentViewParent = loader.load();
          RecipeDetailsViewController controller = loader.getController();
          controller.initData(selectedRecipe);
          Scene commentViewScene = new Scene(commentViewParent);
          Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
          window.setScene(commentViewScene);
          window.show();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Searches recipes by name.
   * @param event The action event.
   */
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

  /**
   * Searches recipes by ingredients.
   * @param event The action event.
   */
  @FXML
  private void searchByIngredients(ActionEvent event) {
    String ingredientQuery = searchByIngredientsField.getText().trim();
    if (!ingredientQuery.isEmpty()) {
      try {
        recipeList.clear();
        recipeList.addAll(RecipeController.getRecipesByIngredients(ingredientQuery));
        mainTable.setItems(recipeList);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Searches recipes by tags.
   * @param event The action event.
   */
  @FXML
  private void searchByTags(ActionEvent event) {
    String tagQuery = searchByTagsField.getText().trim();
    if (!tagQuery.isEmpty()) {
      try {
        recipeList.clear();
        recipeList.addAll(RecipeController.getRecipesByTags(tagQuery));
        mainTable.setItems(recipeList);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
