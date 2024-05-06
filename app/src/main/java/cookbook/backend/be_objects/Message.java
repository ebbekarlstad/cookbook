package cookbook.backend.be_objects;



import java.sql.Timestamp;

public class Message {
  private Long messageId;
  private Long senderId;
  private Long receiverId;
  private Long recipeId;  // Foreign key to the recipes table
  private String content;  // Optional message text about the recipe
  private Timestamp sentTime;  // Timestamp when the message was created

  // Constructor
  public Message(Long senderId, Long receiverId, Long string, String content) {
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.recipeId = string;
    this.content = content;
    this.sentTime = new Timestamp(System.currentTimeMillis()); // Set current time as the timestamp
  }

  public Message(long long1, long long2, String string, String string2) {
    //TODO Auto-generated constructor stub
  }

  // Getters and Setters
  public Long getMessageId() {
    return messageId;
  }

  public void setMessageId(Long messageId) {
    this.messageId = messageId;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public Long getReceiverId() {
    return receiverId;
  }

  public void setReceiverId(Long receiverId) {
    this.receiverId = receiverId;
  }

  public Long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(Long recipeId) {
    this.recipeId = recipeId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Timestamp getSentTime() {
    return sentTime;
  }

  public void setSentTime(Timestamp sentTime) {
    this.sentTime = sentTime;
  }
}

