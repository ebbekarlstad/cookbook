package cookbook.backend.be_controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    // Method to get a user's inbox depending on userID
    public List<Message> getInbox(int userId) throws SQLException {
        String query = "SELECT * FROM messages WHERE receiver_id = ? ORDER BY sent_time DESC;";
        List<Message> messages = new ArrayList<>();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement sqlStatement = conn.prepareStatement(query)) {
            
            sqlStatement.setInt(1, userId);
            ResultSet rs = sqlStatement.executeQuery();
            while (rs.next()) {

                Message message = new Message(
                    rs.getLong("sender_id"),
                    rs.getLong("receiver_id"),
                    rs.getString("recipe_id"),
                    rs.getString("content")
                );

                messages.add(message);
                System.out.println("Added message: " + message.getContent());  // Debugging line
            }
            System.out.println("Total messages fetched: " + messages.size());  // Debugging line
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            throw e;
        }
        return messages;
    }
}