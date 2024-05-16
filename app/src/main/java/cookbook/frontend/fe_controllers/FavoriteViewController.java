package cookbook.frontend.fe_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;

import java.util.List;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.FavoritesController;
import cookbook.backend.be_objects.Recipe;
import cookbook.backend.be_objects.UserSession;
import javafx.application.Platform;

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
		// Initialisera ListView med favoritrecept
		favoritesListView.setItems(favoriteRecipes);
		favoritesListView.setCellFactory(lv -> new ListCell<Recipe>() {
			@Override
			protected void updateItem(Recipe recipe, boolean empty) {
				super.updateItem(recipe, empty);
				if (empty || recipe == null) {
					setText(null);
				} else {
					setText(recipe.getRecipeName());
				}
			}
		});

		// Ladda favoritrecept i en bakgrundstråd om det behövs
		Platform.runLater(this::loadFavoriteRecipes);
	}

	private void loadFavoriteRecipes() {
		Long userId = UserSession.getInstance().getUserId();
		List<Recipe> favorites = favoritesController.getFavorites(userId);
		System.out.println("Favorites for user " + userId + ": " + favorites);
		favoriteRecipes.setAll(favorites);
	}

	@FXML
	public void goBackToNavigator(MouseEvent event) {
		try {
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
}
