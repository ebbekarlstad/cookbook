package cookbook.backend.be_objects;

import java.util.ArrayList;

public class Recipe {
  private String id;
  private String name;
  private String description;
  private String instructions;
  private ArrayList<AmountOfIngredients> QuantityIngredientList = new ArrayList<>();



  private ArrayList<Tag> tagList = new ArrayList<>();
  private Boolean star;

  /**
   * recipe constructor
   */
  public Recipe(String id, String name, String description, String instructions, Boolean Star) {
    setId(id);
    setName(name);
    setDescription(description);
    setInstructions(instructions);
    setStar(star);

  }

  /**
   * returns the id.
   * @return
   */
  public String getId() {
    return id;
  }

  /**
   * sets the id.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * gets the name.
   */
  public String getName() {
    return name;
  }

  /**
   * sets name.
   * @param name
   */
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

  public void setStar(Boolean star) {
    this.star = star;
  }

  public boolean getStar() {
    return star;
  }



  /**
   * adds ingredient to the list of ingredients.
   * @return
   */
  public ArrayList<AmountOfIngredients> getIngredientsList() {
    return new ArrayList<>(QuantityIngredientList);
  }

  public void addIngredient(AmountOfIngredients ingredient) {
    QuantityIngredientList.add(ingredient);
  }

  /**
   * gets a list of tags.
   */
  public ArrayList<Tag> getTagList() {
    return new ArrayList<>(tagList);
  }

  /**
   * adds tag to the list of tags for the recipe.
   */
  public void addTag(Tag tag) {
    tagList.add(tag);
  }

}