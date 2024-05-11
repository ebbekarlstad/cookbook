package cookbook.frontend.fe_controllers;

import cookbook.backend.be_controllers.IngredientController;
import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_objects.AmountOfIngredients;
import cookbook.backend.be_objects.Ingredient;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EditingRecipeController {

  @FXML
  private TableView<AmountOfIngredients> ingredientTable;
  @FXML
  private TableColumn<AmountOfIngredients, String> ingredientColumn;
  @FXML
  private TableColumn<AmountOfIngredients, String> amountColumn;
  @FXML
  private TableColumn<AmountOfIngredients, String> unitColumn;

  private ObservableList<AmountOfIngredients> ingredients = FXCollections.observableArrayList();

  @FXML
  private Button EditIngredient;

  @FXML
  private Button EditTag;

  @FXML
  private ComboBox<Recipe> RecipesComboBox;

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

  Recipe recipe;

  private void loadRecipes() throws SQLException {
    List<Recipe> recipes = RecipeController.getRecipesByUserID(UserSession.getInstance().getUserId());
    RecipesComboBox.setItems(FXCollections.observableArrayList(recipes));
    RecipesComboBox.setConverter(new StringConverter<Recipe>() {
      @Override
      public java.lang.String toString(Recipe recipe) {
        return (recipe != null) ? recipe.getRecipeName() : "Select recipe";
      }

      @Override
      public Recipe fromString(String string) {
        return null;
      }
    });

    if (!recipes.isEmpty()) {
      RecipesComboBox.setValue(recipes.get(0)); // Set default selected recipe if list is not empty
    }

    RecipesComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
      if (newValue != null) {
        updateRecipeDetailsUI(newValue);
        initData();
      }
    });
}

  private void updateRecipeDetailsUI(Recipe recipe) {
    if (recipe != null) {
        recipeName.setText(recipe.getRecipeName());
        recipeShortDesc.setText(recipe.getShortDesc());
        recipeLongDesc.setText(recipe.getDetailedDesc());
    } else {
        recipeName.setText("");
        recipeShortDesc.setText("");
        recipeLongDesc.setText("");
    }
  }

  private Ingredient fetchIngredientDetails(String ingredientID) {
    try {
      // Retrieve ingredient details from the database using IngredientController queries that is already been specified
      List<Ingredient> ingredients = IngredientController.getIngredients();
      for (Ingredient ingredient : ingredients) {
        if (ingredient.getIngredientID().equals(ingredientID)) {
          return ingredient;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private void fetchIngredientsFromDatabase(String recipeID) {
    try {
      // I should implement the DBmanager class inside of here somehow but will change it later.
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM recipe_ingredients WHERE RecipeID = ?");
      statement.setString(1, recipeID);
      // Execute query to fetch ingredients
      ResultSet resultSet = statement.executeQuery();
      // Process the result set with a while loop
      while (resultSet.next()) {
        String ingredientID = resultSet.getString("IngredientID");
        String amount = resultSet.getString("Amount");
        String unit = resultSet.getString("Unit");
        Ingredient ingredient = fetchIngredientDetails(ingredientID);
        AmountOfIngredients amountOfIngredient = new AmountOfIngredients(unit, Float.parseFloat(amount), ingredient);
        ingredients.add(amountOfIngredient);
      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // Set the items of the table view to the list of ingredients
    ingredientTable.setItems(ingredients);
  }

  public void initData() {
    Recipe selectedRecipe = RecipesComboBox.getValue();
    // Set the recipe information

    ingredients.clear();

    ingredientColumn.setCellValueFactory(cellData -> {
        AmountOfIngredients ingredient = cellData.getValue();
        Ingredient ingredientObject = ingredient.getIngredient();
        if (ingredientObject != null) {
            return new SimpleStringProperty(ingredientObject.getIngredientName());
        } else {
            return new SimpleStringProperty("Null Ingredient");
        }
    });
    amountColumn.setCellValueFactory(cellData -> {
        AmountOfIngredients ingredient = cellData.getValue();
        return new SimpleStringProperty(String.valueOf(ingredient.getAmount()));
    });
    unitColumn.setCellValueFactory(cellData -> {
        AmountOfIngredients ingredient = cellData.getValue();
        return new SimpleStringProperty(ingredient.getUnit());
    });

    //Fetches everything from the databse and inserts it in the Table View
    fetchIngredientsFromDatabase(selectedRecipe.getId());

  }

  @FXML
  private void initialize() throws SQLException {
    unit.getItems().addAll("g", "kg", "ml", "L", "mg", "tea spoon", "pinch"); // Add items here
    loadRecipes();
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
  
          ingredientTable.refresh();  // Refresh the table view to show updated values
      }
  }
  

  @FXML
  void EditTagToList(ActionEvent event) {

  }

  @FXML
  void SaveRecipe(ActionEvent event) {
    try {
      Recipe selectedRecipe = RecipesComboBox.getValue();
      if (selectedRecipe == null) {
        System.out.println("No recipe selected");
        return;
      }
      String newRecipeName = recipeName.getText().trim();
      String newShortDesc = recipeShortDesc.getText().trim();
      String newLongDesc = ingredientName.getText().trim();
      boolean updated = RecipeController.updateRecipeDetails(
              selectedRecipe.getId(),
              newRecipeName,
              newShortDesc,
              newLongDesc);
      if (updated) {
        System.out.println("Recipe updated successfully");
        updateIngredients(selectedRecipe.getId());
      } else {
        System.out.println("Recipe update failed");
      }
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  @FXML
  void addIngredientToList(ActionEvent event) {

  }

  @FXML
  void addTagToList(ActionEvent event) {

  }

  public void backButton(ActionEvent event) throws SQLException, IOException {
    try {
      //Load the navigation page FXML
      Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
      Scene navigationPageScene = new Scene(navigationPageParent);

      // Get the current stage and replace it
      Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
      window.setScene(navigationPageScene);
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void updateIngredients(String recipeID) throws SQLException {
    // Loop through the ingredients list
    for (AmountOfIngredients ingredientDetails : ingredients) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
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

}
