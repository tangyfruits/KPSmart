package main;

public class Route {
	private Location origin;
	private Location destination;
	private String company;
	private String type;
	private Priority priority;
	private double weightCost;
	private double volumeCost;
	private int maxWeight;
	private int maxVolume;
	private int duration;
	private int frquency;
	private Day day;
	private CustomerPrice price;

	private enum Priority {
		Standard, DomesticAir, International;
	}

	private enum Day {
		Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
	}

	public Route(Location origin, Location destination, String company, String type, Priority priority,
			double weightCost, double volumeCost, int maxWeight, int maxVolume, int duration, int frquency, Day day,
			CustomerPrice price) {
		this.origin = origin;
		this.destination = destination;
		this.company = company;
		this.type = type;
		this.priority = priority;
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
		this.maxWeight = maxWeight;
		this.maxVolume = maxVolume;
		this.duration = duration;
		this.frquency = frquency;
		this.day = day;
		this.price = price;
	}

	public Location getOrigin() {
		return origin;
	}

	public Location getDestination() {
		return destination;
	}

	public String getCompany() {
		return company;
	}

	public String getType() {
		return type;
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

	public int getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	public int getMaxVolume() {
		return maxVolume;
	}

	public void setMaxVolume(int maxVolume) {
		this.maxVolume = maxVolume;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getFrquency() {
		return frquency;
	}

	public void setFrquency(int frquency) {
		this.frquency = frquency;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public CustomerPrice getPrice() {
		return price;
	}

	public void setPrice(CustomerPrice price) {
		this.price = price;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public double getCost(double weight, double volume){
		return (this.volumeCost * volume + this.weightCost * weight);
	}
}
