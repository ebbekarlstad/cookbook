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

    // Not sure if db operations work
    @FXML
    private void addComment(ActionEvent event) {
        String commentText = commentInput.getText().trim();  // Get text from TextField
        if (!commentText.isEmpty()) {
            CommentObject newComment = new CommentObject(0, 0, 0, commentText, commentText);
            newComment.setRecipeId(1);  // We need to determine how to get this
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
        // Unimplemented method
    }
}