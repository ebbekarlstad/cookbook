package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.List;
import java.util.Map;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.WeeklyController;
import cookbook.backend.be_objects.Recipe;

public class WeeklyViewController {

    @FXML
    private ListView<Recipe> mondayListView;
    @FXML
    private ListView<Recipe> tuesdayListView;
    @FXML
    private ListView<Recipe> wednesdayListView;
    @FXML
    private ListView<Recipe> thursdayListView;
    @FXML
    private ListView<Recipe> fridayListView;
    @FXML
    private ListView<Recipe> saturdayListView;
    @FXML
    private ListView<Recipe> sundayListView;
    @FXML
    private ComboBox<String> weeksComboBox;
    @FXML
    private Button openShoppingList;
    @FXML
    private Button backButton;

    
    @FXML
    public void goBackToNavigator(MouseEvent event) {
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

    private WeeklyController weeklyController;
    private int userId;

    public void initialize() {
        DatabaseMng dbManager = new DatabaseMng();
        weeklyController = new WeeklyController(dbManager);
        userId = 1; 
        loadWeeklyRecipes();
    }

    private void loadWeeklyRecipes() {
        Map<String, List<Recipe>> weeklyRecipes = weeklyController.getWeeklyRecipes(userId);
        if (weeklyRecipes != null) {
            mondayListView.getItems().setAll(weeklyRecipes.getOrDefault("Monday", List.of()));
            tuesdayListView.getItems().setAll(weeklyRecipes.getOrDefault("Tuesday", List.of()));
            wednesdayListView.getItems().setAll(weeklyRecipes.getOrDefault("Wednesday", List.of()));
            thursdayListView.getItems().setAll(weeklyRecipes.getOrDefault("Thursday", List.of()));
            fridayListView.getItems().setAll(weeklyRecipes.getOrDefault("Friday", List.of()));
            saturdayListView.getItems().setAll(weeklyRecipes.getOrDefault("Saturday", List.of()));
            sundayListView.getItems().setAll(weeklyRecipes.getOrDefault("Sunday", List.of()));
        }
    }


}
