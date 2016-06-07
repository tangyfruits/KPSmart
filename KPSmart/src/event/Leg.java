package event;

import main.Location;

public class Leg implements Event{
	
	// FIELDS
	private Location origin;
	private Location destination;
	private String type;
	private String company;
	private double cost;
	private double price;
	private final String eventType = "leg";
	
	// CONSTRUCTOR
	public Leg(Location origin, Location destination, String type, String company, double freightCost, double customerPrice) {
		this.origin = origin;
		this.destination = destination;
		this.type = type;
		this.company = company;
		this.cost = freightCost;
		this.price = customerPrice;
	}
	
	// METHODS
	// Getters
	public Location getOrigin() {
		return origin;
	}
	public Location getDestination() {
		return destination;
	}
	public String getType() {
		return type;
	}
	public String getCompany() {
		return company;
	}
	public double getCost() {
		return cost;
	}
	public double getPrice() {
		return price;
	}
	public String getEventType() {
		return eventType;
	}
	
	// Printer
	public String toString() {
		return origin.getName() + " --> " + destination.getName() + 
				" (" + company + ". " + type + ")\n"; 
	}
}
