package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.LogIn;
import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.UserSession;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

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
        // Empty the errorlabel
        errorLabel.setText("");

        // Show progress indicator
        loginProgress.setVisible(true);

        // Get the input from the username & password field.
        String username = usernameField.getText();
        String password = passwordField.getText();

        Task<boolean[]> loginTask = new Task<>() {
            @Override
            protected boolean[] call() throws Exception {
                Thread.sleep(2000);  // Sleep for 2 seconds
                return new LogIn(dbManager).doLogin(username, password);
            }
        };

        loginTask.setOnSucceeded(e -> {
            loginProgress.setVisible(false);
            boolean[] result = loginTask.getValue();

            if (result[0]) { // Login successful
                System.out.println("Login successful!");
                errorLabel.setTextFill(Color.GREEN);
                errorLabel.setText("Login Successful!");
                
                // Hämta användar-ID och användarens roll (admin eller inte)
                Long userId = UserSession.getInstance().getUserId();
                boolean isAdmin = result[1];
                
                UserSession.getInstance().login(userId, username, isAdmin);

                if (isAdmin) {
                    loadAdminPanelView(event);
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
            Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
            Scene navigationPageScene = new Scene(navigationPageParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(navigationPageScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAdminPanelView(ActionEvent event) {
        try {
            Parent adminPageParent = FXMLLoader.load(getClass().getResource("/AdminRecipeDetailsView.fxml"));
            Scene adminPageScene = new Scene(adminPageParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(adminPageScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
