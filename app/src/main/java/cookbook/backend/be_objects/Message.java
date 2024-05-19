package cookbook.backend.be_objects;

import java.sql.Timestamp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Message {
  private Long messageId;
  private Long senderId;
  private Long receiverId;
  private String recipeId; // Foreign key to the recipes table
  private String content; // Optional message text about the recipe
  private Timestamp sentTime; // Timestamp when the message was created
  private BooleanProperty isOpened;

  // Constructor
  public Message(Long messageId, Long senderId, Long receiverId, String recipeId, String content, Timestamp sentTime,
      boolean isOpened) {
    this.messageId = messageId;
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.recipeId = recipeId;
    this.content = content;
    this.sentTime = new Timestamp(System.currentTimeMillis()); // Set current time as the timestamp
    this.isOpened = new SimpleBooleanProperty(isOpened);
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

  public String getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(String recipeId) {
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

  public boolean isOpened() {
    return isOpened.get();
  }

  public void setOpened(boolean opened) {
    this.isOpened.set(opened);
  }

  public BooleanProperty isOpenedProperty() {
    return isOpened;
  }
}
