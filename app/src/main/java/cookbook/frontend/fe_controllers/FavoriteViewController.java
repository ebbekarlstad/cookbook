package cookbook.frontend.fe_controllers;

import java.io.IOException;
import java.util.List;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.FavoritesController;
import cookbook.backend.be_controllers.RecipeController;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FavoriteViewController {

	@FXML
	private ListView<Recipe> favoritesListView;

	private ObservableList<Recipe> favoriteRecipes = FXCollections.observableArrayList();
	private FavoritesController favoritesController;

	public FavoriteViewController() {
		DatabaseMng dbManager = new DatabaseMng();
		favoritesController = new FavoritesController(dbManager);
	}

	@FXML
	private void initialize() {
		favoritesListView.setItems(favoriteRecipes);
		favoritesListView.setCellFactory(lv -> new ListCell<Recipe>() {
			private final Hyperlink hyperlink = new Hyperlink();

			{
				hyperlink.setOnAction(event -> {
					Recipe recipe = getItem();
					if (recipe != null) {
						openRecipeDetails(recipe.getId());
					}
				});
			}

			@Override
			protected void updateItem(Recipe recipe, boolean empty) {
				super.updateItem(recipe, empty);
				if (empty || recipe == null) {
					setText(null);
					setGraphic(null);
				} else {
					hyperlink.setText(recipe.getRecipeName());
					setGraphic(hyperlink);
				}
			}
		});

		Platform.runLater(this::loadFavoriteRecipes);
	}

	private void loadFavoriteRecipes() {
		Long userId = UserSession.getInstance().getUserId();
		List<Recipe> favorites = favoritesController.getFavorites(userId);
		System.out.println("Favorites for user " + userId + ": " + favorites);
		favoriteRecipes.setAll(favorites);
	}

	private void openRecipeDetails(String recipeId) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecipeDetails.fxml"));
        Parent detailsView = loader.load();

        RecipeDetailsViewController controller = loader.getController();
        DatabaseMng dbManager = new DatabaseMng();
        RecipeController recipeController = new RecipeController(dbManager);
        Recipe recipe = recipeController.getRecipeById(recipeId);
        controller.initData(recipe);
        controller.setReturnContext("FavoriteViewController");

        Scene scene = new Scene(detailsView);
        Stage window = (Stage) favoritesListView.getScene().getWindow(); 
        window.setScene(scene);
        window.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


	@FXML
	public void goBackToNavigator(MouseEvent event) {
		try {
			String fxmlFile = UserSession.getInstance().isAdmin() ? "/NavigationViewAdmin.fxml"
					: "/NavigationView.fxml";

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
}
