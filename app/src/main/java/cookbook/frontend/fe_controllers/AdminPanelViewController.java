package cookbook.frontend.fe_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminPanelViewController {
  
  /**
   * Method to redirect user to registration page on button click.
   *
   * @param event - Redirects to registration page.
   */
  @FXML
  private void redirectToRegister (ActionEvent event) {
    try {
      //Load the login page FXML
      Parent loginPageParent = FXMLLoader.load(getClass().getResource("/RegisterView.fxml"));
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

