package cookbook.backend.be_objects;



import java.sql.Timestamp;

public class Message {
  private Long messageId;
  private Long senderId;
  private Long receiverId;
  private String messageType;
  private String content;
  private Timestamp sentTime;

  // Constructor
  public Message(Long senderId, Long receiverId, String messageType, String content) {
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.messageType = messageType;
    this.content = content;
    this.sentTime = new Timestamp(System.currentTimeMillis()); // Set current time as the timestamp
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

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
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
