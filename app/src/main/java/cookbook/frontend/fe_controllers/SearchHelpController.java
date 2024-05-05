package cookbook.frontend.fe_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class SearchHelpController {

  @FXML
  private TextField searchByNameField;

  @FXML
  private TextArea helpResultField;
  @FXML
  private void searchHelp() {
      String keyword = searchByNameField.getText().trim();
      if (keyword.isEmpty()) {
          helpResultField.setText("Please enter a keyword to search.");
      } else {
          int startIndex = helpResultField.getText().toLowerCase().indexOf(keyword.toLowerCase());
          if (startIndex == -1) {
              helpResultField.setText("No help found for '" + keyword + "'. Try different keywords.");
          } else {
              helpResultField.selectRange(startIndex, startIndex + keyword.length());
              helpResultField.requestFocus();  // Optional: Brings the focus to the TextArea to highlight the text.
          }
      }
  }
  

  private String findHelpContent(String keyword) {
    // Simulated response
    switch (keyword.toLowerCase()) {
      case "add recipe":
        return "To add a recipe, go to the 'Add Recipe' section, fill in the details and click 'Save'.";
      default:
        return "No help found for '" + keyword + "'. Try different keywords.";
    }
  }

@FXML
private void handleHelpBackButton(ActionEvent event) {
    try {
        // Get the button that was clicked
        Button sourceButton = (Button) event.getSource();
        
        // Load the navigation page FXML
        Parent helpPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
        Scene helpPageScene = new Scene(helpPageParent);

        // Get the current stage using the scene from the button
        Stage window = (Stage) sourceButton.getScene().getWindow();
        window.setScene(helpPageScene);
        window.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
