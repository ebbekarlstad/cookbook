package cookbook.backend.be_controllers;

import java.sql.Connection;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.CommentObject;
public class CommentController {

  public boolean addComment(){
    String sql = "INSERT INTO users (recipeId, userId, text, timestamp) VALUES (?, ?, ?, ?)";
      try (Connection conn = DatabaseMng.getConnection(); 
      PreparedStatement pstmt = conn.prepareStatement(sql)) {


      pstmt.setString(1, CommentObject.getRecipeId());
      pstmt.setString(2, CommentObject.getUserId());
      pstmt.setString(3, CommentObject.getText());
      pstmt.setBoolean(4,CommentObject.getTimestamp());





    }


}
}