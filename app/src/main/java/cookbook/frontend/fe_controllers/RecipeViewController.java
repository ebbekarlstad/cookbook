package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.Ingredient;
import cookbook.backend.be_controllers.BE_RecipeDB;
import cookbook.backend.be_objects.RecipeOB;
import cookbook.backend.be_objects.amountOfIngredients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.UUID;

public class RecipeViewController {

  @FXML
  private TableView<Ingredient> IngredientTable;

  @FXML
  private ListView<RecipeOB> ListOfRecipe_s;

  @FXML
  private TextField RecipeInstructions;

  @FXML
  private TextField RecipeName;

  @FXML
  private TextField addAmount;

  @FXML
  private Button addIngredient;

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

  private ObservableList<Ingredient> ingredientsList = FXCollections.observableArrayList();

  @FXML
  void initialize() {
    IngredientTable.setItems(ingredientsList);
    ingredientColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
    TableColumn<Ingredient, Double> amountColumn = new TableColumn<>("Amount");
    amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
  }

  @FXML
  void addIngredientClicked(ActionEvent event) {
    try {
      String ingredientName = addIngredientNameText.getText();
      String selectUnit = addUnit.getSelectionModel().getSelectedItem();
      double selectedAmount = Double.parseDouble(addAmount.getText());

      // Create the Ingredient object with the provided details
      Ingredient ingredient = new Ingredient("", ingredientName, selectedAmount, selectUnit);

      // Add the Ingredient to the table
      ingredientsList.add(ingredient);

      // Clear the input fields
      addIngredientNameText.clear();
      addUnit.getSelectionModel().clearSelection();
      addAmount.clear();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }




  @FXML
  void addRecipe(ActionEvent event) {
    String recipeName = RecipeName.getText();
    String recipeDescription = recipeDescText.getText();
    String recipeInstructions = RecipeInstructions.getText();

    if (!recipeName.isEmpty() && !recipeDescription.isEmpty() && !recipeInstructions.isEmpty() && !ingredientsList.isEmpty()) {
      try {
        UUID uniqueRecipe = UUID.randomUUID();
        String recipeID = uniqueRecipe.toString();

        // Add recipe to database
        BE_RecipeDB.addRecipe(recipeName, recipeDescription, recipeInstructions, ingredientsList);

        // Clear input fields and lists
        RecipeName.clear();
        recipeDescText.clear();
        RecipeInstructions.clear();
        ingredientsList.clear();

        // Show success message or update UI as needed
        System.out.println("Recipe added successfully!");

      } catch (SQLException e) {
        e.printStackTrace();
        // Handle database error
      }
    } else {
      // Show error message or alert user to fill in all fields
      System.out.println("Please fill in all fields.");
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
    // You can handle selecting a recipe here if needed
  }

  @FXML
  void removeTagClicked(ActionEvent event) {
    int selectedIndex = tagsListView.getSelectionModel().getSelectedIndex();
    if (selectedIndex != -1) {
      tagsListView.getItems().remove(selectedIndex);
    }
  }
}
