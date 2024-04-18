package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.LogIn;
import cookbook.backend.DatabaseMng;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class LoginViewController {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label errorLabel;

  @FXML
  private Button loadNavButton;

  private DatabaseMng dbManager = new DatabaseMng();

  @FXML
  private void handleLoginButton(ActionEvent event) {
    errorLabel.setText("");
    String username = usernameField.getText();
    String password = passwordField.getText();

    // Use LogIn class to query database with credentials
    LogIn login = new LogIn(dbManager);

    if (login.doLogin(username, password)) {
      System.out.println("Login successful!");
      errorLabel.setTextFill(Color.GREEN);
      errorLabel.setText("Login successful!");
      
    } else {
      System.out.println("Login failed!");
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Invalid username or password.");
    }
  } 
}
