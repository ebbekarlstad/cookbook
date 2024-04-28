package cookbook.frontend.fe_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;

import java.util.List;

import cookbook.backend.DatabaseMng;
import cookbook.backend.be_controllers.FavoritesController;
import cookbook.backend.be_objects.Recipe;
import javafx.application.Platform;

public class FavoriteViewController {

    @FXML
    private ListView<Recipe> favoritesListView;

    private ObservableList<Recipe> favoriteRecipes = FXCollections.observableArrayList();
    private FavoritesController favoritesController; // Antag att detta är din backend-controller.

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
                    setText(recipe.getRecipeName()); // Eller hur du nu vill visa recepten.
                }
            }
        });

        // Ladda favoritrecept i en bakgrundstråd om det behövs
        Platform.runLater(this::loadFavoriteRecipes);
    }

    private void loadFavoriteRecipes() {
    // Antag att du hämtar det aktuella användar-ID:t på något sätt.
    Long userId = 1L; // Detta borde vara det faktiska användar-ID:t.
    List<Recipe> favorites = favoritesController.getFavorites(userId);
    System.out.println("Favorites for user " + userId + ": " + favorites); // Logga för att se resultatet.
    favoriteRecipes.setAll(favorites);
    }
}
