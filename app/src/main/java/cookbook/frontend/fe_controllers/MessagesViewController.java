package cookbook.frontend.fe_controllers;

import java.util.*;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.MessageController;
import cookbook.backend.be_objects.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import java.sql.SQLException;

public class MessagesViewController {
    private DatabaseMng dbManager;
    private MessageController messageController;

    public MessagesViewController() {
      this.dbManager = new DatabaseMng();
    }

    @FXML
    private ListView<Message> messageListView;

    @FXML
    private void initialize() {
        messageController = new MessageController(dbManager);

        setupMessageListView();
        loadMessages();
    }

    private void loadMessages() {
        try {
            int userId = 2;  // Change this to be dynamic later (and to be a String)
            List<Message> messages = messageController.getInbox(userId); // Create the list
            ObservableList<Message> observableMessages = FXCollections.observableArrayList(messages);
            messageListView.setItems(observableMessages);
        
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to load messages: " + e.getMessage());  // Debug output
        }
    }

    private void setupMessageListView() {
    messageListView.setCellFactory(listView -> new ListCell<Message>() {
        @Override
        protected void updateItem(Message message, boolean empty) {
            super.updateItem(message, empty);
            if (empty || message == null) {
                setText(null);
            } else {
                // Custom display format: could include message content, sender ID, etc.
                setText(message.getContent() + " - From: " + message.getSenderId());
            }
        }
    });
}
}