package controller;

public class CriticalRoutesRow {
	private String origin;
	private String destination;
	private String price;
	private String cost;
	
	public CriticalRoutesRow(String origin, String destination, String price, String cost){
		this.origin = origin;
		this.destination = destination;
		this.price = price;
		this.cost = cost;
	}
	
	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public String getPrice() {
		return price;
	}

	public String getCost() {
		return cost;
	}


}

