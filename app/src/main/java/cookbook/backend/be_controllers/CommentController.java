package cookbook.backend.be_controllers;
import cookbook.backend.be_objects.CommentObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cookbook.backend.DatabaseMng;
public class CommentController {

  private DatabaseMng dbManager;
  private CommentObject commentObj;

  public boolean addComment(){
    String sql = "INSERT INTO users (recipeId, userId, text, timestamp) VALUES (?, ?, ?, ?)";
    try (Connection conn = dbManager.getConnection(); 
      PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setLong(1, commentObj.getRecipeId());
      pstmt.setInt(2, commentObj.getUserId());
      pstmt.setString(3, commentObj.getText());
      pstmt.setString(4, commentObj.getTimestamp());

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
  }



