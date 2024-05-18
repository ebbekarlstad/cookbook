package cookbook.frontend.fe_controllers;

import javafx.event.ActionEvent;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MainViewController {

  @FXML
  private Text welcomeText;

  @FXML
  private void initialize() {
    // Fade animation
    FadeTransition fade = new FadeTransition(Duration.seconds(1.5), welcomeText);
    fade.setFromValue(0.0);
    fade.setToValue(1.0);
    fade.setCycleCount(FadeTransition.INDEFINITE); // Repeat indefinitely
    fade.setAutoReverse(true); // Reverse the animation on each iteration
    fade.play();

    // Scale animation
    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), welcomeText);
    scaleTransition.setFromX(0.5);
    scaleTransition.setFromY(0.5);
    scaleTransition.setToX(1.0);
    scaleTransition.setToY(1.0);
    scaleTransition.setCycleCount(ScaleTransition.INDEFINITE); // Repeat indefinitely
    scaleTransition.setAutoReverse(true); // Reverse the animation on each iteration
    scaleTransition.play();
  }

@FXML
private void handleLoginButton(ActionEvent event) {
    try {
        // Load the login page FXML
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("/LoginView.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a rectangle to cover the scene
        Rectangle colorOverlay = new Rectangle(934, 703);
        colorOverlay.setFill(Color.BLACK);
        colorOverlay.setMouseTransparent(true);

        Group rootWithOverlay = new Group(loginPageParent, colorOverlay);
        Scene sceneWithOverlay = new Scene(rootWithOverlay, 934, 703);

        // Prepare the fade transitions
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), colorOverlay);
        fadeOut.setFromValue(0.0);
        fadeOut.setToValue(0.8);
        fadeOut.setInterpolator(Interpolator.EASE_BOTH);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), colorOverlay);
        fadeIn.setFromValue(0.8);
        fadeIn.setToValue(0.0);
        fadeIn.setInterpolator(Interpolator.EASE_BOTH);

        // Set actions on fade out completion
        fadeOut.setOnFinished(event1 -> {
            window.setScene(sceneWithOverlay);
            fadeIn.play();
        });

        // Start fade out transition
        fadeOut.play();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


  @FXML
  private void handleNavigationButton(ActionEvent event) {
    try {
      // Load the navigation page FXML
      Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
      Scene navigationPageScene = new Scene(navigationPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(navigationPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}