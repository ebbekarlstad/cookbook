package cookbook.backend;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSeeder {

    public void seedTable(String sql) {
        DatabaseMng dbManager = new DatabaseMng();
        try (Connection conn = dbManager.getConnection(); 
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Seeding complete.");
        } catch (SQLException e) {
            System.out.println("Error during seeding: " + e.getMessage());
        }
    }

    public void seedMessages() {
        String dropTableSql = "DROP TABLE IF EXISTS `messages`;";
        String createTableSQL = "CREATE TABLE `messages` ("
                + "`message_id` BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "`sender_id` INT NOT NULL,"
                + "`receiver_id` INT NOT NULL,"
                + "`recipe_id` VARCHAR(255) NOT NULL,"
                + "`content` TEXT,"
                + "`sent_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (`recipe_id`) REFERENCES `recipes`(`RecipeID`),"
                + "FOREIGN KEY (`sender_id`) REFERENCES `users`(`UserID`),"
                + "FOREIGN KEY (`receiver_id`) REFERENCES `users`(`UserID`)"
                + ");";

        seedTable(dropTableSql);
        seedTable(createTableSQL);
    }

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
            
        // Inserts a new user with username 'user' and password 'user'
        String seedUserValues = "INSERT INTO `users` (`UserName`, `DisplayName`, `Password`, `IsAdmin`) "
            + "VALUES ('user', 'User', '4f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 0);";

        String seedUser2Values = "INSERT INTO `users` (`UserName`, `DisplayName`, `Password`, `IsAdmin`) "
            + "VALUES ('testuser', 'testuser', '4f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 0);";
    
        String seedAdminValues = "INSERT INTO `users` (`UserName`, `DisplayName`, `Password`, `IsAdmin`) "
            + "VALUES ('admin', 'Administrator', '4f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 1);";

        seedTable(dropTableSQL);
        seedTable(createTableSQL);
        seedTable(seedUserValues);
        seedTable(seedUser2Values);
        seedTable(seedAdminValues);
    }

    public void seedRecipes() {
        String dropTableSQL = "DROP TABLE IF EXISTS `recipes`;";

        String createTableSQL = "CREATE TABLE `recipes` ("
            + "`RecipeID` varchar(255) NOT NULL, "
            + "`UserID` int NOT NULL, "
            + "`RecipeName` varchar(1000) NOT NULL, "
            + "`ShortDesc` varchar(1000) NOT NULL, "
            + "`DetailedDesc` varchar(1000) NOT NULL, "
            + "PRIMARY KEY (`RecipeID`), "
            + "UNIQUE KEY `RecipeID_UNIQUE` (`RecipeID`), "
            + "KEY `UserID_idx` (`UserID`), "
            + "CONSTRAINT `UserID` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`));";

        String[] recipes = new String[] {
            "('1', 1, 'Spaghetti Bolognese', 'A classic Italian dish', 'This is a detailed description of Spaghetti Bolognese.')",
            "('2', 1, 'Chicken Curry', 'A spicy and flavorful dish', 'This is a detailed description of Chicken Curry.')",
            "('3', 1, 'Beef Stew', 'Hearty and filling', 'This is a detailed description of Beef Stew.')",
            "('4', 1, 'Caesar Salad', 'A fresh and crispy salad', 'This is a detailed description of Caesar Salad.')",
            "('5', 1, 'Grilled Cheese Sandwich', 'A simple and delicious sandwich', 'This is a detailed description of Grilled Cheese Sandwich.')",
            "('6', 1, 'Pancakes', 'Fluffy and sweet', 'This is a detailed description of Pancakes.')",
            "('7', 1, 'Miso Soup', 'A traditional Japanese soup', 'This is a detailed description of Miso Soup.')",
            "('8', 1, 'Tacos', 'A Mexican classic', 'This is a detailed description of Tacos.')",
            "('9', 1, 'Pizza Margherita', 'Classic Italian pizza', 'This is a detailed description of Pizza Margherita.')",
            "('10', 1, 'Lasagna', 'Rich and cheesy', 'This is a detailed description of Lasagna.')",
            "('11', 1, 'Sushi', 'A Japanese delicacy', 'This is a detailed description of Sushi.')",
            "('12', 1, 'Chocolate Cake', 'Rich and decadent', 'This is a detailed description of Chocolate Cake.')",
            "('13', 1, 'Burger', 'Juicy and flavorful', 'This is a detailed description of Burger.')",
            "('14', 1, 'Fried Rice', 'Quick and tasty', 'This is a detailed description of Fried Rice.')",
            "('15', 1, 'Spring Rolls', 'Crispy and fresh', 'This is a detailed description of Spring Rolls.')",
            "('16', 1, 'Lamb Chops', 'Tender and juicy', 'This is a detailed description of Lamb Chops.')",
            "('17', 1, 'Tom Yum Soup', 'Spicy and sour Thai soup', 'This is a detailed description of Tom Yum Soup.')",
            "('18', 1, 'Pad Thai', 'A popular Thai dish', 'This is a detailed description of Pad Thai.')",
            "('19', 1, 'Ratatouille', 'A French vegetable dish', 'This is a detailed description of Ratatouille.')",
            "('20', 1, 'Fish and Chips', 'A British classic', 'This is a detailed description of Fish and Chips.')",
            "('21', 1, 'Butter Chicken', 'Creamy Indian dish', 'This is a detailed description of Butter Chicken.')",
            "('22', 1, 'Falafel Wrap', 'Healthy Middle Eastern wrap', 'This is a detailed description of Falafel Wrap.')",
            "('23', 1, 'Apple Pie', 'Classic dessert', 'This is a detailed description of Apple Pie.')",
            "('24', 1, 'Stuffed Peppers', 'Delicious and healthy', 'This is a detailed description of Stuffed Peppers.')",
            "('25', 1, 'Clam Chowder', 'Hearty soup', 'This is a detailed description of Clam Chowder.')",
            "('26', 1, 'Gnocchi', 'Italian potato dumplings', 'This is a detailed description of Gnocchi.')",
            "('27', 1, 'Chicken Alfredo', 'Creamy pasta dish', 'This is a detailed description of Chicken Alfredo.')",
            "('28', 1, 'BBQ Ribs', 'Tender and smoky', 'This is a detailed description of BBQ Ribs.')",
            "('29', 1, 'Greek Salad', 'Fresh and light', 'This is a detailed description of Greek Salad.')",
            "('30', 1, 'Margarita', 'Classic cocktail', 'This is a detailed description of Margarita.')"
        };

        seedTable(dropTableSQL);
        seedTable(createTableSQL);

        for (String recipe : recipes) {
            String seedRecipe = "INSERT INTO `recipes` (`RecipeID`, `UserID`, `RecipeName`, `ShortDesc`, `DetailedDesc`) VALUES " + recipe + ";";
            seedTable(seedRecipe);
        }
    }

    public void seedIngredients() {
        String dropTableSQL = "DROP TABLE IF EXISTS `ingredients`;";

        String createTableSQL = "CREATE TABLE `ingredients` ("
            + "`IngredientID` varchar(45) NOT NULL, "
            + "`IngredientName` varchar(45) NOT NULL, "
            + "PRIMARY KEY (`IngredientID`));";

        String[] ingredients = new String[] {
            "('1', 'Spaghetti')",
            "('2', 'Ground Beef')",
            "('3', 'Tomato Sauce')",
            "('4', 'Chicken')",
            "('5', 'Curry Powder')",
            "('6', 'Coconut Milk')",
            "('7', 'Beef')",
            "('8', 'Potatoes')",
            "('9', 'Carrots')",
            "('10', 'Lettuce')",
            "('11', 'Croutons')",
            "('12', 'Cheese')",
            "('13', 'Bread')",
            "('14', 'Milk')",
            "('15', 'Eggs')",
            "('16', 'Flour')",
            "('17', 'Miso Paste')",
            "('18', 'Tofu')",
            "('19', 'Tortilla')",
            "('20', 'Pizza Dough')",
            "('21', 'Tomatoes')",
            "('22', 'Basil')",
            "('23', 'Lasagna Noodles')",
            "('24', 'Ricotta Cheese')",
            "('25', 'Seaweed')",
            "('26', 'Rice')",
            "('27', 'Fish')",
            "('28', 'Chocolate')",
            "('29', 'Sugar')",
            "('30', 'Ground Beef')",
            "('31', 'Buns')",
            "('32', 'Vegetables')",
            "('33', 'Rice Paper')",
            "('34', 'Lamb')",
            "('35', 'Herbs')",
            "('36', 'Fish')",
            "('37', 'Chips')",
            "('38', 'Butter')",
            "('39', 'Clams')",
            "('40', 'Potatoes')",
            "('41', 'Dough')",
            "('42', 'Olive Oil')",
            "('43', 'Garlic')",
            "('44', 'Ginger')",
            "('45', 'Chickpeas')",
            "('46', 'Apples')",
            "('47', 'Bell Peppers')",
            "('48', 'Gnocchi')",
            "('49', 'Alfredo Sauce')",
            "('50', 'Ribs')",
            "('51', 'BBQ Sauce')",
            "('52', 'Cucumber')",
            "('53', 'Olives')",
            "('54', 'Feta Cheese')",
            "('55', 'Tequila')",
            "('56', 'Lime')",
            "('57', 'Salt')"
        };

        seedTable(dropTableSQL);
        seedTable(createTableSQL);

        for (String ingredient : ingredients) {
            String seedIngredient = "INSERT INTO `ingredients` (`IngredientID`, `IngredientName`) VALUES " + ingredient + ";";
            seedTable(seedIngredient);
        }
    }

    public void seedRecipeIngredients() {
        String dropTableSQL = "DROP TABLE IF EXISTS `recipe_ingredients`;";

        String createTableSQL = "CREATE TABLE `recipe_ingredients` ("
            + "`RecipeID` varchar(255) NOT NULL, "
            + "`IngredientID` varchar(45) NOT NULL, "
            + "`Unit` varchar(100) NOT NULL,"
            + "`Amount` varchar(100) NOT NULL,"
            + "PRIMARY KEY (`RecipeID`, `IngredientID`), "
            + "FOREIGN KEY (`IngredientID`) REFERENCES `ingredients` (`IngredientID`), "
            + "FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`));";

        String[] recipeIngredients = new String[] {
            // Existing recipes...
            // New recipes
            // Butter Chicken
            "('21', '4', 'g', '500')", // Chicken
            "('21', '38', 'g', '100')", // Butter
            "('21', '5', 'tbsp', '2')", // Curry Powder
            "('21', '43', 'cloves', '4')", // Garlic
            // Falafel Wrap
            "('22', '45', 'g', '200')", // Chickpeas
            "('22', '13', 'pcs', '2')", // Bread
            "('22', '47', 'g', '100')", // Bell Peppers
            "('22', '43', 'cloves', '2')", // Garlic
            // Apple Pie
            "('23', '46', 'g', '500')", // Apples
            "('23', '29', 'g', '150')", // Sugar
            "('23', '16', 'g', '200')", // Flour
            "('23', '15', 'pcs', '2')", // Eggs
            // Stuffed Peppers
            "('24', '47', 'pcs', '4')", // Bell Peppers
            "('24', '32', 'g', '200')", // Vegetables
            "('24', '2', 'g', '200')", // Ground Beef
            // Clam Chowder
            "('25', '39', 'g', '300')", // Clams
            "('25', '8', 'g', '200')", // Potatoes
            "('25', '38', 'g', '100')", // Butter
            "('25', '14', 'ml', '200')", // Milk
            // Gnocchi
            "('26', '48', 'g', '500')", // Gnocchi
            "('26', '42', 'tbsp', '2')", // Olive Oil
            "('26', '43', 'cloves', '3')", // Garlic
            // Chicken Alfredo
            "('27', '4', 'g', '500')", // Chicken
            "('27', '49', 'ml', '300')", // Alfredo Sauce
            "('27', '16', 'g', '200')", // Flour
            "('27', '15', 'pcs', '2')", // Eggs
            // BBQ Ribs
            "('28', '50', 'g', '700')", // Ribs
            "('28', '51', 'ml', '200')", // BBQ Sauce
            "('28', '44', 'tbsp', '2')", // Ginger
            "('28', '43', 'cloves', '3')", // Garlic
            // Greek Salad
            "('29', '52', 'g', '100')", // Cucumber
            "('29', '53', 'g', '50')", // Olives
            "('29', '54', 'g', '50')", // Feta Cheese
            "('29', '42', 'tbsp', '2')", // Olive Oil
            // Margarita
            "('30', '55', 'ml', '50')", // Tequila
            "('30', '56', 'ml', '30')", // Lime
            "('30', '57', 'g', '2')" // Salt
        };

        seedTable(dropTableSQL);
        seedTable(createTableSQL);

        for (String recipeIngredient : recipeIngredients) {
            String seedRecipeIngredient = "INSERT INTO `recipe_ingredients` (`RecipeID`, `IngredientID`, `Unit`, `Amount`) VALUES " + recipeIngredient + ";";
            seedTable(seedRecipeIngredient);
        }
    }

    public void seedTags() {
        String dropTableSQL = "DROP TABLE IF EXISTS `tags`;";

        String createTableSQL = "CREATE TABLE `tags` ("
            + "`TagID` varchar(255) NOT NULL, "
            + "`TagName` varchar(45) NOT NULL, "
            + "PRIMARY KEY (`TagID`));";

        String[] tags = new String[] {
            "('1', 'Comfort Food')",
            "('2', 'Quick Meals')",
            "('3', 'Vegetarian')",
            "('4', 'Healthy')",
            "('5', 'Vegan')",
            "('6', 'Gluten-Free')",
            "('7', 'Dessert')",
            "('8', 'Breakfast')",
            "('9', 'Dinner')",
            "('10', 'Lunch')"
        };

        seedTable(dropTableSQL);
        seedTable(createTableSQL);

        for (String tag : tags) {
            String seedTag = "INSERT INTO `tags` (`TagID`, `TagName`) VALUES " + tag + ";";
            seedTable(seedTag);
        }
    }

    public void seedRecipeTags() {
        String dropTableSQL = "DROP TABLE IF EXISTS `recipe_tags`;";

        String createTableSQL = "CREATE TABLE `recipe_tags` ("
            + "`RecipeID` varchar(255) NOT NULL, "
            + "`TagID` varchar(255) NOT NULL, "
            + "PRIMARY KEY (`RecipeID`, `TagID`), " // Set composite primary key
            + "KEY `TagID_idx` (`TagID`), "
            + "KEY `RecipeID_idx` (`RecipeID`), "
            + "CONSTRAINT `Recipe_FK` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`), "
            + "CONSTRAINT `Tag_FK` FOREIGN KEY (`TagID`) REFERENCES `tags` (`TagID`));";

        String[] recipeTags = new String[] {
            // Existing recipe tags...
            // New recipe tags
            "('21', '1')",
            "('21', '2')",
            "('21', '9')",
            "('21', '10')",
            "('21', '4')",
            "('22', '3')",
            "('22', '4')",
            "('22', '5')",
            "('22', '9')",
            "('23', '1')",
            "('23', '7')",
            "('23', '9')",
            "('23', '10')",
            "('24', '1')",
            "('24', '3')",
            "('24', '4')",
            "('24', '9')",
            "('25', '1')",
            "('25', '2')",
            "('25', '9')",
            "('26', '1')",
            "('26', '2')",
            "('26', '9')",
            "('27', '1')",
            "('27', '2')",
            "('27', '9')",
            "('28', '1')",
            "('28', '2')",
            "('28', '9')",
            "('29', '3')",
            "('29', '4')",
            "('29', '9')",
            "('30', '1')",
            "('30', '9')"
        };

        seedTable(dropTableSQL);
        seedTable(createTableSQL);

        for (String recipeTag : recipeTags) {
            String seedRecipeTag = "INSERT INTO `recipe_tags` (`RecipeID`, `TagID`) VALUES " + recipeTag + ";";
            seedTable(seedRecipeTag);
        }
    }

    public void seedComments() {
        String dropTableSQL = "DROP TABLE IF EXISTS `comments`;";

        String createTableSQL = "CREATE TABLE `comments` ("
            + "`CommentID` int NOT NULL AUTO_INCREMENT, "
            + "`RecipeID` varchar(255) NOT NULL, "
            + "`UserID` int NOT NULL, "
            + "`Text` varchar(255) NOT NULL, "
            + "`Timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (`CommentID`), "
            + "KEY `RecipeID2_idx` (`RecipeID`), "
            + "KEY `UserID0_idx` (`UserID`), "
            + "CONSTRAINT `RecipeID2` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`), "
            + "CONSTRAINT `UserID0` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`));";

        seedTable(dropTableSQL);
        seedTable(createTableSQL);

        String[] recipeIds = new String[] {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"
        };

        String[] comments = new String[] {
            "This recipe is amazing!",
            "My family loved it!",
            "Will definitely make this again.",
            "Easy to follow and delicious.",
            "A new favorite in our house."
        };

        int userId = 1; 
        
        for (String recipeId : recipeIds) {
            for (int i = 0; i < comments.length; i++) {
                String seedComment = String.format(
                    "INSERT INTO `comments` (`RecipeID`, `UserID`, `Text`) VALUES ('%s', '%d', '%s');", 
                    recipeId, userId, comments[i]
                );
                seedTable(seedComment);
                userId++;
                if (userId > 3) { // Reset userId if more than 3 users
                    userId = 1;
                }
            }
        }
    }

    public void seedUserFavorites() {
        String dropTableSQL = "DROP TABLE IF EXISTS `user_favorites`;";

        String createTableSQL = "CREATE TABLE `user_favorites` ("
            + "`UserID` INT NOT NULL, "
            + "`RecipeID` varchar(255) NOT NULL, "
            + "PRIMARY KEY (`UserID`, `RecipeID`), "
            + "FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`), "
            + "FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`));";

        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedWeeklyDinnerLists() {
        String dropTableSQL = "DROP TABLE IF EXISTS `weekly_dinner_lists`;";

        String createTableSQL = "CREATE TABLE `weekly_dinner_lists` (" +
            "`WeeklyDinnerListID` int NOT NULL AUTO_INCREMENT, " +
            "`UserID` int NOT NULL, " +
            "`Week` date NOT NULL, " +
            "PRIMARY KEY (`WeeklyDinnerListID`), " +
            "FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`));";

        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedDinnerListRecipes() {
        String dropTableSQL = "DROP TABLE IF EXISTS `dinner_list_recipes`;";

        String createTableSQL = "CREATE TABLE `dinner_list_recipes` (" +
            "`DinnerListRecipeID` int NOT NULL AUTO_INCREMENT, " +
            "`WeeklyDinnerListID` int NOT NULL, " +
            "`RecipeID` varchar(255) NOT NULL, " +
            "`DayOfWeek` varchar(45) NOT NULL, " +
            "PRIMARY KEY (`DinnerListRecipeID`), " +
            "KEY `WeeklyDinnerListID0_idx` (`WeeklyDinnerListID`), " +
            "KEY `RecipeID3_idx` (`RecipeID`), " +
            "CONSTRAINT `RecipeID3` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`), " +
            "CONSTRAINT `WeeklyDinnerListID0` FOREIGN KEY (`WeeklyDinnerListID`) REFERENCES `weekly_dinner_lists` (`WeeklyDinnerListID`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";

        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedShoppingList() {
        String dropTableSQL = "DROP TABLE IF EXISTS `Shopping_List`;";

        String createTableSQL = "CREATE TABLE `Shopping_List` (" +
            "`UserID` INT NOT NULL," +
            "`ItemID` INT NOT NULL AUTO_INCREMENT," +
            "`ItemName` VARCHAR(45) NOT NULL," +
            "`Amount` FLOAT NOT NULL," +
            "`Unit` VARCHAR(45) NOT NULL," +
            "`WeeklyDinnerListID` INT," +
            " PRIMARY KEY (`ItemID`)," +
            " FOREIGN KEY (`WeeklyDinnerListID`) REFERENCES `weekly_dinner_lists`(`WeeklyDinnerListID`)," +
            " FOREIGN KEY (`UserID`) REFERENCES `users`(`UserID`)" +
            ");";

        seedTable(dropTableSQL);
        seedTable(createTableSQL);
    }

    public void seedSentRecipes() {
        String dropTableSQL = "DROP TABLE IF EXISTS `sent_recepies`;";

        String createTableSQL = "CREATE TABLE `sent_recepies` ("
            + "`SentRecipeID` int NOT NULL, "
            + "`SenderUserID` int NOT NULL, "
            + "`RecieverUserID` int NOT NULL, "
            + "`RecipeID` varchar(255) NOT NULL, "
            + "`Message` varchar(255) NOT NULL, "
            + "`Timestamp` varchar(45) NOT NULL, "
            + "PRIMARY KEY (`SentRecipeID`), "
            + "KEY `SenderUserID_idx` (`SenderUserID`), "
            + "KEY `RecieverUserID_idx` (`RecieverUserID`), "
            + "KEY `RecipeID4_idx` (`RecipeID`), "
            + "CONSTRAINT `RecieverUserID` FOREIGN KEY (`RecieverUserID`) REFERENCES `users` (`UserID`), "
            + "CONSTRAINT `RecipeID4` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`), "
            + "CONSTRAINT `SenderUserID` FOREIGN KEY (`SenderUserID`) REFERENCES `users` (`UserID`));";

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
        seeder.seedUserFavorites();
        seeder.seedRecipeTags();
        seeder.seedComments();
        seeder.seedWeeklyDinnerLists();
        seeder.seedDinnerListRecipes();
        seeder.seedShoppingList();
        seeder.seedSentRecipes();
        seeder.seedMessages();
    }
}
