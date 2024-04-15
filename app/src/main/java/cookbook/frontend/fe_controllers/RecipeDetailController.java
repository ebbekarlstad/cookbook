public class RecipeDetailController {
  @FXML
  private Label ingredientsText;
  @FXML
  private Label descriptionText;

  public void setRecipeDetails(CookingOB recipe) {
      ingredientsText.setText("Ingredients: \n" + recipe.getIngredients());
      descriptionText.setText("Description: \n" + recipe.getName());
  }
}