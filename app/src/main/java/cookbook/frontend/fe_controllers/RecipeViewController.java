package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.CookingOB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;

/* import javafx.scene.input.MouseEvent; */

public class RecipeViewController {

  @FXML
  private ListView<String> ListOfRecipes;

  @FXML
  private TextField RecipeDetails;

  @FXML
  private TextField RecipeIngredients;

  @FXML
  private TextField RecipeName;

  @FXML
  private Button addRecipe;

  @FXML
  private TableView<CookingOB> infoRecipeTable;

  @FXML
  private Button removeRecipe;

  // Denna observableList gör så att den håller datan till tableView
  private ObservableList<CookingOB> recipeData = FXCollections.observableArrayList();

  @FXML
  void addRecipe(ActionEvent event) {
    String name = RecipeName.getText();
    String ingredients = RecipeIngredients.getText();
    String details = RecipeDetails.getText();

    ListOfRecipes.getItems().add(name);

    // Använder CookingOB object för att tillägga detalierna för recipe'n
    CookingOB recipe = new CookingOB(ingredients, details);
    recipeData.add(recipe);
    infoRecipeTable.setItems(recipeData);
  }

  @FXML
  void removeRecipe(ActionEvent event) {
    // Denna metoden gör så att när man tar bort en recipe så raderas det från
    // tableView'n och ListView'n
    int selectedIndex = ListOfRecipes.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
      ListOfRecipes.getItems().remove(selectedIndex);
      recipeData.remove(selectedIndex);
    }
  }

  @FXML
  private void onRecipeSelect(MouseEvent event) {
    // Code to execute when a recipe is selected from the list
  }


  @FXML
  void addIngredientClicked(ActionEvent event) {
    // Add your implementation logic here
  }

  @FXML
  void removeTagClicked(ActionEvent event) {
    // Implementation for removing a tag from the list
  }

  @FXML
  void addTagClicked(ActionEvent event) {
    // Logic for adding a tag
  }
}
