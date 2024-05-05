package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import cookbook.backend.be_objects.Message;
import cookbook.backend.be_objects.UserSession;
import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.MessageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.List;


public class MessagesViewController {
  private DatabaseMng dbManager;
  private MessageController messageController;

  @FXML
  private TableColumn<Message, String> actionColumn;

  @FXML
  private TableView<Message> messageTableView;
  @FXML
  private TableColumn<Message, String> fromColumn;
  @FXML
  private TextArea messageContent;

  public MessagesViewController() {
    this.dbManager = new DatabaseMng();
  }

  @FXML
  private void initialize() {
    messageController = new MessageController(dbManager);
    setupMessageTable();
    loadMessages();
  }

private void setupMessageTable() {
    fromColumn.setCellValueFactory(new PropertyValueFactory<>("senderId")); // Assumes that senderId will be converted to a name or identifier

    // Setup for the action column
    actionColumn.setCellValueFactory(new PropertyValueFactory<>("recipeId"));
    actionColumn.setCellFactory(column -> {
      return new TableCell<Message, String>() {
          private final Hyperlink hyperlink = new Hyperlink("View Recipe");

          {
              hyperlink.setOnAction(event -> {
                  Message msg = getTableView().getItems().get(getIndex());
                  openRecipeDetails(msg.getRecipeId());
              });
          }

          @Override
          protected void updateItem(String item, boolean empty) {
              super.updateItem(item, empty);
              if (empty) {
                  setGraphic(null);
              } else {
                  setGraphic(hyperlink);
              }
          }
      };
  });

    messageTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            try {
                messageContent.setText(messageController.getName(newSelection.getSenderId()) + "\t\t\t\t" + newSelection.getSentTime() 
                +"\n" + newSelection.getContent()
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    });
}

  protected void openRecipeDetails(String recipeId) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'openRecipeDetails'");
}

  private void loadMessages() {
    try {
      int userId = UserSession.getInstance().getUserId();
      List<Message> messages = messageController.getInbox(userId);
      ObservableList<Message> observableMessages = FXCollections.observableArrayList(messages);
      messageTableView.setItems(observableMessages);
    } catch (SQLException e) {
      System.err.println("Failed to load messages: " + e.getMessage());
    }
  }

}
