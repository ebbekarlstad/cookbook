package cookbook.frontend.fe_controllers;

/* import cookbook.backend.LogIn; */
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
/* import javafx.event.ActionEvent; */

public class LoginViewController {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button loadNavButton;

/*   @FXML
  private void handleNavButton(ActionEvent event) {
    String username = usernameField.getText();
    String password = passwordField.getText();

    // Assuming there's a LogIn class that handles the login logic
    LogIn login = new LogIn();
  
  } */
}
