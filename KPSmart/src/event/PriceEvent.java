package event;

import main.Location;

public class PriceEvent implements Event {
	
	// VARIABLES
	private String origin;
	private String destination;
	private String priority;
	private double weightCost;
	private double volumeCost;
	
	// CONSTRUCTOR
	public PriceEvent(String origin, String destination, String priority, double weightCost, double volumeCost) {
		this.origin = origin;
		this.destination = destination;
		this.priority = priority;
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
	}

	
	// METHODS
	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
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

	
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setWeightCost(double weightCost) {
		this.weightCost = weightCost;
	}

	public void setVolumeCost(double volumeCost) {
		this.volumeCost = volumeCost;
	}
		
}
