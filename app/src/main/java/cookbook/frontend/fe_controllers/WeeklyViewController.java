package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.sql.Date;
import java.text.SimpleDateFormat;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.WeeklyController;
import cookbook.backend.be_objects.Recipe;

import java.util.Calendar;
import java.time.LocalDate;
 
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

    ListView<ListView<Recipe>> listOfDaysOfWeek;
    @FXML
    private ComboBox<String> weeksComboBox;
    @FXML
    private Button openShoppingList;
    @FXML
    private Button backButton;

    List<Date> weeks;

    
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
    private Long userId;

    public void initialize() {
        DatabaseMng dbManager = new DatabaseMng();
        weeklyController = new WeeklyController(dbManager);
        userId = getCurrentUserId(); 
        // for (Node node : mondayListView.getParent()) {
        // // get all days, put them in listOfDaysOfWeek
        // }
        loadWeeklyRecipes();
        populateYearlyWeeksComboBox();  // Call this method to load the weeks into the ComboBox
    }
    

    private void populateYearlyWeeksComboBox() {
    weeks = weeklyController.getYearlyWeeks(); // Retrieve yearly weeks from the backend
    if (weeks != null) {
        weeksComboBox.getItems().clear(); // Clear existing items
        // Format and add weeks to the ComboBox
        weeksComboBox.getItems().addAll(weeks.stream().map(date -> new SimpleDateFormat("w-YYYY").format(date)).collect(Collectors.toList()));
    } else {
        System.out.println("Error loading yearly weeks.");
    }

    weeksComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            // Optional: Load recipes for the selected week
            // You need to convert the string back to Date or adjust the method to handle date selection
            System.out.println(newSelection);
            Date selectedWeek = Date.valueOf(newSelection);
            LocalDate date = weeks.get(weeksComboBox.getSelectionModel().getSelectedIndex()).toLocalDate();
            
            loadWeeklyRecipesForSelectedWeek(selectedWeek);
        }
    });
}


    

private void loadWeeklyRecipesForSelectedWeek(Date weekStartDate) {
    Map<String, List<Recipe>> weeklyRecipes = weeklyController.getWeeklyRecipes(userId, weekStartDate);
    updateRecipeViews(weeklyRecipes);
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

     // Method to retrieve current user ID, which needs to be implemented
     private Long getCurrentUserId() {
       
        return 1L; // Placeholder
    }

    private void loadWeeklyRecipes() {
        // Assuming you have a method in your backend to get recipes for the current week
        Date currentWeek = getCurrentWeekStartDate(); // This should match your backend logic
        // get date from database "date"
        Calendar calendar = Calendar.getInstance();
        //LocalDate dt = dt.of(date.getYear(), date.getMonth(), date.getDay());

        Map<String, List<Recipe>> weeklyRecipes = weeklyController.getWeeklyRecipes(userId, currentWeek);
        updateRecipeViews(weeklyRecipes);
    }

    private void updateRecipeViews(Map<String, List<Recipe>> weeklyRecipes) {
        Map<String, ListView<Recipe>> listViewMap = getDayToListViewMap();
        for (String day : listViewMap.keySet()) {
            ListView<Recipe> listView = listViewMap.get(day);
            List<Recipe> recipes = weeklyRecipes.getOrDefault(day, new ArrayList<>());
            listView.getItems().setAll(recipes);
        }
    }

    private Date getCurrentWeekStartDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new Date(cal.getTimeInMillis());
    }





    
}
