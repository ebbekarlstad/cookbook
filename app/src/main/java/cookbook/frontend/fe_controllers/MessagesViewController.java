package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import cookbook.backend.be_objects.Message;
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

    messageTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        messageContent.setText(newSelection.getContent());
      }
    });
  }

  private void loadMessages() {
    try {
      int userId = 2; // This should be dynamic
      List<Message> messages = messageController.getInbox(userId);
      ObservableList<Message> observableMessages = FXCollections.observableArrayList(messages);
      messageTableView.setItems(observableMessages);
    } catch (SQLException e) {
      System.err.println("Failed to load messages: " + e.getMessage());
    }
  }
}
