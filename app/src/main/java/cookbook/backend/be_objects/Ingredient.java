package cookbook.backend.be_objects;

public class Ingredient {
  private String name;
  private String ingredientsID;



  public Ingredient(String name, String ingredientsID) {
    setIngredientsID(ingredientsID);
    setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIngredientsID() {
    return ingredientsID;
  }

  public void setIngredientsID(String ingredientsID) {
    this.ingredientsID = ingredientsID;
  }
}
