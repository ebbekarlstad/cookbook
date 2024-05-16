package cookbook.frontend.fe_controllers;

import cookbook.backend.be_controllers.IngredientController;
import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_controllers.TagController;

import cookbook.backend.be_objects.AmountOfIngredients;
import cookbook.backend.be_objects.Ingredient;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.Tag;
import cookbook.backend.be_objects.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The RecipeViewController class controls the functionality of the recipe view in the frontend.
 */
public class RecipeViewController {

  @FXML
  public TextField recipeName;

  @FXML
  public TextArea recipeShortDesc;

  @FXML
  public TextArea recipeLongDesc;

  @FXML
  public ComboBox<String> tagsDropdown;

  @FXML
  public TextField tagName;

  @FXML
  public Button addTag;

  @FXML
  private TextField fromUser;

  @FXML
  private TextField toUser;

  @FXML
  public TextField ingredientName;

  @FXML
  public Button addIngredient;

  @FXML
  public Button back;

  @FXML
  public Button addRecipie;  // Name needs to be changed...

  @FXML
  public TextField amount;

  @FXML
  public ComboBox<String> unit;

  @FXML
  public Label tagsLabel;

  @FXML
  public Label ingredientLabel;

  public List<Recipe> recipes;

  public List<Ingredient> ingredients;
  public List<AmountOfIngredients> selectedIngredients = new ArrayList<>();

  public List<Tag> tags;
  public List<Tag> selectedTags = new ArrayList<>();


  @FXML
    private void initialize() throws SQLException {
      updateTagBox();
      tagsDropdown.getItems();
        unit.getItems().addAll("g", "kg", "ml", "L", "mg", "tea spoon", "pinch"); // Add items here
    }

  /**
   * Creates a new recipe based on the input provided by the user.
   * Retrieves the recipe name, short description, and long description from the corresponding text fields.
   * Generates a unique recipe ID using UUID.randomUUID().
   * Adds the recipe to the recipeController and creates a new recipeObject.
   * Iterates over the selected ingredients and adds them to the created recipe.
   * Iterates over the selected tags and adds them to the created recipe.
   * Displays a success message if the recipe is successfully created.
   * Displays an error message if an SQLException occurs.
   *
   * @param event The ActionEvent that triggered the method.
   * @throws SQLException If an error occurs while accessing the database.
   * @throws IOException  If an error occurs during I/O operations.
   */

