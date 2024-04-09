package cookbook.frontend;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UIHandler extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Load the FXML layout
    Parent root = FXMLLoader.load(getClass().getResource("/RecipeView.fxml"));
        
    // Set the scene and show the stage
    primaryStage.setScene(new Scene(root, 640, 400));
    primaryStage.setTitle("Cookbook");
    primaryStage.show();
  }
}