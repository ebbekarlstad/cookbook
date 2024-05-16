package cookbook.frontend.fe_controllers;

import java.io.IOException;
import java.sql.SQLException;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NavigationViewController {

  @FXML
  private Button EditRecipeButton;

  @FXML
  private Button favoriteButton;
  @FXML
  private Button favoriteButton1;
  @FXML
  private Button help_button;
  @FXML
  private Button logoutButton;
  @FXML
  private Button newRecipeButton;
  @FXML
  private Label userDisplayName;

  @FXML
  private void initialize() throws SQLException {
    DatabaseMng dbManager = new DatabaseMng();
    userDisplayName.setTextFill(Color.WHITE);
    userDisplayName.setText("Welcome, " + dbManager.getDisplayName(UserSession.getInstance().getUserId()) + "!");
  }

  @FXML
  void handleBrowseRecipes(ActionEvent event) {
    try {
      // Load the main navigation menu FXML
      Parent mainNavigationMenuParent = FXMLLoader.load(getClass().getResource("/RecipeListView.fxml"));
      Scene mainNavigationMenuScene = new Scene(mainNavigationMenuParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(mainNavigationMenuScene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void handleFavoriteButtonAction(ActionEvent event) {
    try {

      Parent favoriteRecipesParent = FXMLLoader.load(getClass().getResource("/favoriteView.fxml"));
      Scene favoriteRecipesScene = new Scene(favoriteRecipesParent);

      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(favoriteRecipesScene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void handleHelpButton(ActionEvent event) {
    try {
      // Load the navigation page FXML
      Parent helpPageParent = FXMLLoader.load(getClass().getResource("/HelpView.fxml"));
      Scene helpPageScene = new Scene(helpPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(helpPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void handleInbox(ActionEvent event) {
    try {
      // Load the main navigation menu FXML
      Parent mainNavigationMenuParent = FXMLLoader.load(getClass().getResource("/MessagesView.fxml"));
      Scene mainNavigationMenuScene = new Scene(mainNavigationMenuParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(mainNavigationMenuScene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void handleLogoutButton(ActionEvent event) {
    try {
      // Load the login page FXML
      Parent loginPageParent = FXMLLoader.load(getClass().getResource("/LoginView.fxml"));
      Scene loginPageScene = new Scene(loginPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(loginPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void handleNewButton(ActionEvent event) {
    try {
      // Load the navigation page FXML
      Parent newRecipePageParent = FXMLLoader.load(getClass().getResource("/RecipeView.fxml"));
      Scene newRecipePageScene = new Scene(newRecipePageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(newRecipePageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void handleEditRecipeButton(ActionEvent event) {
    try {
      // Load the navigation page FXML
      Parent MyRecipePageParent = FXMLLoader.load(getClass().getResource("/UserEditRecipesView.fxml"));
      Scene MyRecipePageScene = new Scene(MyRecipePageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(MyRecipePageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @FXML
  void handleShoppingListButton(ActionEvent event) {
    try {
      // Load the Shopping List view FXML
      Parent shoppingListPageParent = FXMLLoader.load(getClass().getResource("/ShoppingListView.fxml"));
      Scene shoppingListPageScene = new Scene(shoppingListPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(shoppingListPageScene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void handleWeeklyButtonAction(ActionEvent event) {
    try {
      // Load the Shopping List view FXML
      Parent shoppingListPageParent = FXMLLoader.load(getClass().getResource("/WeeklyDinnerView.fxml"));
      Scene shoppingListPageScene = new Scene(shoppingListPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(shoppingListPageScene);
      window.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
