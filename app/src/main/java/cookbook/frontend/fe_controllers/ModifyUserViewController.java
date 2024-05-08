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

public class ModifyUserViewController {

    /**
   * Password Field.
   */
  @FXML
  private PasswordField newPasswordField;
  
  /**
   * Username Field.
   */
  @FXML
  private TextField newUsernameField;

  /**
   * DisplayName field.
   */
  @FXML
  private TextField newDisplayNameField;

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
  private Button submitButton;

  private UserController userController;
  private DatabaseMng dbManager;

  public ModifyUserViewController() throws SQLException {
    this.dbManager = new DatabaseMng();
    this.userController = new UserController(dbManager);
}

  @FXML
  private void modifyUser(ActionEvent event) {

    // Empty the errorlabel
    errorLabel.setText("");

    // Get the input from the username & password field.
    String username = newUsernameField.getText();
    String displayname = newDisplayNameField.getText();
    String password = newPasswordField.getText();

    // Show progress indicator
    progressCircle.setVisible(true);

    /**
     * Boolean that initializes a new Task object.
     * @return False if credentials are wrong. True if correct.
     */
    Task<Boolean> modifyTask = new Task<>() {
      @Override
      protected Boolean call() throws Exception {
        Thread.sleep(2000);  // Sleep for 2 seconds
        User newUser = new User(null, username, displayname, password, null, dbManager, password);
        userController.setUser(newUser);
        return userController.saveToDatabase();
      }
    };
    

    // If modification succededs
    modifyTask.setOnSucceeded(e -> {
      progressCircle.setVisible(false);

      if (modifyTask.getValue()) {
        errorLabel.setTextFill(Color.GREEN);
        System.out.println("Modification Successful!");
        errorLabel.setText("Modification Successful!");
        //loadNavigationView(event);

      } else {
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText("Modification Failed.");
      }
    });

    modifyTask.setOnFailed(e -> {
      progressCircle.setVisible(false);
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Error during modification.");
    });
    // Start login task
    new Thread(modifyTask).start();
  }
}
