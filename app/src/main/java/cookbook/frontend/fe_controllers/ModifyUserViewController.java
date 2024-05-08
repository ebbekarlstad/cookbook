package cookbook.frontend.fe_controllers;

import cookbook.backend.be_controllers.UserController;
import cookbook.backend.be_objects.User;
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
    
    public ModifyUserViewController() throws SQLException {
        this.dbManager = new DatabaseMng();
        this.userController = new UserController(dbManager);
    }
    
    // Method to load all users to the dropdown combobox.
    private void loadUsers() throws SQLException {
        List<User> users = userController.getAllUsers();
        userComboBox.setItems(FXCollections.observableArrayList(users));
        userComboBox.setConverter(new StringConverter<User>() {
            @Override
            public java.lang.String toString(User user) {
                return user.getUserName(); 
            }
            
            @Override
            public User fromString(String string) {
                return null;
            }
        });
    }
    
    @FXML
    private void modifyUser(ActionEvent event) {
        // Empty the errorlabel
        errorLabel.setText("");
        
        // Get the input from the admin.
        String username = newUsernameField.getText();
        String displayname = newDisplayNameField.getText();
        String password = newPasswordField.getText();
        
        // Show progress indicator
        progressCircle.setVisible(true);
        
        // Boolean that initializes a new Task object
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
    
    @FXML
    public void initialize() throws SQLException {
        loadUsers();
    }
}