package cookbook.frontend;

import javafx.event.ActionEvent;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;
/* import cookbook.backend.DatabaseMng;
import java.sql.Connection; */


public class MainViewController {
  @FXML
  private Label mysqlStatus;

  @FXML
  private Text welcomeText;

/*   @FXML
  private void initialize() {
    // Attempt to get a database connection
    Connection conn = DatabaseMng.getConnection();

    if (conn != null) {
            mysqlStatus.setText("Driver found and connected");
        } else {
            mysqlStatus.setText("An error has occurred: " + DatabaseMng.getLastErrorMessage());
        }
  } */

  @FXML
  private void initialize() {
    // Welcome animation
    FadeTransition fade = new FadeTransition(Duration.seconds(3), welcomeText);
    fade.setFromValue(0.0);
    fade.setToValue(1.0);
    fade.play();
  }

  @FXML
  private void handleLoginButton(ActionEvent event) {
    try {
      //Load the login page FXML
      Parent loginPageParent = FXMLLoader.load(getClass().getResource("/LoginView.fxml"));
      Scene loginPageScene = new Scene(loginPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
      window.setScene(loginPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleNavigationButton(ActionEvent event) {
    try {
      //Load the navigation page FXML
      Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
      Scene navigationPageScene = new Scene(navigationPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
      window.setScene(navigationPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleHelpButton(ActionEvent event) {
    try {
      //Load the navigation page FXML
      Parent helpPageParent = FXMLLoader.load(getClass().getResource("/HelpView.fxml"));
      Scene helpPageScene = new Scene(helpPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
      window.setScene(helpPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}