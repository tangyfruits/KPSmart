package main;

import java.util.ArrayList;
import java.util.List;

import event.CustomerPrice;
import event.Route;

public class Location {
	// VARIABLES
	private String name;
	private List<Route> routes = new ArrayList<Route>();
	private List<CustomerPrice> prices = new ArrayList<CustomerPrice>();;
	
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
	public void removeRoute(Route r) {
		routes.remove(r);
		
	}
}
	
	
