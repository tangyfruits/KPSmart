package main;

public class Route {
	// VARIABLES
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
	
	// CONSTRUCTOR
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

	// METHODS
	// Getters
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
	public String getPriority() {
		return priority;
	}
	public double getWeightCost() {
		return weightCost;
	}
	public double getVolumeCost() {
		return volumeCost;
	}
	public int getMaxWeight() {
		return maxWeight;
	}
	public int getMaxVolume() {
		return maxVolume;
	}
	public int getDuration() {
		return duration;
	}
	public int getFrequency() {
		return frequency;
	}
	public String getDay() {
		return day;
	}
	public CustomerPrice getPrice() {
		return price;
	}
	
	// Setters
	public void setWeightCost(double weightCost) {
		this.weightCost = weightCost;
	}	
	public void setVolumeCost(double volumeCost) {
		this.volumeCost = volumeCost;
	}
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}
	public void setMaxVolume(int maxVolume) {
		this.maxVolume = maxVolume;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void setFrequency(int frquency) {
		this.frequency = frquency;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public void setPrice(CustomerPrice price) {
		this.price = price;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	// Other (Stuff for Route Selection?)
	public double getCost(double weight, double volume){
		return (this.volumeCost * volume + this.weightCost * weight);
	}
	
	public String toString(){
		return ("R ORIGIN: "+getOrigin().toString()+" R DEST: "+getDestination().toString());
	}
	public String toPretty() { // (like toString but prettier)
		return getOrigin().toPretty() + " ---> " + getDestination().toPretty();
	}
}
