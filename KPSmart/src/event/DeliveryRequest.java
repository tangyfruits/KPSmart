package event;

import java.time.LocalDateTime;
import java.util.ArrayList;

import main.Location;

public class DeliveryRequest implements Event{
	
	// FIELDS
	private LocalDateTime logTime;
	private Location origin;
	private Location destination;
	private ArrayList<Leg> legs;
	private double weight;
	private double volume;
	private String priority;
	private int duration;
	private final String eventType = "cost";
	
	// CONSTRUCTOR
	public DeliveryRequest(LocalDateTime logTime, Location origin, Location destination, double weight, double volume, 
			String priority, int duration, ArrayList<Leg> legs) {
		this.logTime = logTime;
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
		this.duration = duration;
		this.legs = legs;
	}
	
	// METHODS
	// Getters
	public LocalDateTime getLogTime() {
		return logTime;
	}
	public Location getOrigin() {
		return origin;
	}
	public Location getDestination() {
		return destination;
	}
	public ArrayList<Leg> getLegs() {
		return legs;
	}
	public double getWeight() {
		return weight;
	}
	public double getVolume() {
		return volume;
	}
	public String getPriority() {
		return priority;
	}
	public int getDuration() {
		return duration;
	}
	public String getEventType() {
		return eventType;
	}
}
