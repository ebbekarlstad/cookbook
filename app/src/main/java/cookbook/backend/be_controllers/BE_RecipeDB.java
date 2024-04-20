package cookbook.backend.be_controllers;

import cookbook.backend.be_objects.Ingredient;
import cookbook.backend.be_objects.RecipeOB;
import cookbook.backend.be_objects.amountOfIngredients;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BE_RecipeDB {
  public static ArrayList<RecipeOB> everyRecipe = new ArrayList<>();

  public static List<RecipeOB> getRecpies() throws SQLException {
    ArrayList<RecipeOB> currentRecipeObjects = new ArrayList<>();
    String query = "SELECT * FROM recipes";

    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while (result.next()) {
        RecipeOB newRecipe = new RecipeOB(
                result.getString("recipe_id"),
                result.getString("name"),
                result.getString("description"),
                result.getString("instructions"));

        // this will update the class ArrayList.
        currentRecipeObjects.add(newRecipe);
      }

      // Adding ingredients for each recipe.
      for (RecipeOB recipe : currentRecipeObjects) {
        String id = recipe.getID();
        String ingQuery = "SELECT * "
                + "FROM ingredients "
                + "JOIN recipe_ingredients ON recipe_ingredients.ingredient_id = ingredients.ingredient_id "
                + "WHERE recipe_ingredients.recipe_id = ?";
        try (PreparedStatement ingStatement = conn.prepareStatement(ingQuery)) {
          ingStatement.setString(1, id);
          ResultSet ingResultSet = ingStatement.executeQuery();
          while (ingResultSet.next()) {
            recipe.addIngredient(new amountOfIngredients(
                    ingResultSet.getString("unit"),
                    ingResultSet.getDouble("amount"),
                    new Ingredient(
                            ingResultSet.getString("ingredient_id"),
                            ingResultSet.getString("ingredient_name"),
                            0.0, // Initial amount, you can modify this as needed
                            ingResultSet.getString("unit"))));
          }
        } catch (SQLException e) {
          System.out.println("Error adding ingredients to recipe: " + e);
        }
      }
    } catch (SQLException e) {
      System.out.println("Error getting recipes: " + e);
    } finally {
      conn.close();
    }

    return currentRecipeObjects;
  }

  public static void addRecipe(String recipeName, String recipeDescription, String recipeInstructions, List<Ingredient> ingredientsList) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
    String recipeID = UUID.randomUUID().toString();

    String insertRecipeQuery = "INSERT INTO recipes (recipe_id, name, description, instructions, is_favorite) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement recipeStatement = conn.prepareStatement(insertRecipeQuery)) {
      recipeStatement.setString(1, recipeID);
      recipeStatement.setString(2, recipeName);
      recipeStatement.setString(3, recipeDescription);
      recipeStatement.setString(4, recipeInstructions);
      recipeStatement.setBoolean(5, false);
      recipeStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Error inserting recipe: " + e.getMessage());
      throw e;
    }

    // Insert ingredients into ingredients table if they don't already exist
    for (Ingredient ingredient : ingredientsList) {
      String insertIngredientQuery = "INSERT IGNORE INTO ingredients (ingredient_id, name, amount, unit) VALUES (?, ?, ?, ?)";
      try (PreparedStatement ingredientStatement = conn.prepareStatement(insertIngredientQuery)) {
        ingredientStatement.setString(1, ingredient.getIngredientID());
        ingredientStatement.setString(2, ingredient.getName());
        ingredientStatement.setDouble(3, ingredient.getAmount());
        ingredientStatement.setString(4, ingredient.getUnit());
        ingredientStatement.executeUpdate();
      } catch (SQLException e) {
        System.out.println("Error inserting ingredient: " + e.getMessage());
        throw e;
      }
    }

    // Insert into recipe_ingredients table
    String insertRecipeIngredientsQuery = "INSERT INTO recipe_ingredients (recipe_id, ingredient_id, amount, unit) VALUES (?, ?, ?, ?)";
    try (PreparedStatement recipeIngredientsStatement = conn.prepareStatement(insertRecipeIngredientsQuery)) {
      for (Ingredient ingredient : ingredientsList) {
        recipeIngredientsStatement.setString(1, recipeID);
        recipeIngredientsStatement.setString(2, ingredient.getIngredientID());
        recipeIngredientsStatement.setDouble(3, ingredient.getAmount());
        recipeIngredientsStatement.setString(4, ingredient.getUnit());
        recipeIngredientsStatement.addBatch();
      }
      recipeIngredientsStatement.executeBatch();
    } catch (SQLException e) {
      System.out.println("Error inserting recipe ingredients: " + e.getMessage());
      throw e;
    }

    conn.close();
  }
}
