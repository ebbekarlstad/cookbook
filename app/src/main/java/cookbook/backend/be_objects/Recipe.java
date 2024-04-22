package cookbook.backend.be_objects;

import java.util.ArrayList;

public class Recipe {
  private String RecipeID;
  private String RecipeName;
  private String ShortDesc;
  private String DetailedDesc;
  private ArrayList<AmountOfIngredients> AmountOfIngredientsList = new ArrayList<>();
  private ArrayList<Tag> tagList = new ArrayList<>();

  /**
   * Constructor for Recipe object.
   *
   * @param RecipeID      The ID of the recipe
   * @param RecipeName    The name of the recipe
   * @param ShortDesc     The description of the recipe
   * @param DetailedDesc  The instructions to prepare the recipe
   */
  public Recipe(String RecipeID, String RecipeName, String ShortDesc, String DetailedDesc) {
    setRecipeID(RecipeID);
    setRecipeName(RecipeName);
    setShortDesc(ShortDesc);
    setDetailedDesc(DetailedDesc);
  }

  /**
   * Get the ID of the recipe.
   *
   * @return The ID of the recipe
   */
  public String getId() {
    return RecipeID;
  }

  /**
   * Set the ID of the recipe.
   *
   * @param RecipeID The ID to set
   */
  public void setRecipeID(String RecipeID) {
    this.RecipeID = RecipeID;
  }

  /**
   * Get the name of the recipe.
   *
   * @return The name of the recipe
   */
  public String getRecipeName() {
    return RecipeName;
  }

  /**
   * Set the name of the recipe.
   *
   * @param RecipeName The name to set
   */
  public void setRecipeName(String RecipeName) {
    this.RecipeName = RecipeName;
  }

  /**
   * Get the description of the recipe.
   *
   * @return The description of the recipe
   */
  public String getShortDesc() {
    return ShortDesc;
  }

  /**
   * Set the description of the recipe.
   *
   * @param ShortDesc The description to set
   */
  public void setShortDesc(String ShortDesc) {
    this.ShortDesc = ShortDesc;
  }

  /**
   * Get the instructions of the recipe.
   *
   * @return The instructions of the recipe
   */
  public String getDetailedDesc() {
    return DetailedDesc;
  }

  /**
   * Set the instructions of the recipe.
   *
   * @param DetailedDesc The instructions to set
   */
  public void setDetailedDesc(String DetailedDesc) {
    this.DetailedDesc = DetailedDesc;
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