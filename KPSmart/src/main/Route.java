package main;

public class Route {
	private Location origin;
	private Location destination;
	private String company;
	private String type;
	private String priority;
	private double weightCost;
	private double volumeCost;
	private int maxWeight;
	private int maxVolume;
	private int duration;
	private int frquency;
	private Day day;
	private CustomerPrice price;

	private enum Day {
		Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
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

	public void setDay(String day) {
		this.day = day;
	}

	public CustomerPrice getPrice() {
		return price;
	}

	public void setPrice(CustomerPrice price) {
		this.price = price;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
}
