package cookbook.backend.be_objects;

public class amountOfIngredients {
  private String unit;
  private double amount;
  private Ingredient ingredient;

  public amountOfIngredients(String unit, double amount, Ingredient ingredient) {
    this.unit = unit;
    this.amount = amount;
    this.ingredient = ingredient;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Ingredient getIngredient() {
    return ingredient;
  }

  public void setIngredient(Ingredient ingredient) {
    this.ingredient = ingredient;
  }
}
