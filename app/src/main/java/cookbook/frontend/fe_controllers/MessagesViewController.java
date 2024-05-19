package cookbook.frontend.fe_controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.MessageController;
import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_objects.Message;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MessagesViewController {
  private DatabaseMng dbManager;
  private MessageController messageController;
  private Message selectedMessage;

  @FXML
  private TableColumn<Message, String> actionColumn;
  @FXML
  private TableColumn<Message, String> contentColumn;
  @FXML
  private TableView<Message> messageTableView;
  @FXML
  private TableColumn<Message, String> fromColumn;
  @FXML
  private TextArea messageContent;
  @FXML
  private Button backButton;

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
    fromColumn.setCellValueFactory(cellData -> {
      try {
        String senderName = messageController.getName(cellData.getValue().getSenderId());
        return new SimpleStringProperty(senderName);
      } catch (SQLException e) {
        e.printStackTrace();
        return new SimpleStringProperty("Unknown");
      }
    });

    contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));

    fromColumn.setCellFactory(column -> new TableCell<Message, String>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setStyle("");
        } else {
          Message message = getTableView().getItems().get(getIndex());
          setText(item);
          if (!message.isOpened()) {
            setStyle("-fx-font-weight: bold;");
          } else {
            setStyle("");
          }
        }
      }
    });

    contentColumn.setCellFactory(column -> new TableCell<Message, String>() {
      @Override
      protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setStyle("");
        } else {
          Message message = getTableView().getItems().get(getIndex());
          setText(item);
          if (!message.isOpened()) {
            setStyle("-fx-font-weight: bold;");
          } else {
            setStyle("");
          }
        }
      }
    });

    actionColumn.setCellValueFactory(new PropertyValueFactory<>("recipeId"));
    actionColumn.setCellFactory(column -> {
      return new TableCell<Message, String>() {
        private final Hyperlink hyperlink = new Hyperlink("View Recipe");

        {
          hyperlink.setStyle("-fx-text-fill: black;");
          hyperlink.setOnAction(event -> {
            Message msg = getTableView().getItems().get(getIndex());
            if (msg != null) {
              openRecipeDetails(msg.getRecipeId());
            }
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
        selectedMessage = newSelection;
        try {
          markMessageAsOpened(selectedMessage); // Mark the message as read
          messageContent.setText(messageController.getName(selectedMessage.getSenderId()) + "\t\t\t\t"
              + selectedMessage.getSentTime() + "\n" + selectedMessage.getContent());
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    });
  }

  private void openRecipeDetails(String recipeId) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecipeDetails.fxml"));
      Parent detailsView = loader.load();

      RecipeDetailsViewController controller = loader.getController();
      DatabaseMng dbManager = new DatabaseMng();
      RecipeController recipeController = new RecipeController(dbManager);
      Recipe recipe = recipeController.getRecipeById(recipeId);
      controller.initData(recipe);
      controller.setReturnContext("MessagesViewController");

      Scene scene = new Scene(detailsView);
      Stage window = (Stage) messageTableView.getScene().getWindow();
      window.setScene(scene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadMessages() {
    try {
      Long userId = UserSession.getInstance().getUserId();
      List<Message> messages = messageController.getInbox(userId);
      ObservableList<Message> observableMessages = FXCollections.observableArrayList(messages);
      messageTableView.setItems(observableMessages);
    } catch (SQLException e) {
      System.err.println("Failed to load messages: " + e.getMessage());
    }
  }

  @FXML
  private void handleBackButton(ActionEvent event) {
    try {
      String fxmlFile = UserSession.getInstance().isAdmin() ? "/NavigationViewAdmin.fxml" : "/NavigationView.fxml";
      Parent navigationViewParent = FXMLLoader.load(getClass().getResource(fxmlFile));
      Scene navigationViewScene = new Scene(navigationViewParent);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(navigationViewScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void markMessageAsOpened(Message message) {
    try {
      if (messageController.markMessageAsOpened(message.getMessageId())) {
        message.setOpened(true);
        messageTableView.refresh();
      }
    } catch (SQLException e) {
      System.err.println("Failed to mark message as read: " + e.getMessage());
    }
  }
}
