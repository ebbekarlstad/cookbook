package cookbook.frontend.fe_controllers;

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
      String helpText = 
          "## **Welcome to the Digital Cookbook Help System**\n\n" +
          "This guide is designed to assist you in navigating and using the Digital Cookbook application efficiently. Below you will find detailed instructions on each feature available in the application.\n\n" +
          "## **Application Startup**\n\n" +
          "**1. Launching the Application:**\n" +
          "   - Start the application to see a welcoming animation.\n\n" +
          "**2. Logging In:**\n" +
          "   - The login screen appears post-animation. Enter your credentials or contact admin to create an account.\n\n" +
          "## **Navigating the Interface**\n\n" +
          "**3. Main Screen Overview:**\n" +
          "   - The main screen shows favorite recipes, weekly meal plans, and navigation options.\n\n" +
          "**4. Searching for Recipes:**\n" +
          "   - Use the search bar for recipes by name, ingredient, or tags. View results by clicking or hovering over entries.\n\n" +
          "**5. Adding and Managing Recipes:**\n" +
          "   - Add new recipes via 'Add Recipe', edit or delete from 'My Recipes'.\n\n" +
          "## **Planning and Shopping**\n\n" +
          "**6. Creating Weekly Meal Plans:**\n" +
          "   - Plan your weekly meals by adding recipes to specific days under 'Weekly Plans'.\n\n" +
          "**7. Generating Shopping Lists:**\n" +
          "   - Generate shopping lists based on the week’s meal plans.\n\n" +
          "## **Advanced Features**\n\n" +
          "**8. Sending Recipes to Other Users:**\n" +
          "   - Send recipes with messages using 'Send Recipe'.\n\n" +
          "**9. Accessing Help and Support:**\n" +
          "   - For more assistance, use the 'Help' menu for tutorials and instructions.\n\n" +
          "## **Searching Help Content**\n\n" +
          "   - **Keyword Search:**\n" +
          "       - Type a keyword in the search box to find relevant help content.\n\n" +
          "## **Logout and Security**\n\n" +
          "   - Log out after use to secure your data.\n\n" +
          "## **Contact Admin**\n\n" +
          "   - Contact the admin for issues or additional permissions through 'Contact' in the main menu.\n";
      
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

