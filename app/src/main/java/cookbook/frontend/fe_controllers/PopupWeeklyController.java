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

public class PopupWeeklyController {

    @FXML
    private ComboBox<String> weeksComboBox;
    @FXML
    private ComboBox<String> daysComboBox;

    private WeeklyController weeklyController;
    private int userId = 1;

    public void initialize() {
        DatabaseMng dbManager = new DatabaseMng();
        weeklyController = new WeeklyController(dbManager);
        loadWeeksIntoComboBox();
        loadDaysIntoComboBox();
        // setupComboBoxListeners();
    }

    private void loadDaysIntoComboBox() {
        List<String> days = weeklyController.getWeekdays();
        daysComboBox.getItems().setAll(days);
    }

    private void loadWeeksIntoComboBox() {
        List<Date> weeks = weeklyController.getYearlyWeeks();
        weeksComboBox.getItems().addAll(weeks.stream().map(date -> new SimpleDateFormat("w-YYYY").format(date)).collect(Collectors.toList()));
    }
    
}
