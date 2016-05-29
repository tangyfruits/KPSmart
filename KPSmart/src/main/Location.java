package main;

import java.util.ArrayList;
import java.util.List;

public class Location {
	// VARIABLES
	private String name;
	private List<Route> routes = new ArrayList<Route>();
	private List<CustomerPrice> prices = new ArrayList<CustomerPrice>();;
	
	private String priority;
	private Location fromLocation;
	private double costSoFar;
	private boolean visited;
	
	// CONSTRUCTOR
	public Location(String name) {
		this.name = name;
	}
	
	// METHODS
	// Getters
	public String getName() {
		return name;
	}
	public List<Route> getRoutes() {
		return routes;
	}
	public List<CustomerPrice> getPrices() {
		return prices;
	}

	// Setters (Adders)
	public void addRoute(Route route) {
		this.routes.add(route);
	}
	public void addPrice(CustomerPrice price) {
		this.prices.add(price);
	}
	
	// Other
	public String toString() {
		return ("Loc name: " + getName());
	}
	public String toPretty() { // (like toString but prettier)
		return name;
	}
	
	
	// Shouldn't be in Location
	public String getPriority() {
		return priority;
	}
	public double getCostSoFar() {
		return costSoFar;
	}
	public boolean isVisited() {
		return visited;
	}
	public Location getFrom() {
		return fromLocation;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public void setFrom(Location l) {
		this.fromLocation = l;
	}
	public void setCostSoFar(double costSoFar) {
		this.costSoFar = costSoFar;
	}

	public void removeRoute(Route r) {
		routes.remove(r);
		
	}
}
	
	
