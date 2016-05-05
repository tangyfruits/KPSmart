package main;

public class CustomerPrice {

	private Location origin;
	private Location destination;
	private Priority priority;
	private double weightCost;
	private double volumeCost;

	private enum Priority {
		Standard, DomesticAir, International;
	}

	public CustomerPrice(Location origin, Location destination, Priority priority, double weightCost,
			double volumeCost) {
		this.origin = origin;
		this.destination = destination;
		this.priority = priority;
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
	}

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

	public Priority getPriority() {
		return priority;
	}
}
