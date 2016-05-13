package event;

public class Leg {
	
	// VARIABLES
	private String to;
	private String from;
	
	// CONSTRUCTOR
	public Leg(String to, String from) {
		this.to = to;
		this.from = from;
	}
	
	// METHODS
	public String getTo() {
		return to;
	}
	public String getFrom() {
		return from;
	}

	public void setTo(String to) {
		this.to = to;
	}
	public void setFrom(String from) {
		this.from = from;
	}
}
