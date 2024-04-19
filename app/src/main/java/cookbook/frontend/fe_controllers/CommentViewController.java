package cookbook.frontend.fe_controllers;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.CommentController;
import cookbook.backend.be_objects.CommentObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CommentViewController {

    private CommentController commentController;

    // Constructor
    public CommentViewController() {
        DatabaseMng myDbManager = new DatabaseMng();
        this.commentController = new CommentController(myDbManager);  // Correctly assign to the class field
    }
    
    @FXML
    private ListView<String> commentsListView;  // List to display comments

    @FXML
    private TextField commentInput;  // Input field for comments

    @FXML
    private void addComment(ActionEvent event) {
        String commentText = commentInput.getText().trim();  // Get text from TextField
    if (!commentText.isEmpty()) {
        CommentObject newComment = new CommentObject(0, 0, 0, commentText, commentText);
        newComment.setRecipeId(1);  // You need to determine how to get this
        newComment.setUserId(1);  // Same here
        newComment.setText(commentText);
        newComment.setTimestamp("2023-04-10 10:39:37");  // Determine how to set timestamp

        if (commentController.addComment(newComment)) {
            commentsListView.getItems().add(commentText);  // Add comment to our ListView
            commentInput.clear();
        } else {
            System.out.println("Failed to add comment.");
        }
    }
    }

    @FXML
    private void deleteComment(ActionEvent event) {
        commentController.removeComment(0);
        // Later, implement a check to only allow a person who created the 
        // comment (or an admin user) to remove it.
        int selectedIndex = commentsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
            commentsListView.getItems().remove(selectedIndex);
        }
    }

    @FXML
    private void editComment(ActionEvent event) {
        commentController.editComment(0, null);
        // Later, implement functionality for changing the tite of
        // editButton to say 'Confirm' whilst user is editing.
        int selectedIndex = commentsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) { // Check if an item is actually selected
            String newComment = commentInput.getText().trim(); // Get new text from the TextField
            
            if (!newComment.isEmpty()) {
                commentsListView.getItems().set(selectedIndex, newComment); // Replace the old comment with the new one
                commentInput.clear();
            }
        }
    }
}