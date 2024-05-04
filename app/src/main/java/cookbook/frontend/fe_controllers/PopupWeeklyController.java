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

public class PopupWeeklyController {

    @FXML
    private ComboBox<String> weeksComboBox;
    @FXML
    private ComboBox<String> daysComboBox;

    private WeeklyController weeklyController;
    private Recipe recipe;
    private Long userId;

    public void initialize() {
        DatabaseMng dbManager = new DatabaseMng();
        weeklyController = new WeeklyController(dbManager);
        loadWeeksIntoComboBox();
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
        List<Date> weeks = weeklyController.getYearlyWeeks();
        weeksComboBox.getItems().addAll(weeks.stream().map(date -> new SimpleDateFormat("w-YYYY").format(date)).collect(Collectors.toList()));
    }

    private void setupComboBoxListeners() {
        weeksComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> handleSelectionChange());
        daysComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> handleSelectionChange());
    }

    private void handleSelectionChange() {
        String selectedWeek = weeksComboBox.getSelectionModel().getSelectedItem();
        String selectedDay = daysComboBox.getSelectionModel().getSelectedItem();
        if (selectedWeek != null && selectedDay != null) {
            saveSelectionToDatabase(selectedWeek, selectedDay);
        }
    }

    private void saveSelectionToDatabase(String week, String day) {
        try {
            
    }
    
}
