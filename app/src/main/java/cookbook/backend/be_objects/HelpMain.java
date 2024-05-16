package cookbook.backend.be_objects;

public class HelpMain {

	private String title;
	private String description;

	public HelpMain(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
}
