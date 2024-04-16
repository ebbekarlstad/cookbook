package cookbook.frontend.fe_controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import cookbook.backend.be_objects.CookingOB;

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

    @FXML
    private ComboBox<Integer> amountButton;

    private ObservableList<CookingOB> recipeData = FXCollections.observableArrayList();

    @FXML
    void addRecipe(ActionEvent event) {
        String name = RecipeName.getText();
        String ingredients = RecipeIngredients.getText();
        String details = RecipeDetails.getText();
        ListOfRecipe_s.getItems().add(name);
        CookingOB recipe = new CookingOB(ingredients, details);
        recipeData.add(recipe);
        infoRecipeTable.setItems(recipeData);
    }

    @FXML
    void removeRecipe(ActionEvent event) {
        int selectedIndex = ListOfRecipe_s.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ListOfRecipe_s.getItems().remove(selectedIndex);
            recipeData.remove(selectedIndex);
        }
    }

}
