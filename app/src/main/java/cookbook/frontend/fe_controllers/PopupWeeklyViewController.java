package cookbook.frontend.fe_controllers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.WeeklyController;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

public class PopupWeeklyViewController {

  @FXML
  private ComboBox<String> weeksComboBox;

  @FXML
  private ComboBox<String> daysComboBox;

  private WeeklyController weeklyController;
  private Recipe recipe;
  private Long userId = UserSession.getInstance().getUserId();

  Alert a = new Alert(AlertType.INFORMATION);

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
    this.userId = UserSession.getInstance().getUserId();
    loadWeeksIntoComboBox();
    loadDaysIntoComboBox();
  }

  private void loadDaysIntoComboBox() {
    List<String> days = weeklyController.getWeekdays();
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    String today = sdf.format(new java.util.Date());

    days = days.stream()
        .map(day -> day.equals(today) ? day + " (Today)" : day)
        .collect(Collectors.toList());
    daysComboBox.getItems().setAll(days);
  }

  private void loadWeeksIntoComboBox() {
    try {
      List<Date> weeks = weeklyController.getYearlyWeeks();
      SimpleDateFormat sdf = new SimpleDateFormat("w-YYYY");
      Date now = new Date(System.currentTimeMillis());
      String currentWeekString = sdf.format(now);
      // Filter and deduplicate weeks
      Set<String> weekSet = new HashSet<>();
      Calendar calendar = Calendar.getInstance();
      int currentYear = calendar.get(Calendar.YEAR);
      if (weeks != null) {
        weeks.stream()
            .filter(date -> {
              calendar.setTime(date);
              int year = calendar.get(Calendar.YEAR);
              int week = calendar.get(Calendar.WEEK_OF_YEAR);
              // Check if the week belongs to the current year
              return year == currentYear && (week > 1 || calendar.get(Calendar.DAY_OF_YEAR) <= 7);
            })
            .forEach(date -> {
              String weekString = sdf.format(date);
              weekSet.add(
                  weekString.equals(currentWeekString) ? weekString + " (Current Week)" : weekString);
            });
        // Convert set to list and sort
        List<String> sortedWeeks = weekSet.stream()
            .sorted(Comparator.comparingInt(s -> {
              try {
                return Integer.parseInt(s.split("-")[0]);
              } catch (NumberFormatException e) {
                return Integer.MAX_VALUE;
              }
            }))
            .collect(Collectors.toList());
        weeksComboBox.getItems().setAll(sortedWeeks);
      } else {
        System.out.println("No weeks data available.");
      }
    } catch (Exception e) {
      System.out.println("Error loading weeks data: " + e.getMessage());
    }
  }

  private void setupComboBoxListeners() {
    // setting prompt text
    weeksComboBox.setPromptText("Select Week");
    daysComboBox.setPromptText("Select Day");
    // Setting up for weeks, highlighting current week in red
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

    // Setting up for days, highlighting current day in red
    daysComboBox.setCellFactory(lv -> new ListCell<String>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
          setText(null);
        } else {
          setText(item);
          if (item.contains("(Today)")) {
            setTextFill(Color.RED);
            setStyle("-fx-font-weight: bold;");
          } else {
            setTextFill(Color.BLACK);
            setStyle("");
          }
        }
      }
    });

    weeksComboBox.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldVal, newVal) -> handleSelectionChange());
    daysComboBox.getSelectionModel().selectedItemProperty()
        .addListener((obs, oldVal, newVal) -> handleSelectionChange());
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
        System.out.println("Failed to ensure weekly dinner list exists.");
        return;
      }

      if (weeklyController.recipeExistsInWeeklyList(userId, weekStartDate, recipe.getId(), day)) {
        System.out.println("Recipe already exists for this week and day.");
        showAlert("Recipe already saved", "This recipe has already been saved for this day.");
      } else {
        if (weeklyController.addRecipeToWeeklyList(userId, weekStartDate, recipe.getId(), day)) {
          System.out.println("Recipe successfully added to weekly list.");
          a.show();
          a.setTitle("Success");
          a.setHeaderText("Recipe successfully added to weekly list.");
          a.setContentText("You may now go back, or add the recipe to another day/week.");
        } else {
          System.out.println("Failed to add recipe to weekly list.");
        }
      }
    } catch (ParseException e) {
      System.out.println("Error parsing week start date: " + e.getMessage());
    }
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
