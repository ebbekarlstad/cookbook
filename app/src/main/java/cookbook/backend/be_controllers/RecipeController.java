package cookbook.backend.be_controllers;

import cookbook.backend.be_objects.*;

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
        result.getString("RecipeID"),
        result.getString("RecipeName"),
        result.getString("ShortDesc"),
        result.getString("DetailedDesc"));
        
        // Updates the class ArrayList.
        currentRecipeObjects.add(newRecipe);
      }
      
      // Adding ingredients for each recipe.
      for (Recipe recipeObject : currentRecipeObjects) {
        String id = recipeObject.getId();
        String ingQuery = "SELECT * " +
        "FROM ingredients " +
        "JOIN recipe_ingredients ON recipe_ingredients.IngredientID = ingredients.IngredientName " +
        "WHERE recipe_ingredients.IngredientID = ?";
        try (PreparedStatement ingStatement = conn.prepareStatement(ingQuery)) {
          ingStatement.setString(1, id);
          ResultSet ingResultSet = ingStatement.executeQuery();
          while (ingResultSet.next()) {
            recipeObject.addIngredient(new AmountOfIngredients(
            ingResultSet.getString("Unit"),
            ingResultSet.getFloat("Amount"),
            new Ingredient(ingResultSet.getString("IngredientID"), ingResultSet.getString("IngredientName")))
            );
          }
          
          // Adding tags to the object.
          String tagQuery = "SELECT tags.TagID, tags.TagName " +
          "FROM tags " +
          "JOIN recipe_tags ON recipe_tags.TagID = tags.TagID " +
          "WHERE recipe_tags.RecipeID = ?";

          try (PreparedStatement tagsStatement = conn.prepareStatement(tagQuery)) {
            tagsStatement.setString(1, id);
            ResultSet tagResultSet = tagsStatement.executeQuery();
            while (tagResultSet.next()) {
              Tag newTag = new Tag(
              tagResultSet.getString("TagID"),
              tagResultSet.getString("TagName"));
              recipeObject.addTag(newTag);
            }
            
          } catch (SQLException e) {
            System.out.println("Error adding tags: " + e);
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
    String query = "INSERT into weekly_list values  ((?),(?),(?))";
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
  public static void addRecipe(String recipeId, String UserID, String recipeName, String shortDesc, String longDesc, String Unit, Float Amount) throws SQLException {
    String query = "INSERT INTO recipes (RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount) VALUES(?,?,?,?,?,?,?)";
    try { 
      Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
      PreparedStatement sqlStatement = conn.prepareStatement(query);
      sqlStatement.setString(1, recipeId);
      sqlStatement.setString(2, UserID);
      sqlStatement.setString(3, recipeName);
      sqlStatement.setString(4, shortDesc);
      sqlStatement.setString(5, longDesc);
      sqlStatement.setString(6, Unit);
      sqlStatement.setFloat(7, Amount);
      sqlStatement.executeUpdate();
      
    } catch (SQLException e) {
      System.err.println("..." + e);
    }
  }

  // Method to query database when user searches for recipe by name.
  public static List<Recipe> getRecipesByName(String name) throws SQLException {
    ArrayList<Recipe> recipeList = new ArrayList<>();
    String query = "SELECT * FROM recipes WHERE RecipeName LIKE ?";
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
        PreparedStatement sqlStatement = conn.prepareStatement(query)) {
          sqlStatement.setString(1, "%" + name + "%");
          ResultSet result = sqlStatement.executeQuery();
          while (result.next()) {
            Recipe newRecipe = new Recipe(
              result.getString("RecipeID"),
              result.getString("RecipeName"),
              result.getString("ShortDesc"),
              result.getString("DetailedDesc")
            );
            recipeList.add(newRecipe);
          }
          result.close();

    } catch (SQLException e) {
      System.out.println(e);
    }
    return recipeList;
  }

  public static List<Recipe> getRecipesByIngredients(String ingredient) throws SQLException {
    ArrayList<Recipe> recipeList = new ArrayList<>();

    String query = "SELECT DISTINCT r.* FROM recipes r "

                 + "JOIN recipe_ingredients ri ON r.RecipeID = ri.RecipeID "

                 + "JOIN ingredients i ON ri.IngredientID = i.IngredientID "

                 + "WHERE i.IngredientName = ?";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");

         PreparedStatement sqlStatement = conn.prepareStatement(query)) {

      sqlStatement.setString(1, ingredient);

      ResultSet result = sqlStatement.executeQuery();

      while (result.next()) {
        Recipe newRecipe = new Recipe(
          result.getString("RecipeID"),
          result.getString("RecipeName"),
          result.getString("ShortDesc"),
          result.getString("DetailedDesc")
        );

        recipeList.add(newRecipe);
      }
      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return recipeList;
  }
}
