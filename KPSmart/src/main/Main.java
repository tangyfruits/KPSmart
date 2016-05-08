package main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private List<Location> locations;

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public List<Route> bestRoutes(Location origin, Location destination, double weight, double volume) {
		Route directRoute = getDirectRoute(destination, destination, weight, volume);
		if(directRoute == null){
			 //TODO indirect routes
		}
		else{
			List<Route> best = new ArrayList<>();
			best.add(directRoute);
			return best;
		}
		return null;
		

	}

	public Route getDirectRoute(Location origin, Location destination, double weight, double volume) {
		List<Route> directRoutes = new ArrayList<>();
		for (Route r : origin.getRoutes()) {
			if (r.getDestination() == destination) {
				directRoutes.add(r);
			}
		}
		if(directRoutes.isEmpty()){
			return null;
		}
		return getCheapestRoute(directRoutes, weight, volume);
	}

	public Route getCheapestRoute(List<Route> routes, double weight, double volume) {
		Route cheapest = routes.get(0);
		for(Route r : routes){
			if(cheapest.getCost(weight, volume) > r.getCost(weight, volume)){
				cheapest = r;
			}
		}
		return cheapest;
	}
}
