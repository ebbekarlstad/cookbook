package cookbook.backend.be_controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.CommentObject;
public class CommentController {

  private DatabaseMng dbManager;

  public boolean addComment(){
    String sql = "INSERT INTO users (recipeId, userId, text, timestamp) VALUES (?, ?, ?, ?)";
    try (Connection conn = dbManager.getConnection(); 
      PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setLong(1, CommentObject.getRecipeId());
      pstmt.setInt(2, CommentObject.getUserId());
      pstmt.setString(3, CommentObject.getText());
      pstmt.setString(4,CommentObject.getTimestamp());

      int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
          System.out.println("Comment saved successfully.");
          return true;
        } else {
          System.out.println("No rows affected.");
          return false;
        }
      } catch (SQLException e) {
          System.err.println("Database error during comment insertion: " + e.getMessage());
          return false;


    }

}

    public boolean removeComment(int commentId) {
      String sql = "DELETE FROM users WHERE commentId = ?";
      try (Connection conn = dbManager.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
          pstmt.setInt(1, commentId);
  
          int affectedRows = pstmt.executeUpdate();
          if (affectedRows > 0) {
              System.out.println("Comment deleted successfully.");
              return true;
          } else {
              System.out.println("No rows affected, comment not found.");
              return false;
          }
      } catch (SQLException e) {
          System.err.println("Database error during comment deletion: " + e.getMessage());
          return false;
      }
  }

  public boolean editComment(int commentId, String newText) {
    String sql = "UPDATE comments SET text = ? WHERE commentId = ?";
    try (Connection conn = dbManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, newText);
        pstmt.setInt(2, commentId);

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Comment updated successfully.");
            return true;
        } else {
            System.out.println("No rows affected, comment not found.");
            return false;
        }
    } catch (SQLException e) {
        System.err.println("Database error during comment update: " + e.getMessage());
        return false;
    }
}
  

}



