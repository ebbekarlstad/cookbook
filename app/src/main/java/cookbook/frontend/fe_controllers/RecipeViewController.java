package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.Ingredient;
import cookbook.backend.be_controllers.BE_RecipeDB;
import cookbook.backend.be_objects.RecipeOB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class RecipeViewController {

  @FXML
  private TableView<Ingredient> IngredientTable;

  @FXML
  private ListView<String> ListOfRecipe_s;

  @FXML
  private TextField RecipeInstructions;

  @FXML
  private TextField RecipeName;

  @FXML
  private TextField addAmountText;

  @FXML
  private TextField addIngredientNameText;

  @FXML
  private Button addRecipe;

  @FXML
  private Button addTagButton;

  @FXML
  private TextField addTagTextField;

  @FXML
  private ComboBox<String> addUnit;

  @FXML
  private TableColumn<Ingredient, String> ingredientColumn;

  @FXML
  private Label commentsLabel1;

  @FXML
  private TableColumn<Ingredient, String> unitColumn;

  @FXML
  private TableColumn<Ingredient, Integer> amountColumn;

  @FXML
  private TextArea recipeDescText;

  @FXML
  private ListView<String> tagsListView;

  @FXML
  void addIngredientClicked(ActionEvent event) {

    // Adding implemintations for the ingredients to be added inside of the database
   try {
     String ingredient_name = addIngredientNameText.getText();

     UUID uniqueID = UUID.randomUUID();
     String uniqueIngredientID = uniqueID.toString();
     String selectUnit = addUnit.getSelectionModel().getSelectedItem();
     String additional = addAmountText.getText();
     double selectedAmount = Double.parseDouble(additional);

   } catch (Exception e) {
     throw new RuntimeException(e);
   }
  }

  @FXML
  void addRecipe(MouseEvent event) {

    String recipeName = RecipeName.getText();
    String recipeDescription = recipeDescText.getText();
    String recipeInstructions = RecipeInstructions.getText();
    UUID uniqueRecipe = UUID.randomUUID();
    String recipeID = uniqueRecipe.toString();

    try {
      BE_RecipeDB.addRecipe(recipeID, recipeName, recipeDescription, recipeInstructions);
      RecipeOB addedRecipe = new RecipeOB(recipeID, recipeName, recipeDescription, recipeInstructions, false);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }


  }

  @FXML
  void addTagClicked(ActionEvent event) {
    String tag = addTagTextField.getText();
    tagsListView.getItems().add(tag);
    addTagTextField.clear();
  }

  @FXML
  void onRecipeSelect(javafx.scene.input.MouseEvent event) {

  }

  @FXML
  void removeTagClicked(ActionEvent event) {
    // This method will be used to remove tags from the list view
    int selectedIndex = tagsListView.getSelectionModel().getSelectedIndex();
    if (selectedIndex != -1) {
      tagsListView.getItems().remove(selectedIndex);
    }
  }
}
