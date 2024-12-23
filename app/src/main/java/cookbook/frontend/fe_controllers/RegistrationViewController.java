package cookbook.frontend.fe_controllers;

import cookbook.backend.be_controllers.UserController;
import cookbook.backend.be_objects.User;

import java.sql.SQLException;

import cookbook.backend.DatabaseMng;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

  /**
   * Button that takes session back to admin panel.
   */
  @FXML
  private Button backButton;

  /**
   * A checkbox that allows the creation of administrator accounts.
   */
  @FXML
  private CheckBox adminCheckBox;

  private UserController userController;
  private DatabaseMng dbManager;

  /**
   * Constructor to set the instances of dbManager and Usercontroller.
   *
   * @throws SQLException
   */
  public RegistrationViewController() throws SQLException {
    this.dbManager = new DatabaseMng();
    this.userController = new UserController(dbManager);
  }

  /**
   * Method to register users in to the system. Connects the UI with
   * the UserController methods that save the user to the database.
   *
   * @param event Start the registerTask when Register button is pressed.
   */
  @FXML
  private void registerUser(ActionEvent event) {

    // Empty the errorlabel
    errorLabel.setText("");

    // Get the input from the text fields.
    String username = usernameField.getText();
    String displayname = displayNameField.getText();
    String password = passwordField.getText();
    boolean isAdmin = adminCheckBox.isSelected(); // Returns true if the box is checked.

    // Show progress indicator
    progressCircle.setVisible(true);

    /**
     * Boolean that initializes a new Task object.
     *
     * @return False if credentials are wrong. True if correct.
     */
    Task<Boolean> registerTask = new Task<>() {
      @Override
      protected Boolean call() throws Exception {
        Thread.sleep(2000); // Sleep for 2 seconds
        User newUser = new User(null, username, displayname, password, isAdmin, dbManager, password);
        userController.setUser(newUser);
        return userController.saveToDatabase();
      }
    };

    // If registration succeeded.
    registerTask.setOnSucceeded(e -> {
      progressCircle.setVisible(false);

      if (registerTask.getValue()) {
        errorLabel.setTextFill(Color.GREEN);
        System.out.println("Registration Successful!");
        errorLabel.setText("Registration Successful!");

      } else {
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText("Registration Failed.");
      }
    });

    // If registration failed.
    registerTask.setOnFailed(e -> {
      progressCircle.setVisible(false);
      Throwable exception = registerTask.getException();
      errorLabel.setTextFill(Color.RED);
      errorLabel.setText("Error during registration." + exception.getMessage());
      exception.printStackTrace();
    });
    // Start registration task
    new Thread(registerTask).start();
  }

  @FXML
  private void exitRegisterPage(ActionEvent event) {

    try {
      // Load the registration page FXML
      Parent adminPageParent = FXMLLoader.load(getClass().getResource("/AdminPanelView.fxml"));
      Scene adminPaneScene = new Scene(adminPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(adminPaneScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
