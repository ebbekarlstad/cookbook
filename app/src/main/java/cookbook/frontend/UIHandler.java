package cookbook.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Taskbar.Feature;


public class UIHandler extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    
    Font.loadFont(getClass().getResourceAsStream("/fonts/OpenSans-Italic.ttf"), 20);
    // Load the FXML layout
    Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));

    // Load and set multiple icons
/*     primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_icon_16x16.png")));
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_icon_32x32.png")));
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_icon_64x64.png")));
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_icon_128x128.png")));
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_icon_256x256.png")));
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_icon_512x512.png")));
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app_icon_1024x1024.png"))); */

    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));

    //Set icon on the taskbar/dock
    if (Taskbar.isTaskbarSupported()) {
      var taskbar = Taskbar.getTaskbar();

      if (taskbar.isSupported(Feature.ICON_IMAGE)) {
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        var dockIcon = defaultToolkit.getImage(getClass().getResource("/images/icon.png"));
        taskbar.setIconImage(dockIcon);
      }

    }

    // Rest of your code to set up the scene, etc.  
    primaryStage.setScene(new Scene(root, 934, 703));
    primaryStage.setTitle("Cookbook");
    primaryStage.show();
    
  }
}