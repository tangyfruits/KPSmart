package main;

public class Leg {

	private Location origin;
	private Location destination;
	private String type;
	private String company;
	private double freightCost;
	private double customerPrice;
	
	public Leg(Location origin, Location destination, String type, String company, double freightCost, double customerPrice) {
		this.origin = origin;
		this.destination = destination;
		this.type = type;
		this.company = company;
		this.freightCost = freightCost;
		this.customerPrice = customerPrice;
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
	
	public double getFreightCost() {
		return freightCost;
	}
	
	public double getCustomerPrice() {
		return customerPrice;
	}
}
