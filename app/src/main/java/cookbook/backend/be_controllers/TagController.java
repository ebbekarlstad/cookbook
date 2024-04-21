package cookbook.backend.be_controllers;

import cookbook.backend.be_objects.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagController {
  public ArrayList<Tag> allTags = new ArrayList<>();

  /**
   * Retrieves a list of tags from the database.
   *
   * @return a list of Tag objects representing the tags
   * @throws SQLException if a database error occurs
   */

  public static List<Tag> getTags() throws SQLException {
    ArrayList<Tag> tags = new ArrayList<>();
    String query = "SELECT * FROM tag;";

    Connection conn = DriverManager
            .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while (result.next()) {
        Tag user = new Tag(
                result.getString("tag_id"),
                result.getString("name"));

        tags.add(user);
      }
      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return tags;
  }

  /**
   * Adds a tag to the database.
   *
   * @param tag_id   the ID of the tag
   * @param tag_name the name of the tag
   * @throws SQLException if a database error occurs
   */

  public static void addTag(String tag_id, String tag_name) throws SQLException {
    String query = "INSERT INTO tag VALUES(?, ?);";
    Connection conn = DriverManager
            .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, tag_id);
      sqlStatement.setString(2, tag_name);

      sqlStatement.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  /**
   * Adds a tag to a recipe in the database.
   *
   * @param RecipeID the ID of the recipe
   * @param TagID    the ID of the tag
   * @throws SQLException if a database error occurs
   */

  public static void addTagToRecipe(String RecipeID, String TagID) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
    String query = "INSERT INTO recipe_tag VALUES(?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, TagID);
      sqlStatement.setString(2, RecipeID);
      sqlStatement.executeUpdate();

    }
  }
}
