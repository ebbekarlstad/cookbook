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

  private Long senderId;
  private Long receiverId;
  private String recipeId;
  private String content;

  Recipe recipe;

  public void initData(Recipe recipe) {
    this.recipe = recipe;
    this.recipeId = recipe.getId(); // Store the recipe ID
  }


  @FXML
  public void sendShare(ActionEvent event) {
    User recipient = recipientComboBox.getValue();
    if (recipient != null) {
      Message message = new Message(this.senderId, this.receiverId, this.recipeId, this.content);
      
      message.setMessageId((long) 1);
      message.setSenderId((long) 1);
      message.setReceiverId((long) 2);
      message.setRecipeId(getRecipeId());
      message.setContent(getContent());
      message.setSentTime(new java.sql.Timestamp(new java.util.Date().getTime()));

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
