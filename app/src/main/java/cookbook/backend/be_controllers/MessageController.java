package cookbook.backend.be_controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.Message;

public class MessageController {

  private DatabaseMng dbManager;

  public MessageController(DatabaseMng dbManager) {
    this.dbManager = dbManager; // Assign the passed instance to the dbManager field
  }

  public boolean saveMessage(Message messageObject) {
    String sql = "INSERT INTO messages (sender_id, receiver_id, recipe_id, content, sent_time, is_opened) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setLong(1, messageObject.getSenderId());
      pstmt.setLong(2, messageObject.getReceiverId());
      pstmt.setString(3, messageObject.getRecipeId());
      pstmt.setString(4, messageObject.getContent());
      pstmt.setTimestamp(5, messageObject.getSentTime());
      pstmt.setBoolean(6, messageObject.isOpened());

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("Message saved in DB");
        return true;
      } else {
        System.out.println("No rows affected, message not found.");
        return false;
      }
    } catch (SQLException e) {
      System.err.println("Database error during message saving: " + e.getMessage());
      return false;
    }
  }

  // Method to get a user's inbox depending on userID
  public List<Message> getInbox(Long userId) throws SQLException {
    String query = "SELECT * FROM messages WHERE receiver_id = ? ORDER BY sent_time DESC;";
    List<Message> messages = new ArrayList<>();
    try (Connection conn = dbManager.getConnection();
        PreparedStatement sqlStatement = conn.prepareStatement(query)) {

      sqlStatement.setLong(1, userId);
      ResultSet rs = sqlStatement.executeQuery();
      while (rs.next()) {
        Message message = new Message(
            rs.getLong("message_id"), // Ensure to fetch the message ID
            rs.getLong("sender_id"),
            rs.getLong("receiver_id"),
            rs.getString("recipe_id"),
            rs.getString("content"),
            rs.getTimestamp("sent_time"),
            rs.getBoolean("is_opened"));

        messages.add(message);
        System.out.println("Added message: " + message.getContent()); // Debugging line
      }
      System.out.println("Total messages fetched: " + messages.size()); // Debugging line
    } catch (SQLException e) {
      System.out.println("SQL error: " + e.getMessage());
      throw e;
    }
    return messages;
  }

  public String getName(long userId) throws SQLException {
    String displayName = null;
    String query = "SELECT U.DisplayName FROM users U WHERE U.UserID = ?;";

    // Try-with-resources to ensure that resources are closed after the program is
    // finished with them
    try (Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setLong(1, userId); // Set the user ID parameter
      ResultSet rs = pstmt.executeQuery(); // Execute the query
      if (rs.next()) { // If the result set is not empty
        displayName = rs.getString("DisplayName"); // Get the display name from the result set
      }
      rs.close();
    } catch (SQLException e) {
      // Properly handle exception, could log it and/or rethrow if needed
      throw new SQLException("Error fetching user display name", e);
    }
    return displayName; // Return the display name, or null if not found
  }

  // Method for checking if user has unread messages
  public boolean hasUnreadMessages(long userId) {
    String query = "SELECT COUNT(*) FROM messages WHERE receiver_id = ? AND is_opened = false";
    try (Connection conn = dbManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setLong(1, userId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        int count = rs.getInt(1);
        System.out.println("Unread messages count for user " + userId + ": " + count);
        return count > 0;
      }
    } catch (SQLException e) {
      System.err.println("Error checking for unread messages: " + e.getMessage());
      return false;
    }
    return false;
  }

  // Method for marking messages as read:
  public boolean markMessageAsOpened(long messageId) throws SQLException {
    String sql = "UPDATE messages SET is_opened = TRUE WHERE message_id = ?";
    try (Connection conn = dbManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setLong(1, messageId);
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("Message marked as opened");
        return true;
      } else {
        System.out.println("No message updated");
        return false;
      }
    } catch (SQLException e) {
      System.err.println("Database error during updating message status: " + e.getMessage());
      throw e;
    }
  }

}