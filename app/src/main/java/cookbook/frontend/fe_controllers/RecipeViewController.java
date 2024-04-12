package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RecipeViewController {

    Stage primaryStage;

    @FXML
    private TextField name;

    @FXML
    private ListView<String> ListOfRecipe;

    @FXML
    void addRecipe(MouseEvent event) {
        try {
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/RecipeDetailsView.fxml"));
            // Load the FXML layout
            Parent root = (Parent) fxmlLoader.load();
            RecipeDetailsController recipeDetailsController = (RecipeDetailsController) fxmlLoader.getController();
            // Set the scene and show the stage
            primaryStage.setScene(new Scene(root, 837, 747));
            primaryStage.setTitle("JavaFX Cookbook");
            primaryStage.show();
            ListOfRecipe.getItems().add(name.getText());
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }

    @FXML
    void removeRecipe(MouseEvent event) {
        ListOfRecipe.getItems().remove(name.getText());
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

}

