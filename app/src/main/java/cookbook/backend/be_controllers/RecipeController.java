package cookbook.backend.be_controllers;

import cookbook.backend.be_objects.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeController {

  // ArrayList with the current recipes
  public ArrayList<Recipe> allRecipes = new ArrayList<>();

  /**
   * Returns a list of all recipes.
   *
   * @return List of Recipe objects
   * @throws SQLException if a database access error occurs
   */
  public static List<Recipe> getRecipes() throws SQLException {
    ArrayList<Recipe> currentRecipeObjects = new ArrayList<>();
    String query = "SELECT * FROM recipes";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
         PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while (result.next()) {
        Recipe newRecipe = new Recipe(
                result.getString("recipe_id"),
                result.getString("name"),
                result.getString("description"),
                result.getString("instructions"),
                result.getBoolean("star"));

        // Updates the class ArrayList.
        currentRecipeObjects.add(newRecipe);
      }

      // Adding ingredients for each recipe.
      for (Recipe recipeObject : currentRecipeObjects) {
        String id = recipeObject.getId();
        String ingQuery = "SELECT * " +
                "FROM ingredients " +
                "JOIN recipe_ingredients ON recipe_ingredients.ingredient_id = ingredients.ingredient_id " +
                "WHERE recipe_ingredients.recipe_id = ?";
        try (PreparedStatement ingStatement = conn.prepareStatement(ingQuery)) {
          ingStatement.setString(1, id);
          ResultSet ingResultSet = ingStatement.executeQuery();
          while (ingResultSet.next()) {
            recipeObject.addIngredient(new AmountOfIngredients(
                    ingResultSet.getString("unit"),
                    ingResultSet.getFloat("amount"),
                    new Ingredient(ingResultSet.getString("ingredient_id"), ingResultSet.getString("ingredient_name")))
            );
          }

          // Adding tags to the object.
          String tagQuery = "SELECT tag.tag_id, tag.name " +
                  "FROM tag " +
                  "JOIN recipe_tag ON recipe_tag.tag_id = tag.tag_id " +
                  "WHERE recipe_tag.recipe_id = ?";
          try (PreparedStatement tagStatement = conn.prepareStatement(tagQuery)) {
            tagStatement.setString(1, id);
            ResultSet tagResultSet = tagStatement.executeQuery();
            while (tagResultSet.next()) {
              Tag newTag = new Tag(
                      tagResultSet.getString("tag_id"),
                      tagResultSet.getString("name"));
              recipeObject.addTag(newTag);
            }

          } catch (SQLException e) {
            System.out.println("Error adding tag: " + e);
          }

        } catch (SQLException e) {
          System.out.println("Adding ingredients query" + e);
        }
      }

      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return currentRecipeObjects;
  }

  /**
   * Adds the date to the recipe for the weekly list.
   *
   * @param recipeId   The ID of the recipe
   * @param created_at The timestamp when the recipe was added
   * @param userId     The ID of the user
   * @throws SQLException if a database access error occurs
   */
  public static void addDate(String recipeId, Timestamp created_at, String userId) throws SQLException {
    String query = "INSERT into weekly_list values ((?),(?),(?))";
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
         PreparedStatement preparedStmt = conn.prepareStatement(query)) {
      preparedStmt.setString(1, userId);
      preparedStmt.setString(2, recipeId);
      preparedStmt.setString(3, String.valueOf(created_at));
      preparedStmt.executeUpdate();

    } catch (SQLException e) {
      if (e instanceof SQLIntegrityConstraintViolationException) {
        return;
      } else {
        e.printStackTrace();
      }
    }
  }

  /**
   * Adds a new recipe to the database.
   *
   * @param recipeId   The ID of the recipe
   * @param recipeName The name of the recipe
   * @param shortDesc  A short description of the recipe
   * @param longDesc   A long description of the recipe
   * @throws SQLException if a database access error occurs
   */
  public static void addRecipe(String recipeId, String recipeName, String shortDesc, String longDesc) throws SQLException {
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
         PreparedStatement sqlStatement = conn.prepareStatement("INSERT INTO recipes VALUES(?,?,?,?,?)")) {
      sqlStatement.setString(1, recipeId);
      sqlStatement.setString(2, recipeName);
      sqlStatement.setString(3, shortDesc);
      sqlStatement.setString(4, longDesc);
      sqlStatement.setBoolean(5, false);
      sqlStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("..." + e);
    }
  }
}
