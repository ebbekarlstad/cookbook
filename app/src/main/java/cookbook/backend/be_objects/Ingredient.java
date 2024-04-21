package cookbook.backend.be_objects;

/**
 * The Ingredient class represents an ingredient with its ID and name.
 */
public class Ingredient {
  private String id;
  private String name;

  /**
   * Constructs a new Ingredient with the specified ID and name.
   *
   * @param id   the ID of the ingredient
   * @param name the name of the ingredient
   */
  public Ingredient(String id, String name) {
    setId(id);
    setName(name);
  }

  /**
   * Get the ID of the ingredient.
   *
   * @return the ID of the ingredient
   */
  public String getId() {
    return id;
  }

  /**
   * Set the ID of the ingredient.
   *
   * @param id the ID of the ingredient
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get the name of the ingredient.
   *
   * @return the name of the ingredient
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of the ingredient.
   *
   * @param name the name of the ingredient
   */
  public void setName(String name) {
    this.name = name;
  }
}
