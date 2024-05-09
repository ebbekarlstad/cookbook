package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.LogIn;

import java.io.IOException;

import cookbook.backend.DatabaseMng;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginViewController {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label errorLabel;

  @FXML
  private ProgressIndicator loginProgress;

  @FXML
  private Button loadNavButton;

  private DatabaseMng dbManager = new DatabaseMng();

  /**
   * Method that handles the ActionEvent when the Login button is clicked.
   * @param event Login button click.
   */
  @FXML
  private void handleLoginButton(ActionEvent event) {

    // Empty the errorlabel
    errorLabel.setText("");

    // Show progress indicator
    loginProgress.setVisible(true);

    // Get the input from the username & password field.
    String username = usernameField.getText();
    String password = passwordField.getText();

    /**
     * Boolean that initializes a new Task object.
     * @return False if credentials are wrong. True if correct.
     */
    Task<boolean[]> loginTask = new Task<>() {
      @Override
      protected boolean[] call() throws Exception {
        Thread.sleep(2000);  // Sleep for 2 seconds
        return new LogIn(dbManager).doLogin(username, password);
      }
    };
    

    // If login succeded
    loginTask.setOnSucceeded(e -> {
      loginProgress.setVisible(false);
      boolean[] result = loginTask.getValue();

      if (result[0]) { // Login successful
        System.out.println("Login successful!");
        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setText("Login Successful!");
        if (result[1]) {
          //loadAdminPanelView(event);
        } else {
          loadNavigationView(event);
        }

    } else {
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText("Invalid username or password.");
      }
    });

    loginTask.setOnFailed(e -> {
      loginProgress.setVisible(false);
      Throwable th = loginTask.getException();
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Login Failed" + (th != null ? th.getMessage() : "Unknown Error"));
    });
    // Start login task
    new Thread(loginTask).start();
  }

  private void loadNavigationView(ActionEvent event) {
    try {
      //Load the navigation page FXML
      Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
      Scene navigationPageScene = new Scene(navigationPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
      window.setScene(navigationPageScene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
