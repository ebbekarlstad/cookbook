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
              "('1', 1, 'Spaghetti Bolognese', 'A beloved classic Italian dish that combines tender spaghetti with a rich, meaty tomato sauce, perfect for a comforting and hearty meal.', 'Cook spaghetti according to the package instructions until al dente. In a separate pan, sauté finely chopped onion and minced garlic in olive oil until fragrant and golden. Add ground beef to the pan and cook until browned and crumbly. Pour in a generous amount of tomato sauce, mix well, and let it simmer on low heat to develop the flavors. Once the sauce has thickened, combine it with the cooked spaghetti, ensuring each strand is well coated. Serve hot, optionally garnished with freshly grated Parmesan cheese and a sprinkle of chopped fresh basil.')",
              "('2', 1, 'Chicken Curry', 'A spicy and flavorful dish that brings together tender pieces of chicken in a rich, aromatic curry sauce, perfect for warming up on a chilly day.', 'Cut chicken into bite-sized pieces. In a large pan, heat oil and sauté chopped onion until translucent. Add the chicken pieces and cook until browned on all sides. Stir in a mixture of curry powder and additional spices like cumin and coriander, cooking for a bit longer to toast the spices. Add chopped tomatoes and creamy coconut milk, stirring to combine. Simmer the curry until the chicken is fully cooked and the sauce has thickened, allowing the flavors to meld together beautifully. Serve hot with rice or naan.')",
              "('3', 1, 'Beef Stew', 'A hearty and filling dish that features tender chunks of beef and a medley of vegetables, slow-cooked to perfection in a savory broth.', 'Cut beef into large chunks. In a large pot, brown the beef on all sides to seal in the juices. Add chopped onion, diced potatoes, and sliced carrots to the pot, stirring to combine. Pour in beef broth and bring the mixture to a boil. Reduce heat and let it simmer gently for several hours, until the beef is tender and the vegetables are fully cooked and flavorful. Season with salt, pepper, and fresh herbs. Serve hot, with crusty bread on the side.')",
              "('4', 1, 'Caesar Salad', 'A fresh and crispy salad that combines crunchy romaine lettuce with savory croutons, Parmesan cheese, and a tangy Caesar dressing.', 'Chop fresh romaine lettuce into bite-sized pieces and place in a large bowl. Add crunchy croutons and a generous amount of grated Parmesan cheese. Toss with a creamy Caesar dressing until all the ingredients are well coated. For an extra touch, top with anchovy fillets and a sprinkle of freshly ground black pepper. Serve immediately.')",
              "('5', 1, 'Grilled Cheese Sandwich', 'A simple and delicious sandwich featuring melted cheese between two slices of perfectly grilled, buttery bread.', 'Place slices of your favorite cheese, such as cheddar or Swiss, between two slices of bread. Butter the outside of the bread generously. Heat a pan over medium heat and grill the sandwich, pressing down lightly with a spatula, until the bread is golden brown and crispy, and the cheese is fully melted. Flip and repeat on the other side. Serve hot, optionally with a bowl of tomato soup.')",
              "('6', 1, 'Pancakes', 'Fluffy and sweet pancakes that are perfect for a delightful breakfast or brunch, served with your favorite toppings.', 'In a large bowl, mix milk, eggs, flour, sugar, and baking powder until smooth and free of lumps. Heat a lightly oiled griddle or frying pan over medium-high heat. Pour or scoop the batter onto the griddle, using approximately a quarter cup for each pancake. Cook until bubbles form on the surface and the edges look set, then flip and cook until the other side is golden brown. Serve hot, with butter, syrup, fresh fruit, or whipped cream.')",
              "('7', 1, 'Miso Soup', 'A traditional Japanese soup that is both comforting and nourishing, featuring a savory broth with tofu and seaweed.', 'In a pot, bring water to a boil. Reduce the heat and dissolve a generous amount of miso paste in the hot water, stirring until fully combined. Add diced tofu and a splash of soy sauce. Simmer for a few minutes to allow the flavors to meld. Serve hot, garnished with sliced green onions and seaweed.')",
              "('8', 1, 'Tacos', 'A Mexican classic that combines seasoned ground beef with fresh toppings, all wrapped in a warm tortilla.', 'Cook ground beef in a pan over medium heat until browned and fully cooked. Season with a mix of taco spices, such as cumin, chili powder, and paprika. Warm tortillas in a separate pan or microwave. Fill each tortilla with the cooked beef, shredded lettuce, grated cheese, and a spoonful of salsa. Serve immediately, with lime wedges on the side.')",
              "('9', 1, 'Pizza Margherita', 'A classic Italian pizza featuring a simple yet delicious combination of tomatoes, mozzarella, and fresh basil on a thin, crispy crust.', 'Preheat your oven to a high temperature, ideally with a pizza stone inside. Roll out pizza dough on a floured surface to your desired thickness. Spread thinly sliced tomatoes over the dough, followed by slices of fresh mozzarella cheese and whole basil leaves. Drizzle with olive oil and a sprinkle of salt. Bake in the oven until the crust is golden and the cheese is bubbly and slightly browned. Serve hot.')",
              "('10', 1, 'Lasagna', 'A rich and cheesy Italian casserole with layers of pasta, meat sauce, and a blend of cheeses, baked to perfection.', 'Preheat your oven to a moderate temperature. Cook lasagna noodles according to the package instructions. In a baking dish, spread a thin layer of tomato sauce. Place a layer of noodles on top, followed by a mixture of ricotta cheese and egg, more sauce, and shredded mozzarella cheese. Repeat the layers, ending with a layer of sauce and a generous sprinkling of grated Parmesan cheese. Bake until bubbly and golden brown on top. Let it rest before serving.')",
              "('11', 1, 'Sushi', 'A Japanese delicacy that involves rolling vinegared rice and fresh fish or vegetables in seaweed, then slicing into bite-sized pieces.', 'Cook sushi rice according to the package instructions, then season with a mixture of rice vinegar, sugar, and salt. Place a sheet of nori (seaweed) on a bamboo sushi mat. Spread an even layer of the seasoned rice over the nori, leaving a border at the top. Add slices of fresh fish, cucumber, or avocado as desired. Using the mat, roll up the sushi tightly and slice into bite-sized pieces. Serve with soy sauce, pickled ginger, and wasabi.')",
              "('12', 1, 'Chocolate Cake', 'A rich and decadent dessert with a moist, chocolatey sponge and a smooth, melt-in-your-mouth texture, perfect for chocolate lovers.', 'Preheat your oven to a moderate temperature. Melt dark chocolate and butter together in a double boiler or microwave, stirring until smooth. In a separate bowl, whisk eggs with sugar until light and fluffy. Fold the melted chocolate mixture into the egg mixture, then gently stir in flour until just combined. Pour the batter into a greased baking pan and bake until a toothpick inserted into the center comes out clean. Let cool before serving, optionally dusted with powdered sugar or topped with chocolate ganache.')",
              "('13', 1, 'Burger', 'A juicy and flavorful sandwich with a seasoned beef patty, fresh vegetables, and a soft bun, perfect for a casual meal.', 'Shape ground beef into patties, seasoning with salt and pepper. Cook the patties on a hot grill or in a pan until they reach your desired level of doneness. Place each patty on a soft burger bun and top with fresh lettuce, a slice of cheese, tomato, and onion. Add condiments like ketchup, mustard, and mayonnaise as desired. Serve with your favorite sides, such as fries or a salad.')",
              "('14', 1, 'Fried Rice', 'A quick and tasty dish that combines cooked rice with scrambled eggs, mixed vegetables, and savory soy sauce, perfect for a speedy meal.', 'Cook rice and let it cool to prevent it from becoming mushy. In a large pan or wok, scramble eggs and set them aside. Stir-fry mixed vegetables, such as peas, carrots, and bell peppers, in a bit of oil. Add the cooled rice to the pan, stirring to combine. Pour in soy sauce to taste and stir well. Add the scrambled eggs back in and mix until everything is heated through. Serve hot.')",
              "('15', 1, 'Spring Rolls', 'Crispy and fresh, these rolls are filled with a variety of vegetables and served with a flavorful dipping sauce.', 'Soak sheets of rice paper in water to soften. Fill each sheet with a mixture of shredded lettuce, carrots, cucumber, and fresh herbs like mint and cilantro. Roll tightly, folding in the sides as you go. Serve the spring rolls with a dipping sauce made from soy sauce, hoisin sauce, and a splash of lime juice.')",
              "('16', 1, 'Lamb Chops', 'Tender and juicy, these lamb chops are simply seasoned and seared to perfection, making for an elegant and delicious meal.', 'Season lamb chops with salt, pepper, and fresh herbs like rosemary and thyme. Heat olive oil in a pan over medium-high heat. Cook the lamb chops for a few minutes on each side until they reach your desired level of doneness. Let the chops rest for a few minutes before serving to allow the juices to redistribute. Serve with sides like roasted vegetables or mashed potatoes.')",
              "('17', 1, 'Tom Yum Soup', 'A spicy and sour Thai soup that combines fragrant herbs with fish and a flavorful broth, perfect for a warming starter.', 'Bring water to a boil in a pot. Add a generous amount of Tom Yum paste, stirring until fully dissolved. Add pieces of fish, such as white fish or shrimp, and let it simmer until cooked through. Finish the soup with fresh herbs like cilantro and kaffir lime leaves. Serve hot, garnished with additional fresh herbs and a wedge of lime.')",
              "('18', 1, 'Pad Thai', 'A popular Thai dish featuring stir-fried rice noodles, mixed vegetables, and a tangy sauce, topped with crunchy peanuts.', 'Cook rice noodles according to the package instructions until al dente. In a large pan or wok, stir-fry mixed vegetables like bean sprouts, carrots, and bell peppers in a bit of oil. Add the cooked noodles and pour over a tangy Pad Thai sauce made from tamarind paste, fish sauce, and sugar. Push the noodles to one side of the pan and scramble a couple of eggs on the other side, then mix everything together. Cook for another minute until everything is well combined and heated through. Serve hot, garnished with crushed peanuts and fresh lime wedges.')",
              "('19', 1, 'Ratatouille', 'A French vegetable dish that features thinly sliced vegetables layered and baked until tender, infused with the flavors of fresh herbs.', 'Preheat your oven to a moderate temperature. Slice a variety of vegetables, such as zucchini, eggplant, bell peppers, and tomatoes, into thin rounds. Layer the vegetables in a baking dish, overlapping them in a beautiful pattern. Sprinkle with fresh herbs like thyme and rosemary, and drizzle with olive oil. Bake until the vegetables are tender and slightly caramelized. Serve hot or at room temperature, as a side dish or a main course with crusty bread.')",
              "('20', 1, 'Fish and Chips', 'A British classic that pairs crispy battered fish with golden, crispy fries, served with a tangy tartar sauce.', 'Heat oil in a deep fryer or a large pot to a high temperature. Coat pieces of white fish, such as cod or haddock, in a thick batter made from flour, water, and a bit of baking powder. Fry the fish in the hot oil until golden and crispy. Cut potatoes into thick chips and fry them until golden and crispy. Serve the fish and chips immediately, with a side of tartar sauce and a wedge of lemon.')"
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
              "('4', 'Onion')",
              "('5', 'Garlic')",
              "('6', 'Chicken')",
              "('7', 'Curry Powder')",
              "('8', 'Coconut Milk')",
              "('9', 'Tomatoes')",
              "('10', 'Beef')",
              "('11', 'Potatoes')",
              "('12', 'Carrots')",
              "('13', 'Beef Broth')",
              "('14', 'Lettuce')",
              "('15', 'Croutons')",
              "('16', 'Parmesan Cheese')",
              "('17', 'Caesar Dressing')",
              "('18', 'Bread')",
              "('19', 'Cheese')",
              "('20', 'Butter')",
              "('21', 'Milk')",
              "('22', 'Eggs')",
              "('23', 'Flour')",
              "('24', 'Sugar')",
              "('25', 'Baking Powder')",
              "('26', 'Miso Paste')",
              "('27', 'Tofu')",
              "('28', 'Water')",
              "('29', 'Soy Sauce')",
              "('30', 'Tortilla')",
              "('31', 'Lettuce')",
              "('32', 'Cheese')",
              "('33', 'Salsa')",
              "('34', 'Pizza Dough')",
              "('35', 'Basil')",
              "('36', 'Mozzarella Cheese')",
              "('37', 'Olive Oil')",
              "('38', 'Lasagna Noodles')",
              "('39', 'Ricotta Cheese')",
              "('40', 'Parmesan Cheese')",
              "('41', 'Seaweed')",
              "('42', 'Sushi Rice')",
              "('43', 'Fish')",
              "('44', 'Rice Vinegar')",
              "('45', 'Chocolate')",
              "('46', 'Butter')",
              "('47', 'Buns')",
              "('48', 'Mixed Vegetables')",
              "('49', 'Rice Paper')",
              "('50', 'Lamb Chops')",
              "('51', 'Fresh Herbs')",
              "('52', 'Tom Yum Paste')",
              "('53', 'Pad Thai Sauce')",
              "('54', 'Rice Noodles')",
              "('55', 'Oil for Frying')",
              "('56', 'Tartar Sauce')"
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
            "('1', '4', 'pcs', '1')", // Onion
            "('1', '5', 'cloves', '2')", // Garlic
            // Chicken Curry
            "('2', '6', 'g', '300')", // Chicken
            "('2', '7', 'tbsp', '2')", // Curry Powder
            "('2', '8', 'ml', '400')", // Coconut Milk
            "('2', '4', 'pcs', '1')", // Onion
            "('2', '9', 'pcs', '2')", // Tomatoes
            // Beef Stew
            "('3', '10', 'g', '500')", // Beef
            "('3', '11', 'g', '200')", // Potatoes
            "('3', '12', 'g', '100')", // Carrots
            "('3', '13', 'ml', '500')", // Beef Broth
            "('3', '4', 'pcs', '1')", // Onion
            // Caesar Salad
            "('4', '14', 'g', '150')", // Lettuce
            "('4', '15', 'g', '50')", // Croutons
            "('4', '16', 'g', '30')", // Parmesan Cheese
            "('4', '17', 'tbsp', '3')", // Caesar Dressing
            // Grilled Cheese Sandwich
            "('5', '18', 'slices', '2')", // Bread
            "('5', '19', 'slices', '2')", // Cheese
            "('5', '20', 'tbsp', '2')", // Butter
            // Pancakes
            "('6', '21', 'ml', '200')", // Milk
            "('6', '22', 'pcs', '2')", // Eggs
            "('6', '23', 'g', '250')", // Flour
            "('6', '24', 'tbsp', '2')", // Sugar
            "('6', '25', 'tbsp', '1')", // Baking Powder
            // Miso Soup
            "('7', '26', 'tbsp', '2')", // Miso Paste
            "('7', '27', 'g', '100')", // Tofu
            "('7', '28', 'cups', '4')", // Water
            "('7', '29', 'tbsp', '1')", // Soy Sauce
            // Tacos
            "('8', '30', 'pcs', '3')", // Tortilla
            "('8', '2', 'g', '250')", // Ground Beef
            "('8', '31', 'g', '50')", // Lettuce
            "('8', '32', 'g', '50')", // Cheese
            "('8', '33', 'tbsp', '4')", // Salsa
            // Pizza Margherita
            "('9', '34', 'g', '500')", // Pizza Dough
            "('9', '9', 'g', '200')", // Tomatoes
            "('9', '35', 'g', '20')", // Basil
            "('9', '36', 'g', '100')", // Mozzarella Cheese
            "('9', '37', 'tbsp', '2')", // Olive Oil
            // Lasagna
            "('10', '38', 'sheets', '8')", // Lasagna Noodles
            "('10', '39', 'g', '200')", // Ricotta Cheese
            "('10', '3', 'ml', '500')", // Tomato Sauce
            "('10', '36', 'g', '200')", // Mozzarella Cheese
            "('10', '16', 'g', '50')", // Parmesan Cheese
            // Sushi
            "('11', '41', 'sheets', '2')", // Seaweed
            "('11', '42', 'g', '200')", // Sushi Rice
            "('11', '43', 'g', '150')", // Fish
            "('11', '44', 'tbsp', '2')", // Rice Vinegar
            "('11', '24', 'tbsp', '1')", // Sugar
            // Chocolate Cake
            "('12', '45', 'g', '200')", // Chocolate
            "('12', '22', 'pcs', '2')", // Eggs
            "('12', '24', 'g', '150')", // Sugar
            "('12', '23', 'g', '100')", // Flour
            "('12', '46', 'g', '100')", // Butter
            // Burger
            "('13', '2', 'g', '200')", // Ground Beef
            "('13', '47', 'pcs', '2')", // Buns
            "('13', '31', 'g', '50')", // Lettuce
            "('13', '19', 'slices', '2')", // Cheese
            "('13', '29', 'tbsp', '2')", // Ketchup
            // Fried Rice
            "('14', '42', 'g', '200')", // Rice
            "('14', '48', 'g', '150')", // Mixed Vegetables
            "('14', '29', 'tbsp', '2')", // Soy Sauce
            "('14', '22', 'pcs', '2')", // Eggs
            // Spring Rolls
            "('15', '48', 'g', '200')", // Mixed Vegetables
            "('15', '49', 'sheets', '10')", // Rice Paper
            "('15', '29', 'tbsp', '4')", // Dipping Sauce
            // Lamb Chops
            "('16', '50', 'g', '400')", // Lamb Chops
            "('16', '51', 'g', '50')", // Fresh Herbs
            "('16', '37', 'tbsp', '2')", // Olive Oil
            // Tom Yum Soup
            "('17', '43', 'g', '200')", // Fish
            "('17', '51', 'g', '20')", // Fresh Herbs
            "('17', '52', 'tbsp', '2')", // Tom Yum Paste
            "('17', '28', 'cups', '4')", // Water
            // Pad Thai
            "('18', '54', 'g', '200')", // Rice Noodles
            "('18', '48', 'g', '150')", // Mixed Vegetables
            "('18', '53', 'tbsp', '4')", // Pad Thai Sauce
            "('18', '22', 'pcs', '2')", // Eggs
            // Ratatouille
            "('19', '48', 'g', '300')", // Mixed Vegetables
            "('19', '51', 'g', '20')", // Fresh Herbs
            "('19', '37', 'tbsp', '2')", // Olive Oil
            // Fish and Chips
            "('20', '43', 'g', '200')", // Fish
            "('20', '11', 'g', '200')", // Potatoes (cut into chips)
            "('20', '55', 'ml', '500')", // Oil for Frying
            "('20', '56', 'tbsp', '4')", // Tartar Sauce
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
