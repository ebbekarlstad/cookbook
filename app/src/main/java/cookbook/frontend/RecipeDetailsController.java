package cookbook.frontend;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class RecipeDetailsController implements Initializable {

    @FXML
    private Button ClearFields;

    @FXML
    private Button CreateRecipe;

    @FXML
    private TextArea DetailDescription;

    @FXML
    private ListView<?> Ingredients;

    @FXML
    private TextField Name;

    @FXML
    private TextArea ShortDescription;

    @FXML
    void ClearFields(MouseEvent event) {

    }

    @FXML
    void CreateRecipe(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        // Stage stage = (Stage) ClearFields.getScene().getWindow();
        // stage.setWidth(837);
        // stage.setHeight(747);
        
    }

}


