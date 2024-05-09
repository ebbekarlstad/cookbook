package cookbook.frontend.fe_controllers;

import cookbook.backend.be_controllers.UserController;
import cookbook.backend.be_objects.User;

import java.sql.SQLException;



import cookbook.backend.DatabaseMng;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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

  private UserController userController;
  private DatabaseMng dbManager;

  public RegistrationViewController() throws SQLException {
    this.dbManager = new DatabaseMng();
    this.userController = new UserController(dbManager);
}

  @FXML
  private void registerUser(ActionEvent event) {

        // Empty the errorlabel
    errorLabel.setText("");

    // Get the input from the username & password field.
    String username = usernameField.getText();
    String displayname = displayNameField.getText();
    String password = passwordField.getText();

    // Show progress indicator
    progressCircle.setVisible(true);

    /**
     * Boolean that initializes a new Task object.
     * @return False if credentials are wrong. True if correct.
     */
    Task<Boolean> registerTask = new Task<>() {
      @Override
      protected Boolean call() throws Exception {
        Thread.sleep(2000);  // Sleep for 2 seconds
        User newUser = new User(null, username, displayname, password, false, dbManager, password);
        userController.setUser(newUser);
        return userController.saveToDatabase();
      }
    };
    

    // If login succeded
    registerTask.setOnSucceeded(e -> {
      progressCircle.setVisible(false);

      if (registerTask.getValue()) {
        errorLabel.setTextFill(Color.GREEN);
        System.out.println("Registration Successful!");
        errorLabel.setText("Registration Successful!");
        //loadNavigationView(event);

      } else {
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText("Registration Failed.");
      }
    });

    registerTask.setOnFailed(e -> {
      progressCircle.setVisible(false);
      Throwable exception = registerTask.getException();
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Error during registration." + exception.getMessage());
      exception.printStackTrace();
    });
    // Start login task
    new Thread(registerTask).start();
  }
}
