package event;

import java.time.LocalDateTime;

import main.*;

public class CustomerPrice implements Event {
	
	// FIELDS
	private LocalDateTime logTime;
	private String user;
	private Location origin;
	private Location destination;
	private String priority;
	private double weightCost;
	private double volumeCost;
	private final String eventType = "price";

	// CONSTRUCTORS
	public CustomerPrice(Location origin, Location destination,
			String priority, double weightCost, double volumeCost, LocalDateTime logTime, String user) {
		this.origin = origin;
		this.destination = destination;
		this.priority = priority;
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
		this.logTime = logTime;
		this.user = user;
	}
	
	// METHODS
	// Getters
	public Location getOrigin() {
		return origin;
	}
	public Location getDestination() {
		return destination;
	}
	public String getPriority() {
		return priority;
	}
	public double getWeightCost() {
		return weightCost;
	}
	public double getVolumeCost() {
		return volumeCost;
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
	
	// Setters
	public void setWeightCost(double weightCost) {
		this.weightCost = weightCost;
	}
	public void setVolumeCost(double volumeCost) {
		this.volumeCost = volumeCost;
	}
	public void setLogTime(LocalDateTime logTime){
		this.logTime = logTime;
	}
	public void setUser(String user){
		this.user = user;
	}
	
	// To String
	@Override
	public String toString() {
		return "CustomerPrice [origin=" + origin + ", destination=" + destination + ", priority=" + priority
				+ ", weightCost=" + weightCost + ", volumeCost=" + volumeCost + "]";
	}
}
