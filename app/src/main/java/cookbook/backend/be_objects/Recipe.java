package cookbook.backend.be_objects;

import java.util.ArrayList;

public class Recipe {
  private String id;
  private String name;
  private String description;
  private String instructions;
  private ArrayList<AmountOfIngredients> AmountOfIngredientsList = new ArrayList<>();
  private ArrayList<Tag> tagList = new ArrayList<>();
  private Boolean star;

  /**
   * Constructor for Recipe object.
   *
   * @param id           The ID of the recipe
   * @param name         The name of the recipe
   * @param description  The description of the recipe
   * @param instructions The instructions to prepare the recipe
   * @param Star         Boolean value indicating if the recipe is starred
   */
  public Recipe(String id, String name, String description, String instructions, Boolean Star) {
    setId(id);
    setName(name);
    setDescription(description);
    setInstructions(instructions);
    setStar(Star);
  }

  /**
   * Get the ID of the recipe.
   *
   * @return The ID of the recipe
   */
  public String getId() {
    return id;
  }

  /**
   * Set the ID of the recipe.
   *
   * @param id The ID to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get the name of the recipe.
   *
   * @return The name of the recipe
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of the recipe.
   *
   * @param name The name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the description of the recipe.
   *
   * @return The description of the recipe
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of the recipe.
   *
   * @param description The description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Get the instructions of the recipe.
   *
   * @return The instructions of the recipe
   */
  public String getInstructions() {
    return instructions;
  }

  /**
   * Set the instructions of the recipe.
   *
   * @param instructions The instructions to set
   */
  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }

  /**
   * Set whether the recipe is starred.
   *
   * @param star Boolean value indicating if the recipe is starred
   */
  public void setStar(Boolean star) {
    this.star = star;
  }

  /**
   * Check if the recipe is starred.
   *
   * @return True if the recipe is starred, false otherwise
   */
  public boolean getStar() {
    return star;
  }

  /**
   * Get a list of ingredients for the recipe.
   *
   * @return List of AmountOfIngredients for the recipe
   */
  public ArrayList<AmountOfIngredients> getIngredientsList() {
    return new ArrayList<>(AmountOfIngredientsList);
  }

  /**
   * Add an ingredient to the list of ingredients for the recipe.
   *
   * @param ingredient The ingredient to add
   */
  public void addIngredient(AmountOfIngredients ingredient) {
    AmountOfIngredientsList.add(ingredient);
  }

  /**
   * Get a list of tags for the recipe.
   *
   * @return List of tags for the recipe
   */
  public ArrayList<Tag> getTagList() {
    return new ArrayList<>(tagList);
  }

  /**
   * Add a tag to the list of tags for the recipe.
   *
   * @param tag The tag to add
   */
  public void addTag(Tag tag) {
    tagList.add(tag);
  }
}