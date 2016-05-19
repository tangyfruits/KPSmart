package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

	private List<Location> locations;
	private List<User> accounts;
	private User currentUser;
	public Main() {
		locations = new ArrayList<Location>();
		accounts = new ArrayList<User>();
		//read from encrypted file and add them in!
		//if ( accounts.containsValue("String") && accounts.get("String").equals("password);
		//currentUser = new User();
	}
	
	public void logTransportCostUpdate(String origin, String destination,
			String company, String type, String priority, double weightCost,
			double volumeCost, int maxWeight, int maxVolume, int duration,
			int frequency, String day) {

		// find the Locations matching the given strings, if they are already in
		// the graph
		Location originLoc = null;
		Location destinationLoc = null;
		CustomerPrice price = null;
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).getName().equals(origin)) {
				originLoc = locations.get(i);
			}
			if (locations.get(i).getName().equals(destination)) {
				destinationLoc = locations.get(i);
			}
		}

		// if Locations don't exist yet, add them to the graph
		if (originLoc == null) {
			originLoc = new Location(origin);
			addLocation(originLoc);
		}
		if (destinationLoc == null) {
			destinationLoc = new Location(destination);
			addLocation(destinationLoc);
		}

		// get customer price matching the route
		price = getCustomerPrice(originLoc, destinationLoc, origin,
				destination, priority);

		//check if route already exists, if it does, update it
		for(int k = 0; k<originLoc.getRoutes().size(); k++ ){
			Route r = originLoc.getRoutes().get(k);
			if(r.getDestination().equals(destinationLoc) && r.getCompany().equals(company) && r.getType().equals(type)){
				r.setWeightCost(weightCost);
				r.setVolumeCost(volumeCost);
				r.setMaxWeight(maxWeight);
				r.setMaxVolume(maxVolume);
				r.setDuration(duration);
				r.setFrequency(frequency);
				r.setDay(day);
			}
		}
		
		// if it doesn't always exist, create route and add to graph
		Route route = new Route(originLoc, destinationLoc, company, type,
				priority, weightCost, volumeCost, maxWeight, maxVolume,
				duration, frequency, day, price);
		
		originLoc.addRoute(route);

		//TODO add event to logfile
	}
	
	public CustomerPrice getCustomerPrice(Location originLoc,
			Location destinationLoc, String origin, String destination,
			String priority) {
		// check if there's already a price for the (origin, destination,
		// priority)
		CustomerPrice customerPrice = null;
		for (int k = 0; k < originLoc.getPrices().size(); k++) {
			if (originLoc.getPrices().get(k).getDestination()
					.equals(destination)
					&& originLoc.getPrices().get(k).getPriority()
							.equals(priority)) {
				customerPrice = originLoc.getPrices().get(k);
			}
		}
		//if there's no customer price
		if (customerPrice == null) {
			// load customerPrice
			//TODO call logCustomerPrice here and replace this stuff
			double custWeightCost = -1; 
			double custVolumeCost = -1;
			customerPrice = new CustomerPrice(originLoc, destinationLoc,
					priority, custWeightCost, custVolumeCost);
			
		}
		return customerPrice;
	}
	
	public List<Location> getLocations() {
		return locations;
	}
	public void addLocation(Location location) {
		locations.add(location);
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
