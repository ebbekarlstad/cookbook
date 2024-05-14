package cookbook.frontend.fe_controllers;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.CommentController;
import cookbook.backend.be_controllers.FavoritesController;
import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_objects.AmountOfIngredients;
import cookbook.backend.be_objects.CommentObject;
import cookbook.backend.be_objects.Ingredient;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;
import cookbook.backend.be_controllers.IngredientController;
import javafx.scene.control.Alert.AlertType;



import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.sql.*;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RecipeDetailsViewController {

    @FXML
    private TableView<AmountOfIngredients> ingredientTable;

    @FXML
    private TableColumn<AmountOfIngredients, String> ingredientColumn;

    @FXML
    private TableColumn<AmountOfIngredients, String> amountColumn;

    @FXML
    private TableColumn<AmountOfIngredients, String> unitColumn;

    @FXML
    private Button deleteRecipeButton;


    private ObservableList<AmountOfIngredients> ingredients = FXCollections.observableArrayList();



    @FXML
    private Button LessPersons;

    @FXML
    private Button MorePersons;

    @FXML
    private TextField MultipliedAmount;

    @FXML
    private ToggleButton toggleFavorite; 

    @FXML
    private ImageView favoriteIcon;  // Se till att denna ImageView har en fx:id som matchar FXML


    private String recipeId;
    private int commentId;
    Recipe recipe;

    @FXML
    private Label titleLabel; // Label for the recipe title.
    @FXML
    private Label shortLabel; // Label for the short description.
    @FXML
    private Label longLabel; // Label for the detailed description.

    @FXML
    private Button shareRecipeButton;


    DatabaseMng myDbManager;

    private FavoritesController favoritesController = new FavoritesController(myDbManager);



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

    @FXML
    private void initialize() {
        DatabaseMng dbManager = new DatabaseMng();
        this.favoritesController = new FavoritesController(dbManager);
        // Andra initialiseringsuppgifter...
    }

    public void initData(Recipe recipe) {
        this.recipe = recipe;
        // Set the recipe information
        titleLabel.setText(recipe.getRecipeName());
        shortLabel.setText(recipe.getShortDesc());
        longLabel.setText(recipe.getDetailedDesc());

        this.recipeId = recipe.getId();
        loadComments();

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
        fetchIngredientsFromDatabase(recipeId);

    }

    // The variables needed to make this logic work.
    private int numberOfPersons = 1;
    private float currentMultipliedAmount = 1.0f;



    //for incrementing and decrementing the amount based on how many people.
    @FXML
    void DecrementPeople(ActionEvent event) {
        if (numberOfPersons > 1) {
            numberOfPersons--;
            updateMultipliedAmountAndIngredients();
        }
    }

    @FXML
    void IncrementPeople(ActionEvent event) {
        numberOfPersons++;
        updateMultipliedAmountAndIngredients();
    }

    private void updateMultipliedAmountAndIngredients() {
        // Update the current multiplied amount
        currentMultipliedAmount = (float) numberOfPersons;
        MultipliedAmount.setText(String.valueOf(currentMultipliedAmount));
        updateIngredientsAmount(); // Update ingredients amount based on the new multiplied amount
    }


    private void updateIngredientsAmount() {
        for (AmountOfIngredients ingredient : ingredients) {
            float originalAmount = ingredient.getOriginalAmount();
            float adjustedAmount = originalAmount * currentMultipliedAmount; // Multiply by the current multiplied amount
            ingredient.setAmount(adjustedAmount);
        }
        ingredientTable.refresh(); // Refresh the table view to reflect the changes
    }


    private CommentController commentController;

    // Constructor
    public RecipeDetailsViewController() {
        myDbManager = new DatabaseMng();
        this.commentController = new CommentController(myDbManager);
        favoritesController = new FavoritesController(myDbManager);
    }

    private void loadComments() {
        List<String> comments = commentController.fetchComments(this.recipeId);
        commentsListView.getItems().setAll(comments); // Clears existing items and adds all fetched comments
    }

    @FXML
    private ListView<String> commentsListView;  // List to display comments

    @FXML
    private TextField commentInput;  // Input field for comments

    private String currentCommentText;

    @FXML
    private void addComment(ActionEvent event) {
        String commentText = commentInput.getText().trim();  // Get text from TextField
        if (!commentText.isEmpty()) {

            CommentObject newComment = new CommentObject(this.commentId, this.recipeId, UserSession.getInstance().getUserId(), commentText, "yy-mm-dd hh:mm:ss"); // Adjusted constructor
            commentsListView.getItems().add(commentText);  // Add comment to ListView
            commentInput.clear();  // Clear the input field

            if (!commentController.addComment(newComment)) {
                System.out.println("Failed to add comment.");
                Alert failure = new Alert(Alert.AlertType.INFORMATION);
                failure.setTitle("Error..:(");
                failure.setContentText("There was a problem with adding a comment.");
                failure.show();
                
            }
        }
    }

    @FXML
    private void removeComment(ActionEvent event) {
        int selectedIndex = commentsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedComment = commentsListView.getItems().get(selectedIndex);

            if (commentController.removeComment(selectedComment)) {
                commentsListView.getItems().remove(selectedIndex);  // Remove the comment from the ListView
                System.out.println("Comment removed successfully.");
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success!");
                success.setContentText("You successfully removed the comment!");
                success.show();
            } else {
                System.out.println("Failed to remove comment.");
                Alert failure = new Alert(Alert.AlertType.INFORMATION);
                failure.setTitle("Error..:(");
                failure.setContentText("There was a problem deleting the comment.");
                failure.show();
            }
        } else {
            System.out.println("No comment selected.");
        }
    }

    @FXML
    private void editComment(ActionEvent event) {
        int selectedIndex = commentsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            currentCommentText = commentsListView.getItems().get(selectedIndex);
            commentInput.setText(currentCommentText); // Set the text of the selected comment into the text field for editing
        } else {
            System.out.println("No comment selected.");
        }
    }

    @FXML
    public void updateComment(ActionEvent event) {
        String newCommentText = commentInput.getText().trim();
        if (!newCommentText.isEmpty() && !newCommentText.equals(currentCommentText)) {
            if (commentController.updateComment(currentCommentText, newCommentText)) {
                int selectedIndex = commentsListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    commentsListView.getItems().set(selectedIndex, newCommentText); // Update the comment in the ListView
                    commentInput.clear(); // Clear the text field after updating
                    System.out.println("Comment updated successfully.");
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("Success!");
                    success.setContentText("Comment updated!");
                    success.show();
                }
            } else {
                System.out.println("Failed to update comment.");
                Alert failure = new Alert(Alert.AlertType.INFORMATION);
                failure.setTitle("Error..:(");
                failure.setContentText("There was a probelm with updating the comment.");
                failure.show();
            }
        } else {
            System.out.println("No changes made to the comment.");
        }
    }

    public void handleHelpBackButton(ActionEvent event){
        try {
            //Load the navigation page FXML
            Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/RecipeListView.fxml"));
            Scene navigationPageScene = new Scene(navigationPageParent);

            // Get the current stage and replace it
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(navigationPageScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // When user clicks share recipe.
    public void shareRecipe(ActionEvent event) {
        try {
            // Load the ShareDialog FXML and get the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShareDialog.fxml"));
            Parent sharePageParent = loader.load();
            ShareDialogController shareDialogController = loader.getController();

            // Pass the recipe data to the ShareDialogController
            shareDialogController.initData(this.recipe);

            // Create a new stage (window)
            Stage shareStage = new Stage();
            shareStage.setTitle("Share Recipe");
            shareStage.setScene(new Scene(sharePageParent));
            shareStage.initModality(Modality.APPLICATION_MODAL); // This will make it so user cant interact with old window
            shareStage.show(); // Showw the new stage
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleToggleFavorite() {
        Long userId = UserSession.getInstance().getUserId();
        if (userId != null && this.recipe != null) {
            try {
                boolean success;
                if (toggleFavorite.isSelected()) {
                    favoriteIcon.setImage(new Image(getClass().getResourceAsStream("/images/starBLACK.png")));
                    success = favoritesController.addFavorite(userId, this.recipe);
                    showMessage(success, "added to");
                } else {
                    favoriteIcon.setImage(new Image(getClass().getResourceAsStream("/images/starWHITE.png")));
                    success = favoritesController.removeFavorite(userId, this.recipe);
                    showMessage(success, "removed from");
                }
            } catch (Exception e) {
                System.err.println("Error updating favorite status: " + e.getMessage());
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "There was a problem updating the favorites status.");
            }
        } else {
            System.out.println("UserId or Recipe is null, cannot update favorites.");
            showAlert(Alert.AlertType.ERROR, "Error", "User or Recipe data is missing.");
        }
    }
    
    private void showMessage(boolean success, String action) {
        if (success) {
            System.out.println("Favorite " + action + " successfully.");
            showAlert(Alert.AlertType.INFORMATION, "Success!", "Recipe successfully " + action + " your favorites!");
        } else {
            System.out.println("Failed to " + action + " favorite.");
            showAlert(Alert.AlertType.ERROR, "Error..:(", "There was a problem " + action + " this recipe to your favorites.");
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }

  

    @FXML
    private void handleweekButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PopupWeekList.fxml"));
            Parent parent = loader.load();

            PopupWeeklyViewController popupController = loader.getController();
            if (popupController != null) {
                popupController.initData(recipe, UserSession.getInstance().getUserId()); // Skickar nu den här lokalt satta userId
            } else {
                System.out.println("Popup controller was not initialized.");
                return; // To avoid further execution
            }
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Weekly Recipe Planner");
            stage.initModality(Modality.APPLICATION_MODAL); // Restricts interaction to the other windows
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println("Failed to load the weekly list popup: " + e.getMessage());
            e.printStackTrace();
            System.out.println("Error when opening the popup: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteRecipe(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Recipe");
        alert.setHeaderText("Are you sure you want to delete this recipe?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean success = RecipeController.deleteRecipeById(recipeId);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Recipe deleted successfully.");
                    // Navigate back to the recipe list view
                    Parent navigationPageParent = FXMLLoader.load(getClass().getResource("/RecipeListView.fxml"));
                    Scene navigationPageScene = new Scene(navigationPageParent);
                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.setScene(navigationPageScene);
                    window.show();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the recipe.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the recipe.");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading the recipe list view.");
            }
        }
    }

 

}
