package cookbook.frontend.fe_controllers;

import java.util.ArrayList;
import java.util.List;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.CommentController;
import cookbook.backend.be_controllers.FavoritesController;
import cookbook.backend.be_objects.CommentObject;
import cookbook.backend.be_objects.Recipe;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class RecipeDetailsViewController {

    private String recipeId;
    private Long userId;
    private int commentId;
    Recipe recipe;
    
    @FXML
    private Label titleLabel; // Label for the recipe title.
    @FXML
    private Label shortLabel; // Label for the short description.
    @FXML
    private Label longLabel; // Label for the detailed description.
    //favorit attribute
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    DatabaseMng myDbManager;
    private FavoritesController favoritesController = new FavoritesController(myDbManager); 
    public void initData(Recipe recipe) {
        this.recipe = recipe;
        // Set the recipe information in your controls
        titleLabel.setText(recipe.getRecipeName());
        shortLabel.setText(recipe.getShortDesc());
        longLabel.setText(recipe.getDetailedDesc());

        this.recipeId = recipe.getId();
        System.out.println("InitData - UserId: " + this.userId); 
    }


    private CommentController commentController;

    // Constructor
    public RecipeDetailsViewController() {
        myDbManager = new DatabaseMng();
        this.commentController = new CommentController(myDbManager);  
        favoritesController = new FavoritesController(myDbManager); 
       

       
    }
    
    @FXML
    private ListView<String> commentsListView;  // List to display comments
    private List<Integer> commentIdList = new ArrayList<>();

    @FXML
    private TextField commentInput;  // Input field for comments

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
            // Check if the text box is already loaded with the selected comment for editing
            if (commentInput.getText().equals(commentsListView.getItems().get(selectedIndex))) {
                // User has edited the comment and is ready to update
                String updatedCommentText = commentInput.getText().trim();
            
                int commentId = getCommentIdByIndex(selectedIndex); 

                if (commentController.editComment(commentId, updatedCommentText)) {
                    commentsListView.getItems().set(selectedIndex, updatedCommentText);
                    commentInput.clear();
                    System.out.println("Comment updated successfully.");
                } else {
                    System.out.println("Failed to update comment.");
                }
            } else {
                // Load the selected comment into the text box for editing
                commentInput.setText(commentsListView.getItems().get(selectedIndex));
            }
        } else {
            System.out.println("No comment selected.");
        }
    }

    private int getCommentIdByIndex(int index) {
        if (index >= 0 && index < commentIdList.size()) {
            return commentIdList.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds for comment ID list");
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

        PopupWeeklyController popupController = loader.getController();
        if (popupController != null) {
            popupController.initData(recipe, userId);
        } else {
            System.out.println("Popupcontroller was not initialized.");
            return; //to avoid further execusion
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Weeckly Recipe Planner");
        stage.initModality(Modality.APPLICATION_MODAL); // restricts interaction to the other windows
        stage.showAndWait();
      } catch (IOException e) {
        System.out.println("Failed to load the weekly list popup: " + e.getMessage());
        e.printStackTrace();
        System.out.println("Error when opening the popup: " + e.getMessage());
        }
    }


    
}