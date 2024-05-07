package cookbook.frontend.fe_controllers;

import java.util.Arrays;
import java.util.List;

import cookbook.backend.be_objects.HelpMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

private void initializeHelpText() {
    List<HelpMain> helpTopics = Arrays.asList(
        new HelpMain("How to add a new recipe?",
            "To add a new recipe, click on the 'Add Recipe' option from the main menu, which will navigate you to the adding recipe menu. You will then be able to provide all the relevant information for the desired recipe you want to add. When done, click on 'Add Recipe' to save the recipe you added to your cookbook."),
        new HelpMain("How do I send a recipe to another person?",
            "To send a recipe: Click on the recipe you would like to send, then click the Mail icon on the bottom. Then, click on the user you would like to send the recipe to, and fill in an optional message. Once done, click 'Send' and you are finished."),
        new HelpMain("How do I check my messages?",
            "To check your messages: Click the 'Messages' icon. This takes you to your messages menu. Click the user you'd like to see your messages from, which shows your inbox from them."),
        // Add all other help topics similarly
    );
      helpResultField.setText(helpText);
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
