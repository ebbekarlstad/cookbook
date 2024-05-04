package cookbook.backend.be_objects;

import javafx.beans.property.*;

public class ShoppingListItem {
  private final IntegerProperty id;
  private final StringProperty name;
  private final FloatProperty amount;
  private final StringProperty unit;

  public ShoppingListItem(int id, String name, float amount, String unit) {
    this.id = new SimpleIntegerProperty(id);
    this.name = new SimpleStringProperty(name);
    this.amount = new SimpleFloatProperty(amount);
    this.unit = new SimpleStringProperty(unit);
  }

  public int getId() {
    return id.get();
  }

  public IntegerProperty idProperty() {
    return id;
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public String getName() {
    return name.get();
  }

  public StringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public float getAmount() {
    return amount.get();
  }

  public FloatProperty amountProperty() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount.set(amount);
  }

  public String getUnit() {
    return unit.get();
  }

  public StringProperty unitProperty() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit.set(unit);
  }
}

