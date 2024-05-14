package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;
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
  private Pagination pagination;
  @FXML
  private ListView<Recipe> mainTable;

  private ObservableList<Recipe> recipeList = FXCollections.observableArrayList();
  private static final int ITEMS_PER_PAGE = 10;

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

    updatePagination();
  }

    @FXML
    public void backButton(ActionEvent event){
        try {
            // Kontrollerar om användaren är admin
            boolean isAdmin = UserSession.getInstance().isAdmin();
            String fxmlFile = isAdmin ? "/NavigationViewAdmin.fxml" : "/NavigationView.fxml";

            // Laddar rätt vy baserat på användarens roll
            Parent navigationPageParent = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene navigationPageScene = new Scene(navigationPageParent);

            // Byter scen
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(navigationPageScene);
            window.show();
        } catch (IOException e) {
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

  private void updatePagination() {
        int pageCount = (int) Math.ceil((double) recipeList.size() / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, recipeList.size());
        mainTable.setItems(FXCollections.observableArrayList(recipeList.subList(fromIndex, toIndex)));
        return new VBox(mainTable);
    }

    // Update recipe list and refresh pagination
    public void refreshRecipeList() {
        try {
            recipeList.clear();
            recipeList.addAll(RecipeController.getRecipes()); // Adjust this method call as needed
            updatePagination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
