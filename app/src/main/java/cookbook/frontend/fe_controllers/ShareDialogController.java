package cookbook.frontend.fe_controllers;

import java.util.List;

import java.lang.String;
import java.sql.SQLException;

import cookbook.backend.be_controllers.UserController;
import cookbook.backend.be_objects.User;
import cookbook.backend.be_objects.UserSession;
import javafx.collections.FXCollections;
import javafx.util.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import cookbook.backend.DatabaseMng;

import cookbook.backend.be_objects.Message;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_controllers.MessageController;

public class ShareDialogController {
  
  private DatabaseMng dbManager;

  public ShareDialogController() {
    this.dbManager = new DatabaseMng();
  }

  @FXML
  private ComboBox<User> recipientComboBox;
  
  @FXML
  private TextField messageField;

  private String recipeId;

  Recipe recipe;

  public void initData(Recipe recipe) {
    this.recipe = recipe;
    this.recipeId = recipe.getId(); // Store the recipe ID
  }


  @FXML
  public void sendShare(ActionEvent event) {
      User recipient = recipientComboBox.getValue();
      if (recipient != null) {
          
          long senderId = UserSession.getInstance().getUserId();
          long receiverId = recipient.getUserId();
          String content = getContent();
          String recipeId = getRecipeId();

          // Create the message with dynamic sender and receiver IDs
          Message message = new Message(senderId, receiverId, recipeId, content);
          message.setSentTime(new java.sql.Timestamp(new java.util.Date().getTime()));

          // Attempt to save the message using the MessageController
          MessageController messageManager = new MessageController(dbManager);
          boolean result = messageManager.saveMessage(message);
          if (result) {
              System.out.println("Message sent successfully!");
          } else {
              System.out.println("Failed to send message.");
          }
      } else {
          System.out.println("No recipient selected.");
      }
  }

  private String getContent() {
    return messageField.getText();
  }

  private String getRecipeId() {
    return recipeId;
  }

  @FXML
  public void initialize() throws SQLException {
    loadUsers();
  }

  // Method to load all users to dropdown combobox.
  private void loadUsers() throws SQLException {
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
