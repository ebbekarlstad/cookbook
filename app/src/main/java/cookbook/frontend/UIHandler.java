package cookbook.frontend;

import cookbook.backend.DatabaseMng;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UIHandler extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set some JavaFX variables
        VBox root = new VBox();
        root.setPadding(new Insets(5));
        Label title = new Label("JavaFX");
        Label mysql = new Label();

        // Show the backend status (for actual DB operations, do that in DatabaseMng)
        if (DatabaseMng.connect()) {
            mysql.setText("Driver found and connected");
        } else {
            mysql.setText("An error has occurred: " + DatabaseMng.getLastErrorMessage());
        }

        // JavaFX actions to get window running
        root.getChildren().addAll(title, mysql);

        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.setTitle("JavaFX Cookbook");
        primaryStage.show();
    }
}