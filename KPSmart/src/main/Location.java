package main;

import java.util.List;

public class Location {

	private String name;
	private List<Route> routes;
	private List<CustomerPrice> prices;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	
	public void addRoute(Route route) {
		this.routes.add(route);
	}

	public List<CustomerPrice> getPrices() {
		return prices;
	}

	public void setPrices(List<CustomerPrice> prices) {
		this.prices = prices;
	}
	
	public void addPrice(CustomerPrice price) {
		this.prices.add(price);
	}
	
}
