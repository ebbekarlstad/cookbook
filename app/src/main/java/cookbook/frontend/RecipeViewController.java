package cookbook.frontend;

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

public class RecipeViewController {

  @FXML
  private ListView<String> ListOfRecipe_s;

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

  //Denna observableList gör så att den håller datan till tableView
  private ObservableList<CookingOB> recipeData = FXCollections.observableArrayList();


  @FXML
  void addRecipe(ActionEvent event) {
    String name = RecipeName.getText();
    String ingredients = RecipeIngredients.getText();
    String details = RecipeDetails.getText();


    ListOfRecipe_s.getItems().add(name);


    //Använder CookingOB object för att tillägga detalierna för recipe'n
    CookingOB recipe = new CookingOB(ingredients, details);
    recipeData.add(recipe);
    infoRecipeTable.setItems(recipeData);
  }

  @FXML
  void removeRecipe(ActionEvent event) {

  }

}
