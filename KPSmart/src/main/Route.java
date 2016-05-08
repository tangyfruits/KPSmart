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
	private int frequency;
	private String day;
	private CustomerPrice price;
	
	public Route(Location origin, Location destination, String company, String type, 
			String priority, double weightCost, double volumeCost, int maxWeight, 
			int maxVolume, int duration, int frequency, String day, CustomerPrice customerPrice){
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
		this.frequency = frequency;
		this.day = day;
		this.price = customerPrice;
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
	
	public int getFrequency() {
		return frequency;
	}
	
	public void setFrequency(int frquency) {
		this.frequency = frquency;
	}
	
	public String getDay() {
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
}
