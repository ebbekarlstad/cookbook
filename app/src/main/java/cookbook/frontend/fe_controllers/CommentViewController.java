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
        int selectedIndex = commentsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) { // Check if an item is actually selected
            commentsListView.getItems().remove(selectedIndex);
        }
    }
}