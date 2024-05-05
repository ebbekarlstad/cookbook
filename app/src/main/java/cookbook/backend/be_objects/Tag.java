package cookbook.backend.be_objects;

/**
 * The Tag class represents a tag with its ID and name.
 */
public class Tag {
  private String TagID;
  private String TagName;

  /**
   * Constructs a new Tag with the specified ID and name.
   *
   * @param TagID   the ID of the tag
   * @param TagName    the name of the tag
   */
  public Tag(String TagID, String TagName) {
    setTagID(TagID);
    setTagName(TagName);
  }

  /**
   * Get the ID of the tag.
   *
   * @return the ID of the tag
   */
  public String getTagID() {
    return TagID;
  }

  /**
   * Set the ID of the tag.
   *
   * @param TagID the ID of the tag
   */
  public void setTagID(String TagID) {
    this.TagID = TagID;
  }

  /**
   * Get the name of the tag.
   *
   * @return the name of the tag
   */
  public String getTagName() {
    return TagName;
  }

  /**
   * Set the name of the tag.
   *
   * @param TagName the name of the tag
   */
  public void setTagName(String TagName) {
    this.TagName = TagName;
  }
}
