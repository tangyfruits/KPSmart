package controller;

public class CriticalRoutesRow {
	private String origin;
	private String destination;
	private String priority;
	private String price;
	private String cost;
	private String diff;
	
	public CriticalRoutesRow(String origin, String destination, String priority, String price, String cost, String diff){
		this.origin = origin;
		this.destination = destination;
		this.priority = priority;
		this.price = price;
		this.cost = cost;
		this.diff = diff;
	}
	
	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}
	
	public String getPriority() {
		return priority;
	}

	public String getPrice() {
		return price;
	}

	public String getCost() {
		return cost;
	}

	public String getDiff() {
		return diff;
	}

}

