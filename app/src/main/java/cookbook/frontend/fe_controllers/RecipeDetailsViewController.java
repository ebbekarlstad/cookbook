package cookbook.frontend.fe_controllers;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.CommentController;
import cookbook.backend.be_controllers.FavoritesController;
import cookbook.backend.be_objects.AmountOfIngredients;
import cookbook.backend.be_objects.CommentObject;
import cookbook.backend.be_objects.Recipe;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class RecipeDetailsViewController {

    @FXML
    private TableView<AmountOfIngredients> ingredientTable;
    @FXML
    private TableColumn<AmountOfIngredients, String> amountColumn;
    @FXML
    private TableColumn<AmountOfIngredients, String> ingredientColumn;

    private String recipeId;
    private Long userId = 1L;
    private int commentId;
    Recipe recipe;
    
    @FXML
    private Label titleLabel; // Label for the recipe title.
    @FXML
    private Label shortLabel; // Label for the short description.
    @FXML
    private Label longLabel; // Label for the detailed description.

    @FXML
    private Button shareRecipeButton;
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    DatabaseMng myDbManager;
    
    private FavoritesController favoritesController = new FavoritesController(myDbManager); 
    
    public void initData(Recipe recipe) {
        this.recipe = recipe;
        // Set the recipe information
        titleLabel.setText(recipe.getRecipeName());
        shortLabel.setText(recipe.getShortDesc());
        longLabel.setText(recipe.getDetailedDesc());

        this.recipeId = recipe.getId();
        loadComments();
        // Populate the TableView with ingredients
        List<AmountOfIngredients> ingredientsList = recipe.getIngredientsList();
        if (ingredientsList != null) {
            ingredientTable.getItems().addAll(ingredientsList);
        }

        // Configure the columns
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        ingredientColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

    private CommentController commentController;

    // Constructor
    public RecipeDetailsViewController() {
        myDbManager = new DatabaseMng();
        this.commentController = new CommentController(myDbManager);  
        favoritesController = new FavoritesController(myDbManager); 
    }
    
    private void loadComments() {
        List<String> comments = commentController.fetchComments(this.recipeId);
        commentsListView.getItems().setAll(comments); // Clears existing items and adds all fetched comments
    }

    @FXML
    private ListView<String> commentsListView;  // List to display comments

    @FXML
    private TextField commentInput;  // Input field for comments

    private String currentCommentText;

    @FXML
    private void addComment(ActionEvent event) {
        String commentText = commentInput.getText().trim();  // Get text from TextField
        if (!commentText.isEmpty()) {
        
            CommentObject newComment = new CommentObject(this.commentId, this.recipeId, 1, commentText, "yy-mm-dd hh:mm:ss"); // Adjusted constructor
            commentsListView.getItems().add(commentText);  // Add comment to ListView
            commentInput.clear();  // Clear the input field

            if (!commentController.addComment(newComment)) {
                System.out.println("Failed to add comment.");
            }
        }
    }

    @FXML
    private void removeComment(ActionEvent event) {
        int selectedIndex = commentsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedComment = commentsListView.getItems().get(selectedIndex);

            if (commentController.removeComment(selectedComment)) {
                commentsListView.getItems().remove(selectedIndex);  // Remove the comment from the ListView
                System.out.println("Comment removed successfully.");
            } else {
                System.out.println("Failed to remove comment.");
            }
        } else {
            System.out.println("No comment selected.");
        }
    }

    @FXML
    private void editComment(ActionEvent event) {
        int selectedIndex = commentsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            currentCommentText = commentsListView.getItems().get(selectedIndex);
            commentInput.setText(currentCommentText); // Set the text of the selected comment into the text field for editing
        } else {
            System.out.println("No comment selected.");
        }
    }

    @FXML
    public void updateComment(ActionEvent event) {
        String newCommentText = commentInput.getText().trim();
        if (!newCommentText.isEmpty() && !newCommentText.equals(currentCommentText)) {
            if (commentController.updateComment(currentCommentText, newCommentText)) {
                int selectedIndex = commentsListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    commentsListView.getItems().set(selectedIndex, newCommentText); // Update the comment in the ListView
                    commentInput.clear(); // Clear the text field after updating
                    System.out.println("Comment updated successfully.");
                }
            } else {
                System.out.println("Failed to update comment.");
            }
        } else {
            System.out.println("No changes made to the comment.");
        }
    }

    public void handleHelpBackButton(ActionEvent event){
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
		
		// When user clicks share recipe.
		public void shareRecipe(ActionEvent event) {
            try {
                // Load the ShareDialog FXML and get the controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShareDialog.fxml"));
                Parent sharePageParent = loader.load();
                ShareDialogController shareDialogController = loader.getController();
        
                // Pass the recipe data to the ShareDialogController
                shareDialogController.initData(this.recipe);
        
                // Create a new stage (window)
                Stage shareStage = new Stage();
                shareStage.setTitle("Share Recipe");
                shareStage.setScene(new Scene(sharePageParent));
                shareStage.initModality(Modality.APPLICATION_MODAL); // This will make it so user cant interact with old window
                shareStage.show(); // Showw the new stage
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

      @FXML
      public void addToFavorites(ActionEvent event) {
        //   try {
        //       // Skriv ut userID och recipeID för att verifiera att de inte är null
        //       System.out.println("UserId: " + (this.userId != null ? this.userId: "null"));
        //       System.out.println("Recipe: " + (this.recipe != null ? this.recipe.getId() : "null"));
      
        //       // Kontrollera att både userId och recipe är korrekt innan du fortsätter
        //       if (this.userId != null && this.recipe != null) {
        //           if (favoritesController.addFavorite(this.userId, this.recipe)) {
        //               System.out.println("Favorite added successfully.");
        //               // Uppdatera UI här
        //           } else {
        //               System.out.println("Failed to add favorite.");
        //           }
        //       } else {
        //           System.out.println("UserId or Recipe is null, cannot add to favorites.");
        //       }
        //   } catch (Exception e) {
        //       System.err.println("Error adding favorite: " + e.getMessage());
        //       e.printStackTrace();
        //   }
        // String sql = "INSERT INTO user_favorites (UserID, RecipeID) VALUES (?, ?)";
        // try (Connection conn = myDbManager.getConnection();
        //       PreparedStatement pstmt = conn.prepareStatement(sql)) {
        //     this.userId = 1L;
        //     pstmt.setLong(1, userId);
        //     pstmt.setString(2, recipe.getId());  
        //     int affectedRows = pstmt.executeUpdate();
        //     if ( affectedRows > 0) System.out.println("Done");;
        // } catch (SQLException e) {
        //   System.err.println("Error adding favorite: " + e.getMessage());
        // }
        this.userId = 1L;
        favoritesController.addFavorite(userId, recipe);
      }
    


      @FXML
      public void removeFromFavorites(ActionEvent event) {

        //   // Använd favoritesController för att ta bort från databasen istället
        //   if (favoritesController.removeFavorite(this.userId, this.recipe)) {
        //       System.out.println("Favorite removed successfully.");
        //       // Uppdatera ditt UI här om nödvändigt, t.ex. aktivera 'Add to favorite'-knappen
        //   } else {
        //       System.out.println("Failed to remove favorite.");
        //       // Visa felmeddelande till användaren
        //   }
        // String sql = "DELETE FROM user_favorites WHERE UserID = ? AND RecipeID = ?";
        // try (Connection conn = myDbManager.getConnection();
        //       PreparedStatement pstmt = conn.prepareStatement(sql)) {
        //     pstmt.setLong(1, userId);
        //     pstmt.setString(2, recipe.getId());
        //     int affectedRows = pstmt.executeUpdate();
        //     if ( affectedRows > 0) System.out.println("Done");;
        // } catch (SQLException e) {
        //   System.err.println("Failed to remove favorite: ");
        // }

        this.userId = 1L;
        favoritesController.removeFavorite(userId, recipe);
      }

@FXML
private void handleweekButtonAction(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PopupWeekList.fxml"));
        Parent parent = loader.load();

        PopupWeeklyViewController popupController = loader.getController();
        this.userId = 1L; // Sätter userId direkt här
        System.out.println(userId + " " + recipe.getId());
        if (popupController != null) {
            popupController.initData(recipe, userId); // Skickar nu den här lokalt satta userId
        } else {
            System.out.println("Popup controller was not initialized.");
            return; // To avoid further execution
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Weekly Recipe Planner");
        stage.initModality(Modality.APPLICATION_MODAL); // Restricts interaction to the other windows
        stage.showAndWait();
    } catch (IOException e) {
        System.out.println("Failed to load the weekly list popup: " + e.getMessage());
        e.printStackTrace();
        System.out.println("Error when opening the popup: " + e.getMessage());
    }
}



    
}