package cookbook.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class UIHandler extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Load the FXML layout
    Parent root = FXMLLoader.load(getClass().getResource("/AdminPanelView.fxml"));

    // Set the application icon
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));

    // Set the scene and show the stage
    primaryStage.setScene(new Scene(root, 934, 703));
    primaryStage.setTitle("Cookbook");
    primaryStage.show();
  }
}