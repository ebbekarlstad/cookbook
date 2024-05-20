package cookbook.frontend.fe_controllers;

import cookbook.backend.be_controllers.IngredientController;
import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_controllers.TagController;
import cookbook.backend.be_objects.AmountOfIngredients;
import cookbook.backend.be_objects.Ingredient;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.Tag;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.UUID;

public class UserEditingRecipeController {
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
	private TableView<Tag> tagTable;
	@FXML
	private TableColumn<Tag, String> tagColumn;

	private ObservableList<Tag> tags = FXCollections.observableArrayList();

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
	private ComboBox<String> unit;

	Recipe recipe;

	private void loadRecipes() throws SQLException {
    List<Recipe> recipes = RecipeController.getRecipesByUserID(UserSession.getInstance().getUserId());
    RecipesComboBox.setItems(FXCollections.observableArrayList(recipes));
    RecipesComboBox.setConverter(new StringConverter<Recipe>() {
        @Override
        public String toString(Recipe recipe) {
            return (recipe != null) ? recipe.getRecipeName() : "Select recipe";
        }

        @Override
        public Recipe fromString(String string) {
            return null;
        }
    });

    if (!recipes.isEmpty()) {
        RecipesComboBox.setValue(recipes.get(0)); // Set default selected recipe if list is not empty
        recipe = recipes.get(0); // Initialize the recipe object
    }

    RecipesComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
        if (newValue != null) {
            updateRecipeDetailsUI(newValue);
            try {
							initData();
						} catch (SQLException e) {
							e.printStackTrace();
						}
            recipe = newValue; // Update the recipe object when a new recipe is selected
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
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM recipe_ingredients WHERE RecipeID = ?");
			statement.setString(1, recipeID);
			ResultSet resultSet = statement.executeQuery();
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
		ingredientTable.setItems(ingredients);
	}

	private void fetchTagsFromDatabase(String recipeID) {
		try {
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM recipe_tags WHERE RecipeID = ?");
			statement.setString(1, recipeID);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String tagID = resultSet.getString("TagID");
				Tag tag = fetchTagDetails(tagID);
				if (tag != null) {
					tags.add(tag);
				}
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tagTable.setItems(tags);
	}

	private Tag fetchTagDetails(String tagID) {
		try {
			List<Tag> allTags = TagController.getTags();
			for (Tag tag : allTags) {
				if (tag.getTagID().equals(tagID)) {
					return tag;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void initData() throws SQLException {
		Recipe selectedRecipe = RecipesComboBox.getValue();
		ingredients.clear();
		tags.clear();
		updateTagBox();
		tagsDropdown.getItems();


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

		tagColumn.setCellValueFactory(cellData -> {
			Tag tag = cellData.getValue();
			return new SimpleStringProperty(tag.getTagName());
		});

		fetchIngredientsFromDatabase(selectedRecipe.getId());
		fetchTagsFromDatabase(selectedRecipe.getId());
	}

	@FXML
	private void initialize() throws SQLException {
			unit.getItems().addAll("g", "kg", "ml", "L", "mg", "tea spoon", "pinch");
			loadRecipes();
			updateTagBox();
	
			tagsDropdown.setOnAction(event -> handleTagSelection());
	
			tagColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTagName()));
			tagTable.setItems(tags);
	}

	private void handleTagSelection() {
    String selectedTagName = tagsDropdown.getSelectionModel().getSelectedItem();
    if (selectedTagName != null && !selectedTagName.isEmpty()) {
        Tag selectedTag = findTagByName(selectedTagName);
        if (selectedTag != null && !tags.contains(selectedTag)) {
            tags.add(selectedTag);
            tagTable.refresh();
        }
    }
	}

	private Tag findTagByName(String tagName) {
    try {
        List<Tag> allTags = TagController.getTags();
        for (Tag tag : allTags) {
            if (tag.getTagName().equals(tagName)) {
                return tag;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
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
	void EditTagToList(ActionEvent event) {
		Tag selected = tagTable.getSelectionModel().getSelectedItem();
		if (selected != null) {
			tagName.setText(selected.getTagName());
		}
	}

	@FXML
	void updateTagInList(ActionEvent event) {
		Tag selected = tagTable.getSelectionModel().getSelectedItem();
		if (selected != null) {
			selected.setTagName(tagName.getText());
			tagTable.refresh();
		}
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
			String newLongDesc = recipeLongDesc.getText().trim();
			boolean updated = RecipeController.updateRecipeDetails(
					selectedRecipe.getId(),
					newRecipeName,
					newShortDesc,
					newLongDesc);
			if (updated) {
				System.out.println("Recipe updated successfully");
				updateIngredients(selectedRecipe.getId());
				updateTags(selectedRecipe.getId());
				saveNewTags(selectedRecipe.getId());
			} else {
				System.out.println("Recipe update failed");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	void addIngredientToList(ActionEvent event) {
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

				AmountOfIngredients newQuantityIngredients = new AmountOfIngredients(selectedUnit, selectedAmount,
						newIngredientObj);
				ingredients.add(newQuantityIngredients);
				ingredientTable.setItems(ingredients);
			} else {
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

	@FXML
	void addTagToList(ActionEvent event) {
		try {
			String TagName = tagName.getText();
			UUID uniqueID = UUID.randomUUID();
			String TagID = uniqueID.toString();
			TagController.addTag(TagID, TagName);
			Tag newTag = new Tag(TagID, TagName);
			tags.add(newTag);
			tagTable.setItems(tags);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void backButton(ActionEvent event) throws SQLException, IOException {
		try {
			Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/NavigationView.fxml"));
			Scene navigationPageScene = new Scene(navigationPageParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(navigationPageScene);
			window.show();
		} catch (Exception e) {
			e.printStackTrace();
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

	private void updateTags(String recipeID) throws SQLException {
		for (Tag tagDetails : tags) {
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

	private void saveNewTags(String recipeID) throws SQLException {
    for (Tag tagDetails : tags) {
        // Check if the tag already exists in the recipe_tags table
        if (!isTagAlreadyInRecipe(recipeID, tagDetails.getTagID())) {
            try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
                PreparedStatement insertStmt = connection.prepareStatement(
                        "INSERT INTO recipe_tags (RecipeID, TagID) VALUES (?, ?)")
            ) {
                insertStmt.setString(1, recipeID);
                insertStmt.setString(2, tagDetails.getTagID());
                insertStmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error inserting new tag: " + tagDetails.getTagName());
                e.printStackTrace();
            }
        }
    }
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
        } catch (SQLException e) {
            System.out.println("Error deleting ingredient: " + selectedIngredient.getIngredient().getIngredientName());
            e.printStackTrace();
        }
    } else {
        System.out.println("No ingredient selected for deletion or recipe not set.");
    }
}

	@FXML
	void deleteTagFromList(ActionEvent event) {
		Tag selectedTag = tagTable.getSelectionModel().getSelectedItem();
		if (selectedTag != null) {
				try (
						Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
						PreparedStatement deleteStmt = connection.prepareStatement("DELETE FROM recipe_tags WHERE TagID = ?")
				) {
						deleteStmt.setString(1, selectedTag.getTagID());
						deleteStmt.executeUpdate();

						// Remove the tag from the ObservableList and refresh the TableView
						tags.remove(selectedTag);
						tagTable.refresh();
				} catch (SQLException e) {
						System.out.println("Error deleting tag: " + selectedTag.getTagName());
						e.printStackTrace();
				}
		} else {
				System.out.println("No tag selected for deletion.");
		}
	}

	private boolean isTagAlreadyInRecipe(String recipeID, String tagID) {
    try (
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cookbookdb?user=root&password=root&useSSL=false");
        PreparedStatement checkStmt = connection.prepareStatement(
                "SELECT COUNT(*) FROM recipe_tags WHERE RecipeID = ? AND TagID = ?")
    ) {
        checkStmt.setString(1, recipeID);
        checkStmt.setString(2, tagID);
        ResultSet resultSet = checkStmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
	}

	public void updateTagBox() throws SQLException {
    tagsDropdown.getItems().clear();
    List<Tag> allTags = TagController.getTags();
    if (allTags != null && !allTags.isEmpty()) {
        for (Tag tag : allTags) {
            tagsDropdown.getItems().add(tag.getTagName());
        }
    }
	}
}
