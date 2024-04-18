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

    

    
}
