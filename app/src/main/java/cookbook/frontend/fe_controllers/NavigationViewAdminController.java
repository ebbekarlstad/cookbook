package cookbook.frontend.fe_controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
/* import cookbook.backend.DatabaseMng;
import java.sql.Connection; */


public class NavigationViewAdminController  {
  @FXML
  private Label mysqlStatus;

  @FXML
  private Text welcomeText;

/*   @FXML
  private void initialize() {
    // Attempt to get a database connection
    Connection conn = DatabaseMng.getConnection();

    if (conn != null) {
            mysqlStatus.setText("Driver found and connected");
        } else {
            mysqlStatus.setText("An error has occurred: " + DatabaseMng.getLastErrorMessage());
        }
  } */

  @FXML
  private void initialize() {
    // Fade animation
    FadeTransition fade = new FadeTransition(Duration.seconds(1.5), welcomeText);
    fade.setFromValue(0.0);
    fade.setToValue(1.0);
    fade.setCycleCount(FadeTransition.INDEFINITE); // Repeat indefinitely
    fade.setAutoReverse(true); // Reverse the animation on each iteration
    fade.play();

    // Scale animation
    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), welcomeText);
    scaleTransition.setFromX(0.5);
    scaleTransition.setFromY(0.5);
    scaleTransition.setToX(1.0);
    scaleTransition.setToY(1.0);
    scaleTransition.setCycleCount(ScaleTransition.INDEFINITE); // Repeat indefinitely
    scaleTransition.setAutoReverse(true); // Reverse the animation on each iteration
    scaleTransition.play();
}

  @FXML
  private void handleLoginButton(ActionEvent event) {
    try {
        // Load the login page FXML
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("/LoginView.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        // Create a rectangle to cover the scene
        Rectangle colorOverlay = new Rectangle(934, 703);
        colorOverlay.setFill(Color.TRANSPARENT);
        
        Group rootWithOverlay = new Group(loginPageParent, colorOverlay);
        Scene sceneWithOverlay = new Scene(rootWithOverlay, 934, 703);

        // Prepare the fade transitions
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.8), colorOverlay);
        fadeOut.setFromValue(0.0);
        fadeOut.setToValue(0.8);
        fadeOut.setInterpolator(Interpolator.EASE_BOTH);

        FillTransition fillTransition = new FillTransition(Duration.seconds(0.5), colorOverlay, Color.TRANSPARENT, Color.BLACK);
        fillTransition.setOnFinished(event1 -> {
            window.setScene(sceneWithOverlay);
            
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.8), colorOverlay);
            fadeIn.setFromValue(0.8);
            fadeIn.setToValue(0.0);
            fadeIn.setInterpolator(Interpolator.EASE_BOTH);
            fadeIn.setOnFinished(event2 -> colorOverlay.setFill(Color.TRANSPARENT)); // Reset color after transition
            fadeIn.play();
        });

        // Start transitions
        new SequentialTransition(fadeOut, fillTransition).play();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

  @FXML
  private void handleLogoutButton(ActionEvent event) {
    try {
      //Load the login page FXML
      Parent loginPageParent = FXMLLoader.load(getClass().getResource("/LoginView.fxml"));
      Scene loginPageScene = new Scene(loginPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
      window.setScene(loginPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleNavigationButton(ActionEvent event) {
    try {
      //Load the navigation page FXML
      Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
      Scene navigationPageScene = new Scene(navigationPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
      window.setScene(navigationPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleHelpButton(ActionEvent event) {
    try {
      //Load the navigation page FXML
      Parent helpPageParent = FXMLLoader.load(getClass().getResource("/HelpView.fxml"));
      Scene helpPageScene = new Scene(helpPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
      window.setScene(helpPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  @FXML
  private void handleNewButton(ActionEvent event) {
    try {
      //Load the navigation page FXML
      Parent newRecipePageParent = FXMLLoader.load(getClass().getResource("/RecipeView.fxml"));
      Scene newRecipePageScene = new Scene(newRecipePageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
      window.setScene(newRecipePageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @FXML
  void handleUserCreatedRecipesButtonAction(ActionEvent event) {
    try {
      //Load the navigation page FXML
      Parent MyRecipePageParent = FXMLLoader.load(getClass().getResource("/UserRecipesView.fxml"));
      Scene MyRecipePageScene = new Scene(MyRecipePageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
      window.setScene(MyRecipePageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @FXML
  private void handleBrowseRecipes(ActionEvent event) {
      try {
          // Load the main navigation menu FXML
          Parent mainNavigationMenuParent = FXMLLoader.load(getClass().getResource("/RecipeListView.fxml"));
          Scene mainNavigationMenuScene = new Scene(mainNavigationMenuParent);
  
          // Get the current stage and replace it
          Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
          window.setScene(mainNavigationMenuScene);
          window.show();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  @FXML
  private void handleInbox(ActionEvent event) {
      try {
          // Load the main navigation menu FXML
          Parent mainNavigationMenuParent = FXMLLoader.load(getClass().getResource("/MessagesView.fxml"));
          Scene mainNavigationMenuScene = new Scene(mainNavigationMenuParent);
  
          // Get the current stage and replace it
          Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
          window.setScene(mainNavigationMenuScene);
          window.show();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  @FXML
    private void handleFavoriteButtonAction(ActionEvent event) {
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
private void handleShoppingListButton(ActionEvent event) {
    try {
        // Load the Shopping List view FXML
        Parent shoppingListPageParent = FXMLLoader.load(getClass().getResource("/ShoppingListView.fxml"));
        Scene shoppingListPageScene = new Scene(shoppingListPageParent);

        // Get the current stage and replace it
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(shoppingListPageScene);
        window.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

@FXML
private void handleWeeklyButtonAction(ActionEvent event) {
    try {
        // Load the Shopping List view FXML
        Parent shoppingListPageParent = FXMLLoader.load(getClass().getResource("/WeeklyDinnerView.fxml"));
        Scene shoppingListPageScene = new Scene(shoppingListPageParent);

        // Get the current stage and replace it
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(shoppingListPageScene);
        window.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}



  
}