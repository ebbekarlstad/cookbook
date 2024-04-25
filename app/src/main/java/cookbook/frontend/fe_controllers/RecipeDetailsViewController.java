package cookbook.frontend.fe_controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.CommentController;
import cookbook.backend.be_objects.CommentObject;
import cookbook.backend.be_objects.Recipe;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecipeDetailsViewController {

    private String recipeId;
    private int commentId;
    Recipe recipe;

    @FXML
    private Label titleLabel; // Label for the recipe title.
    @FXML
    private Label shortLabel; // Label for the short description.
    @FXML
    private Label longLabel; // Label for the detailed description.

    DatabaseMng myDbManager;
    public void initData(Recipe recipe) {
        this.recipe = recipe;
        // Set the recipe information in your controls
        titleLabel.setText(recipe.getRecipeName());
        shortLabel.setText(recipe.getShortDesc());
        longLabel.setText(recipe.getDetailedDesc());

        this.recipeId = recipe.getId();
    }

    private CommentController commentController;

    // Constructor
    public RecipeDetailsViewController() {
        myDbManager = new DatabaseMng();
        this.commentController = new CommentController(myDbManager);  // Correctly assign to the class field
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
            // Assuming recipeId and userId should be handled as Strings
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
                // Assuming the ListView items are directly mapped to comment IDs or you have a way to get IDs
                int commentId = getCommentIdByIndex(selectedIndex); // Implement this method based on your application's data structure

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
        String sql = "INSERT INTO favorites (RecipeID, UserID, Name, ShortDesc, DetailedDesc, Serves) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = myDbManager.getConnection(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, recipe.getId());
            // pstmt.setInt(2, recipe.getUserID());
            pstmt.setString(3, recipe.getRecipeName());
            pstmt.setString(4,recipe.getShortDesc());
            pstmt.setString(5,recipe.getDetailedDesc());
            // pstmt.setString(6,recipe.getServes());
                
      int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
          System.out.println("Favorite saved successfully.");
        } else {
          System.out.println("No rows affected.");
        }
      } catch (SQLException e) {
          System.err.println("Database error during comment insertion: " + e.getMessage());
        }   
      }

      @FXML
      public void removeFromFavorites(ActionEvent event) {
        String sql = "DELETE FROM favorites WHERE RecipeID = ?";
        try (Connection conn = myDbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, recipe.getId());
    
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Favorite deleted csuccessfully.");
            } else {
                System.out.println("No rows affected, omment not found.");
            }
        } catch (SQLException e) {
            System.err.println("Database error during comment deletion: " + e.getMessage());
        }
      }



}