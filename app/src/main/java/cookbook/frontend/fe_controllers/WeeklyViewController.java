package cookbook.frontend.fe_controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.WeeklyController;
import cookbook.backend.be_objects.Recipe;
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

        setupRecipeListView(mondayListView, "Monday");
        setupRecipeListView(tuesdayListView, "Tuesday");
        setupRecipeListView(wednesdayListView, "Wednesday");
        setupRecipeListView(thursdayListView, "Thursday");
        setupRecipeListView(fridayListView, "Friday");
        setupRecipeListView(saturdayListView, "Saturday");
        setupRecipeListView(sundayListView, "Sunday");

        populateWeeksComboBox();
        
        String selectedWeek = weeksComboBox.getSelectionModel().getSelectedItem();
        if (selectedWeek != null) {
            loadWeeklyRecipesForSelectedWeek(selectedWeek);
            highlightCurrentDay();
        }

        weeksComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadWeeklyRecipesForSelectedWeek(newVal);
                highlightCurrentDay();
            }
        });
    }

    private void populateWeeksComboBox() {
        List<Date> weeks = weeklyController.getYearlyWeeks();
        SimpleDateFormat sdf = new SimpleDateFormat("w-YYYY");
        String currentWeek = sdf.format(new java.util.Date());

        weeksComboBox.getItems().clear();
        weeksComboBox.setPromptText("Select Week");

        List<String> weekLabels = weeks.stream().map(date -> {
            String weekString = sdf.format(date);
            return weekString.equals(currentWeek) ? weekString + " (Current Week)" : weekString;
        }).collect(Collectors.toList());

        weeksComboBox.getItems().addAll(weekLabels);
        int currentWeekIndex = weekLabels.indexOf(currentWeek + " (Current Week)");
        if (currentWeekIndex != -1) {
            weeksComboBox.getSelectionModel().select(currentWeekIndex);
        }
        
        // current week visible in red
        weeksComboBox.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);
                    if (item.contains("Current Week")) {
                        setTextFill(Color.RED);
                        setStyle("-fx-font-weight: bold;");
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("");
                    }
                }
            }
        });
            
    }

    private void loadWeeklyRecipesForSelectedWeek(String selectedWeek) {
        Task<Map<String, List<Recipe>>> fetchRecipeTask = new Task<>() {
            @Override
            protected Map<String, List<Recipe>> call() throws Exception {
                SimpleDateFormat sdf = new SimpleDateFormat("w-YYYY");
                java.util.Date parsedDate = sdf.parse(selectedWeek);
                java.sql.Date weekStartDate = new java.sql.Date(parsedDate.getTime());
                return weeklyController.getWeeklyRecipes(userId, weekStartDate);
            }
        };

        fetchRecipeTask.setOnSucceeded(event -> {
            Map<String, List<Recipe>> weeklyRecipes = fetchRecipeTask.getValue();
            updateRecipeViews(weeklyRecipes);
            highlightCurrentDay();
        });
        fetchRecipeTask.setOnFailed(event -> {
            Throwable e = fetchRecipeTask.getException();
            e.printStackTrace();
        });

        new Thread(fetchRecipeTask).start();
    }
    private String getCurrentDayOfWeek() {
        return weeklyController.getCurrentDay();
    }

    private void setupRecipeListView(ListView<Recipe> listView, String dayName) {
        listView.setCellFactory(lv -> new ListCell<Recipe>() {
            @Override
            protected void updateItem(Recipe item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getRecipeName() + " - " + item.getShortDesc());
            }
        });

        if (dayName.equals(getCurrentDayOfWeek()) && isCurrentWeekSelected()) {
            listView.setStyle("-fx-background-color: lightblue;");
        } else {
            listView.setStyle("");
        }
    }

    private boolean isCurrentWeekSelected() {
        SimpleDateFormat sdf = new SimpleDateFormat("w-YYYY");
        String selectedWeek = weeksComboBox.getValue();
        String currentWeek = sdf.format(new java.util.Date());
        return selectedWeek != null && selectedWeek.contains(currentWeek);
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

    private void highlightCurrentDay() {
        setupRecipeListView(mondayListView, "Monday");
        setupRecipeListView(tuesdayListView, "Tuesday");
        setupRecipeListView(wednesdayListView, "Wednesday");
        setupRecipeListView(thursdayListView, "Thursday");
        setupRecipeListView(fridayListView, "Friday");
        setupRecipeListView(saturdayListView, "Saturday");
        setupRecipeListView(sundayListView, "Sunday");
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

    private Long getCurrentUserId() {
        // This method should correctly fetch the user's ID
        return 1L; // Placeholder
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
