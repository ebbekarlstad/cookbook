package cookbook.backend.be_objects;


public class CommentObject {
  private int commentId;
  private String recipeId;
  private int userId;
  private String text;
  private String timestamp;

  public CommentObject(int commentId, String recipeId, int userId, String text, String timestamp) {
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
  public String getRecipeId() {
      return recipeId;
  }
  public void setRecipeId(String recipeId) {
      this.recipeId = recipeId;
  }
  public int getUserId() {
      return userId;
  }
  public void setUserId(int userId) {
      this.userId = userId;
  }
  public String getText() {
      return text;
  }
  public void setText(String text) {
      this.text = text;
  }
  public String getTimestamp() {
      return timestamp;
  }
  public void setTimestamp(String timestamp) {
      this.timestamp = timestamp;
  }
}