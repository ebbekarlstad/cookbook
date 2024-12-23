package cookbook.backend.be_controllers;

//imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.CommentObject;

public class CommentController {

  private DatabaseMng dbManager;

  public CommentController(DatabaseMng dbManager) {
    this.dbManager = dbManager; // Assign the passed instance to the dbManager field
  }

  public List<String> fetchComments(String recipeId) {
    List<String> comments = new ArrayList<>();
    String sql = "SELECT Text FROM comments WHERE RecipeID = ?";
    try (Connection conn = dbManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, recipeId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        comments.add(rs.getString("Text"));
      }
    } catch (SQLException e) {
      System.err.println("Database error during fetching comments: " + e.getMessage());
    }
    return comments;
  }

  public boolean addComment(CommentObject commentObj) {
    String sql = "INSERT INTO comments (RecipeID, UserID, Text, Timestamp) VALUES (?, ?, ?, ?)";
    try (Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, commentObj.getRecipeId());
      pstmt.setLong(2, commentObj.getUserId());
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

  public boolean removeComment(String commentText) {
    String sql = "DELETE FROM comments WHERE Text = ?";
    try (Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, commentText);

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

  public boolean updateComment(String oldText, String newText) {
    String sql = "UPDATE comments SET Text = ? WHERE Text = ?";
    try (Connection conn = dbManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, newText);
      pstmt.setString(2, oldText);

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("Comment updated successfully.");
        return true;
      } else {
        System.out.println("No rows affected, comment not updated.");
        return false;
      }
    } catch (SQLException e) {
      System.err.println("Database error during comment update: " + e.getMessage());
      return false;
    }
  }
}