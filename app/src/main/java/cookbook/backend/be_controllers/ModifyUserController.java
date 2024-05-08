package cookbook.backend.be_controllers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import cookbook.backend.DatabaseMng;

public class ModifyUserController {
    
    private DatabaseMng dbManager;
    
    public ModifyUserController(DatabaseMng dbManager) {
        this.dbManager = dbManager; // Assign the passed instance to the dbManager field
    }

    public boolean modifyUser(Long userId, String userName, String displayName, String password) {
        String sql = "UPDATE users SET UserName = ?, DisplayName = ?, Password = ? WHERE UserID = ?";
        try (PreparedStatement statement = dbManager.getConnection().prepareStatement(sql)) {
            statement.setString(1, userName);
            statement.setString(2, displayName);
            statement.setString(3, password);
            statement.setLong(4, userId);

            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        }
    }
}
