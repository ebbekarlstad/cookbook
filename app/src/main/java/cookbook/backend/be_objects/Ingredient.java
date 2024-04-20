package cookbook.backend.be_objects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ingredient {
  private StringProperty ingredientID;
  private StringProperty name;
  private DoubleProperty amount;
  private StringProperty unit;

  public Ingredient(String ingredientID, String name, double amount, String unit) {
    this.ingredientID = new SimpleStringProperty(ingredientID);
    this.name = new SimpleStringProperty(name);
    this.amount = new SimpleDoubleProperty(amount);
    this.unit = new SimpleStringProperty(unit);
  }


  public StringProperty ingredientIDProperty() {
    return ingredientID;
  }

  public String getIngredientID() {
    return ingredientID.get();
  }

  public void setIngredientID(String ingredientId) {
    this.ingredientID.set(ingredientId);
  }

  public StringProperty nameProperty() {
    return name;
  }

  public String getName() {
    return name.get();
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public DoubleProperty amountProperty() {
    return amount;
  }

  public double getAmount() {
    return amount.get();
  }

  public void setAmount(double amount) {
    this.amount.set(amount);
  }

  public StringProperty unitProperty() {
    return unit;
  }

  public String getUnit() {
    return unit.get();
  }

  public void setUnit(String unit) {
    this.unit.set(unit);
  }

}
