package cookbook.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class UIHandler extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Loading all fonts
    Font.loadFont(UIHandler.class.getResource("/fonts/SFNSText-Regular.otf").toExternalForm(), 10);
    Font.loadFont(UIHandler.class.getResource("/fonts/SFNSDisplay-Black.otf").toExternalForm(), 10);
    Font.loadFont(UIHandler.class.getResource("/fonts/SFNSDisplay-Bold.otf").toExternalForm(), 10);
    Font.loadFont(UIHandler.class.getResource("/fonts/SFNSDisplay-Heavy.otf").toExternalForm(), 10);
    Font.loadFont(UIHandler.class.getResource("/fonts/SFNSDisplay-Light.otf").toExternalForm(), 10);
    Font.loadFont(UIHandler.class.getResource("/fonts/SFNSDisplay-Medium.otf").toExternalForm(), 10);
    Font.loadFont(UIHandler.class.getResource("/fonts/SFNSDisplay-Regular.otf").toExternalForm(), 10);
    Font.loadFont(UIHandler.class.getResource("/fonts/SFNSDisplay-Semibold.otf").toExternalForm(), 10);
    Font.loadFont(UIHandler.class.getResource("/fonts/SFNSDisplay-Thin.otf").toExternalForm(), 10);
    Font.loadFont(UIHandler.class.getResource("/fonts/SFNSDisplay-Ultralight.otf").toExternalForm(), 10);
    // Load the FXML layout
    Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));

    // Set the application icon
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));

    // Set the scene and show the stage
    primaryStage.setScene(new Scene(root, 934, 703));
    primaryStage.setTitle("Cookbook");
    primaryStage.show();
  }
}