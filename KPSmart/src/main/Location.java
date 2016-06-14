package main;

import java.util.ArrayList;
import java.util.List;

import event.CustomerPrice;
import event.Route;

/**
 * This is a simple class to represent a Location. It has a name a list of
 * routes and a list of customer prices for these routes. A location for example
 * is an origin and destination at the same time. A location could be
 * Wellington, Hamilton or Auckland etc.
 * 
 * @author Donald Tang
 */
public class Location {

	private String name;
	private List<Route> routes = new ArrayList<Route>();
	private List<CustomerPrice> prices = new ArrayList<CustomerPrice>();;

	public Location(String name) {
		this.name = name;
	}

	/**
	 * Getter
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter
	 * 
	 * @return list of routes
	 */
	public List<Route> getRoutes() {
		return routes;
	}

	/**
	 * Getter
	 * 
	 * @return list of customer prices
	 */
	public List<CustomerPrice> getPrices() {
		return prices;
	}

	/**
	 * Setter
	 * 
	 * @set manager status.
	 */
	public void addRoute(Route route) {
		this.routes.add(route);
	}

	/**
	 * Setter
	 * 
	 * @set manager status.
	 */
	public void addPrice(CustomerPrice price) {
		this.prices.add(price);
	}

	/**
	 * Setter
	 * 
	 * @set manager status.
	 */
	public void removeRoute(Route r) {
		routes.remove(r);

	}

	/**
	 * toString method
	 * 
	 * @return a beefier name.
	 */

	public String toString() {
		return ("Loc name: " + getName());
	}

	/**
	 * Getter
	 * 
	 * @return name
	 */
	public String toPretty() {
		return name;
	}

}
