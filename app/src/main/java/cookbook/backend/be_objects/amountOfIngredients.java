package cookbook.backend.be_objects;

public class amountOfIngredients {
  private String unit;
  private double amount;
  private String name;
  private Ingredient ingredient_s;


  public amountOfIngredients(String unit, double amount, Ingredient ingredient_s) {
    this.unit = unit;
    this.amount = amount;
    this.ingredient_s = ingredient_s;
    this.name = ingredient_s.getName();
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

  public void addAmount(double addingUTIL) {
    amount += addingUTIL;
  }
  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Ingredient getIngredient_s() {
    return ingredient_s;
  }

  public void setIngredient_s(Ingredient ingredient_s) {
    this.ingredient_s = ingredient_s;
  }
  
}
