package main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private static List<Location> locations = new ArrayList<Location>();
	
	
	public static void logTransportCostUpdate(String origin, String destination,
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
			locations.add(originLoc);
		}
		if (destinationLoc == null) {
			destinationLoc = new Location(destination);
			locations.add(destinationLoc);
		}

		// get customer price matching the route
		price = getCustomerPrice(originLoc, destinationLoc, origin,
				destination, priority);

		// create route and add to graph
		Route route = new Route(originLoc, destinationLoc, company, type,
				priority, weightCost, volumeCost, maxWeight, maxVolume,
				duration, frequency, day, price);
		originLoc.addRoute(route);

		//TODO add event to logfile
	}
	
	public static CustomerPrice getCustomerPrice(Location originLoc,
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
	
	public static List<Location> getLocations() {
		return locations;
	}
	public static void setLocations(List<Location> location) {
		locations = location;
	}

}
