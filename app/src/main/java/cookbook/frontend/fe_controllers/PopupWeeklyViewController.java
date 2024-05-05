package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Date;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.WeeklyController;
import cookbook.backend.be_objects.Recipe;

public class PopupWeeklyViewController {

    @FXML
    private ComboBox<String> weeksComboBox;

    @FXML
    private ComboBox<String> daysComboBox;

    private WeeklyController weeklyController;
    private Recipe recipe;
    private Long userId;

    public void initialize() {
        try {
            DatabaseMng dbManager = new DatabaseMng();
            weeklyController = new WeeklyController(dbManager);
            loadWeeksIntoComboBox();
            setupComboBoxListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initData(Recipe recipe, Long userId) {
        this.recipe = recipe;
        this.userId = userId;
        loadWeeksIntoComboBox();
        loadDaysIntoComboBox();

    }

    private void loadDaysIntoComboBox() {
        List<String> days = weeklyController.getWeekdays();
        daysComboBox.getItems().setAll(days);
    }

    private void loadWeeksIntoComboBox() {
        try {
            List<Date> weeks = weeklyController.getYearlyWeeks();
            if (weeks != null) {
                weeksComboBox.getItems().addAll(weeks.stream().map(date -> new SimpleDateFormat("w-YYYY").format(date)).collect(Collectors.toList()));
            } else {
                System.out.println("No weeks data available.");
            }

        } catch (Exception e) {
            System.out.println("Error loading weeks data: " + e.getMessage());
        }
    }

    private void setupComboBoxListeners() {
        weeksComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> handleSelectionChange());
        daysComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> handleSelectionChange());
    }

    private void handleSelectionChange() {
        try {
            String selectedWeek = weeksComboBox.getSelectionModel().getSelectedItem();
            String selectedDay = daysComboBox.getSelectionModel().getSelectedItem();
            if (selectedWeek != null && selectedDay != null) {
                saveSelectionToDatabase(selectedWeek, selectedDay);
            }
        } catch (Exception e) {
            System.out.println("Error handling selection change: " + e.getMessage());
        }
    }

    private void saveSelectionToDatabase(String week, String day) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("w-YYYY");
            java.util.Date parsedWeekDate = sdf.parse(week);
            java.sql.Date weekStartDate = new java.sql.Date(parsedWeekDate.getTime());


            int weeklyDinnerListId = weeklyController.ensureWeeklyDinnerListExists(userId, weekStartDate);
            if (weeklyDinnerListId == -1) {
                System.out.println("Failed to ensure weeklyd inner list exists.");
                return;
            }

            if (recipe != null && weeklyController.addRecipeToWeeklyList(userId, weekStartDate, recipe.getId(), day)) {
                System.out.println("Recipe successfully added to weekly list.");
            } else {
                System.out.println("Failed to add recipe to weekly list.");
            }
        } catch (ParseException e) {
            System.out.println("Error parsing week start date: " + e.getMessage());
        }
    }
    
}
