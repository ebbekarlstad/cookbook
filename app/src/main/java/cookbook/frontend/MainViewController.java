package cookbook.frontend;

import cookbook.backend.DatabaseMng;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class MainViewController {
  @FXML
  private Label mysqlStatus;

  @FXML
  private void initialize() {
    if (DatabaseMng.connect()) {
      mysqlStatus.setText("Driver found and connected");
    } else {
      mysqlStatus.setText("An error has occurred: " + DatabaseMng.getLastErrorMessage());
    }
  }


  @FXML
  private Button adminLogin;

  @FXML
  private Button usrLogin;

  @FXML
  void adminLogin(ActionEvent event) {

  }

  @FXML
  void usrLogin(ActionEvent event) {

  }
}