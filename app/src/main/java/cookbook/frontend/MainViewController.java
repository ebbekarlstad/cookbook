package cookbook.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
/* import cookbook.backend.DatabaseMng;
import java.sql.Connection; */


public class MainViewController {
  @FXML
  private Label mysqlStatus;

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
}