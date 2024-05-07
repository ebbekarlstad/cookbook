package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

public class RegistrationViewController {

    /**
   * Password Field.
   */
  @FXML
  private PasswordField passwordField;
  
  /**
   * Username Field.
   */
  @FXML
  private TextField usernameField;

  /**
   * DisplayName field.
   */
  @FXML
  private TextField displayNameField;

  /**
   * Progress indicator that runs during the registration process in backend.
   */
  @FXML
  private ProgressIndicator progressCircle;

  /**
   * Error label that displays a message in case of any error in registration.
   */
  @FXML
  private Label errorLabel;

  /**
   * Registration button that takes input and connects it to the backend methods.
   */
  @FXML
  private Button registerButton;
}
