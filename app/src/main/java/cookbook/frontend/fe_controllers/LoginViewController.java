package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.LogIn;
import cookbook.backend.DatabaseMng;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class LoginViewController {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button loadNavButton;

  private DatabaseMng dbManager = new DatabaseMng();

  @FXML
  private void handleLoginButtonAction(ActionEvent event) {
    String username = usernameField.getText();
    String password = passwordField.getText();

    // Use LogIn class to query database with credentials
    LogIn login = new LogIn(dbManager);

    if (login.doLogin(username, password)) {
      System.out.println("Login successful!");
    } else {
      System.out.println("Login failed!");
    }
  } 
}
