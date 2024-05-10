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
      new HelpMain("How to login to Cookbook",
      "To log in, enter your username and password on the login screen and click the 'Login' button."),
      new HelpMain("How to add a new recipe",
      "Navigate to 'New Recipe'. Enter the 'Recipe Name', and provide a 'Short Description' of the recipe. Under 'Ingredients', enter the name, amount, and select the unit for each ingredient, then click 'Add ingredient' to list it. Fill in the 'Instructions' section with the steps required to prepare the dish. Optionally, add 'Tags' to categorize your recipe, which can be selected from existing tags or by creating new ones. Click 'Add Recipe' to save the recipe to your cookbook."),
      new HelpMain("How to view Shopping List",
                  "To generate a shopping list, first navigate to the 'Shopping List' section from the main menu. This page displays three columns: 'Weeks', 'Dishes', and 'Ingredients'. \n\n" +
                  "1. Select a week from the 'Weeks' column to view the dishes planned for that week.\n" +
                  "2. Click on a dish to view its ingredients in the 'Ingredients' column. This column aggregates the quantities of each ingredient needed for the selected dishes, adjusting for overlapping ingredients across different meals.\n" +
                  "3. Review the list of ingredients to ensure it matches your needs. You can edit the list directly to remove items you already have in your kitchen.\n\n" +
                  "This feature simplifies the process of preparing for your weekly meals by providing a consolidated list of everything you need to purchase."),
      new HelpMain("How to create and Managing Weekly Meal Plans",
                  "The 'Weekly' section allows you to plan your meals by day for each week. Navigate to this section via the 'Weekly' button on the main navigation bar.\n\n" +
                  "1. Select a Week: Use the dropdown menu to select the week for which you want to plan meals. Weeks are displayed in a YYYY-WW format, where WW is the week number.\n" +
                  "2. Add Dishes to Days: The week's view displays a grid with days from Monday to Sunday. To add a dish to a specific day, click on the day, then search and select the dish from your recipes. Each day can hold multiple dishes.\n" +
                  "3. View and Edit Daily Meals: Click on any day to see a list of dishes planned. You can remove dishes or add more as needed.\n" +
                  "4. Link to Recipes: Each dish listed in the daily plan links directly to its recipe, allowing for quick access to cooking instructions and ingredient lists.\n\n" +
                  "This feature simplifies meal planning and helps ensure that you have varied and organized meals throughout the week."),
      new HelpMain("How to share Recipes with Other Users",
                  "The 'Share Recipe' feature allows you to send your favorite recipes to other users of the cookbook. This function fosters a community environment and encourages sharing of culinary ideas and experiences.\n\n" +
                  "1. Access the Recipe: Navigate to the recipe you wish to share. You can do this by browsing recipes or by viewing your list of favorite recipes.\n" +
                  "2. Share the Recipe: Click on the 'Share Recipe' button located at the top of the recipe page. A popup will appear.\n" +
                  "3. Select a Recipient: In the popup, select the user you want to share the recipe with from a dropdown list of users. You can only send recipes to registered users.\n" +
                  "4. Add a Personal Message: Optionally, you can add a personal message to the recipient explaining why you think they might like the recipe or any specific tips you have regarding the preparation.\n" +
                  "5. Send the Recipe: After selecting the recipient and adding your message, click on 'Send'. The recipient will receive a notification in their 'My Inbox' section with your message and a link to the recipe.\n\n" +
                  "This feature makes it easy to share your culinary discoveries and personal favorites with friends and family who also use the app."),
      new HelpMain("How to Browse Recipes",
                  "The 'Browse Recipes' feature allows you to view and search through all the recipes available in the Cookbook. From the main menu, click on 'Browse Recipes' to access the recipe list.\n\n" +
                  "1. View Recipes: All recipes are listed alphabetically. Scroll through the list to see all the available recipes.\n" +
                  "2. Search by Name: Enter a recipe name or a part of it in the 'Search by name' field to quickly find specific recipes.\n" +
                  "3. Search by Ingredients: Use the 'Search by ingredients' field to find recipes based on the ingredients they use. This is useful if you have specific ingredients at hand and want to know what you can cook.\n" +
                  "4. Search by Tags: The 'Search by tags' option allows you to filter recipes based on tags like 'Vegetarian', 'Gluten-Free', etc. This helps you find recipes that meet specific dietary requirements.\n\n" +
                  "Each recipe entry in the list can be clicked to open up the full recipe view where you can see detailed instructions, ingredients, and options to edit, delete, or add the recipe to your favorites."),
      new HelpMain("How to View and Managing Favorite Recipes",
                  "The 'Favorite Recipes' section allows you to easily access and manage the recipes you love most. Here's how to use this feature:\n\n" +
                  "1. Accessing Favorite Recipes: From the main navigation, select 'Favorite' to open your list of favorite recipes. If no recipes are displayed, it means you haven't added any favorites yet.\n" +
                  "2. Adding Favorites: To add a recipe to your favorites, navigate to the recipe detail page and click 'Add to favorite'. The recipe will then appear in your 'Favorite Recipes' list.\n" +
                  "3. Removing Favorites: If you wish to remove a recipe from your favorites, open the recipe detail from the 'Favorite Recipes' list and click 'Remove from favorite'.\n" +
                  "4. Navigating Recipes: Each entry in the favorite list provides easy access to the full recipe details, ensuring you can quickly find and prepare your favorite dishes.\n\n" +
                  "Utilize the 'Go back' button to return to the main menu or navigate to other sections. This feature is designed to make your cooking experience more personalized and enjoyable by keeping your best-loved recipes at your fingertips."),
              
      new HelpMain("How to Adjust the Number of Persons for Recipes",
                  "This feature allows you to scale the ingredients of a recipe based on the number of people you are preparing the meal for.\n\n" +
                  "1. Accessing the Feature: Navigate to any recipe and locate the 'Adjust number of people' section, typically found below the ingredients list.\n" +
                  "2. Adjusting Servings: Use the '+' and '-' buttons to increase or decrease the number of servings. The ingredient amounts will automatically update to match the new serving size.\n" +
                  "3. Viewing Adjusted Quantities: After adjusting the servings, the amounts specified for each ingredient in the recipe will reflect the changes, ensuring accurate preparation for the desired number of people.\n\n" +
                  "This tool is designed to help you modify recipes according to your needs, making it easier to cook for varying group sizes without having to manually calculate ingredient proportions."),
              
      new HelpMain("How to Add and Managing Comments on Recipes",
                  "The comments section in each recipe allows users to share their experiences, tips, or modifications related to the recipe.\n\n" +
                  "1. Adding a Comment: To add a comment, type your message in the 'Write a comment' box and click 'Add'. Your comment will then appear in the 'Comments' area on the right side of the recipe page.\n" +
                  "2. Editing and Removing Comments: If you need to edit or remove a comment you've posted, use the 'Edit' or 'Remove' buttons associated with your comment. Make the necessary changes in the comment text box and click 'Update' to save edits or 'Remove' to delete the comment.\n" +
                  "3. Interacting with Comments: Scroll through the comments section to read insights from other users. This can provide valuable information and different perspectives on the recipe.\n\n" +
                  "Use the comments feature to engage with the community, share your culinary successes or challenges, and gain insights from other home cooks.")
              
          
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
          int topicNumber = listTutorials.getSelectionModel().getSelectedIndex() + 1;  // This gets the correct numbering
          searchByNameField.setText(selected);
          String keyword = topicNumber + "- " + selected;
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
      String keyword = searchByNameField.getText().trim().toLowerCase();
      if (!keyword.isEmpty()) {
          StringBuilder content = new StringBuilder(helpResultField.getText().toLowerCase());
          int topicNumber = 1;
          boolean found = false;
  
          for (HelpMain topic : helpTopics) {
              String topicIdentifier = topicNumber + "- " + topic.getTitle().toLowerCase() + "\n\n" + topic.getDescription().toLowerCase() + "\n\n";
              if (topicIdentifier.contains(keyword)) {
                  int startIndex = content.indexOf(keyword);
                  int endIndex = content.indexOf("\n\n", startIndex + keyword.length());
                  if (startIndex != -1) {
                      helpResultField.selectRange(startIndex, endIndex); // Highlight the text where keyword is found
                      helpResultField.requestFocus();
                      found = true;
                      break; // Break after the first match is found
                  }
              }
              topicNumber++;
          }
  
          // If no topic contains the keyword, do nothing
          if (!found) {
              helpResultField.deselect(); // Deselect any previous selection
          }
      } else {
          
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
