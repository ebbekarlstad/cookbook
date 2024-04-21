package cookbook.backend.be_objects;

/**
 * The AmountOfIngredients class represents the quantity of an ingredient, including its unit, amount, and name.
 */
public class AmountOfIngredients {
  private String unit;
  private float amount;
  private Ingredient ingredient;
  private String name;

  /**
   * Constructs a new AmountOfIngredients object with the specified unit, amount, and ingredient.
   *
   * @param unit       the unit of measurement for the ingredient (e.g., "g", "ml")
   * @param amount     the amount of the ingredient
   * @param ingredient the ingredient object
   */
  public AmountOfIngredients(String unit, float amount, Ingredient ingredient) {
    this.unit = (unit != null && !unit.equals("unit")) ? unit : "";
    this.amount = amount;
    this.ingredient = ingredient;
    this.name = ingredient.getName();
  }

  /**
   * Constructs a new AmountOfIngredients object with the specified unit, amount, and ingredient name.
   *
   * @param unit   the unit of measurement for the ingredient (e.g., "g", "ml")
   * @param amount the amount of the ingredient
   * @param name   the name of the ingredient
   */
  public AmountOfIngredients(String unit, float amount, String name) {
    this.unit = (unit != null && !unit.equals("unit")) ? unit : "";
    this.amount = amount;
    this.name = name;
  }

  /**
   * Get the ingredient object.
   *
   * @return the Ingredient object
   */
  public Ingredient getIngredient() {
    return ingredient;
  }

  /**
   * Get the ID of the ingredient.
   *
   * @return the ID of the ingredient
   */
  public String ingredientID() {
    return ingredient.getId();
  }

  /**
   * Get the unit of measurement for the ingredient.
   *
   * @return the unit of measurement
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Get the name of the ingredient.
   *
   * @return the name of the ingredient
   */
  public String getName() {
    return name;
  }

  /**
   * Get the amount of the ingredient.
   *
   * @return the amount of the ingredient
   */
  public float getAmount() {
    return amount;
  }

  /**
   * Add an additional amount to the current amount.
   *
   * @param additional the additional amount to add
   */
  public void addAmount(float additional) {
    amount += additional;
  }

  /**
   * Set the amount of the ingredient.
   *
   * @param amount the amount to set
   */
  public void setAmount(float amount) {
    this.amount = amount;
  }

  /**
   * Convert the object to a string representation.
   *
   * @return a string representation of the object
   */
  @Override
  public String toString() {
    return amount + " " + unit + " " + name;
  }

  /**
   * Convert the object to a data string for serialization.
   *
   * @return a data string representation of the object
   */
  public String toData() {
    return "INGREDIENT:" + amount + ":" + unit + ":" + name;
  }
}