package cookbook.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSeeder {

    public void seedTable(String sql) {
        DatabaseMng dbManager = new DatabaseMng(); // Create an instance of DatabaseMng
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
            "('20', 1, 'Fish and Chips', 'A British classic', 'This is a detailed description of Fish and Chips.')"
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
            "('20', 'Beef')",
            "('21', 'Pizza Dough')",
            "('22', 'Tomatoes')",
            "('23', 'Basil')",
            "('24', 'Lasagna Noodles')",
            "('25', 'Ricotta Cheese')",
            "('26', 'Seaweed')",
            "('27', 'Rice')",
            "('28', 'Fish')",
            "('29', 'Chocolate')",
            "('30', 'Sugar')",
            "('31', 'Ground Beef')",
            "('32', 'Buns')",
            "('33', 'Rice')",
            "('34', 'Vegetables')",
            "('35', 'Rice Paper')",
            "('36', 'Lamb')",
            "('37', 'Herbs')",
            "('38', 'Fish')",
            "('39', 'Chips')"
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
          // Spaghetti Bolognese
          "('1', '1', 'g', '200')", // Spaghetti
          "('1', '2', 'g', '250')", // Ground Beef
          "('1', '3', 'ml', '500')", // Tomato Sauce
          // Chicken Curry
          "('2', '4', 'g', '300')", // Chicken
          "('2', '5', 'tbsp', '2')", // Curry Powder
          "('2', '6', 'ml', '400')", // Coconut Milk
          // Beef Stew
          "('3', '7', 'g', '500')", // Beef
          "('3', '8', 'g', '200')", // Potatoes
          "('3', '9', 'g', '100')", // Carrots
          // Caesar Salad
          "('4', '10', 'g', '150')", // Lettuce
          "('4', '11', 'g', '50')", // Croutons
          "('4', '12', 'g', '30')", // Cheese
          // Grilled Cheese Sandwich
          "('5', '13', 'slices', '2')", // Bread
          "('5', '12', 'slices', '2')", // Cheese
          // Pancakes
          "('6', '14', 'ml', '200')", // Milk
          "('6', '15', 'pcs', '2')", // Eggs
          "('6', '16', 'g', '250')", // Flour
          // Miso Soup
          "('7', '17', 'tbsp', '2')", // Miso Paste
          "('7', '18', 'g', '100')", // Tofu
          // Tacos
          "('8', '19', 'pcs', '3')", // Tortilla
          "('8', '2', 'g', '250')", // Ground Beef
          // Pizza Margherita
          "('9', '21', 'g', '500')", // Pizza Dough
          "('9', '22', 'g', '200')", // Tomatoes
          "('9', '23', 'g', '20')", // Basil
          // Lasagna
          "('10', '24', 'sheets', '8')", // Lasagna Noodles
          "('10', '25', 'g', '200')", // Ricotta Cheese
          "('10', '3', 'ml', '500')", // Tomato Sauce
          // Sushi
          "('11', '26', 'sheets', '2')", // Seaweed
          "('11', '27', 'g', '200')", // Rice
          "('11', '28', 'g', '150')", // Fish
          // Chocolate Cake
          "('12', '29', 'g', '200')", // Chocolate
          "('12', '15', 'pcs', '2')", // Eggs
          "('12', '30', 'g', '150')", // Sugar
          // Burger
          "('13', '31', 'g', '200')", // Ground Beef
          "('13', '32', 'pcs', '2')", // Buns
          // Fried Rice
          "('14', '27', 'g', '200')", // Rice
          "('14', '33', 'g', '150')", // Vegetables
          // Spring Rolls
          "('15', '34', 'g', '200')", // Vegetables
          "('15', '35', 'sheets', '10')", // Rice Paper
          // Lamb Chops
          "('16', '36', 'g', '400')", // Lamb
          "('16', '37', 'g', '50')", // Herbs
          // Tom Yum Soup
          "('17', '38', 'g', '200')", // Fish
          "('17', '37', 'g', '20')", // Herbs
          // Pad Thai
          "('18', '27', 'g', '200')", // Rice
          "('18', '33', 'g', '150')", // Vegetables
          // Ratatouille
          "('19', '33', 'g', '300')", // Vegetables
          "('19', '37', 'g', '20')", // Herbs
          // Fish and Chips
          "('20', '38', 'g', '200')", // Fish
          "('20', '39', 'g', '200')" // Chips
      };
  
      seedTable(dropTableSQL);
      seedTable(createTableSQL);
  
      for (String recipeIngredient : recipeIngredients) {
          String seedRecipeIngredient = "INSERT INTO `recipe_ingredients` (`RecipeID`, `IngredientID`, `Unit`, `Amount`) VALUES " + recipeIngredient + ";";
          seedTable(seedRecipeIngredient);
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
            "('1', '1')",
            "('1', '2')",
            "('1', '9')",
            "('1', '10')",
            "('1', '4')",
            
            "('2', '1')",
            "('2', '2')",
            "('2', '9')",
            "('2', '10')",
            "('2', '4')",
            
            "('3', '1')",
            "('3', '2')",
            "('3', '9')",
            "('3', '10')",
            "('3', '4')",
            
            "('4', '3')",
            "('4', '4')",
            "('4', '9')",
            "('4', '10')",
            "('4', '6')",
            
            "('5', '1')",
            "('5', '2')",
            "('5', '9')",
            "('5', '10')",
            "('5', '4')",
            
            "('6', '8')",
            "('6', '2')",
            "('6', '9')",
            "('6', '10')",
            "('6', '4')",
            
            "('7', '4')",
            "('7', '5')",
            "('7', '9')",
            "('7', '10')",
            "('7', '6')",
            
            "('8', '1')",
            "('8', '2')",
            "('8', '9')",
            "('8', '10')",
            "('8', '4')",
            
            "('9', '1')",
            "('9', '2')",
            "('9', '9')",
            "('9', '10')",
            "('9', '4')",
            
            "('10', '1')",
            "('10', '2')",
            "('10', '9')",
            "('10', '10')",
            "('10', '4')",
            
            "('11', '1')",
            "('11', '2')",
            "('11', '9')",
            "('11', '10')",
            "('11', '4')",
            
            "('12', '1')",
            "('12', '7')",
            "('12', '9')",
            "('12', '10')",
            "('12', '4')",
            
            "('13', '1')",
            "('13', '2')",
            "('13', '9')",
            "('13', '10')",
            "('13', '4')",
            
            "('14', '1')",
            "('14', '2')",
            "('14', '9')",
            "('14', '10')",
            "('14', '4')",
            
            "('15', '1')",
            "('15', '2')",
            "('15', '9')",
            "('15', '10')",
            "('15', '4')",
            
            "('16', '1')",
            "('16', '2')",
            "('16', '9')",
            "('16', '10')",
            "('16', '4')",
            
            "('17', '1')",
            "('17', '2')",
            "('17', '9')",
            "('17', '10')",
            "('17', '4')",
            
            "('18', '1')",
            "('18', '2')",
            "('18', '9')",
            "('18', '10')",
            "('18', '4')",
            
            "('19', '1')",
            "('19', '3')",
            "('19', '9')",
            "('19', '10')",
            "('19', '4')",
            
            "('20', '1')",
            "('20', '2')",
            "('20', '9')",
            "('20', '10')",
            "('20', '4')"
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
          + "`Timestamp` varchar(45) NOT NULL, "
          + "PRIMARY KEY (`CommentID`), "
          + "KEY `RecipeID2_idx` (`RecipeID`), "
          + "KEY `UserID0_idx` (`UserID`), "
          + "CONSTRAINT `RecipeID2` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`), "
          + "CONSTRAINT `UserID0` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`));";
  
            //Sample comments for variety
            String[] comments = {
              "Great recipe!", "Loved it!", "Will make it again!", "Delicious!", "Very tasty!",
              "Yummy!", "Fantastic!", "Amazing!", "So good!", "Perfect!",
              "Loved the taste!", "Would make again!", "So easy to make!", "Absolutely delicious!", "A new favorite!",
              "Highly recommend!", "Five stars!", "Best recipe ever!", "Superb!", "Will make this often!"
            };

            seedTable(dropTableSQL);
            seedTable(createTableSQL);

            for (int i = 1; i <= 20; i++) {
              for (int j = 0; j < 5; j++) { // Add 5 comments per recipe
                  int commentIndex = (i - 1) * 5 + j;
                  String insertComment = String.format(
                      "INSERT INTO `comments` (`RecipeID`, `UserID`, `Text`, `Timestamp`) VALUES ('%d', 1, '%s', '2024-05-15 %02d:00:00');",
                      i, comments[commentIndex % comments.length], 9 + i
                  );
                  seedTable(insertComment);
              }}}


  


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

    public void seedShoppingList() {
        // SQL command to drop the table if it already exists
        String dropTableSQL = "DROP TABLE IF EXISTS `Shopping_List`;";

        // SQL command to create the table
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

        // Execute the SQL commands to seed the table
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
