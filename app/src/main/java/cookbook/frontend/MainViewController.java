package cookbook.frontend;

import cookbook.backend.DatabaseMng;
import java.sql.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainViewController {
  @FXML
  private Label mysqlStatus;

  @FXML
  private void initialize() {
    // Attempt to get a database connection
    Connection conn = DatabaseMng.getConnection();

    if (conn != null) {
            mysqlStatus.setText("Driver found and connected");
        } else {
            mysqlStatus.setText("An error has occurred: " + DatabaseMng.getLastErrorMessage());
        }
  }
}