package event;

public class DiscontinueEvent implements Event {
	
	// VARIABLES
	private final String eventType = "discontinue";
	private String company;
	private String to;
	private String from;
	private String type;
	
	// CONSTRUCTOR
	public DiscontinueEvent(String company, String to, String from, String type) {
		this.company = company;
		this.to = to;
		this.from = from;
		this.type = type;
	}
	public DiscontinueEvent() {
		
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
	
	// Setters
 	public void setCompany(String company) {
		this.company = company;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public void setType(String type) {
		this.type = type;
	}
}
