package cookbook.backend.be_objects;

public class Ingredient {
  private final String name;
  private final String unit;
  private final int amount;


  public Ingredient(String name, String unit, int amount) {
    this.name = name;
    this.unit = unit;
    this.amount = amount;
  }

  public String getName() {
    return name;
  }

  public String getUnit() {
    return unit;
  }

  public int getAmount() {
    return amount;
  }
}
