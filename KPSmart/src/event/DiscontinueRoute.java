package event;

public class DiscontinueRoute implements Event {
	
	// FIELDS
	private final String eventType = "discontinue";
	private String company;
	private String to;
	private String from;
	private String type;
	
	// CONSTRUCTOR
	public DiscontinueRoute(String company, String to, String from, String type) {
		this.company = company;
		this.to = to;
		this.from = from;
		this.type = type;
	}
	
	// METHODS
	// Getters
	public String getCompany() {
		return company;
	}
	public String getTo() {
		return to;
	}
	public String getFrom() {
		return from;
	}
	public String getType() {
		return type;
	}
	public String getEventType() {
		return eventType;
	}
}
