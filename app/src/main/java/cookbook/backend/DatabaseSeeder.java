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

    // Inserts a new user with username 'user' and password 'user'
    String seedUserValues = "INSERT INTO `users` (`UserName`, `DisplayName`, `Password`, `IsAdmin`) "
        + "VALUES ('user', 'User', '4f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 0);";

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
    seedTable(seedUserValues);
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

    // String insertDataSQL = "INSERT INTO recipes (RecipeID, UserID, RecipeName,
    // ShortDesc, DetailedDesc, Unit, Amount)"
    // + " VALUES (1, 1, 'placeHolderName', 'placeHolderShort', 'placeHolderLong',
    // 'Servings amount test', 10);";

    String insertDataSQL1 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (1, 1, 'Creamy Tomato Soup', 'A comforting and creamy soup made with fresh tomatoes and herbs.', 'This recipe is a classic comfort food dish that''s perfect for a chilly evening. The creamy texture comes from a mixture of heavy cream and Parmesan cheese, while the fresh tomatoes add a burst of flavor.', 'Servings', 6);";

    String insertDataSQL2 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (2, 1, 'Spicy Shrimp Tacos', 'A flavorful and spicy taco recipe featuring succulent shrimp and crispy tortillas.', 'This recipe is a twist on traditional tacos, with succulent shrimp marinated in a spicy mixture of chili flakes, lime juice, and cumin. Serve with crispy tortillas, sliced avocado, and a dollop of sour cream.', 'Tacos', 8);";

    String insertDataSQL3 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (3, 1, 'Roasted Vegetable Quinoa Bowl', 'A healthy and flavorful bowl filled with roasted vegetables and quinoa.', 'This recipe is a great way to get your daily dose of veggies, with a variety of colorful vegetables roasted to perfection and served over a bed of quinoa. Add a dollop of tzatziki sauce for extra creaminess.', 'Servings', 6);";

    String insertDataSQL4 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (4, 1, 'Chicken Fajita Wrap', 'A flavorful and easy wrap recipe featuring chicken, peppers, and onions.', 'This recipe is a quick and easy lunch or dinner option, with marinated chicken breast cooked with sliced peppers and onions and wrapped in a warm flour tortilla.', 'Wraps', 6);";

    String insertDataSQL5 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (5, 1, 'Baked Salmon with Lemon and Herbs', 'A flavorful and healthy salmon recipe featuring a bright and citrusy sauce.', 'This recipe is a great way to prepare salmon, with a flavorful sauce made from lemon juice, olive oil, and herbs like thyme and parsley. Serve with roasted vegetables and quinoa for a well-rounded meal.', 'Servings', 6);";

    String insertDataSQL6 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (6, 1, 'Creamy Mushroom Pasta', 'A rich and creamy pasta recipe featuring sautéed mushrooms and a velvety sauce.', 'This recipe is a comforting and indulgent dish, with sautéed mushrooms and a creamy sauce made from heavy cream and Parmesan cheese. Serve with-cooked pasta and a sprinkle of parsley.', 'Servings', 6);";

    String insertDataSQL7 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (7, 1, 'Grilled Chicken and Pineapple Skewers', 'A colorful and flavorful skewer recipe featuring chicken, pineapple, and bell peppers.', 'This recipe is a great way to enjoy the flavors of summer, with marinated chicken breast, pineapple, and bell peppers grilled to perfection. Serve with a side of quinoa and steamed vegetables.', 'Skewers', 8);";

    String insertDataSQL8 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (8, 1, 'Spinach and Feta Stuffed Chicken Breast', 'A flavorful and healthy chicken recipe featuring a creamy spinach and feta filling.', 'This recipe is a great way to add some excitement to your chicken breast, with a creamy filling made from spinach, feta cheese, and lemon zest. Serve with roasted vegetables and quinoa for a well-rounded meal.', 'Servings', 6);";

    String insertDataSQL9 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (9, 1, 'Lentil Soup with Crusty Bread', 'A hearty and comforting soup recipe featuring red lentils and crusty bread.', 'This recipe is a great way to warm up on a chilly day, with a flavorful soup made from red lentils, onions, and carrots. Serve with a side of crusty bread for dipping.', 'Servings', 6);";

    String insertDataSQL10 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (10, 1, 'Pan-Seared Steak with Garlic Butter', 'A flavorful and indulgent steak recipe featuring a rich garlic butter sauce.', 'This recipe is a great way to treat yourself to a special dinner, with a tender steak cooked to perfection and served with a rich garlic butter sauce. Serve with roasted vegetables and mashed potatoes.', 'Servings', 4);";

    String insertDataSQL11 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (11, 1, 'Roasted Sweet Potato and Black Bean Tacos', 'A flavorful and healthy taco recipe featuring roasted sweet potatoes and black beans.', 'This recipe is a great way to enjoy a vegetarian meal, with roasted sweet potatoes and black beans served in warm tortillas. Top with sliced avocado, salsa, and a dollop of sour cream.', 'Tacos', 8);";

    String insertDataSQL12 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (12, 1, 'Creamy Broccoli and Cheddar Soup', 'A comforting and creamy soup recipe featuring broccoli and sharp cheddar cheese.', 'This recipe is a great way to enjoy a classic comfort food dish, with a creamy soup made from broccoli, onions, and chicken broth. Serve with a side of crusty bread for dipping.', 'Servings', 6);";

    String insertDataSQL13 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (13, 1, 'Chickpea and Spinach Curry', 'A flavorful and healthy curry recipe featuring chickpeas and spinach.', 'This recipe is a great way to enjoy a vegetarian meal, with a flavorful curry made from chickpeas, spinach, and a variety of spices. Serve with cooked rice and a side of naan bread.', 'Servings', 6);";

    String insertDataSQL14 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (14, 1, 'Baked Lemon Herb Salmon', 'A flavorful and healthy salmon recipe featuring a bright and citrusy marinade.', 'This recipe is a great way to prepare salmon, with a flavorful marinade made from lemon juice, olive oil, and herbs like thyme and parsley. Serve with roasted vegetables and quinoa for a well-rounded meal.', 'Servings', 6);";

    String insertDataSQL15 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (15, 1, 'Creamy Tomato Basil Pasta', 'A rich and creamy pasta recipe featuring fresh tomatoes and basil.', 'This recipe is a comforting and indulgent dish, with sautéed tomatoes and a creamy sauce made from heavy cream and Parmesan cheese. Serve with cooked pasta and a sprinkle of basil.', 'Servings', 6);";

    String insertDataSQL16 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (16, 1, 'Grilled Chicken and Vegetable Kabobs', 'A colorful and flavorful kabob recipe featuring chicken, vegetables, and a tangy marinade.', 'This recipe is a great way to enjoy the flavors of summer, with marinated chicken breast, vegetables, and a tangy marinade grilled to perfection. Serve with a side of quinoa and steamed vegetables.', 'Kabobs', 8);";

    String insertDataSQL17 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (17, 1, 'Spinach and Feta Stuffed Portobello Mushrooms', 'A flavorful and healthy vegetarian recipe featuring stuffed portobello mushrooms.', 'This recipe is a great way to enjoy a vegetarian meal, with portobello mushrooms stuffed with a creamy filling made from spinach, feta cheese, and lemon zest. Serve with roasted vegetables and quinoa for a well-rounded meal.', 'Servings', 6);";

    String insertDataSQL18 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (18, 1, 'Lentil and Vegetable Stew', 'A hearty and comforting stew recipe featuring lentils and a variety of vegetables.', 'This recipe is a great way to warm up on a chilly day, with a flavorful stew made from lentils, onions, carrots, and celery. Serve with a side of crusty bread for dipping.', 'Servings', 6);";

    String insertDataSQL19 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (19, 1, 'Pan-Seared Scallops with Lemon Butter', 'A flavorful and indulgent scallop recipe featuring a rich lemon butter sauce.', 'This recipe is a great way to treat yourself to a special dinner, with tender scallops cooked to perfection and served with a rich lemon butter sauce. Serve with roasted vegetables and mashed potatoes.', 'Servings', 4);";

    String insertDataSQL20 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (20, 1, 'Roasted Vegetable and Quinoa Salad', 'A healthy and flavorful salad recipe featuring roasted vegetables and quinoa.', 'This recipe is a great way to enjoy a vegetarian meal, with a variety of colorful vegetables roasted to perfection and served over a bed of quinoa. Add a dollop of tzatziki sauce for extra creaminess.', 'Servings', 6);";

    String insertDataSQL21 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (21, 1, 'Chicken and Vegetable Stir-Fry', 'A flavorful and healthy stir-fry recipe featuring chicken and a variety of vegetables.', 'This recipe is a quick and easy dinner option, with marinated chicken breast and sliced vegetables cookedin a flavorful sauce. Serve with cooked rice or noodles.', 'Servings', 6);";

    String insertDataSQL22 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (22, 1, 'Baked Zucchini Fries', 'A healthy and flavorful snack recipe featuring crispy baked zucchini fries.', 'This recipe is a great way to enjoy a healthy snack, with zucchini slices coated in a crispy breadcrumb mixture and baked to perfection. Serve with a side of marinara sauce for dipping.', 'Servings', 6);";

    String insertDataSQL23 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (23, 1, 'Creamy Avocado Pasta', 'A rich and creamy pasta recipe featuring ripe avocados and a tangy lime dressing.', 'This recipe is a comforting and indulgent dish, with a creamy sauce made from ripe avocados, garlic, and lime juice. Serve with cooked pasta and a sprinkle of parsley.', 'Servings', 6);";

    String insertDataSQL24 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (24, 1, 'Grilled Chicken and Pineapple Salad', 'A colorful and flavorful salad recipe featuring grilled chicken, pineapple, and a tangy vinaigrette.', 'This recipe is a great way to enjoy the flavors of summer, with marinated chicken breast, grilled pineapple, and a tangy vinaigrette. Serve with a side of quinoa or brown rice.', 'Servings', 6);";

    String insertDataSQL25 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (25, 1, 'Spicy Shrimp and Vegetable Stir-Fry', 'A flavorful and spicy stir-fry recipe featuring succulent shrimp and a variety of vegetables.', 'This recipe is a quick and easy dinner option, with succulent shrimp and sliced vegetables cooked in a spicy sauce. Serve with cooked rice or noodles.', 'Servings', 6);";

    String insertDataSQL26 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (26, 1, 'Baked Sweet Potato Fries', 'A healthy and flavorful snack recipe featuring crispy baked sweet potato fries.', 'This recipe is a great way to enjoy a healthy snack, with sweet potato slices coated in a crispy breadcrumb mixture and baked to perfection. Serve with a side of ketchup or aioli for dipping.', 'Servings', 6);";

    String insertDataSQL27 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (27, 1, 'Creamy Mushroom and Spinach Pasta', 'A rich and creamy pasta recipe featuring sautéed mushrooms, spinach, and a velvety sauce.', 'This recipe is a comforting and indulgent dish, with sautéed mushrooms and spinach and a creamy sauce made from heavy cream and Parmesan cheese. Serve with cooked pasta and a sprinkle of parsley.', 'Servings', 6);";

    String insertDataSQL28 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (28, 1, 'Grilled Chicken Shawarma Bowl', 'A flavorful and healthy bowl recipe featuring grilled chicken, vegetables, and a tangy yogurt sauce.', 'This recipe is a great way to enjoy a Mediterranean-inspired meal, with marinated chicken breast, grilled vegetables, and a tangy yogurt sauce. Serve with cooked quinoa or rice and a side of pita bread.', 'Bowls', 6);";

    String insertDataSQL29 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (29, 1, 'Vegetarian Chili', 'A hearty and healthy chili recipe featuring a variety of beans and vegetables.', 'This recipe is a great way to enjoy a vegetarian meal, with a variety of beans and vegetables simmered in a flavorful tomato sauce. Serve with a side of cornbread or crackers.', 'Servings', 6);";

    String insertDataSQL30 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (30, 1, 'Baked Lemon Herb Chicken', 'A flavorful and healthy chicken recipe featuring a bright and citrusy marinade.', 'This recipe is a great way to prepare chicken, with a flavorful marinade made from lemon juice, olive oil, and herbs like thyme and parsley. Serve with roasted vegetables and quinoa for a well-rounded meal.', 'Servings', 6);";

    String insertDataSQL31 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (31, 1, 'Creamy Pumpkin Pasta', 'A rich and creamy pasta recipe featuring roasted pumpkin and a velvety sauce.', 'This recipe is a comforting and indulgent dish, with roasted pumpkin and a creamy sauce made from heavy cream and Parmesan cheese. Serve with cooked pasta and a sprinkle of parsley.', 'Servings', 6);";

    String insertDataSQL32 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (32, 1, 'Grilled Vegetable Skewers', 'A colorful and flavorful skewer recipe featuring a variety of grilled vegetables.', 'This recipe is a great way to enjoy the flavors of summer, with a variety of vegetables grilled to perfection. Serve with a side of quinoa and a dollop of tzatziki sauce.', 'Skewers', 8);";

    String insertDataSQL33 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (33, 1, 'Spinach and Artichoke Dip', 'A creamy and flavorful dip recipe featuring spinach, artichokes, and Parmesan cheese.', 'This recipe is a great way to enjoy a party favorite, with a creamy dip made from spinach, artichokes, and Parmesan cheese. Serve with a side of pita chips or veggies.', 'Dips', 6);";

    String insertDataSQL34 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (34, 1, 'Baked Sweet Potato', 'A healthy and flavorful side dish recipe featuring a baked sweet potato.', 'This recipe is a great way to enjoy a healthy side dish, with a baked sweet potato topped with a dollop of Greek yogurt and a sprinkle of cinnamon.', 'Servings', 2);";

    String insertDataSQL35 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (35, 1, 'Creamy Tomato Risotto', 'A rich and creamy rice dish recipe featuring sautéed tomatoes and a velvety sauce.', 'This recipe is a comforting and indulgent dish, with sautéed tomatoes and a creamy sauce made from heavy cream and Parmesan cheese. Serve with a side of roasted vegetables and a sprinkle of parsley.', 'Servings', 6);";

    String insertDataSQL36 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (36, 1, 'Grilled Steak Salad', 'A colorful and flavorful salad recipe featuring grilled steak, vegetables, and a tangy vinaigrette.', 'This recipe is a great way to enjoy a hearty salad, with grilled steak, vegetables, and atangy vinaigrette. Serve with a side of crusty bread or quinoa.', 'Salads', 6);";

    String insertDataSQL37 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (37, 1, 'Spicy Shrimp and Avocado Toast', 'A flavorful and healthy breakfast or snack recipe featuring spicy shrimp and creamy avocado.', 'This recipe is a great way to start your day or enjoy a healthy snack, with spicy shrimp and creamy avocado served on toasted bread. Serve with a side of fruit or a green smoothie.', 'Toasts', 4);";

    String insertDataSQL38 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (38, 1, 'Baked Lemon Herb Tilapia', 'A flavorful and healthy fish recipe featuring a bright and citrusy marinade.', 'This recipe is a great way to prepare fish, with a flavorful marinade made from lemon juice, olive oil, and herbs like thyme and parsley. Serve with roasted vegetables and quinoa for a well-rounded meal.', 'Servings', 6);";

    String insertDataSQL39 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (39, 1, 'Creamy Roasted Red Pepper Pasta', 'A rich and creamy pasta recipe featuring roasted red peppers and a velvety sauce.', 'This recipe is a comforting and indulgent dish, with roasted red peppers and a creamy sauce made from heavy cream and Parmesan cheese. Serve with cooked pasta and a sprinkle of parsley.', 'Servings', 6);";

    String insertDataSQL40 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (40, 1, 'Grilled Chicken and Vegetable Quinoa Bowl', 'A healthy and flavorful bowl recipe featuring grilled chicken, vegetables, and quinoa.', 'This recipe is a great way to enjoy a balanced meal, with grilled chicken, vegetables, and quinoa. Serve with a side of hummus or tzatziki sauce.', 'Bowls', 6);";

    String insertDataSQL41 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (41, 1, 'Spicy Black Bean and Sweet Potato Tacos', 'A flavorful and healthy taco recipe featuring spicy black beans and roasted sweet potatoes.', 'This recipe is a great way to enjoy a vegetarian meal, with spicy black beans and roasted sweet potatoes served in warm tortillas. Top with sliced avocado, salsa, and a dollop of sour cream.', 'Tacos', 8);";

    String insertDataSQL42 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (42, 1, 'Baked Zucchini Boats', 'A healthy and flavorful side dish recipe featuring baked zucchini boats.', 'This recipe is a great way to enjoy a healthy side dish, with zucchini boats filled with a mixture of tomatoes, onions, and Parmesan cheese. Serve with a side of roasted vegetables or a salad.', 'Servings', 6);";

    String insertDataSQL43 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (43, 1, 'Creamy Butternut Squash Pasta', 'A rich and creamy pasta recipe featuring roasted butternut squash and a velvety sauce.', 'This recipe is a comforting and indulgent dish, with roasted butternut squash and a creamy sauce made from heavy cream and Parmesan cheese. Serve with cooked pasta and a sprinkle of parsley.', 'Servings', 6);";

    String insertDataSQL44 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (44, 1, 'Grilled Vegetable and Goat Cheese Pizza', 'A colorful and flavorful pizza recipe featuring grilled vegetables and creamy goat cheese.', 'This recipe is a great way to enjoy a vegetarian meal, with grilled vegetables and creamy goat cheese served on a crispy pizza crust. Serve with a side of arugula or mixed greens.', 'Pizzas', 4);";

    String insertDataSQL45 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (45, 1, 'Spicy Shrimp and Pineapple Fried Rice', 'A flavorful and spicy rice dish recipe featuring succulent shrimp and sweet pineapple.', 'This recipe is a quick and easy dinner option, with succulent shrimp and sweet pineapple cooked in a spicy sauce and served over cooked rice. Serve with a side of steamed vegetables or a salad.', 'Servings', 6);";

    String insertDataSQL46 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (46, 1, 'Baked Lemon Herb Cod', 'A flavorful and healthy fish recipe featuring a bright and citrusy marinade.', 'This recipe is a great way to prepare fish, with a flavorful marinade made from lemon juice, olive oil, and herbs like thyme and parsley. Serve with roasted vegetables and quinoa for a well-rounded meal.', 'Servings', 6);";

    String insertDataSQL47 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (47, 1, 'Creamy Mushroom and Leek Pasta', 'A rich and creamy pasta recipe featuring sautéed mushrooms and leeks and a velvety sauce.', 'This recipe is a comforting and indulgent dish, with sautéed mushrooms and leeks and a creamy sauce made from heavy cream and Parmesan cheese. Serve with cooked pasta and a sprinkle of parsley.', 'Servings', 6);";

    String insertDataSQL48 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (48, 1, 'Grilled Chicken and Avocado Salad', 'A colorful and flavorful salad recipe featuring grilled chicken, avocado, and a tangy vinaigrette.', 'This recipe is a great way to enjoy a hearty salad, with grilled chicken, avocado, and a tangy vinaigrette. Serve with a side of crusty bread or quinoa.', 'Salads', 6);";

    String insertDataSQL49 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (49, 1, 'Spicy Shrimp and Quinoa Bowl', 'A flavorful and healthy bowl recipe featuring spicy shrimp and quinoa.', 'This recipe is a great way to enjoy a balanced meal, with spicy shrimp and quinoa. Serve with a side of steamed vegetables or a salad.', 'Bowls', 6);";

    String insertDataSQL50 = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount)"
        + "VALUES (50, 1, 'Baked Lemon Herb Salmon and Asparagus', 'A flavorful and healthy fish and vegetable recipe featuring a bright and citrusy marinade.', 'This recipe is a great way to prepare fish and vegetables, with a flavorful marinade made from lemon juice, olive oil, and herbs like thyme and parsley. Serve with a side of quinoa or brown rice.', 'Servings', 6);";

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
    // seedTable(insertDataSQL);
    seedTable(insertDataSQL1);
    seedTable(insertDataSQL2);
    seedTable(insertDataSQL3);
    seedTable(insertDataSQL4);
    seedTable(insertDataSQL5);
    seedTable(insertDataSQL6);
    seedTable(insertDataSQL7);
    seedTable(insertDataSQL8);
    seedTable(insertDataSQL9);
    seedTable(insertDataSQL10);
    seedTable(insertDataSQL11);
    seedTable(insertDataSQL12);
    seedTable(insertDataSQL13);
    seedTable(insertDataSQL14);
    seedTable(insertDataSQL15);
    seedTable(insertDataSQL16);
    seedTable(insertDataSQL17);
    seedTable(insertDataSQL18);
    seedTable(insertDataSQL19);
    seedTable(insertDataSQL20);
    seedTable(insertDataSQL21);
    seedTable(insertDataSQL22);
    seedTable(insertDataSQL23);
    seedTable(insertDataSQL24);
    seedTable(insertDataSQL25);
    seedTable(insertDataSQL26);
    seedTable(insertDataSQL27);
    seedTable(insertDataSQL28);
    seedTable(insertDataSQL29);
    seedTable(insertDataSQL30);
    seedTable(insertDataSQL31);
    seedTable(insertDataSQL32);
    seedTable(insertDataSQL33);
    seedTable(insertDataSQL34);
    seedTable(insertDataSQL35);
    seedTable(insertDataSQL36);
    seedTable(insertDataSQL37);
    seedTable(insertDataSQL38);
    seedTable(insertDataSQL39);
    seedTable(insertDataSQL40);
    seedTable(insertDataSQL41);
    seedTable(insertDataSQL42);
    seedTable(insertDataSQL43);
    seedTable(insertDataSQL44);
    seedTable(insertDataSQL45);
    seedTable(insertDataSQL46);
    seedTable(insertDataSQL47);
    seedTable(insertDataSQL48);
    seedTable(insertDataSQL49);
    seedTable(insertDataSQL50);
  }

  public void seedIngredients() {
    String dropTableSQL = "DROP TABLE IF EXISTS `ingredients`;";

    String createTableSQL = "CREATE TABLE `ingredients` ("
        + "`IngredientID` varchar(45) NOT NULL, "
        + "`IngredientName` varchar(45) NOT NULL, "
        + "PRIMARY KEY (`IngredientID`));";

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
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

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
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

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
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

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
  }

  public void seedWeeklyDinnerLists() {
    String dropTableSQL = "DROP TABLE IF EXISTS `weekly_dinner_lists`;";

    String createTableSQL = "CREATE TABLE `weekly_dinner_lists` ("
        + "`WeeklyDinnerListID` int NOT NULL, "
        + "`UserID` int NOT NULL, "
        + "`Week` date NOT NULL, " // change to data format yyyy-mm-dd
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
        + "`RecipeID` varchar(255) NOT NULL, "
        + "`DayOfWeek` varchar(45) NOT NULL, "
        + "PRIMARY KEY (`DinnerListRecipeID`), "
        + "KEY `WeeklyDinnerListID0_idx` (`WeeklyDinnerListID`), "
        + "KEY `RecipeID3_idx` (`RecipeID`), "
        + "CONSTRAINT `RecipeID3` FOREIGN KEY (`RecipeID`) REFERENCES `recipes` (`RecipeID`), "
        + "CONSTRAINT `WeeklyDinnerListID0` FOREIGN KEY (`WeeklyDinnerListID`) REFERENCES `weekly_dinner_lists` (`WeeklyDinnerListID`))";

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

  // Existing methods...

  public void seedShoppingLists() {
    String dropTableSQL = "DROP TABLE IF EXISTS `shopping_lists`;";

    String createTableSQL = "CREATE TABLE `shopping_lists` ("
        + "`ShoppingListID` int NOT NULL AUTO_INCREMENT, "
        + "`UserID` int NOT NULL, "
        + "`CreatedDate` DATE NOT NULL, "
        + "PRIMARY KEY (`ShoppingListID`), "
        + "FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`));";

    seedTable(dropTableSQL);
    seedTable(createTableSQL);
  }

  public void seedShoppingListItems() {
    String dropTableSQL = "DROP TABLE IF EXISTS `shopping_list_items`;";

    String createTableSQL = "CREATE TABLE `shopping_list_items` ("
        + "`ItemID` int NOT NULL AUTO_INCREMENT, "
        + "`ShoppingListID` int NOT NULL, "
        + "`IngredientID` varchar(45) NOT NULL, " // Ensure this matches the `IngredientID` data type in `ingredients`
        + "`Quantity` varchar(100) NOT NULL, "
        + "PRIMARY KEY (`ItemID`), "
        + "FOREIGN KEY (`ShoppingListID`) REFERENCES `shopping_lists` (`ShoppingListID`), "
        + "FOREIGN KEY (`IngredientID`) REFERENCES `ingredients` (`IngredientID`));";

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
    seeder.seedShoppingLists();
    seeder.seedShoppingListItems();
    seeder.seedSentRecipes();
    seeder.seedHelpSystem();
  }
}
