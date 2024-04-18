package cookbook.backend.be_objects;


public class CommentObject {
  private int commentId;
  private int recipeId;
  private int userId;
  private String text;
  private String timestamp;

  public CommentObject(int commentId, int recipeId, int userId, String text, String timestamp) {
      this.commentId = commentId;
      this.recipeId = recipeId;
      this.userId = userId;
      this.text = text;
      this.timestamp = timestamp;
  }
  public int getCommentId() {
      return commentId;
  }
  public void setCommentId(int commentId) {
      this.commentId = commentId;
  }
  public static int getRecipeId() {
      return recipeId;
  }
  public void setRecipeId(int recipeId) {
      this.recipeId = recipeId;
  }
  public static int getUserId() {
      return userId;
  }
  public void setUserId(int userId) {
      this.userId = userId;
  }
  public static String getText() {
      return text;
  }
  public void setText(String text) {
      this.text = text;
  }
  public static String getTimestamp() {
      return timestamp;
  }
  public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
  }

    



    
}
