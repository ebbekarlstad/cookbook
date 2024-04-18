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

  @FXML
  private void handleLoginButton(ActionEvent event) {
    errorLabel.setText("");
    String username = usernameField.getText();
    String password = passwordField.getText();

    // Show progress indicator
    loginProgress.setVisible(true);

    Task<Boolean> loginTask = new Task<>() {
      @Override
      protected Boolean call() throws Exception {
        Thread.sleep(2000);
        return new LogIn(dbManager).doLogin(username, password);
      }
    };

    loginTask.setOnSucceeded(e -> {
      loginProgress.setVisible(false);

      if (loginTask.getValue()) {
        System.out.println("Login successful!");
        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setText("Login Successful!");
        loadNavigationView();

      } else {
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText("Invalid username or password.");
      }
    });

    loginTask.setOnFailed(e -> {
      loginProgress.setVisible(false);
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Login Failed.");
    });
    // Start login task
    new Thread(loginTask).start();
  }

  private void loadNavigationView() {
    try {
      //Load the navigation view FXML
      FXMLLoader loader = new FXMLLoader(getClass().getResource("NavigationView.fxml"));
      Stage stage = (Stage) usernameField.getScene().getWindow();
      stage.setScene(new Scene(loader.load()));
      stage.show();
    }catch (IOException e) {
      e.printStackTrace();
    }
  }
}
