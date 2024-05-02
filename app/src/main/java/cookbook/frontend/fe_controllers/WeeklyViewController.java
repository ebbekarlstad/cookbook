package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.WeeklyController;
import cookbook.backend.be_objects.Recipe;

import java.util.List;
import java.util.Map;

public class WeeklyViewController {
    //  @FXML
    // private TableView<Map.Entry<String, List<Recipe>>> weeklyListView;

    // @FXML
    // private TableColumn<Map.Entry<String, List<Recipe>>, String> mondayColumn;

    // @FXML
    // private TableColumn<Map.Entry<String, List<Recipe>>, String> tuesdayColumn;

    // @FXML
    // private TableColumn<Map.Entry<String, List<Recipe>>, String> wednesdayColumn;

    // @FXML
    // private TableColumn<Map.Entry<String, List<Recipe>>, String> thursdayColumn;

    // @FXML
    // private TableColumn<Map.Entry<String, List<Recipe>>, String> fridayColumn;

    // @FXML
    // private TableColumn<Map.Entry<String, List<Recipe>>, String> saturdayColumn;

    // @FXML
    // private TableColumn<Map.Entry<String, List<Recipe>>, String> sundayColumn;
    
    // private WeeklyController weeklyController;
    // private DatabaseMng dbManager = new DatabaseMng();
    

    // public  WeeklyViewController(DatabaseMng dbManager) {
    //     this.dbManager = new DatabaseMng();
    //     this.weeklyController = new WeeklyController(this.dbManager);
    // }

    // @FXML 
    // private void initialize() {
    //     setupTableView();
    //     loadRecipe();
    // }

    // private void setupTableView() {
    //     mondayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
    //         cellData.getValue().getValue().stream().map(Recipe::getRecipeName).reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b)
    //     ));
    
    //     tuesdayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
    //         cellData.getValue().getValue().stream().map(Recipe::getRecipeName).reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b)
    //     ));
    
    //     wednesdayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
    //         cellData.getValue().getValue().stream().map(Recipe::getRecipeName).reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b)
    //     ));
        
    //     thursdayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
    //         cellData.getValue().getValue().stream().map(Recipe::getRecipeName).reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b)
    //     ));

    //     fridayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
    //         cellData.getValue().getValue().stream().map(Recipe::getRecipeName).reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b)
    //     ));

    //     saturdayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
    //         cellData.getValue().getValue().stream().map(Recipe::getRecipeName).reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b)
    //     ));

    //     sundayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
    //         cellData.getValue().getValue().stream().map(Recipe::getRecipeName).reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b)
    //     ));
    // }

    // private void loadRecipe() {
    //     Map<String, List<Recipe>> weeklyRecipes = weeklyController.getWeeklyRecipes(1);
    //     ObservableList<Map.Entry<String, List<Recipe>>> items = FXCollections.observableArrayList(weeklyRecipes.entrySet());
    //     weeklyListView.setItems(items);
    // }

}
