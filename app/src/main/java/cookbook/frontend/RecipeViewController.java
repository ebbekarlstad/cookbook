package cookbook.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RecipeViewController {

    @FXML
    private TextField name;

    @FXML
    private ListView<String> ListOfRecipe;

    @FXML
    void addRecipe(MouseEvent event) {
        ListOfRecipe.getItems().add(name.getText());
    }

    @FXML
    void removeRecipe(MouseEvent event) {
        ListOfRecipe.getItems().remove(name.getText());
    }

}

