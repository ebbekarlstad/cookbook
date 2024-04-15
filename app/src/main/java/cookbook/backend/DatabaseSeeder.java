package cookbook.backend;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSeeder {

    public void seedTable(String sql) {
        DatabaseMng dbManager = new DatabaseMng(); // Create an instance of DatabaseMng
        try {
            Connection conn = dbManager.getConnection(); // Use the instance to call getConnection()
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Seeding complete.");
        } catch (SQLException e) {
            System.out.println("Error during seeding: " + e.getMessage());
        }
    }

    // Seeding the Users table
    public void seedUsers() {
        String dropTableSQL = "DROP TABLE IF EXISTS `users`;";

        String createTableSQL = "CREATE TABLE `users` ("
            + "`UserID` int NOT NULL AUTO_INCREMENT, "
            + "`UserName` varchar(45) NOT NULL, "
            + "`DisplayName` varchar(45) NOT NULL, "
            + "`Password` varchar(255) NOT NULL, "
            + "`IsAdmin` tinyint NOT NULL, "
            + "PRIMARY KEY (`UserID`), "
            + "UNIQUE KEY `userID_UNIQUE` (`UserID`));";
            seedTable(dropTableSQL);
            seedTable(createTableSQL);
    }

    public void seedRecipes() {
        String dropTableSQL = "DROP TABLE IF EXISTS `recipes`;";

        String createTableSQL = "CREATE TABLE `recipes` ("
            + "`RecipeID` int NOT NULL, "
            + "`UserID` int NOT NULL, "
            + "`Name` varchar(45) NOT NULL, "
            + "`ShortDesc` varchar(45) NOT NULL, "
            + "`DetailedDesc` varchar(255) NOT NULL, "
            + "`Serves` int NOT NULL, "
            + "PRIMARY KEY (`RecipeID`), "
            + "UNIQUE KEY `RecipeID_UNIQUE` (`RecipeID`), "
            +"KEY `UserID_idx` (`UserID`), "
            + "CONSTRAINT `UserID` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`));";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedIngredients() {
        String dropTableSQL = "DROP TABLE IF EXISTS `ingredients`;";

        String createTableSQL = "CREATE TABLE `ingredients` ("
            + "`IngredientID` int NOT NULL, "
            + "`Name` varchar(45) NOT NULL, "
            + "PRIMARY KEY (`IngredientID`));";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedRecipeIngredients() {
        String dropTableSQL = "DROP TABLE IF EXISTS `recipe_ingredients`;";

        String createTableSQL = "CREATE TABLE `recipe_ingredients` ("
            + "`RecipeIngredientID` int NOT NULL, "
            + "`RecipeID` int NOT NULL, "
            + "`IngredientID` int NOT NULL, "
            + "`Quantity` int NOT NULL, "
            + "PRIMARY KEY (`RecipeIngredientID`), "
            + "KEY `RecipeID_idx` (`RecipeID`), "
            + "KEY `IngredientID_idx` (`IngredientID`), "
            + "CONSTRAINT `IngredientID0` FOREIGN KEY (`IngredientID`) REFERENCES `ingredients` (`IngredientID`), "
            + "CONSTRAINT `RecipeID0` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`))";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedTags() {
        String dropTableSQL = "DROP TABLE IF EXISTS `tags`;";

        String createTableSQL = "CREATE TABLE `tags` ("
            + "`TagID` int NOT NULL, "
            + "`Name` varchar(45) NOT NULL, "
            + "PRIMARY KEY (`TagID`));";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedRecipeTags() {
        String dropTableSQL = "DROP TABLE IF EXISTS `recipe_tags`;";

        String createTableSQL = "CREATE TABLE `recipe_tags` ("
            + "`RecipeTagID` int NOT NULL, "
            + "`RecipeID` int NOT NULL, "
            + "`TagID` int NOT NULL, "
            + "PRIMARY KEY (`RecipeTagID`), "
            + "KEY `TagID_idx` (`TagID`), "
            + "KEY `RecipeID1_idx` (`RecipeID`), "
            + "CONSTRAINT `RecipeID1` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`), "
            + "CONSTRAINT `TagID` FOREIGN KEY (`TagID`) REFERENCES `tags` (`TagID`))";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedComments() {
        String dropTableSQL = "DROP TABLE IF EXISTS `comments`;";

        String createTableSQL = "CREATE TABLE `comments` ("
            + "`CommentID` int NOT NULL, "
            + "`RecipeID` int NOT NULL, "
            + "`UserID` int NOT NULL, "
            + "`Text` varchar(255) NOT NULL, "
            + "`Timestamp` varchar(45) NOT NULL, "
            + "PRIMARY KEY (`CommentID`), "
            + "KEY `RecipeID2_idx` (`RecipeID`), "
            + "KEY `UserID0_idx` (`UserID`), "
            + "CONSTRAINT `RecipeID2` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`), "
            + "CONSTRAINT `UserID0` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`))";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedWeeklyDinnerLists() {
        String dropTableSQL = "DROP TABLE IF EXISTS `weekly_dinner_lists`;";

        String createTableSQL = "CREATE TABLE `weekly_dinner_lists` ("
            + "`WeeklyDinnerListID` int NOT NULL, "
            + "`UserID` int NOT NULL, "
            + "`Week` date NOT NULL, "
            + "PRIMARY KEY (`WeeklyDinnerListID`), "
            + "KEY `UserID1_idx` (`UserID`), "
            + "CONSTRAINT `UserID1` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`))";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedDinnerListRecipes() {
        String dropTableSQL = "DROP TABLE IF EXISTS `dinner_list_recipes`;";

        String createTableSQL = "CREATE TABLE `dinner_list_recipes` ("
            + "`DinnerListRecipeID` int NOT NULL, "
            + "`WeeklyDinnerListID` int NOT NULL, "
            + "`RecipeID` int NOT NULL, "
            + "`DayOfWeek` varchar(45) NOT NULL, "
            + "PRIMARY KEY (`DinnerListRecipeID`), "
            + "KEY `WeeklyDinnerListID0_idx` (`WeeklyDinnerListID`), "
            + "KEY `RecipeID3_idx` (`RecipeID`), "
            + "CONSTRAINT `RecipeID3` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`), "
            + "CONSTRAINT `WeeklyDinnerListID0` FOREIGN KEY (`WeeklyDinnerListID`) REFERENCES `weekly_dinner_lists` (`WeeklyDinnerListID`))";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedShoppingLists() {
        String dropTableSQL = "DROP TABLE IF EXISTS `shopping_lists`;";

        String createTableSQL = "CREATE TABLE `shopping_lists` ("
            + "`ShoppingListID` int NOT NULL, "
            + "`WeeklyDinnerListID` int NOT NULL, "
            + "`Edited` tinyint NOT NULL, "
            + "PRIMARY KEY (`ShoppingListID`), "
            + "KEY `WeeklyDinnerListID1_idx` (`WeeklyDinnerListID`), "
            + "CONSTRAINT `WeeklyDinnerListID1` FOREIGN KEY (`WeeklyDinnerListID`) REFERENCES `weekly_dinner_lists` (`WeeklyDinnerListID`))";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedShoppingListItems() {
        String dropTableSQL = "DROP TABLE IF EXISTS `shopping_list_items`;";

        String createTableSQL = "CREATE TABLE `shopping_list_items` ("
            + "`ShoppingListItemID` int NOT NULL, "
            + "`ShoppingListID` int NOT NULL, "
            + "`IngredientID` int NOT NULL, "
            + "`Quantity` int NOT NULL, "
            + "`IsPurchased` tinyint NOT NULL, "
            + "PRIMARY KEY (`ShoppingListItemID`), "
            + "KEY `ShoppingListID_idx` (`ShoppingListID`), "
            + "KEY `IngredientID_idx` (`IngredientID`), "
            + "CONSTRAINT `IngredientID1` FOREIGN KEY (`IngredientID`) REFERENCES `ingredients` (`IngredientID`), "
            + "CONSTRAINT `ShoppingListID` FOREIGN KEY (`ShoppingListID`) REFERENCES `shopping_lists` (`ShoppingListID`))";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedSentRecipes() {
        String dropTableSQL = "DROP TABLE IF EXISTS `sent_recepies`;";

        String createTableSQL = "CREATE TABLE `sent_recepies` ("
            + "`SentRecipeID` int NOT NULL, "
            + "`SenderUserID` int NOT NULL, "
            + "`RecieverUserID` int NOT NULL, "
            + "`RecipeID` int NOT NULL, "
            + "`Message` varchar(255) NOT NULL, "
            + "`Timestamp` varchar(45) NOT NULL, "
            + "PRIMARY KEY (`SentRecipeID`), "
            + "KEY `SenderUserID_idx` (`SenderUserID`), "
            + "KEY `RecieverUserID_idx` (`RecieverUserID`), "
            + "KEY `RecipeID4_idx` (`RecipeID`), "
            + "CONSTRAINT `RecieverUserID` FOREIGN KEY (`RecieverUserID`) REFERENCES `users` (`UserID`), "
            + "CONSTRAINT `RecipeID4` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`), "
            + "CONSTRAINT `SenderUserID` FOREIGN KEY (`SenderUserID`) REFERENCES `users` (`UserID`))";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedHelpSystem() {
        String dropTableSQL = "DROP TABLE IF EXISTS `help_system`;";

        String createTableSQL = "CREATE TABLE `help_system` ("
            + "`HelpID` int NOT NULL, "
            + "`Title` varchar(45) NOT NULL, "
            + "`Content` varchar(255) NOT NULL, "
            + "PRIMARY KEY (`HelpID`))";
        
        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public static void main(String[] args) {
        DatabaseSeeder seeder = new DatabaseSeeder();
        seeder.seedUsers();
        seeder.seedRecipes();
        seeder.seedIngredients();
        seeder.seedRecipeIngredients();
        seeder.seedTags();
        seeder.seedRecipeTags();
        seeder.seedComments();
        seeder.seedWeeklyDinnerLists();
        seeder.seedDinnerListRecipes();
        seeder.seedShoppingLists();
        seeder.seedShoppingListItems();
        seeder.seedSentRecipes();
        seeder.seedHelpSystem();
        
        // Call other seed functions here
    }
}