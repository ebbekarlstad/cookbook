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
import javafx.stage.Stage;

public class SearchHelpController {

  @FXML
  private TextField searchByNameField;
  @FXML
  private TextArea helpResultField;
  @FXML
  private Button back;

  @FXML
  private void initialize() {
    initializeHelpText();
  }

@FXML
private ListView<String> listTutorials;  // Update this line to use generic type String

private void initializeHelpText() {
    List<HelpMain> helpTopics = Arrays.asList(
        new HelpMain("How to add a new recipe?", "..."),
        new HelpMain("How do I send a recipe to another person?", "..."),
        new HelpMain("How do I check my messages?", "...")
    );

    ObservableList<String> titles = FXCollections.observableArrayList();
    for (HelpMain topic : helpTopics) {
        titles.add(topic.getTitle());
    }
    listTutorials.setItems(titles);  // Set titles to the ListView

    StringBuilder helpTextBuilder = new StringBuilder("## **Welcome to the Digital Cookbook Help System**\n\n");
    for (HelpMain topic : helpTopics) {
        helpTextBuilder.append("## **").append(topic.getTitle()).append("**\n\n").append(topic.getDescription()).append("\n\n");
    }
    helpResultField.setText(helpTextBuilder.toString());
}




  @FXML
  private void searchHelp() {
    String keyword = searchByNameField.getText().trim().toLowerCase();
    String content = helpResultField.getText().toLowerCase();
    int startIndex = content.indexOf(keyword);
    if (startIndex != -1) {
      helpResultField.selectRange(startIndex, startIndex + keyword.length());
      helpResultField.requestFocus();
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
