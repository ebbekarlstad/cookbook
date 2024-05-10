package cookbook.frontend.fe_controllers;

import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditingRecipeController {

  @FXML
  private Button EditIngredient;

  @FXML
  private Button EditTag;

  @FXML
  private ComboBox<Recipe> RecipesComboBox;

  @FXML
  private Button SaveRecipe;

  @FXML
  private Button addIngredient;

  @FXML
  private Button addTag;

  @FXML
  private TextField amount;

  @FXML
  private Button back;

  @FXML
  private Label ingredientLabel;

  @FXML
  private TextField ingredientName;

  @FXML
  private TextArea recipeLongDesc;

  @FXML
  private TextField recipeName;

  @FXML
  private TextArea recipeShortDesc;

  @FXML
  private TextField tagName;

  @FXML
  private ComboBox<String> tagsDropdown;

  @FXML
  private Label tagsLabel;

  @FXML
  private ComboBox<String> unit;

  private void loadRecipes() throws SQLException {
    List<Recipe> recipes = RecipeController.getRecipesByUserID(UserSession.getInstance().getUserId());
    RecipesComboBox.setItems(FXCollections.observableArrayList(recipes));
    RecipesComboBox.setConverter(new StringConverter<Recipe>() {

      public java.lang.String toString(Recipe recipe) {
        return (recipe != null) ? recipe.getRecipeName() : "Select user";
      }

      @Override
      public Recipe fromString(String string) {
        return null;
      }
    });
  }

  @FXML
  private void initialize() throws SQLException {
    unit.getItems().addAll("g", "kg", "ml", "L", "mg", "tea spoon", "pinch"); // Add items here
    loadRecipes();
  }



  @FXML
  void EditIngredientToList(ActionEvent event) {

  }

  @FXML
  void EditTagToList(ActionEvent event) {

  }

  @FXML
  void SaveRecipe(ActionEvent event) {
    try {
      Recipe selectedRecipe = RecipesComboBox.getValue();
      if (selectedRecipe == null) {
        System.out.println("No recipe selected");
        return;
      }
      String newRecipeName = recipeName.getText().trim();
      String newShortDesc = recipeShortDesc.getText().trim();
      String newLongDesc = ingredientName.getText().trim();
      boolean updated = RecipeController.updateRecipeDetails(
              selectedRecipe.getId(),
              newRecipeName,
              newShortDesc,
              newLongDesc);
      if (updated) {
        System.out.println("Recipe updated successfully");
      } else {
        System.out.println("Recipe update failed");
      }
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  @FXML
  void addIngredientToList(ActionEvent event) {

  }

  @FXML
  void addTagToList(ActionEvent event) {

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

}
