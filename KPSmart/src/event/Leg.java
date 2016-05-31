package event;

import main.Location;

public class Leg implements Event{
	
	// FIELDS
	private final String eventType = "leg";
	private Location origin;
	private Location destination;
	private String type;
	private String company;
	private double cost;
	private double price;
	
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
	public String getEventType() {
		return eventType;
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
	public String getCompany() {
		return company;
	}
	public double getCost() {
		return cost;
	}
	public double getPrice() {
		return price;
	}
}
