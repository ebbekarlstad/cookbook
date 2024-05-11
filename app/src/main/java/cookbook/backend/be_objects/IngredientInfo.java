package cookbook.backend.be_objects;


// A class that i created to make the LoadAllIngredients method work with the displayed units and combonating the amount.
public class IngredientInfo {
  private float amount;
  private String unit;

  public IngredientInfo(float amount, String unit) {
    this.amount = amount;
    this.unit = unit;
  }

  public float getAmount() {
    return amount;
  }

  public String getUnit() {
    return unit;
  }
}
