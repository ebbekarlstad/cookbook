package cookbook.frontend.fe_controllers;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.CommentController;
import cookbook.backend.be_controllers.FavoritesController;
import cookbook.backend.be_objects.AmountOfIngredients;
import cookbook.backend.be_objects.CommentObject;
import cookbook.backend.be_objects.Ingredient;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;
import cookbook.backend.be_controllers.IngredientController;


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

    private ObservableList<AmountOfIngredients> ingredients = FXCollections.observableArrayList();



    @FXML
    private Button LessPersons;

    @FXML
    private Button MorePersons;

    @FXML
    private TextField MultipliedAmount;


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
            } else {
                System.out.println("Failed to remove comment.");
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
                }
            } else {
                System.out.println("Failed to update comment.");
            }
        } else {
            System.out.println("No changes made to the comment.");
        }
    }

    public void handleHelpBackButton(ActionEvent event){
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
    public void addToFavorites(ActionEvent event) {
        Long userId = UserSession.getInstance().getUserId(); 
        if (userId != null && this.recipe != null) {
            try {
                boolean success = favoritesController.addFavorite(userId, this.recipe);
                if (success) {
                    System.out.println("Favorite added successfully.");
                    // Update UI to reflect the addition
                } else {
                    System.out.println("Failed to add favorite.");
                    // Optionally update UI to show failure message
                }

    }



    @FXML
    public void removeFromFavorites(ActionEvent event) {

        //   // Använd favoritesController för att ta bort från databasen istället
        //   if (favoritesController.removeFavorite(this.userId, this.recipe)) {
        //       System.out.println("Favorite removed successfully.");
        //       // Uppdatera ditt UI här om nödvändigt, t.ex. aktivera 'Add to favorite'-knappen
        //   } else {
        //       System.out.println("Failed to remove favorite.");
        //       // Visa felmeddelande till användaren
        //   }
        // String sql = "DELETE FROM user_favorites WHERE UserID = ? AND RecipeID = ?";
        // try (Connection conn = myDbManager.getConnection();
        //       PreparedStatement pstmt = conn.prepareStatement(sql)) {
        //     pstmt.setLong(1, userId);
        //     pstmt.setString(2, recipe.getId());
        //     int affectedRows = pstmt.executeUpdate();
        //     if ( affectedRows > 0) System.out.println("Done");;
        // } catch (SQLException e) {
        //   System.err.println("Failed to remove favorite: ");
        // }

        favoritesController.removeFavorite(UserSession.getInstance().getUserId(), recipe);
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


}