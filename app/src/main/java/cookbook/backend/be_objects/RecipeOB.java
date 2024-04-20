package cookbook.backend.be_objects;

import java.util.ArrayList;
import java.util.List;

public class RecipeOB {
  private String name;
  private String description;
  private String instructions;
  private String ID;
  private List<amountOfIngredients> ingredients;

  public RecipeOB(String ID, String name, String description, String instructions) {
    this.ID = ID;
    this.name = name;
    this.description = description;
    this.instructions = instructions;
    this.ingredients = new ArrayList<>();
  }

  public void addIngredient(amountOfIngredients ingredient) {
    ingredients.add(ingredient);
  }

  // Getters and setters
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getInstructions() {
    return instructions;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public List<amountOfIngredients> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<amountOfIngredients> ingredients) {
    this.ingredients = ingredients;
  }
}
