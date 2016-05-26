package main;

public class CustomerPrice {
	// VARIABLES
	private Location origin;
	private Location destination;
	private String priority;
	private double weightCost;
	private double volumeCost;

	// CONSTRUCTORS
	public CustomerPrice(Location origin, Location destination,
			String priority, double weightCost, double volumeCost) {
		this.origin = origin;
		this.destination = destination;
		this.priority = priority;
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
	}
	
	// METHODS
	// Getters
	public double getWeightCost() {
		return weightCost;
	}
	public double getVolumeCost() {
		return volumeCost;
	}
	public Location getOrigin() {
		return origin;
	}
	public Location getDestination() {
		return destination;
	}
	public String getPriority() {
		return priority;
	}

	// Setters
	public void setWeightCost(double weightCost) {
		this.weightCost = weightCost;
	}
	public void setVolumeCost(double volumeCost) {
		this.volumeCost = volumeCost;
	}

	// To String
	@Override
	public String toString() {
		return "CustomerPrice [origin=" + origin + ", destination=" + destination + ", priority=" + priority
				+ ", weightCost=" + weightCost + ", volumeCost=" + volumeCost + "]";
	}
}
