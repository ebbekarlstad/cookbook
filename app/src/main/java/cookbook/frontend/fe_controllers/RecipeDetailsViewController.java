package cookbook.frontend.fe_controllers;

import java.util.ArrayList;
import java.util.List;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.CommentController;
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
import javafx.stage.Stage;

public class RecipeDetailsViewController {

    private String recipeId;

    @FXML
    private Label titleLabel; // Label for the recipe title.
    @FXML
    private Label shortLabel; // Label for the short description.
    @FXML
    private Label longLabel; // Label for the detailed description.

    public void initData(Recipe recipe) {
        // Set the recipe information in your controls
        titleLabel.setText(recipe.getRecipeName());
        shortLabel.setText(recipe.getShortDesc());
        longLabel.setText(recipe.getDetailedDesc());

        this.recipeId = recipe.getId();
    }

    private CommentController commentController;

    // Constructor
    public RecipeDetailsViewController() {
        DatabaseMng myDbManager = new DatabaseMng();
        this.commentController = new CommentController(myDbManager);  // Correctly assign to the class field
    }
    
    @FXML
    private ListView<String> commentsListView;  // List to display comments
    private List<Integer> commentIdList = new ArrayList<>();

    @FXML
    private TextField commentInput;  // Input field for comments

    // Not sure if db operations work
    @FXML
    private void addComment(ActionEvent event) {
        String commentText = commentInput.getText().trim();  // Get text from TextField
        if (!commentText.isEmpty()) {
            CommentObject newComment = new CommentObject(0, 0, 0, commentText, commentText);
            newComment.setRecipeId(Integer.parseInt(this.recipeId));
            newComment.setUserId(1);  // Same here
            newComment.setText(commentText);
            newComment.setTimestamp("yy-mm-dd hh:mm:ss");  // Determine how to set timestamp

            if (commentController.addComment(newComment)) {
                commentsListView.getItems().add(commentText);  // Add comment to our ListView
                commentInput.clear();
            } else {
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
}