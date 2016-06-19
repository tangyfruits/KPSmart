package event;

import java.time.LocalDateTime;

import main.Location;

public class DiscontinueRoute implements Event {
	
	// FIELDS
	private LocalDateTime logTime;
	private String user;
	private Location origin;
	private Location destination;
	private String company;
	private String type;
	private final String eventType = "discontinue";
	
	// CONSTRUCTOR
	public DiscontinueRoute(Location origin, Location destination, String company, String type, 
			LocalDateTime logTime, String user) {
		this.origin = origin;
		this.destination = destination;
		this.company = company;
		this.type = type;
		this.logTime = logTime;
		this.user = user;
	}
	
	// METHODS
	// Getters
	public String getCompany() {
		return company;
	}
	public Location getOrigin() {
		return origin;
	}
	public Location getDestination() {
		return destination;
	}
	public String getType() {
		return type;
	}
	public String getEventType() {
		return eventType;
	}
	public LocalDateTime getLogTime(){
		return logTime;
	}
	public String getUser() {
		return user;
	}
}
