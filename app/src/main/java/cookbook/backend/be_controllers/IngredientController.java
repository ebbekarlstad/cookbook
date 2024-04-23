package cookbook.backend.be_controllers;

import cookbook.backend.be_objects.Ingredient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class IngredientController {

  /**
   * Retrieves a list of ingredients from the database.
   *
   * @return a list of Ingredient objects representing the ingredients
   * @throws SQLException if a database error occurs
   */

  public static List<Ingredient> getIngredients() throws SQLException {
    ArrayList<Ingredient> currentIngredients = new ArrayList<>();
    String query = "SELECT * FROM ingredients";
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
    try (PreparedStatement sqlstatement = conn.prepareStatement(query)) {
      ResultSet result = sqlstatement.executeQuery();
      while(result.next()) {
        Ingredient newIngredient = new Ingredient(
                result.getString("IngredientID"),
                result.getString("IngredientName"));
        currentIngredients.add(newIngredient);
      }
      result.close();
    }
    catch (SQLException e) {
      System.out.println(e);
    }
    return currentIngredients;
  }

  /**
   * Adds an ingredient to the database.
   *
   * @param IngredientID       the unique ID of the ingredient
   * @param IngredientName     the name of the ingredient
   * @throws SQLException if a database error occurs
   */

  public static void addIngredient(String IngredientID, String IngredientName) throws SQLException{
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
    String query = "INSERT INTO ingredients VALUES(?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, IngredientID);
      sqlStatement.setString(2, IngredientName);
      sqlStatement.executeUpdate();
    }
    catch (SQLException e) {
      System.out.println(e);
    }
  }

  /**
   * Adds an ingredient to a recipe in the database.
   *
   * @param RecipeID     the ID of the recipe
   * @param IngredientID the ID of the ingredient
   * @param Unit         the unit of measurement for the ingredient
   * @param Amount       the amount of the ingredient
   * @throws SQLException if a database error occurs
   */


  public static void addIngredientToRecipe(String RecipeID, String IngredientID, String Unit, Float Amount) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
    String query = "INSERT INTO recipe_ingredients VALUES (?,?,?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, RecipeID);
      sqlStatement.setString(2, IngredientID);
      sqlStatement.setString(3, Unit);
      sqlStatement.setFloat(4, Amount);
      sqlStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("..." + e);
    }
  }

}
