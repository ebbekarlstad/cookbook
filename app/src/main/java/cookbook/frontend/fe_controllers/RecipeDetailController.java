package cookbook.frontend.fe_controllers;

import cookbook.backend.be_objects.CookingOB;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;

public class RecipeDetailController {
  @FXML
  private TextArea ingredientsText;
  @FXML
  private TextArea descriptionText;

  public void setRecipeDetails(CookingOB recipe) {
      ingredientsText.setText(recipe.getIngredients());
      descriptionText.setText(recipe.getName());
  }
}
