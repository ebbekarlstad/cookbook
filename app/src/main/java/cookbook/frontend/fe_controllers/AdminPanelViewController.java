package cookbook.frontend.fe_controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminPanelViewController {

  @FXML
  private Button backButton;

  /**
   * Method to redirect user to registration page on button click.
   *
   * @param event - Redirects to registration page.
   */
  @FXML
  private void redirectToRegister(ActionEvent event) {
    try {
      // Load the registration page FXML
      Parent registrationPageParent = FXMLLoader.load(getClass().getResource("/RegistrationView.fxml"));
      Scene registrationPageScene = new Scene(registrationPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(registrationPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void redirectToModify(ActionEvent event) {
    try {
      // Load the registration page FXML
      Parent registrationPageParent = FXMLLoader.load(getClass().getResource("/ModifyUserView.fxml"));
      Scene registrationPageScene = new Scene(registrationPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(registrationPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleLogoutButton(ActionEvent event) {
    try {
      // Load the navigation page FXML
      Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/LoginView.fxml"));
      Scene navigationPageScene = new Scene(navigationPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(navigationPageScene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleBackButton(ActionEvent event) {
    try {
      Parent navigationAdminViewParent = FXMLLoader.load(getClass().getResource("/NavigationViewAdmin.fxml"));
      Scene navigationAdminViewScene = new Scene(navigationAdminViewParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(navigationAdminViewScene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
