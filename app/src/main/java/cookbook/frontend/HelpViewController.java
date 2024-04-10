package cookbook.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class HelpViewController {
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