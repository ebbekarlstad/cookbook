package cookbook.backend.be_objects;

/**
 * The Ingredient class represents an ingredient with its ID and name.
 */
public class Ingredient {
  private String IngredientID;
  private String IngredientName;

  /**
   * Constructs a new Ingredient with the specified ID and name.
   *
   * @param IngredientID   the ID of the ingredient
   * @param IngredientName the name of the ingredient
   */
  public Ingredient(String IngredientID, String IngredientName) {
    setIngredientID(IngredientID);
    setIngredientName(IngredientName);
  }

  /**
   * Get the ID of the ingredient.
   *
   * @return the ID of the ingredient
   */
  public String getIngredientID() {
    return IngredientID;
  }

  /**
   * Set the ID of the ingredient.
   *
   * @param IngredientID the ID of the ingredient
   */
  public void setIngredientID(String IngredientID) {
    this.IngredientID = IngredientID;
  }

  /**
   * Get the name of the ingredient.
   *
   * @return the name of the ingredient
   */
  public String getIngredientName() {
    return IngredientName;
  }

  /**
   * Set the name of the ingredient.
   *
   * @param IngredientName the name of the ingredient
   */
  public void setIngredientName(String IngredientName) {
    this.IngredientName = IngredientName;
  }
}
