package cookbook.frontend.fe_controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.WeeklyController;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;

import java.sql.Date;

public class WeeklyViewController {

    @FXML private ListView<Recipe> mondayListView;
    @FXML private ListView<Recipe> tuesdayListView;
    @FXML private ListView<Recipe> wednesdayListView;
    @FXML private ListView<Recipe> thursdayListView;
    @FXML private ListView<Recipe> fridayListView;
    @FXML private ListView<Recipe> saturdayListView;
    @FXML private ListView<Recipe> sundayListView;
    @FXML private ComboBox<String> weeksComboBox;

    private WeeklyController weeklyController;
    private Long userId;

    @FXML
    public void initialize() {
        DatabaseMng dbManager = new DatabaseMng();
        weeklyController = new WeeklyController(dbManager);
        userId = getCurrentUserId();  // Assume this method fetches a valid user ID

        setupRecipeListView(mondayListView);
        setupRecipeListView(tuesdayListView);
        setupRecipeListView(wednesdayListView);
        setupRecipeListView(thursdayListView);
        setupRecipeListView(fridayListView);
        setupRecipeListView(saturdayListView);
        setupRecipeListView(sundayListView);

        populateWeeksComboBox();
        weeksComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadWeeklyRecipesForSelectedWeek(newVal);
            }
        });
    }

    private void populateWeeksComboBox() {
        List<Date> weeks = weeklyController.getWeeklyList(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("w-YYYY");
        weeksComboBox.getItems().clear();
        weeksComboBox.getItems().addAll(weeks.stream().map(sdf::format).collect(Collectors.toList()));
    }

// In WeeklyViewController.java

private void loadWeeklyRecipesForSelectedWeek(String selectedWeek) {
    SimpleDateFormat sdf = new SimpleDateFormat("w-YYYY");
    try {
        java.util.Date parsedDate = sdf.parse(selectedWeek);
        java.sql.Date weekStartDate = new java.sql.Date(parsedDate.getTime());
        Map<String, List<Recipe>> weeklyRecipes = weeklyController.getWeeklyRecipes(userId, weekStartDate);
        updateRecipeViews(weeklyRecipes);
    } catch (Exception e) {
        System.out.println("Failed to parse date or load recipes: " + e.getMessage());
        // Display error message to user or log it appropriately
    }
}





    private void setupRecipeListView(ListView<Recipe> listView) {
        listView.setCellFactory(lv -> new ListCell<Recipe>() {
            @Override
            protected void updateItem(Recipe item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getRecipeName() + " - " + item.getShortDesc());
            }
        });
    }

    private void updateRecipeViews(Map<String, List<Recipe>> weeklyRecipes) {
        Map<String, ListView<Recipe>> listViewMap = getDayToListViewMap();

        Platform.runLater(() -> {
            listViewMap.forEach((day, listView) -> {
                List<Recipe> recipes = weeklyRecipes.getOrDefault(day, List.of());
                listView.getItems().setAll(recipes);
            });
        });
    }

    private Map<String, ListView<Recipe>> getDayToListViewMap() {
        Map<String, ListView<Recipe>> map = new HashMap<>();
        map.put("Monday", mondayListView);
        map.put("Tuesday", tuesdayListView);
        map.put("Wednesday", wednesdayListView);
        map.put("Thursday", thursdayListView);
        map.put("Friday", fridayListView);
        map.put("Saturday", saturdayListView);
        map.put("Sunday", sundayListView);
        return map;
    }

 

        @FXML
    public void goBackToNavigator(MouseEvent event) {
        try {
            // Load the navigation page FXML
            Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
            Scene navigationPageScene = new Scene(navigationPageParent);

            // Get the current stage and replace it
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(navigationPageScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
