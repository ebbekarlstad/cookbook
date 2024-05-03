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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.sql.Date;

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

    private void populateWeeksComboBox() {
        List<Date> weeks = weeklyController.getWeeklyList(userId);
        if (weeks != null) {
            weeksComboBox.getItems().setAll(weeks.stream().map(Date::toString).collect(Collectors.toList()));
        } else {
            System.out.println("Error loading weeks.");
        }
        weeksComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadWeeklyRecipes(); // You might need to modify this to accept the selected week
            }
        });
    }

    

    private void loadWeeklyRecipes() {
        Map<String, List<Recipe>> weeklyRecipes = weeklyController.getWeeklyRecipes(userId);
        Map<String, ListView<Recipe>> listViewMap = getDayToListViewMap();
        for (String day : listViewMap.keySet()) {
            ListView<Recipe> listView = listViewMap.get(day);
            List<Recipe> recipes = weeklyRecipes.getOrDefault(day, new ArrayList<>());
            if (listView != null) {
                listView.getItems().setAll(recipes);
            } else {
                System.out.println("ListView for " + day + " is null");
            }
        }
    }


}
