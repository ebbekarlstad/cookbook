package cookbook.frontend.fe_controllers;

import cookbook.backend.be_controllers.ModifyUserController;
import cookbook.backend.be_controllers.UserController;
import cookbook.backend.be_objects.User;
import cookbook.backend.be_objects.LogIn;
import cookbook.backend.DatabaseMng;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class ModifyUserViewController {
    
    // All the fields
    @FXML
    private TextField newPasswordField;
    
    @FXML
    private ComboBox<User> userComboBox;
    
    @FXML
    private TextField newUsernameField;

    @FXML
    private TextField newDisplayNameField;

    @FXML
    private ProgressIndicator progressCircle;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private Button submitButton;
    
    // Reference the UserController and the Database
    private UserController userController;
    private DatabaseMng dbManager;
    private ModifyUserController modifyUserController;
    private LogIn login;
    
    public ModifyUserViewController() throws SQLException {
        this.dbManager = new DatabaseMng();
        this.userController = new UserController(dbManager);
        this.modifyUserController = new ModifyUserController(dbManager);
        this.login = new LogIn(dbManager);
    }
    
    // Method to load all users to the dropdown combobox.
    private void loadUsers() throws SQLException {
        List<User> users = userController.getAllUsers();
        userComboBox.setItems(FXCollections.observableArrayList(users));
        userComboBox.setConverter(new StringConverter<User>() {
            @Override
            public java.lang.String toString(User user) {
                return (user != null) ? user.getUserName() : "Select user";
            }
    
            @Override
            public User fromString(String string) {
                return null;
            }
        });
    }
    
    @FXML
    private void submitUserChanges(ActionEvent event) {
        // Error label and progress bar
        errorLabel.setText("");
        progressCircle.setVisible(true);
        
        // Get the input from the admin.
        String username = newUsernameField.getText();
        String displayname = newDisplayNameField.getText();
        String password = newPasswordField.getText();
        User selectedUser = userComboBox.getValue();

        String hashedPassword = login.hashPassword(password);
        
        // Boolean that initializes a new Task object
        if (selectedUser != null) {
            Task<Boolean> modifyTask = new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    return modifyUserController.modifyUser(selectedUser.getUserId(), username, displayname, hashedPassword);
                }
            };
            
            // If task succeeds
            modifyTask.setOnSucceeded(e -> {
                progressCircle.setVisible(false);
                if (modifyTask.getValue()) {
                    errorLabel.setTextFill(Color.GREEN);
                    errorLabel.setText("Modification Successful!");
                } else {
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Modification Failed.");
                }
            });

            // If task fails
            modifyTask.setOnFailed(e -> {
                progressCircle.setVisible(false);
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Error during modification.");
            });

            new Thread(modifyTask).start();
        } else {
            progressCircle.setVisible(false);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("No user selected.");
        }
    }
    
    @FXML
    public void initialize() throws SQLException {
        try {
            loadUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}