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
        + "`Unit` varchar(1000) NOT NULL, "
        + "`Amount` varchar(1000) NOT NULL, "
        + "`IsFavorite` tinyint, "
        + "PRIMARY KEY (`RecipeID`), "
        + "UNIQUE KEY `RecipeID_UNIQUE` (`RecipeID`), "
        + "KEY `UserID_idx` (`UserID`), "
        + "CONSTRAINT `UserID` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`));";

    String[] insertDataSQL = new String[20];  // Array to hold SQL commands for each recipe
    insertDataSQL[0] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('1', 1, 'Creamy Tomato Soup', 'A comforting, creamy blend of tomatoes and herbs.', 'Perfect for chilly evenings. Rich with cream and parmesan, complemented by fresh tomatoes.', 'servings', '4');";
    insertDataSQL[1] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('2', 1, 'Spicy Shrimp Tacos', 'Tasty shrimp with a kick, wrapped in a soft tortilla.', 'Juicy shrimp seasoned with chili and lime, topped with fresh salsa and avocado.', 'servings', '4');";
    insertDataSQL[2] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('3', 1, 'Roasted Vegetable Quinoa Bowl', 'A warm bowl of healthy goodness.', 'Roasted seasonal vegetables over fluffy quinoa, drizzled with a balsamic glaze.', 'servings', '4');";
    insertDataSQL[3] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('4', 1, 'Chicken Fajita Wrap', 'Spicy chicken with crunchy veggies.', 'Chicken strips with bell peppers and onions, served in a warm tortilla with sour cream.', 'servings', '4');";
    insertDataSQL[4] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('5', 1, 'Baked Salmon with Lemon and Herbs', 'Lemon and herb infused salmon.', 'Oven-baked salmon seasoned with fresh herbs and a zesty lemon butter sauce.', 'servings', '4');";
    insertDataSQL[5] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('6', 1, 'Creamy Mushroom Pasta', 'Comforting pasta in a creamy sauce.', 'Penne pasta smothered in a rich, garlic mushroom cream sauce.', 'servings', '4');";
    insertDataSQL[6] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('7', 1, 'Beef Stroganoff', 'Rich and creamy Russian classic.', 'Tender slices of beef and mushrooms in a savory sour cream sauce served over egg noodles.', 'servings', '4');";
    insertDataSQL[7] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('8', 1, 'Thai Green Curry', 'Spicy and aromatic Thai favorite.', 'Green curry paste cooked with coconut milk, chicken, and mixed vegetables.', 'servings', '4');";
    insertDataSQL[8] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('9', 1, 'Vegetable Stir Fry', 'Quick and colorful veggie dish.', 'A variety of fresh vegetables sautéed with soy sauce and sesame oil.', 'servings', '4');";
    insertDataSQL[9] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('10', 1, 'Classic Margherita Pizza', 'Simple and fresh.', 'A thin crust pizza topped with tomato sauce, fresh mozzarella, and basil leaves.', 'servings', '2');";
    insertDataSQL[10] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('11', 1, 'Pumpkin Spice Latte', 'Warm and seasonal favorite.', 'Espresso mixed with steamed milk, pumpkin spice syrup, topped with whipped cream and a sprinkle of cinnamon.', 'servings', '1');";
    insertDataSQL[11] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('12', 1, 'Maple Glazed Salmon', 'Sweet and savory delight.', 'Fresh salmon fillet glazed with a sweet maple syrup sauce.', 'servings', '4');";
    insertDataSQL[12] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('13', 1, 'Butternut Squash Soup', 'Creamy and comforting.', 'Smooth soup made from roasted butternut squash, onions, and a hint of nutmeg.', 'servings', '4');";
    insertDataSQL[13] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('14', 1, 'Grilled Cheese Sandwich', 'Classic comfort food.', 'Crispy bread grilled with melted cheese inside, served with tomato soup.', 'servings', '1');";
    insertDataSQL[14] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('15', 1, 'Mediterranean Chickpea Salad', 'Light and refreshing.', 'Chickpeas mixed with diced cucumbers, tomatoes, red onion, feta cheese, and olives, dressed with olive oil and lemon juice.', 'servings', '4');";
    insertDataSQL[15] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('16', 1, 'BBQ Ribs', 'Tender and smoky.', 'Slow-cooked ribs coated in a homemade BBQ sauce, grilled to perfection.', 'servings', '4');";
    insertDataSQL[16] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('17', 1, 'Garlic Butter Shrimp', 'Quick and flavorful.', 'Shrimp sautéed in a rich sauce of butter, garlic, and parsley.', 'servings', '4');";
    insertDataSQL[17] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('18', 1, 'Chocolate Lava Cake', 'Decadent dessert.', 'Rich chocolate cake with a gooey molten chocolate center, served warm.', 'servings', '4');";
    insertDataSQL[18] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('19', 1, 'Caesar Salad', 'Classic and crisp.', 'Crisp romaine lettuce tossed with Caesar dressing, croutons, and Parmesan cheese.', 'servings', '4');";
    insertDataSQL[19] = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES ('20', 1, 'Lemon Herb Roasted Chicken', 'Juicy and flavorful.', 'Whole chicken roasted with a blend of lemon, garlic, and herbs.', 'servings', '4');";
    
    seedTable(dropTableSQL);
    seedTable(createTableSQL);
    for (String sql : insertDataSQL) {
        seedTable(sql);     // Seed each recipe
    }
  }


  
  public void seedIngredients() {
    String dropTableSQL = "DROP TABLE IF EXISTS `ingredients`;";

    String createTableSQL = "CREATE TABLE `ingredients` ("
        + "`IngredientID` varchar(45) NOT NULL, "
        + "`IngredientName` varchar(45) NOT NULL, "
        + "PRIMARY KEY (`IngredientID`));";
    
    String seedIngredients = "INSERT INTO `ingredients` (IngredientID, IngredientName) VALUES " +
    "('1', 'Tomato'), " +
    "('2', 'Shrimp'), " +
    "('3', 'Quinoa'), " +
    "('4', 'Chicken'), " +
    "('5', 'Salmon'), " +
    "('6', 'Mushroom'), " +
    "('7', 'Beef'), " +
    "('8', 'Coconut Milk'), " +
    "('9', 'Soy Sauce'), " +
    "('10', 'Mozzarella');";
    seedTable(dropTableSQL);
    seedTable(createTableSQL);
    seedTable(seedIngredients);
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

    String seedRecipeIngredients = "INSERT INTO `recipe_ingredients` (RecipeID, IngredientID, Unit, Amount) VALUES " +
    "('1', '1', 'cups', '2'), " +
    "('2', '2', 'pieces', '12'), " +
    "('3', '3', 'cups', '1'), " +
    "('4', '4', 'grams', '300'), " +
    "('5', '5', 'fillets', '4'), " +
    "('6', '6', 'grams', '200'), " +
    "('7', '7', 'grams', '500'), " +
    "('8', '8', 'ml', '400'), " +
    "('9', '9', 'ml', '100'), " +
    "('10', '10', 'grams', '250');";

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
    seedTable(seedRecipeIngredients);
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
    String seedTagValues = "INSERT INTO `tags` (`TagID`, `TagName`) VALUES ('1', 'Comfort Food'), ('2', 'Quick Meals'), ('3', 'Vegetarian');";
    seedTable(dropTableSQL);
    seedTable(createTableSQL);
    seedTable(seedTagValues);
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

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
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
        + "CONSTRAINT `UserID0` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`))";
    String[] insertCommentValues = new String[29];

    // Recipe ID 1

insertCommentValues[0] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('1', 1, 'Absolutely love this soup, its a family favorite now!', '2023-05-05 17:30:00');";

insertCommentValues[1] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('1', 2, 'The vegetables are so fresh and crunchy!', '2023-05-06 10:00:00');";

insertCommentValues[2] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('1', 1, 'A healthy and delicious soup!', '2023-05-07 14:30:00');";

insertCommentValues[3] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('1', 2, 'So easy to make and so tasty!', '2023-05-08 18:00:00');";

insertCommentValues[4] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('1', 2, 'Will definitely make this soup again!', '2023-05-09 21:30:00');";


// Recipe ID 2

insertCommentValues[5] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('2', 1, 'A perfect dish for a quick lunch!', '2023-05-10 12:15:00');";

insertCommentValues[6] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('2', 2, 'The cheese and tomato combination is amazing!', '2023-05-11 17:00:00');";

insertCommentValues[7] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('2', 2, 'So easy to prepare and so delicious!', '2023-05-12 20:30:00');";

insertCommentValues[8] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('2', 2, 'Will make this for my family soon!', '2023-05-13 11:15:00');";

insertCommentValues[9] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('2', 2, 'A great vegetarian option!', '2023-05-14 18:45:00');";


// Recipe ID 3

insertCommentValues[10] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('3', 2, 'A delicious and healthy salad!', '2023-05-15 13:30:00');";

insertCommentValues[11] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('3', 1, 'The avocado and chicken are a great combination!', '2023-05-16 19:00:00');";

insertCommentValues[12] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('3', 2, 'A perfect lunch option!', '2023-05-17 11:15:00');";

insertCommentValues[13] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('3', 2, 'Will make this again for sure!', '2023-05-18 17:45:00');";

insertCommentValues[14] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('3', 2, 'So easy to make and so tasty!', '2023-05-19 20:00:00');";


// Recipe ID 4

insertCommentValues[15] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('4', 1, 'A perfect dinner option!', '2023-05-20 18:30:00');";

insertCommentValues[16] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('4', 1, 'The steak is so tender and juicy!', '2023-05-21 13:00:00');";

insertCommentValues[17] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('4', 1, 'The mushrooms and onions add so much flavor!', '2023-05-22 20:15:00');";

insertCommentValues[18] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('4', 2, 'A great recipe for a special occasion!', '2023-05-23 12:00:00');";

insertCommentValues[19] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('4', 1, 'Will definitely make this again!', '2023-05-24 17:30:00');";


// Recipe ID 5

insertCommentValues[20] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('5', 1, 'A perfect breakfast option!', '2023-05-25 09:00:00');";

insertCommentValues[21] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('5', 2, 'The pancakes are so fluffy and delicious!', '2023-05-26 13:30:00');";

insertCommentValues[22] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('5', 1, 'A great recipe for a weekend morning!', '2023-05-27 10:45:00');";

insertCommentValues[23] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('5', 1, 'Will make this again for sure!', '2023-05-28 18:00:00');";

insertCommentValues[24] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('5', 1, 'So easy to make and so tasty!', '2023-05-29 21:30:00');";


// Recipe ID 6

insertCommentValues[25] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('6', 1, 'A perfect dinner option!', '2023-05-30 19:00:00');";

insertCommentValues[26] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('6', 2, 'The chicken is so tender and juicy!', '2023-05-31 13:30:00');";

insertCommentValues[27] = "INSERT INTO `comments` (RecipeID, UserID, Text, Timestamp) VALUES ('6', 1, 'The lemon and herbs add so much flavor!', '2023-06-01 11:00:00');";

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
    for (String sql : insertCommentValues) {
        seedTable(sql);     // Seed each comment
    }
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
        + "CONSTRAINT `SenderUserID` FOREIGN KEY (`SenderUserID`) REFERENCES `users` (`UserID`))";

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
  }


  public void seedShoppingList() {
    // SQL command to drop the table if it already exists
    String dropTableSQL = "DROP TABLE IF EXISTS `Shopping_List`;";

    // SQL command to create the table
    String createTableSQL = "CREATE TABLE `Shopping_List` (" +
    "`UserID` INT NOT NULL," +  // Ensure there's a space between `UserID` and its type
    "`ItemID` INT NOT NULL AUTO_INCREMENT," +  // Auto-incrementing ID for each item
    "`ItemName` VARCHAR(45) NOT NULL," +  // Name of the shopping item
    "`Amount` FLOAT NOT NULL," +  // Amount of each item needed
    "`Unit` VARCHAR(45) NOT NULL," +  // Unit of measurement for the amount
    "`WeeklyDinnerListID` INT," +  // Reference to the weekly dinner list
    " PRIMARY KEY (`ItemID`)," +
    " FOREIGN KEY (`WeeklyDinnerListID`) REFERENCES `weekly_dinner_lists`(`WeeklyDinnerListID`)," + // Add a comma here
    " FOREIGN KEY (`UserID`) REFERENCES `users`(`UserID`)" + // Foreign key linking to user
    ");";  // Ensure the statement ends with a semicolon


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
