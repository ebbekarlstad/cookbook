package cookbook.backend.be_controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_objects.Message;

public class MessageController {

    private DatabaseMng dbManager;

    public MessageController(DatabaseMng dbManager) {
        this.dbManager = dbManager; // Assign the passed instance to the dbManager field
    }

    public boolean saveMessage(Message messageObject) {
        String sql = "INSERT INTO messages (message_id, sender_id, receiver_id, recipe_id, content, sent_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, messageObject.getMessageId());
            pstmt.setLong(2, messageObject.getSenderId());
            pstmt.setLong(3, messageObject.getReceiverId());
            pstmt.setString(4, messageObject.getRecipeId());
            pstmt.setString(5, messageObject.getContent());
            pstmt.setTimestamp(6, messageObject.getSentTime());
    
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
}