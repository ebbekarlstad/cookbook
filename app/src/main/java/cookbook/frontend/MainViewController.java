package cookbook.frontend;

import cookbook.backend.DatabaseMng;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
}
