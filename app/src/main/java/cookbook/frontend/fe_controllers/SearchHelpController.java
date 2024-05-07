package cookbook.frontend.fe_controllers;

import java.util.Arrays;
import java.util.List;

import cookbook.backend.be_objects.HelpMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SearchHelpController {

  @FXML
  private TextField searchByNameField;
  @FXML
  private TextArea helpResultField;
  @FXML
  private ListView<String> listTutorials;
  @FXML
  private Button back;

  private List<HelpMain> helpTopics;

  @FXML
  private void initialize() {
    initializeHelpText();
    loadListView();
  }

  private void initializeHelpText() {
    helpTopics = Arrays.asList(
        new HelpMain("How to add a new recipe?",
            "To add a new recipe, click on the 'Add Recipe' option from the main menu..."),
        new HelpMain("How do I send a recipe to another person?",
            "To send a recipe: Click on the recipe you would like to send, then click the Mail icon..."),
        new HelpMain("How do I check my messages?",
            "To check your messages: Click the 'Messages' icon...")
        // Add other topics similarly
    );

    StringBuilder helpTextBuilder = new StringBuilder();
    helpTextBuilder.append("Welcome to the Digital Cookbook Help System\n\n");
    int topicNumber = 1;
    for (HelpMain topic : helpTopics) {
        helpTextBuilder.append(topicNumber).append("- ").append(topic.getTitle()).append("\n\n")
                       .append(topic.getDescription()).append("\n\n");
        topicNumber++;
    }
    helpResultField.setText(helpTextBuilder.toString());
}


  private void loadListView() {
    ObservableList<String> titles = FXCollections.observableArrayList();
    for (HelpMain topic : helpTopics) {
        titles.add(topic.getTitle());
    }
    listTutorials.setItems(titles);
  }

  @FXML
  private void tutorialSelected(MouseEvent event) {
      String selected = listTutorials.getSelectionModel().getSelectedItem();
      if (selected != null) {
          searchByNameField.setText(selected);
          int index = listTutorials.getSelectionModel().getSelectedIndex() + 1;  // This gets the correct numbering
          String keyword = index + "- " + selected;
          String content = helpResultField.getText().toLowerCase();
          int startIndex = content.indexOf(keyword.toLowerCase());
          if (startIndex != -1) {
              int endIndex = content.indexOf("\n\n", startIndex + keyword.length());
              helpResultField.selectRange(startIndex, endIndex);
              helpResultField.requestFocus();
          }
      }
  }
  

  
  @FXML
  private void searchHelp() {
      String keyword = searchByNameField.getText().trim();
      int topicNumber = listTutorials.getItems().indexOf(keyword) + 1;  // Calculate the number based on position in list
      if (topicNumber > 0) {
          String content = helpResultField.getText().toLowerCase();
          String searchKeyword = topicNumber + "- " + keyword.toLowerCase(); // Form the search keyword with the number
          int startIndex = content.indexOf(searchKeyword);
          if (startIndex != -1) {
              int endIndex = content.indexOf("\n\n", startIndex + searchKeyword.length());
              helpResultField.selectRange(startIndex, endIndex);
              helpResultField.requestFocus();
          }
      }
  }
  
  

  @FXML
  private void handleHelpBackButton(ActionEvent event) {
    try {
      Parent helpPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
      Scene helpPageScene = new Scene(helpPageParent);
      Stage window = (Stage) back.getScene().getWindow();
      window.setScene(helpPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
