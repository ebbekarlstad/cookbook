package cookbook.backend;

import java.sql.Connection;
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
				+ "`is_opened` TINYINT(1) DEFAULT 0,"
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

        String[] recipes = new String[]{
          "('1', 1, 'Spaghetti Bolognese', 'Spaghetti Bolognese is a traditional Italian pasta dish consisting of spaghetti served with a rich meat sauce made from ground beef, tomatoes, onions, and herbs.', 'Cook the spaghetti according to the package instructions. In a separate large pan, heat some olive oil over medium heat and brown the ground beef until fully cooked. Add finely chopped onions and cook until they are translucent. Stir in minced garlic and cook for another minute. Pour in the tomato sauce and add diced tomatoes. Season with salt, pepper, and Italian herbs. Let the sauce simmer for at least 30 minutes, stirring occasionally. Combine the spaghetti and sauce, then serve hot with grated Parmesan cheese.', 'grams', '200')",
          "('2', 1, 'Chicken Curry', 'Chicken Curry is a flavorful dish made with chicken simmered in a spicy curry sauce that typically includes ingredients like curry powder, coconut milk, and various spices.', 'Heat oil in a large pan over medium heat. Add diced onions and cook until they are golden brown. Add the chicken pieces and cook until browned on all sides. Stir in minced garlic and ginger, and cook for another minute. Sprinkle in the curry powder and mix well to coat the chicken. Pour in the coconut milk and bring the mixture to a simmer. Add salt, pepper, and other desired spices. Simmer until the chicken is fully cooked and the sauce has thickened. Serve with rice.', 'grams', '300')",
          "('3', 1, 'Beef Stew', 'Beef Stew is a hearty and comforting dish made by slow-cooking beef with vegetables like potatoes and carrots in a flavorful broth.', 'In a large pot, heat some oil over medium-high heat and brown the beef on all sides. Remove the beef and set aside. In the same pot, add chopped onions and cook until translucent. Add minced garlic and cook for another minute. Return the beef to the pot and add chopped potatoes and carrots. Pour in beef broth until the ingredients are just covered. Add bay leaves, salt, and pepper. Bring to a boil, then reduce the heat and let it simmer for 1.5 to 2 hours, or until the beef is tender and the vegetables are cooked through. Serve hot.', 'grams', '500')",
          "('4', 1, 'Caesar Salad', 'Caesar Salad is a classic salad made with romaine lettuce, croutons, and grated cheese, typically dressed with a creamy Caesar dressing.', 'Wash and chop the romaine lettuce. In a large bowl, combine the chopped lettuce with croutons and grated Parmesan cheese. Drizzle with Caesar dressing and toss well to coat the ingredients evenly. Serve immediately.', 'grams', '150')",
          "('5', 1, 'Grilled Cheese Sandwich', 'A Grilled Cheese Sandwich is a simple and delicious sandwich made by grilling bread with slices of cheese until the bread is golden brown and the cheese is melted.', 'Place slices of cheese between two slices of bread. Heat a skillet over medium heat and melt a small amount of butter. Place the sandwich in the skillet and cook until the bottom slice of bread is golden brown. Flip the sandwich and cook until the other side is golden brown and the cheese is melted. Serve hot.', 'slices', '2')",
          "('6', 1, 'Pancakes', 'Pancakes are fluffy and sweet breakfast items made from a batter of flour, milk, and eggs, cooked on a griddle and often served with syrup.', 'In a mixing bowl, combine flour, milk, and eggs to form a smooth batter. Heat a lightly oiled griddle or frying pan over medium-high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. Cook until bubbles form on the surface of the pancakes and the edges appear dry. Flip and cook until browned on the other side. Serve hot with syrup.', 'grams', '250')",
          "('7', 1, 'Miso Soup', 'Miso Soup is a traditional Japanese soup made with a base of miso paste dissolved in hot water, often containing tofu and seaweed.', 'In a pot, bring water to a boil. Reduce the heat and dissolve miso paste in the hot water, stirring well. Add small cubes of tofu and dried seaweed. Simmer for a few minutes until the tofu is heated through. Serve hot, optionally garnished with chopped green onions.', 'grams', '100')",
          "('8', 1, 'Tacos', 'Tacos are a Mexican classic consisting of tortillas filled with various ingredients such as seasoned ground beef, lettuce, cheese, and salsa.', 'In a pan, cook the ground beef over medium heat until browned and fully cooked. Season with taco seasoning according to package instructions. Warm the tortillas in a separate pan or microwave. Fill each tortilla with the seasoned beef and top with shredded lettuce, grated cheese, salsa, and any other desired toppings. Serve immediately.', 'pieces', '3')",
          "('9', 1, 'Pizza Margherita', 'Pizza Margherita is a classic Italian pizza topped with tomato sauce, fresh tomatoes, mozzarella cheese, and basil.', 'Preheat the oven to the highest temperature, typically around 475°F (245°C). Roll out the pizza dough on a floured surface to your desired thickness. Spread tomato sauce evenly over the dough, leaving a small border for the crust. Top with sliced fresh tomatoes and torn pieces of mozzarella cheese. Bake in the preheated oven until the crust is golden brown and the cheese is bubbly and slightly browned. Remove from the oven and add fresh basil leaves before serving.', 'grams', '500')",
          "('10', 1, 'Lasagna', 'Lasagna is a rich and cheesy Italian dish made by layering lasagna noodles with ricotta cheese, ground meat, and tomato sauce, baked until bubbly.', 'Preheat the oven to 375°F (190°C). In a large skillet, cook the ground meat over medium heat until browned. Stir in tomato sauce and let it simmer. In a separate bowl, mix ricotta cheese with an egg and some grated Parmesan cheese. In a baking dish, spread a thin layer of the meat sauce. Place a layer of lasagna noodles over the sauce. Spread a layer of the ricotta mixture over the noodles, followed by a layer of mozzarella cheese. Repeat the layers, ending with a layer of meat sauce and a generous topping of mozzarella cheese. Cover with foil and bake for 25 minutes. Remove the foil and bake for an additional 25 minutes until the cheese is bubbly and golden brown. Let it cool slightly before serving.', 'sheets', '8')",
          "('11', 1, 'Sushi', 'Sushi is a Japanese delicacy consisting of vinegared rice accompanied by various ingredients such as raw fish, vegetables, and seaweed.', 'Cook the sushi rice according to package instructions and let it cool slightly. Place a sheet of nori (seaweed) on a bamboo sushi mat. Spread an even layer of rice over the nori, leaving a small border at the top. Place slices of raw fish and vegetables in a line along the bottom edge of the rice. Use the bamboo mat to carefully roll the sushi into a tight cylinder. Slice the roll into bite-sized pieces and serve with soy sauce, pickled ginger, and wasabi.', 'grams', '200')",
          "('12', 1, 'Chocolate Cake', 'Chocolate Cake is a rich and decadent dessert made with cocoa powder or melted chocolate, flour, sugar, eggs, and butter.', 'Preheat the oven to 350°F (175°C). In a large mixing bowl, combine flour, sugar, cocoa powder, baking powder, and a pinch of salt. In another bowl, whisk together eggs, milk, melted butter, and vanilla extract. Gradually add the wet ingredients to the dry ingredients, mixing until well combined. Pour the batter into a greased and floured baking tin. Bake for 30-35 minutes, or until a toothpick inserted into the center comes out clean. Allow the cake to cool in the tin for 10 minutes, then transfer to a wire rack to cool completely before frosting or serving.', 'grams', '200')",
          "('13', 1, 'Burger', 'A Burger is a juicy and flavorful sandwich made with a ground beef patty served in a bun with various toppings such as lettuce, tomato, and cheese.', 'Preheat a grill or skillet to medium-high heat. Season the ground beef with salt and pepper, and form it into patties. Cook the patties on the grill or skillet for about 4-5 minutes per side, or until they reach your desired level of doneness. Toast the burger buns on the grill or in a toaster. Assemble the burgers by placing each patty on a bun and adding your favorite toppings such as lettuce, tomato, cheese, pickles, and condiments. Serve immediately.', 'grams', '200')",
          "('14', 1, 'Fried Rice', 'Fried Rice is a quick and tasty dish made by stir-frying cooked rice with vegetables and often some type of protein, seasoned with soy sauce.', 'Cook the rice according to package instructions and let it cool. In a large pan or wok, heat some oil over medium-high heat. Add diced vegetables such as carrots, peas, and bell peppers, and stir-fry until tender. Add the cooled rice to the pan and stir-fry for a few minutes, breaking up any clumps. Push the rice to the side of the pan and crack an egg into the empty space. Scramble the egg and then mix it into the rice. Season with soy sauce and stir well to combine. Serve hot.', 'grams', '200')",
          "('15', 1, 'Spring Rolls', 'Spring Rolls are crispy and fresh appetizers made by filling rice paper wrappers with a mixture of vegetables and sometimes meat or seafood.', 'Prepare the filling by slicing vegetables such as carrots, cucumbers, and bell peppers into thin strips. If using meat or seafood, cook it in a pan until fully cooked and slice into thin pieces. Soak the rice paper wrappers in warm water until they are pliable. Place a small amount of filling on each wrapper and roll tightly, folding in the sides as you go. Serve with dipping sauce.', 'sheets', '10')",
          "('16', 1, 'Lamb Chops', 'Lamb Chops are tender and juicy cuts of lamb, typically seasoned with herbs and grilled or pan-seared to desired doneness.', 'Season the lamb chops with salt, pepper, and herbs such as rosemary and thyme. Preheat a grill or skillet to medium-high heat. Cook the lamb chops for about 3-4 minutes per side for medium-rare, or longer if you prefer them more well done. Let the chops rest for a few minutes before serving.', 'grams', '400')",
          "('17', 1, 'Tom Yum Soup', 'Tom Yum Soup is a spicy and sour Thai soup made with a broth of lemongrass, lime leaves, galangal, and fish or shrimp.', 'In a pot, bring water to a boil and add lemongrass, lime leaves, and sliced galangal. Simmer for a few minutes to infuse the flavors. Add fish or shrimp and cook until it is done. Season with fish sauce, lime juice, and chili paste to taste. Serve hot, garnished with fresh cilantro.', 'grams', '200')",
          "('18', 1, 'Pad Thai', 'Pad Thai is a popular Thai dish consisting of stir-fried rice noodles with vegetables, egg, and a tamarind-based sauce, often garnished with peanuts.', 'Cook the rice noodles according to package instructions and set aside. In a large pan or wok, heat some oil over medium-high heat. Add diced vegetables and stir-fry until tender. Push the vegetables to the side of the pan and crack an egg into the empty space. Scramble the egg and then mix it into the vegetables. Add the cooked noodles and stir-fry for a few minutes, then add the Pad Thai sauce and mix well. Serve hot, garnished with chopped peanuts and lime wedges.', 'grams', '200')",
          "('19', 1, 'Ratatouille', 'Ratatouille is a French vegetable dish made by layering and baking slices of vegetables like zucchini, eggplant, and tomatoes, seasoned with herbs.', 'Preheat the oven to 375°F (190°C). Slice zucchini, eggplant, and tomatoes into thin rounds. In a baking dish, layer the vegetable slices in an alternating pattern. Drizzle with olive oil and season with salt, pepper, and herbs such as thyme and basil. Cover with foil and bake for about 45 minutes, then uncover and bake for an additional 15 minutes until the vegetables are tender and slightly browned. Serve hot or warm.', 'grams', '300')",
          "('20', 1, 'Fish and Chips', 'Fish and Chips is a British classic made by deep-frying battered fish fillets until golden brown and serving with thick-cut fries.', 'Preheat the oil in a deep fryer or large pot to 350°F (175°C). Prepare the batter by mixing flour, baking powder, and a pinch of salt with cold water until smooth. Dip the fish fillets into the batter, letting any excess drip off, and carefully lower them into the hot oil. Fry until golden brown and crispy, about 4-5 minutes. Remove and drain on paper towels. Serve the fish with thick-cut fries, seasoned with salt and vinegar.', 'grams', '200')"
      };

		seedTable(dropTableSQL);
		seedTable(createTableSQL);

		for (String recipe : recipes) {
			String seedRecipe = "INSERT INTO `recipes` (`RecipeID`, `UserID`, `RecipeName`, `ShortDesc`, `DetailedDesc`, `Unit`, `Amount`) VALUES "
					+ recipe + ";";
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
			String seedRecipeIngredient = "INSERT INTO `recipe_ingredients` (`RecipeID`, `IngredientID`, `Unit`, `Amount`) VALUES "
					+ recipeIngredient + ";";
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

		// Sample comments for variety
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
						i, comments[commentIndex % comments.length], 9 + i);
				seedTable(insertComment);
			}
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
				"CONSTRAINT `WeeklyDinnerListID0` FOREIGN KEY (`WeeklyDinnerListID`) REFERENCES `weekly_dinner_lists` (`WeeklyDinnerListID`)"
				+
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
