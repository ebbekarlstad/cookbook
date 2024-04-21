package cookbook.backend.be_controllers;

import cookbook.backend.be_objects.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class RecipeController {

  // ArrayList with the current recipes
  public ArrayList<Recipe> allRecipes = new ArrayList<>();

  /**
   * returns a list of all recipes.
   */
  public static List<Recipe> getRecpies() throws SQLException {
    ArrayList<Recipe> currentRecipeObjects = new ArrayList<>();
    String query = "SELECT * FROM recipe";

    Connection conn = DriverManager
            .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while (result.next()) {
        Recipe newRecipe = new Recipe(
                result.getString("recipe_id"),
                result.getString("name"),
                result.getString("description"),
                result.getString("instructions"),
                result.getBoolean("star"));

        // this will upade the class ArrayList.
        // Updates the calls ArrayList in the recipe object
        currentRecipeObjects.add(newRecipe);

      }

      // Comment so iker can access it.
      // adding ingredients for each recipe.
      for (Recipe recipeObject : currentRecipeObjects) {
        String id = recipeObject.getId();
        String ingQuery = "SELECT * " +
                "FROM ingredients " +
                "JOIN recipe_ingredients ON recipe_ingredients.ingredient_id = ingredients.ingredient_id " +
                "WHERE recipe_ingredients.recipe_id = ?";
        try (PreparedStatement ingStatement = conn.prepareStatement(ingQuery)) {
          ingStatement.setString(1, id);
          ResultSet ingResultSet = ingStatement.executeQuery();
          System.out.println(ingResultSet);
          while (ingResultSet.next()) {
            System.out.println("ingredient_name: " + ingResultSet.getString("ingredient_name"));
            System.out.println("ingredient_id: " + ingResultSet.getString("ingredient_id"));
            recipeObject.addIngredient(new AmountOfIngredients(
                    ingResultSet.getString("unit"),
                    ingResultSet.getFloat("amount"),
                    new Ingredient(ingResultSet.getString("ingredient_id"), ingResultSet.getString("ingredient_name")))
            );
          }

          // we can add tag to the object here.

          String tagQuery = "SELECT tag.tag_id, tag.name " +
                  "FROM tag " +
                  "JOIN recipe_tag ON recipe_tag.tag_id = tag.tag_id " +
                  "WHERE recipe_tag.recipe_id = ?";
          try (PreparedStatement tagtatement = conn.prepareStatement(tagQuery)) {
            tagtatement.setString(1, id);
            ResultSet tagResultSet = tagtatement.executeQuery();
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
   * adds the date to the recipe for weekly list.
   */
  public static void adddate(String recipeId, Timestamp created_at , String userId) throws SQLException {


    String query = "INSERT into weekly_list values ((?),(?),(?))";
    Connection conn = DriverManager
            .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
    try (PreparedStatement preparedStmnt = conn.prepareStatement(query)) {
      preparedStmnt.setString(1, userId);
      preparedStmnt.setString(2, recipeId);
      preparedStmnt.setString(3, String.valueOf(created_at));
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      if (e instanceof SQLIntegrityConstraintViolationException) {
        return;
      } else {
        e.printStackTrace();
      }
    }

  }

  /**
   * adds new recipe.
   */
  public static void addRecipe(String recipeId, String recipeName, String shortDesc, String longDesc) throws SQLException{
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
    String query = "INSERT INTO recipe VALUES(?,?,?,?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
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