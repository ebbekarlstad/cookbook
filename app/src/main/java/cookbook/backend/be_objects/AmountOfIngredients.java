package cookbook.backend.be_objects;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * The AmountOfIngredients class represents the quantity of an ingredient, including its unit, amount, and name.
 */
public class AmountOfIngredients {
  private String Unit;
  private float Amount;
  private Ingredient ingredient;
  private String IngredientName;

  /**
   * Constructs a new AmountOfIngredients object with the specified unit, amount, and ingredient.
   *
   * @param Unit       the unit of measurement for the ingredient (e.g., "g", "ml")
   * @param Amount     the amount of the ingredient
   * @param ingredient the ingredient object
   */
  public AmountOfIngredients(String Unit, float Amount, Ingredient ingredient) {
    this.Unit = Unit;
    if(Unit == null) {
      this.Unit = "";
    }
    if(Unit == "Unit") {
      this.Unit = "";
    }
    this.Amount = Amount;
    this.ingredient = ingredient;
    this.IngredientName = ingredient.getIngredientName();
  }


  public SimpleFloatProperty amountProperty() {
    return new SimpleFloatProperty(Amount);
  }

  public SimpleStringProperty nameProperty() {
    return new SimpleStringProperty(IngredientName);
  }
  /**
   * Constructs a new AmountOfIngredients object with the specified unit, amount, and ingredient name.
   *
   * @param Unit   the unit of measurement for the ingredient (e.g., "g", "ml")
   * @param Amount the amount of the ingredient
   * @param IngredientName   the name of the ingredient
   */
  public AmountOfIngredients(String Unit, float Amount, String IngredientName) {
    this.Unit = Unit;
    if(Unit == null) {
      this.Unit = "";
    }
    if(Unit == "Unit") {
      this.Unit = "";
    }
    this.Amount = Amount;
    this.IngredientName = IngredientName;
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
    return ingredient.getIngredientID();
  }

  /**
   * Get the unit of measurement for the ingredient.
   *
   * @return the unit of measurement
   */
  public String getUnit() {
    return Unit;
  }

  /**
   * Get the name of the ingredient.
   *
   * @return the name of the ingredient
   */
  public String getName() {
    return IngredientName;
  }

  /**
   * Get the amount of the ingredient.
   *
   * @return the amount of the ingredient
   */
  public float getAmount() {
    return Amount;
  }

  /**
   * Add an additional amount to the current amount.
   *
   * @param additional the additional amount to add
   */
  public void addAmount(float additional) {
    Amount += additional;
  }

  /**
   * Set the amount of the ingredient.
   *
   * @param Amount the amount to set
   */
  public void setAmount(float Amount) {
    this.Amount = Amount;
  }

  /**
   * Convert the object to a string representation.
   *
   * @return a string representation of the object
   */
  @Override
  public String toString() {
    return Amount + " " + Unit + " " + IngredientName;
  }

  /**
   * Convert the object to a data string for serialization.
   *
   * @return a data string representation of the object
   */
  public String toData() {
    return "INGREDIENT:" + Amount + ":" + Unit + ":" + IngredientName;
  }
}