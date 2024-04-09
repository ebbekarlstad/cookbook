package cookbook.backend;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSeeder {

    public static void seedTable(String sql) {
        // Tries to connect and creates a Statement
        try {
            Statement stmt = DatabaseMng.getConnection().createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Seeding complete.");

        } catch (SQLException e) {
            System.out.println("Error during seeding: " + e.getMessage());
        }
    }

    // Seeding the Users table
    public static void seedUsers() {
        String sql = "CREATE TABLE `users` ("
                + "`UserID` int NOT NULL AUTO_INCREMENT, "
                + "`UserName` varchar(45) NOT NULL, "
                + "`DisplayName` varchar(45) NOT NULL, "
                + "`Password` varchar(45) NOT NULL, "
                + "`IsAdmin` tinyint NOT NULL, "
                + "PRIMARY KEY (`UserID`), "
                + "UNIQUE KEY `userID_UNIQUE` (`UserID`));";
        seedTable(sql);
    }

    public static void main(String[] args) {
        seedUsers();
        // Call other seed functions here
    }
}

