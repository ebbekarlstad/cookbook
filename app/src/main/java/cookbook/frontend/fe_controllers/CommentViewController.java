package cookbook.frontend.fe_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CommentViewController {
    
    @FXML
    private ListView<String> commentsListView;  // List to display comments

    @FXML
    private TextField commentInput;  // Input field for comments

    @FXML
    private void addComment(ActionEvent event) {
        String comment = commentInput.getText().trim();  // Get text from TextField
        if (!comment.isEmpty()) {
            commentsListView.getItems().add(comment);  // Add comment to our ListView
            commentInput.clear();
        }
    }

    @FXML
    private void deleteComment(ActionEvent event) {
        // Later, implement a check to only allow a person who created the 
        // comment (or an admin user) to remove it.
        int selectedIndex = commentsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
            commentsListView.getItems().remove(selectedIndex);
        }
    }

    @FXML
    private void editComment(ActionEvent event) {
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