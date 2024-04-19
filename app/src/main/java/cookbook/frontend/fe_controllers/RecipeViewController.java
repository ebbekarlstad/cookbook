package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.Ingredient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RecipeViewController {

  @FXML
  private TableView<Ingredient> IngredientTable;

  @FXML
  private ListView<String> ListOfRecipe_s;

  @FXML
  private TextField RecipeDetails;

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
  private TextField addUnitText;

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
    // This method will be used to add the ingredients to the table.
    String name = addIngredientNameText.getText();
    String unit = addUnitText.getText();
    int amount = Integer.parseInt(addAmountText.getText());

    Ingredient ingredient = new Ingredient(name, unit, amount);
    IngredientTable.getItems().add(ingredient);

    addIngredientNameText.clear();
    addUnitText.clear();
    addAmountText.clear();
  }

  @FXML
  void addRecipe(MouseEvent event) {

    String recipeName = RecipeName.getText();
    String recipeDescription = recipeDescText.getText();

    ListOfRecipe_s.getItems().add(recipeName);

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
