package cookbook.frontend.fe_controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import cookbook.backend.be_objects.Message;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;
import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.MessageController;
import cookbook.backend.be_controllers.RecipeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
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
              // Changed color to black to be more visible
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

  private void openRecipeDetails(String recipeId) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecipeDetails.fxml"));
        Parent detailsView = loader.load();
    
        RecipeDetailsViewController controller = loader.getController();
        DatabaseMng dbManager = new DatabaseMng();  // Ensure this is the right place to instantiate
        RecipeController recipeController = new RecipeController(dbManager);
        Recipe recipe = recipeController.getRecipeById(recipeId);
        controller.initData(recipe);
    
        Scene scene = new Scene(detailsView);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Recipe Details");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
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
