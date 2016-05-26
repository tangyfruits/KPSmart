package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Date;
import java.util.List;

public class Main {

	private List<Location> locations;
	private List<User> accounts;
	private User currentUser;

	private List<DeliveryRequest> deliveryRequests;


	public Main() {
		locations = new ArrayList<Location>();
		accounts = new ArrayList<User>();
		// read from encrypted file and add them in!

		// read from encrypted file,create User objects and add them in!
		// if ( accounts.containsValue("String") &&
		// accounts.get("String").equalsss("password);
		// currentUser = new User();
		// want to look into apache shiro tbh but everyone will have to install
		// maven. Apache shiro is a really good framework for logins
	}


	public Route[] getPossibleRoutes(String origin, String destination,
			double weight, double volume) {
		Location originLoc = null;
		Location desinationLoc = null;

		// find the locations matching the given strings
		Location destinationLoc = null;
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).getName().equals(origin)) {
				originLoc = locations.get(i);
			}
			if (locations.get(i).getName().equals(destination)) {
				destinationLoc = locations.get(i);
			}
		}
		
		//route selection
		//TODO uncomment once route selection is finished and merged in
		Route[] route = new Route[5];
		//List<Route> route = bestRoutes(originLoc, destinationLoc, weight, volume);
		
		return route;
	}
	
	public DeliveryRequest logDeliveryRequest(String origin, String destination,
			double weight, double volume, List<Route> route) {
		
		Location originLoc = null;
		Location desinationLoc = null;

		// find the locations matching the given strings
		Location destinationLoc = null;
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).getName().equals(origin)) {
				originLoc = locations.get(i);
			}
			if (locations.get(i).getName().equals(destination)) {
				destinationLoc = locations.get(i);
			}
		}
		
		//calculate priority
		//TODO replace placeholder
		String priority = "";
		
		//calculate duration
		//TODO replace placeholder
		int duration = 0;
		
		//translate route list into legs
		List<Leg>legs = new ArrayList<>();
		for (Route r: route){
			double freightCost = weight*r.getWeightCost() + volume*r.getVolumeCost();
			double customerPrice = weight*r.getPrice().getWeightCost() + volume*r.getPrice().getVolumeCost();
			legs.add(new Leg(r.getOrigin(), 
					r.getDestination(), 
					r.getType(),
					r.getCompany(),
					freightCost, customerPrice));
		}
		
		//create Delivery request
		DeliveryRequest request = new DeliveryRequest(new Date(), originLoc, destinationLoc, weight, volume, priority, duration, legs);
		
		//add to delivery events field
		deliveryRequests.add(request);
		
		//TODO log in file
		//TODO add to reports: revenue, expenditure, total events
		
		return request;
		

	}

	public CustomerPrice logCustomerPriceUpdate(String origin,
			String destination, String priority, double weightCost,
			double volumeCost) {

		// find the locations matching the given strings, if they are already in
		// the graph
		Location originLoc = null;
		Location destinationLoc = null;
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).getName().equals(origin)) {
				originLoc = locations.get(i);
			}
			if (locations.get(i).getName().equals(destination)) {
				destinationLoc = locations.get(i);
			}
		}

		// if locations don't exist yet, add them to the graph
		if (originLoc == null) {
			originLoc = new Location(origin);
			addLocation(originLoc);
		}
		if (destinationLoc == null) {
			destinationLoc = new Location(destination);
			addLocation(destinationLoc);
		}

		// check if customer price already exists, if so, update it
		for (int i = 0; i < originLoc.getPrices().size(); i++) {
			CustomerPrice c = originLoc.getPrices().get(i);
			if (c.getDestination().equals(destinationLoc)
					&& c.getPriority().equals(priority)) {
				c.setVolumeCost(volumeCost);
				c.setWeightCost(weightCost);
				return c;
				// TODO add event to log
				// TODO add 1 to total events
			}
		}

		// if it doesn't exist, create it, add it to the relevant Location
		CustomerPrice price;
		price = new CustomerPrice(originLoc, destinationLoc, priority,
				weightCost, volumeCost);
		originLoc.addPrice(price);
		return price;
		// TODO add event to log
		// TODO add 1 to total events
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
		price = getCustomerPrice(originLoc, destinationLoc, origin, destination, priority);

		// check if route already exists, if it does, update it
		Boolean routeExists = false;
		for (int k = 0; k < originLoc.getRoutes().size(); k++) {
			Route r = originLoc.getRoutes().get(k);
			if (r.getDestination().equals(destinationLoc)
					&& r.getCompany().equals(company)
					&& r.getType().equals(type)) {
				r.setWeightCost(weightCost);
				r.setVolumeCost(volumeCost);
				r.setMaxWeight(maxWeight);
				r.setMaxVolume(maxVolume);
				r.setDuration(duration);
				r.setFrequency(frequency);
				r.setDay(day);
				routeExists = true;
			}
		}

		if (!routeExists) {
			// if it doesn't always exist, create route and add to graph
			Route route = new Route(originLoc, destinationLoc, company, type,
					priority, weightCost, volumeCost, maxWeight, maxVolume,
					duration, frequency, day, price);
			originLoc.addRoute(route);
		}

		// TODO add event to logfile
		// TODO add 1 to total events
	}

	public CustomerPrice getCustomerPrice(Location originLoc,
			Location destinationLoc, String origin, String destination,
			String priority) {
		// check if there's already a price for the (origin, destination,
		// priority)
		CustomerPrice customerPrice = null;
		for (int k = 0; k < originLoc.getPrices().size(); k++) {
			

			if (originLoc.getPrices().get(k).getDestination()
					.equals(destinationLoc)
					&& originLoc.getPrices().get(k).getPriority()
							.equals(priority)) {
				customerPrice = originLoc.getPrices().get(k);
			}
		}
		// if there's no customer price, request one
		// TODO replace console input with GUI
		if (customerPrice == null) {
			
			double custWeightCost = -1;
			double custVolCost = -1;
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("Enter weightCost");
			try {
				custWeightCost = Double.parseDouble(input.readLine());
			} catch (IOException e) {
			}
			System.out.print("Enter volumeCost");
			try {
				custVolCost = Double.parseDouble(input.readLine());
			} catch (IOException e) {
			}

			logCustomerPriceUpdate(origin, destination, priority,
					custWeightCost, custVolCost);
		}
		return customerPrice;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void addLocation(Location location) {
		locations.add(location);
	}

	public ArrayList<ArrayList<Route>> bestRoutes(Location origin, Location destination, double weight, double volume) {
		ArrayList<ArrayList<Route>> listOfListOfRoutes = new ArrayList<ArrayList<Route>>();
		Route directRoute = getDirectRoute(destination, destination, weight, volume);
		if (directRoute == null) {
			AStar astar = new AStar(origin, destination);
			return astar.twoListsOfRoutes(weight, volume);
		} else {
			ArrayList<Route> best = new ArrayList<>();
			best.add(directRoute);
			listOfListOfRoutes.add(best);
			return listOfListOfRoutes;
		}
	}

	public Route getDirectRoute(Location origin, Location destination, double weight, double volume) {
		ArrayList<Route> directRoutes = new ArrayList<>();
		for (Route r : origin.getRoutes()) {
			if (r.getDestination() == destination) {
				directRoutes.add(r);
			}
		}
		if (directRoutes.isEmpty()) {
			return null;
		}
		return getCheapestRoute(directRoutes, weight, volume);
	}

	public Route getCheapestRoute(List<Route> routes, double weight, double volume) {
		Route cheapest = routes.get(0);
		for (Route r : routes) {
			if (cheapest.getCost(weight, volume) > r.getCost(weight, volume)) {
				cheapest = r;
			}
		}
		return cheapest;
	}
}