   public void createRecipe(ActionEvent event) throws SQLException, IOException {
    // For Recipe
    String RecipeName = recipeName.getText();
    String userPlaceholder = "1"; // Placeholder for UserID
    String UserID = userPlaceholder; // Convert to string if necessary
    String ShortDesc = recipeShortDesc.getText();
    String DetailedDesc = recipeLongDesc.getText();
    UUID uniqueRecipe = UUID.randomUUID();
    String RecipeID = uniqueRecipe.toString();
    String Unit = unit.getSelectionModel().getSelectedItem();  // Get selected item from ComboBox
    Float Amount;
    try {
        Amount = Float.parseFloat(amount.getText());  // Try to parse the text to Float
    } catch (NumberFormatException e) {
        System.out.println("Invalid input for amount, defaulting to 0.");
        Amount = 0.0f;  // Setting a default value if parsing fails
    }

    try {
        RecipeController.addRecipe(RecipeID, UserID, RecipeName, ShortDesc, DetailedDesc, Unit, Amount);
        Recipe createdRecipe = new Recipe(RecipeID, RecipeName, ShortDesc, DetailedDesc);

        for (AmountOfIngredients ingredient : selectedIngredients) {
            createdRecipe.addIngredient(ingredient);
            IngredientController.addIngredientToRecipe(RecipeID, ingredient.ingredientID(), ingredient.getUnit(),
                    ingredient.getAmount());
        }

        for (Tag tag : selectedTags) {
            createdRecipe.addTag(tag);
            TagController.addTagToRecipe(RecipeID, tag.getTagID());
        }

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success");
        success.setContentText("Recipe created successfully! You may now go back.");
        success.show();
    } catch (SQLException x) {
        Alert failure = new Alert(Alert.AlertType.ERROR);
        failure.setTitle("Error");
        failure.setContentText("Failed to create recipe due to a database error.");
        failure.show();
        System.err.println("SQL Exception: " + x.getMessage());
    }
}

/**
 * Returns to the main menu screen when the back button is clicked.
 * Loads the main menu scene and sets it as the current scene.
 * Displays the main menu stage with specified dimensions.
 *
 * @param event The ActionEvent that triggered the method.
 * @throws SQLException If an error occurs while accessing the database.
 * @throws IOException  If an error occurs during I/O operations.
 */
public void backButton(ActionEvent event) throws SQLException, IOException {
    try {
        // Determine the correct FXML file based on the user's role
        String fxmlFile = UserSession.getInstance().isAdmin() ? "/NavigationViewAdmin.fxml" : "/NavigationView.fxml";

        // Load the appropriate navigation page FXML
        Parent navigationPageParent = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene navigationPageScene = new Scene(navigationPageParent);

        // Get the current stage and replace it
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(navigationPageScene);
        window.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


  /**
   * Adds the selected tag to the temporary list of tags.
   * Retrieves the tag name from the text field or the dropdown menu.
   * Generates a unique tag ID using UUID.randomUUID().
   * Checks if the tag already exists in the list, and displays an error message if it does.
   * Adds the new tag to the tagController and creates a new tagObject.
   * Displays a success message if the tag is successfully created.
   *
   * @param event The ActionEvent that triggered the method.
   * @throws SQLException If an error occurs while accessing the database.
   * @throws IOException  If an error occurs during I/O operations.
   */

  public void addTagToList(ActionEvent event) throws SQLException, IOException {
    if (selectedTags == null) {
      selectedTags = new ArrayList<>();
  }
    if (tagsDropdown.getSelectionModel().getSelectedItem() == null) {
      String TagName = tagName.getText();
      UUID uniqueID = UUID.randomUUID();
      String TagID = uniqueID.toString();

      //If duplicate, do this. Else, do that.
      if (findTag(TagName) != null) {
        //If tag already exists, show this.
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error!");
        error.setContentText("Tag already exists!");
        error.show();
      } else {
        // Add the tag to the database and create an object.
        TagController.addTag(TagID, TagName);
        Tag newTag = new Tag(TagID, TagName);
        selectedTags.add(newTag);

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success!");
        success.setContentText("You successfully created a new tag!");
        success.show();
        updateTagBox();

      }
    } else if (tagsDropdown.getSelectionModel().getSelectedItem() != null) {
      String TagName = tagsDropdown.getSelectionModel().getSelectedItem();
      Tag myTag = findTag(TagName);

      if (myTag != null && !selectedTags.contains(myTag)) {
        selectedTags.add(myTag);
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success!");
        success.setContentText("You successfully added a new tag!");
        success.show();
      }
    }

    tagsDropdown.setValue(null);
    tagName.setText("");
    updateTagsLabel();
  }


  /**
   * Adds the entered ingredient to the temporary list of ingredients.
   * Retrieves the ingredient name, unit, and amount from the corresponding text fields and combo box.
   * Generates a unique ingredient ID using UUID.randomUUID().
   * Adds the new ingredient to the ingredientController and creates a new ingredientObject.
   * Adds the new ingredient to the temporary list of selectedIngredients.
   * Updates the ingredientLabel to display the added ingredient.
   * Displays a success message if the ingredient is successfully added.
   *
   * @param event The ActionEvent that triggered the method.
   * @throws SQLException If an error occurs while accessing the database.
   * @throws IOException  If an error occurs during I/O operations.
   */

  public void addIngredientToList(ActionEvent event) throws SQLException, IOException {
    if (selectedIngredients == null) {
      selectedIngredients = new ArrayList<>();
  }
    try {
      String IngredientName = ingredientName.getText();
      UUID uniqueID = UUID.randomUUID();
      String uniqueIngredientID = uniqueID.toString();
      String selectedUnit = unit.getSelectionModel().getSelectedItem();
      if (selectedUnit != null) {
        String a = amount.getText();
        float selectedAmount = Float.parseFloat(a);
        IngredientController.addIngredient(uniqueIngredientID, IngredientName);
        Ingredient newIngredientObj = new Ingredient(uniqueIngredientID, IngredientName);
  
        AmountOfIngredients newQuantityIngredients = new AmountOfIngredients(selectedUnit, selectedAmount, newIngredientObj);
        selectedIngredients.add(newQuantityIngredients);
        String currentLabelText = ingredientLabel.getText();
        if (currentLabelText.isEmpty()) {
          ingredientLabel.setText(IngredientName);
        } else {
          ingredientLabel.setText(currentLabelText + ", " + IngredientName);
        }
  
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success!");
        success.setContentText("You successfully created a new ingredient!");
        success.show();
      } else{
        System.out.println("You must choose a unit!");
        Alert failure = new Alert(Alert.AlertType.ERROR);
        failure.setTitle("Error");
        failure.setContentText("You must choose a unit.");
        failure.show();
      }
      } catch (SQLException e) {
        System.out.println(e);
      }
  }

  /**
   * Updates the tags dropdown box with the list of available tags.
   *
   * @throws SQLException If an error occurs while accessing the database.
   */
  public void updateTagBox() throws SQLException {
    tagsDropdown.getItems().clear();
    tagsDropdown.getItems().add(null);
    for (Tag tag : TagController.getTags()) {
      String TagName = tag.getTagName();
      tagsDropdown.getItems().add(TagName);
    }

  }

  public Tag findTag(String TagName) throws SQLException {
    for (Tag tag : TagController.getTags()) {
      if (tag.getTagName().equals(TagName)) {
        return tag;
      }
    }
    return null;
  }

  /**
   * Initializes the controller when the corresponding view is loaded.
   * Retrieves the recipes, initializes the lists for selected tags and ingredients,
   * and initializes the tags list. Updates the tag box and sets the items for the unit ComboBox.
   * Prints the size of the recipes list.
   *
   * @param location  The location used to resolve relative paths for the root object,
   *                  or null if the location is not known.
   * @param resources The resources used to localize the root object,
   *                  or null if the root object was not localized.
   */

  /**
   * Updates the tags label to display the names of the selected tags.
   * Retrieves the tag names from the selectedTags list using stream and map operations.
   * Concatenates the tag names into a comma-separated string.
   * Sets the resulting string as the text of the tagsLabel.
   */

  private void updateTagsLabel() {
    List<String> tagNames = selectedTags.stream().map(Tag::getTagName).collect(Collectors.toList());
    tagsLabel.setText(String.join(", ", tagNames));
  }
}