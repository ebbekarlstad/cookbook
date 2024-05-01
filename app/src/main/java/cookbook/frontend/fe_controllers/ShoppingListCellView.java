package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.AmountOfIngredients;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Custom ListCell for displaying QuanitityIngredients in a ListView.
 */

public class ShoppingListCellView extends ListCell<AmountOfIngredients> {
    @FXML
    private Label ingrString;

    @FXML
    private GridPane gridPane;

    private FXMLLoader loader;

    @Override
    protected void updateItem(AmountOfIngredients IngredientObj, boolean empty) {
        super.updateItem(IngredientObj, empty);

        if (empty || IngredientObj == null) {
            ;
        } else {
            if (loader == null) {
                URL url;
                try {
                    url = new File("src/main/java/cookbook/resources/shoppingListCell.fxml").toURI().toURL();
                    loader = new FXMLLoader(url);
                    loader.setController(this);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ingrString.setText(String.valueOf(IngredientObj.toString()));
        }
        setText(null);
        setGraphic(gridPane);
    }
}