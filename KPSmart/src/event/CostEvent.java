package event;

import java.time.DayOfWeek;

public class CostEvent implements Event {
	
	// VARIABLES
	private String origin;
	private String destination;
	private String company;
	private String type;
	private String priority;
	private double weightCost;
	private double volumeCost;
	private int maxWeight;
	private int maxVolume;
	private int duration;
	private int frequency;
	private DayOfWeek day;
	
	// CONSTRUCTOR
	public CostEvent(String origin, String destination, String company, String type, String priority, 
			double weightCost, double volumeCost, int maxWeight, int maxVolume, int duration, 
			int frequency, DayOfWeek day) {
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
	}
	
	// METHODS
	// Getter
	public String getOrigin() {
		return origin;
	}
	public String getDestination() {
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
		return day.name();
	}
	
	// Setters
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setType(String type) {
		this.type = type;
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
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}
	public void setMaxVolume(int maxVolume) {
		this.maxVolume = maxVolume;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public void setDay(DayOfWeek day) {
		this.day = day;
	}
}
