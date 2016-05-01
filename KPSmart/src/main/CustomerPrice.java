package main;

public class CustomerPrice {
	
	private Location origin;
	private Location destination;
	private String priority;
	private double weightCost;
	private double volumeCost;
		
	public double getWeightCost() {
		return weightCost;
	}
	public void setWeightCost(double weightCost) {
		this.weightCost = weightCost;
	}
	public double getVolumeCost() {
		return volumeCost;
	}
	public void setVolumeCost(double volumeCost) {
		this.volumeCost = volumeCost;
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
}
