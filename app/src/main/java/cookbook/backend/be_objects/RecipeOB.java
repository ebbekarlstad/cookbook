package cookbook.backend.be_objects;

import java.util.ArrayList;

public class RecipeOB {
  private String name;
  private String descriptions;
  private String instructions;
  private String ID;
  private ArrayList<amountOfIngredients> amountOfIngredients = new ArrayList<>();

  public RecipeOB(String name, String descriptions, String instructions, String ID, boolean b) {
    setName(name);
    setDescriptions(descriptions);
    setInstructions(instructions);
    setID(ID);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(String descriptions) {
    this.descriptions = descriptions;
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

  public ArrayList<amountOfIngredients> getAmountOfIngredients() {
    return amountOfIngredients;
  }

  public void setAmountOfIngredients(ArrayList<amountOfIngredients> amountOfIngredients) {
    this.amountOfIngredients = amountOfIngredients;
  }

  public void addIngredient(amountOfIngredients ingredient) {
    amountOfIngredients.add(ingredient);
  }
}
