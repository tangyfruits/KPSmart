package event;

import main.Leg;

public class LegEvent implements Event{
	
	// VARIABLES
	private final String eventType = "leg";
	private String origin;
	private String destination;
	private String type;
	private String company;
	private double cost;
	private double price;
	
	// CONSTRUCTOR
	public LegEvent(String origin, String destination, String type, String company, double freightCost, double customerPrice) {
		this.origin = origin;
		this.destination = destination;
		this.type = type;
		this.company = company;
		this.cost = freightCost;
		this.price = customerPrice;
	}
	public LegEvent(Leg leg) {
		this.origin = leg.getOrigin().getName();
		this.destination = leg.getDestination().getName();
		this.type = leg.getType();
		this.company = leg.getCompany();
		this.cost = leg.getFreightCost();
		this.price = leg.getCustomerPrice();
	}
	public LegEvent() {
		
	}
	
	// METHODS
	// Getters
	public String getOrigin() {
		return origin;
	}
	public String getDestination() {
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
	
	// Setters
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
