package main;

import java.util.Date;
import java.util.List;

public class DeliveryRequest {
	
	private Date logTime;
	private Location origin;
	private Location destination;
	private double weight;
	private double volume;
	private String priority;
	private int duration;
	private List <Leg> legs;
	
	public DeliveryRequest(Date logTime, Location origin, Location destination, double weight, double volume, 
			String priority, int duration, List <Leg> legs) {
		this.logTime = logTime;
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
		this.duration = duration;
		this.legs = legs;
	}
	
	public Date getLogTime() {
		return logTime;
	}
	public Location getOrigin() {
		return origin;
	}
	public Location getDestination() {
		return destination;
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
	public int getDuration() {
		return duration;
	}
	public List<Leg> getLegs() {
		return legs;
	}
	
	
	

}
