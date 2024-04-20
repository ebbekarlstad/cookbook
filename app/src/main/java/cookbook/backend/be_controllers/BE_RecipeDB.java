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

public class BE_RecipeDB {
  public ArrayList<RecipeOB> everyRecipe = new ArrayList<>();

  public static List<RecipeOB> getRecpies() throws SQLException {
    ArrayList<RecipeOB> currentRecipeObjects = new ArrayList<>();
    String query = "SELECT * FROM recipes";

    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while (result.next()) {
        RecipeOB newRecipe = new RecipeOB(
            result.getString("recipe_id"),
            result.getString("name"),
            result.getString("description"),
            result.getString("instructions"), false);

        // this will upade the class ArrayList.
        currentRecipeObjects.add(newRecipe);
      }

      // Comment so iker can access it.
      // adding ingredients for each recipe.
      for (RecipeOB RecipeOB : currentRecipeObjects) {
        String id = RecipeOB.getID();
        String ingQuery = "SELECT * "
            + "FROM ingredients "
            + "JOIN recipe_ingredients ON recipe_ingredients.ingredient_id = ingredients.ingredient_id "
            + "WHERE recipe_ingredients.recipe_id = ?";
        try (PreparedStatement ingStatement = conn.prepareStatement(ingQuery)) {
          ingStatement.setString(1, id);
          ResultSet ingResultSet = ingStatement.executeQuery();
          System.out.println(ingResultSet);
          while (ingResultSet.next()) {
            System.out.println("ingredient_name: " + ingResultSet.getString("ingredient_name"));
            System.out.println("ingredient_id: " + ingResultSet.getString("ingredient_id"));
            RecipeOB.addIngredient(new amountOfIngredients(
                ingResultSet.getString("unit"),
                ingResultSet.getFloat("amount"),
                new Ingredient(ingResultSet.getString("ingredient_id"), ingResultSet.getString("ingredient_name"))));
          }
        } catch (SQLException e) {
          System.out.println("Error adding comment to recipe via recipeController" + e);
        }
      }
    }
    return currentRecipeObjects;
  }

  public static void addRecipe(String recipeID, String recipeName, String recipeDescription, String recipeInstructions) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
    String query = "INSERT INTO recipes VALUES(?,?,?,?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, recipeID);
      sqlStatement.setString(2, recipeName);
      sqlStatement.setString(3, recipeDescription);
      sqlStatement.setString(4, recipeInstructions);
      sqlStatement.setBoolean(5, false);
      sqlStatement.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Bruh" + e);
    }
  }
}
