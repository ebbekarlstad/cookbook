package cookbook.frontend.fe_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditingRecipeController {

  @FXML
  private Button EditIngredient;

  @FXML
  private Button EditTag;

  @FXML
  private Button SaveRecipe;

  @FXML
  private ListView<?> UserCurrentRecipes;

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
  private ComboBox<?> tagsDropdown;

  @FXML
  private Label tagsLabel;

  @FXML
  private ComboBox<?> unit;

  @FXML
  void EditIngredientToList(ActionEvent event) {

  }

  @FXML
  void EditTagToList(ActionEvent event) {

  }

  @FXML
  void SaveRecipe(ActionEvent event) {

  }

  @FXML
  void addIngredientToList(ActionEvent event) {

  }

  @FXML
  void addTagToList(ActionEvent event) {

  }

  @FXML
  void backButton(ActionEvent event) {

  }

}
