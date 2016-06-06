package event;

import java.util.ArrayList;

import main.DeliveryRequest;
import main.Leg;

public class MailEvent implements Event {
	
	// VARIABLES
	private String day;
	private ArrayList<LegEvent> legList;
	private double weight;
	private double volume;
	private String priority;
	private double price;
	private double cost;
	private int duration;
	
	// CONSTRUCTORS
	public MailEvent(String day, ArrayList<LegEvent> legList, double weight, double volume, 
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
	@SuppressWarnings("deprecation")
	public MailEvent(DeliveryRequest request) {
		// TODO THIS DATE THING IS SUPER BAD - CHANGE IT SOMETHING NICE WHEN YOU CAN - Peter 29th May
		this.day = request.getLogTime().getDayOfWeek().name();
		this.legList = new ArrayList<LegEvent>();
		for (Leg leg : request.getLegs()) {
			legList.add(new LegEvent(leg));
		}
		this.weight = request.getWeight();
		this.volume = request.getVolume();
		this.priority = request.getPriority();
		//this.price;
		//this.cost = null;
		this.duration = request.getDuration();
	}
	
	// METHODS
	// Getters
	public String getDay() {
		return day;
	}
	public ArrayList<LegEvent> getLegList() {
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
	public void setLegList(ArrayList<LegEvent> legList) {
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
	
	// Helper
	// TODO THIS DIS ALSO KINDA - MAYBE CHANGE IT LATER? - Peter 29th May
	private String dayIntToString(int dayInt) {
		String dayName = "";
		switch (dayInt) {
		case 1: dayName = "Monday";
				break;
		case 2: dayName = "Tuesday";
				break;
		case 3: dayName = "Wednesday";
				break;
		case 4: dayName = "Thursday";
				break;
		case 5: dayName = "Friday";
				break;
		case 6: dayName = "Saturday";
				break;
		case 0: dayName = "Sunday";
				break;
		}
		return dayName;
	}
}
