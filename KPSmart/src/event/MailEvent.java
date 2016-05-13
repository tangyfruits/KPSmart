package event;

import java.util.ArrayList;

public class MailEvent implements Event {
	
	// VARIABLES
	private String day;
	private ArrayList<Leg> legList;
	private double weight;
	private double volume;
	private String priority;
	private double price;
	private double cost;
	private int duration;

	// CONSTRUCTOR
	public MailEvent(String day, ArrayList<Leg> legList, double weight, double volume, 
					  String priority, double price, double cost, int duration) {
		this.day = day;
		this.legList = legList;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
		this.price = price;
		this.cost = cost;
		this.duration = duration;
	}
	
	// METHODS
	// Getters
	public String getDay() {
		return day;
	}
	public ArrayList<Leg> getLegList() {
		return legList;
	}
	public double getWeight() {
		return weight;
	}
	public double getVolume() {
		return volume;
	}
	public String getPriority() {
		return priority;
	}
	public double getPrice() {
		return price;
	}
	public double getCost() {
		return cost;
	}
	public int getDuration() {
		return duration;
	}
	
	// Setters
	public void setDay(String day) {
		this.day = day;
	}
	public void setLegList(ArrayList<Leg> legList) {
		this.legList = legList;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
