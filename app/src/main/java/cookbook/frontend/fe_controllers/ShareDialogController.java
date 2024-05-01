package cookbook.frontend.fe_controllers;

import java.util.List;

import java.lang.String;
import cookbook.backend.be_controllers.UserController;
import cookbook.backend.be_objects.User;
import javafx.collections.FXCollections;
import javafx.util.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import cookbook.backend.DatabaseMng;

public class ShareDialogController {
  
  private DatabaseMng dbManager;

  public ShareDialogController() {
    this.dbManager = new DatabaseMng();
  }

  @FXML
  private ComboBox<User> recipientComboBox; 

  @FXML
  public void sendShare(ActionEvent event) {
    // Method implementation
  }

  @FXML
  public void initialize() {
    loadUsers();
  }

  // Method to load all users to dropdown combobox.
  private void loadUsers() {
    UserController userController = new UserController(dbManager);
    List<User> users = userController.getAllUsers();
    recipientComboBox.setItems(FXCollections.observableArrayList(users));
    recipientComboBox.setConverter(new StringConverter<User>() {
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
}
