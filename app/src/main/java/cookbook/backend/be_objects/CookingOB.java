package cookbook.backend.be_objects;

public class CookingOB {
  private String ingredients;
  private String recipeName;


  public CookingOB(String ingredients, String recipeName) {
    this.recipeName = recipeName;
    this.ingredients = ingredients;
  }

  public String getIngredients() {
    return ingredients;
  }

  public void setIngredients(String ingredients) {
    this.ingredients = ingredients;
  }

  public String getName() {
    return recipeName;
  }

  public void setName(String name) {
    this.recipeName = name;
  }
}
