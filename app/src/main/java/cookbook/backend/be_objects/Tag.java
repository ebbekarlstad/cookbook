package cookbook.backend.be_objects;

/**
 * The Tag class represents a tag with its ID and name.
 */
public class Tag {
  private String tag_id;
  private String tag_name;

  /**
   * Constructs a new Tag with the specified ID and name.
   *
   * @param tag_id   the ID of the tag
   * @param tag_name the name of the tag
   */
  public Tag(String tag_id, String tag_name) {
    setTag_id(tag_id);
    setTag_name(tag_name);
  }

  /**
   * Get the ID of the tag.
   *
   * @return the ID of the tag
   */
  public String getTag_id() {
    return tag_id;
  }

  /**
   * Set the ID of the tag.
   *
   * @param tag_id the ID of the tag
   */
  public void setTag_id(String tag_id) {
    this.tag_id = tag_id;
  }

  /**
   * Get the name of the tag.
   *
   * @return the name of the tag
   */
  public String getTag_name() {
    return tag_name;
  }

  /**
   * Set the name of the tag.
   *
   * @param tag_name the name of the tag
   */
  public void setTag_name(String tag_name) {
    this.tag_name = tag_name;
  }
}
