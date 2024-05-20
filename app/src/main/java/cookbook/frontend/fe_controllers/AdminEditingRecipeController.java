package cookbook.frontend.fe_controllers;

import cookbook.backend.be_controllers.IngredientController;
import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_controllers.TagController;
import cookbook.backend.be_objects.AmountOfIngredients;
import cookbook.backend.be_objects.Ingredient;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.Tag;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AdminEditingRecipeController {

  @FXML
  private TableView<AmountOfIngredients> ingredientTable;
  @FXML
  private TableColumn<AmountOfIngredients, String> ingredientColumn;
  @FXML
  private TableColumn<AmountOfIngredients, String> amountColumn;
  @FXML
  private TableColumn<AmountOfIngredients, String> unitColumn;

  @FXML
  private TableView<Tag> tagTable;

  @FXML
  private Button deleteIngredientButton;
  @FXML
  private Button deleteTagButton;

  @FXML
  private TableColumn<Tag, String> tagColumn;

  private ObservableList<AmountOfIngredients> ingredients = FXCollections.observableArrayList();
  private ObservableList<AmountOfIngredients> newIngredients = FXCollections.observableArrayList();
  private ObservableList<Tag> tags = FXCollections.observableArrayList();
  private ObservableList<Tag> newTags = FXCollections.observableArrayList();
  private ObservableList<Tag> updatedTags = FXCollections.observableArrayList();

  @FXML
  private Button EditIngredient;

  @FXML
  private Button EditTag;

  @FXML
  private Button SaveRecipe;

  @FXML
  private Button addIngredient;

  @FXML
  private Button addTag;

  @FXML
  private TextField amount;

  @FXML
  private Button back;

  @FXML
  private Label ingredientLabel;

  @FXML
  private TextField ingredientName;

  @FXML
  private TextArea recipeLongDesc;

  @FXML
  private TextField recipeName;

  @FXML
  private TextArea recipeShortDesc;

  @FXML
  private TextField tagName;

  @FXML
  private ComboBox<String> tagsDropdown;

  @FXML
  private Label tagsLabel;

  @FXML
  private ComboBox<String> unit;

  private Recipe recipe;

  public void initData(Recipe recipe) {
    this.recipe = recipe;
    recipeName.setText(recipe.getRecipeName());
    recipeShortDesc.setText(recipe.getShortDesc());
    recipeLongDesc.setText(recipe.getDetailedDesc());
    fetchIngredientsFromDatabase(recipe.getId());
    fetchTagsFromDatabase(recipe.getId());
  }

  private void fetchIngredientsFromDatabase(String recipeID) {
    try {
      Connection connection = DriverManager
          .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
      PreparedStatement statement = connection.prepareStatement(
          "SELECT ingredients.IngredientID, ingredients.IngredientName, recipe_ingredients.Amount, recipe_ingredients.Unit "
              +
              "FROM recipe_ingredients " +
              "JOIN ingredients ON recipe_ingredients.IngredientID = ingredients.IngredientID " +
              "WHERE recipe_ingredients.RecipeID = ?");
      statement.setString(1, recipeID);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        String ingredientID = resultSet.getString("IngredientID");
        String ingredientName = resultSet.getString("IngredientName");
        String amount = resultSet.getString("Amount");
        String unit = resultSet.getString("Unit");

        Ingredient ingredient = new Ingredient(ingredientID, ingredientName);
        AmountOfIngredients amountOfIngredient = new AmountOfIngredients(unit, Float.parseFloat(amount), ingredient);
        ingredients.add(amountOfIngredient);
      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    ingredientTable.setItems(ingredients);
  }

  @FXML
  void deleteIngredientFromList(ActionEvent event) {
      AmountOfIngredients selectedIngredient = ingredientTable.getSelectionModel().getSelectedItem();
      if (selectedIngredient != null && recipe != null) {
          try (
              Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
              PreparedStatement deleteStmt = connection.prepareStatement("DELETE FROM recipe_ingredients WHERE IngredientID = ? AND RecipeID = ?")
          ) {
              deleteStmt.setString(1, selectedIngredient.getIngredient().getIngredientID());
              deleteStmt.setString(2, recipe.getId());
              deleteStmt.executeUpdate();
  
              // Remove the ingredient from the ObservableList and refresh the TableView
              ingredients.remove(selectedIngredient);
              ingredientTable.refresh();
  
              Alert success = new Alert(Alert.AlertType.INFORMATION);
              success.setTitle("Success!");
              success.setContentText("Ingredient deleted successfully.");
              success.show();
          } catch (SQLException e) {
              System.out.println("Error deleting ingredient: " + selectedIngredient.getIngredient().getIngredientName());
              e.printStackTrace();
          }
      } else {
          Alert error = new Alert(Alert.AlertType.ERROR);
          error.setTitle("Error");
          error.setContentText("No ingredient selected for deletion or recipe not set.");
          error.show();
      }
  }

  @FXML
  void deleteTagFromList(ActionEvent event) {
      Tag selectedTag = tagTable.getSelectionModel().getSelectedItem();
      if (selectedTag != null) {
          try (
              Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
              PreparedStatement deleteStmt = connection.prepareStatement("DELETE FROM recipe_tags WHERE TagID = ? AND RecipeID = ?")
          ) {
              deleteStmt.setString(1, selectedTag.getTagID());
              deleteStmt.setString(2, recipe.getId());
              deleteStmt.executeUpdate();
  
              // Remove the tag from the ObservableList and refresh the TableView
              tags.remove(selectedTag);
              tagTable.refresh();
  
              Alert success = new Alert(Alert.AlertType.INFORMATION);
              success.setTitle("Success!");
              success.setContentText("Tag deleted successfully.");
              success.show();
          } catch (SQLException e) {
              System.out.println("Error deleting tag: " + selectedTag.getTagName());
              e.printStackTrace();
          }
      } else {
          Alert error = new Alert(Alert.AlertType.ERROR);
          error.setTitle("Error");
          error.setContentText("No tag selected for deletion.");
          error.show();
      }
  }

  private void fetchTagsFromDatabase(String recipeID) {
    try {
      Connection connection = DriverManager
          .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
      PreparedStatement statement = connection.prepareStatement(
          "SELECT tags.TagID, tags.TagName " +
              "FROM recipe_tags " +
              "JOIN tags ON recipe_tags.TagID = tags.TagID " +
              "WHERE recipe_tags.RecipeID = ?");
      statement.setString(1, recipeID);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        String tagID = resultSet.getString("TagID");
        String tagName = resultSet.getString("TagName");

        Tag tag = new Tag(tagID, tagName);
        tags.add(tag);
      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    tagTable.setItems(tags);
  }

  @FXML
  private void initialize() throws SQLException {
    unit.getItems().addAll("g", "kg", "ml", "L", "mg", "tea spoon", "pinch");

    ingredientColumn.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getIngredient().getIngredientName()));
    amountColumn
        .setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAmount())));
    unitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnit()));

    tagColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTagName()));

    tagsDropdown.getItems().clear();
    for (Tag tag : TagController.getTags()) {
      tagsDropdown.getItems().add(tag.getTagName());
    }
  }

  @FXML
  void EditIngredientToList(ActionEvent event) {
    AmountOfIngredients selected = ingredientTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      ingredientName.setText(selected.getIngredient().getIngredientName());
      amount.setText(String.valueOf(selected.getAmount()));
      unit.getSelectionModel().select(selected.getUnit());
    }
  }

  @FXML
  void updateIngredientInList(ActionEvent event) {
    AmountOfIngredients selected = ingredientTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      selected.getIngredient().setIngredientName(ingredientName.getText());
      selected.setAmount(Float.parseFloat(amount.getText()));
      selected.setUnit(unit.getValue());
      ingredientTable.refresh();
    }
  }

  @FXML
  void addIngredientToList(ActionEvent event) throws SQLException, IOException {
    String IngredientName = ingredientName.getText();
    UUID uniqueID = UUID.randomUUID();
    String uniqueIngredientID = uniqueID.toString();
    String selectedUnit = unit.getSelectionModel().getSelectedItem();
    if (selectedUnit != null) {
      String a = amount.getText();
      float selectedAmount = Float.parseFloat(a);
      IngredientController.addIngredient(uniqueIngredientID, IngredientName);
      Ingredient newIngredientObj = new Ingredient(uniqueIngredientID, IngredientName);

      AmountOfIngredients newQuantityIngredients = new AmountOfIngredients(selectedUnit, selectedAmount,
          newIngredientObj);
      ingredients.add(newQuantityIngredients);
      newIngredients.add(newQuantityIngredients);
      ingredientTable.refresh();

      String currentLabelText = ingredientLabel.getText();
      if (currentLabelText.isEmpty()) {
        ingredientLabel.setText(IngredientName);
      } else {
        ingredientLabel.setText(currentLabelText + ", " + IngredientName);
      }

      Alert success = new Alert(Alert.AlertType.INFORMATION);
      success.setTitle("Success!");
      success.setContentText("You successfully added a new ingredient!");
      success.show();
    } else {
      System.out.println("You must choose a unit!");
      Alert failure = new Alert(Alert.AlertType.ERROR);
      failure.setTitle("Error");
      failure.setContentText("You must choose a unit.");
      failure.show();
    }
  }

  @FXML
  void addTagToList(ActionEvent event) throws SQLException {
    String tagNameInput = tagName.getText();
    String selectedTag = tagsDropdown.getSelectionModel().getSelectedItem();

    if (tagNameInput != null && !tagNameInput.trim().isEmpty()) {
      UUID uniqueID = UUID.randomUUID();
      String uniqueTagID = uniqueID.toString();

      TagController.addTag(uniqueTagID, tagNameInput);
      Tag newTag = new Tag(uniqueTagID, tagNameInput);
      tags.add(newTag);
      newTags.add(newTag);
      tagTable.refresh();

      Alert success = new Alert(Alert.AlertType.INFORMATION);
      success.setTitle("Success!");
      success.setContentText("You successfully created a new tag!");
      success.show();
    } else if (selectedTag != null) {
      Tag tag = findTagByName(selectedTag);
      if (tag != null && !tags.contains(tag)) {
        tags.add(tag);
        tagTable.refresh();

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success!");
        success.setContentText("You successfully added an existing tag!");
        success.show();
      }
    }

    tagsDropdown.setValue(null);
    tagName.clear();
  }

  private Tag findTagByName(String tagName) throws SQLException {
    for (Tag tag : TagController.getTags()) {
      if (tag.getTagName().equals(tagName)) {
        return tag;
      }
    }
    return null;
  }

  @FXML
  void EditTagToList(ActionEvent event) {
    Tag selected = tagTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
      tagName.setText(selected.getTagName());
    }
  }

  @FXML
  void updateTagInList(ActionEvent event) {
    Tag selected = tagTable.getSelectionModel().getSelectedItem();
    String updatedTagName = tagName.getText();
    if (updatedTagName.isEmpty()) {
      Alert error = new Alert(Alert.AlertType.ERROR);
      error.setTitle("Error...");
      error.setContentText("Tag name cannot be empty");
      error.show();
    } else {
      if (selected != null) {
        selected.setTagName(tagName.getText());
        updatedTags.add(selected);
        tagTable.refresh();
      }
    }
  }

  @FXML
  void SaveRecipe(ActionEvent event) {
    try {
      if (recipe == null) {
        System.out.println("No recipe selected");
        return;
      }
      String newRecipeName = recipeName.getText().trim();
      String newShortDesc = recipeShortDesc.getText().trim();
      String newLongDesc = recipeLongDesc.getText().trim();
      boolean updated = RecipeController.updateRecipeDetails(
          recipe.getId(),
          newRecipeName,
          newShortDesc,
          newLongDesc);
      if (updated) {
        System.out.println("Recipe updated successfully");
        updateIngredients(recipe.getId());
        saveNewIngredients(recipe.getId());
        saveNewTags(recipe.getId());
        updateTags(recipe.getId()); // Call the method to update existing tags
      } else {
        System.out.println("Recipe update failed");
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void updateIngredients(String recipeID) throws SQLException {
    for (AmountOfIngredients ingredientDetails : ingredients) {
      try (
          Connection connection = DriverManager
              .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
          PreparedStatement updateStmt = connection.prepareStatement(
              "UPDATE recipe_ingredients SET Amount = ?, Unit = ? WHERE RecipeID = ? AND IngredientID = ?")) {
        updateStmt.setFloat(1, ingredientDetails.getAmount());
        updateStmt.setString(2, ingredientDetails.getUnit());
        updateStmt.setString(3, recipeID);
        updateStmt.setString(4, ingredientDetails.getIngredient().getIngredientID());
        updateStmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println("Error updating ingredient: " + ingredientDetails.getIngredient().getIngredientName());
        e.printStackTrace();
      }
    }
  }

  private void saveNewIngredients(String recipeID) throws SQLException {
    for (AmountOfIngredients ingredientDetails : newIngredients) {
      try (
          Connection connection = DriverManager
              .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
          PreparedStatement insertStmt = connection.prepareStatement(
              "INSERT INTO recipe_ingredients (RecipeID, IngredientID, Amount, Unit) VALUES (?, ?, ?, ?)")) {
        insertStmt.setString(1, recipeID);
        insertStmt.setString(2, ingredientDetails.getIngredient().getIngredientID());
        insertStmt.setFloat(3, ingredientDetails.getAmount());
        insertStmt.setString(4, ingredientDetails.getUnit());
        insertStmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println("Error saving new ingredient: " + ingredientDetails.getIngredient().getIngredientName());
        e.printStackTrace();
      }
    }
  }

  private void saveNewTags(String recipeID) throws SQLException {
    for (Tag tagDetails : newTags) {
      try (
          Connection connection = DriverManager
              .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
          PreparedStatement insertStmt = connection.prepareStatement(
              "INSERT INTO recipe_tags (RecipeID, TagID) VALUES (?, ?)")) {
        insertStmt.setString(1, recipeID);
        insertStmt.setString(2, tagDetails.getTagID());
        insertStmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println("Error saving new tag: " + tagDetails.getTagName());
        e.printStackTrace();
      }
    }
  }

  private void updateTags(String recipeID) throws SQLException {
    for (Tag tagDetails : updatedTags) {
      try (
          Connection connection = DriverManager
              .getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
          PreparedStatement updateStmt = connection.prepareStatement(
              "UPDATE tags SET TagName = ? WHERE TagID = ?")) {
        updateStmt.setString(1, tagDetails.getTagName());
        updateStmt.setString(2, tagDetails.getTagID());
        updateStmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println("Error updating tag: " + tagDetails.getTagName());
        e.printStackTrace();
      }
    }
  }

  public void backButton(ActionEvent event) throws SQLException, IOException {
    try {
      Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/RecipeListView.fxml"));
      Scene navigationPageScene = new Scene(navigationPageParent);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(navigationPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
