package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

	private ArrayList<Location> locations;
	private ArrayList<User> accounts;
	private User currentUser;
	private List<DeliveryRequest> deliveryRequests;
	
	private int events;
	private double totalExp;
	private double totalRev;

	public Main() {
		locations = new ArrayList<Location>();
		accounts = new ArrayList<User>();
		deliveryRequests = new ArrayList<DeliveryRequest>();
		totalRev = 63;

		// read from encrypted file and add them in!

		// read from encrypted file,create User objects and add them in!
		// if ( accounts.containsValue("String") &&
		// accounts.get("String").equalsss("password);
		// currentUser = new User();
		// want to look into apache shiro tbh but everyone will have to install
		// maven. Apache shiro is a really good framework for logins
	}

	public ArrayList<RouteDisplay> getPossibleRoutes(String origin,
			String destination, double weight, double volume) {

		// find the locations matching the given strings
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

		// route selection
		AStar astar = new AStar(locations, originLoc, destinationLoc);
		ArrayList<ArrayList<Route>> routes = astar.twoListsOfRoutes(weight,
				volume);
		
		//set up list to pass to GUI
		ArrayList<RouteDisplay> out = new ArrayList<>();
		for (ArrayList<Route> list : routes) {
			
			// calculate priority 
			String overallPriority = "";
			List<String> domesticCities = new ArrayList<>();
			domesticCities.add("Auckland");
			domesticCities.add("Hamilton");
			domesticCities.add("Rotorua");
			domesticCities.add("Palmerston North");
			domesticCities.add("Wellington");
			domesticCities.add("Christchurch"); 
			domesticCities.add("Dunedin");

			if (domesticCities.contains(origin)
					&& domesticCities.contains(destination)) {
				overallPriority = overallPriority+"Domestic ";
			} else {
				overallPriority = overallPriority +"International ";
			}

			String priority = "Air";
			for (Route r : list) {
				if (!r.getPriority().equals("Air")) {
					priority = "Standard";
				}
			}

			overallPriority = overallPriority+priority;
			
			//calculate customer price
			double price = 0.0;
				for(Route k: list){
				price += ( weight * k.getPrice().getWeightCost()
						+ volume *k.getPrice().getVolumeCost());
			}

			RouteDisplay rDisp = new RouteDisplay(overallPriority, list, price);

			// check if route is already in the list to be returned - only add
			// it if it isn't
			Boolean exists = false;
			for (RouteDisplay r : out) {
				if (r.equals(rDisp)) {
					exists = true;
				}
			}

			if (!exists) {
				out.add(rDisp);
			}
		}
		return out;

	}

	public DeliveryRequest logDeliveryRequest(String origin,
			String destination, double weight, double volume, RouteDisplay route) {

		// find the locations matching the given strings
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

		// get duration
		int duration = route.getTotalDuration(LocalDateTime.now());

		// translate route list into legs
		List<Leg> legs = new ArrayList<>();
		for (Route r : route.getRoute()) {
			double freightCost = weight * r.getWeightCost() + volume
					* r.getVolumeCost();
			double customerPrice = weight * r.getPrice().getWeightCost()
					+ volume * r.getPrice().getVolumeCost();
			legs.add(new Leg(r.getOrigin(), r.getDestination(), r.getType(), r
					.getCompany(), freightCost, customerPrice));
		}

		// create Delivery request
		DeliveryRequest request = new DeliveryRequest(LocalDateTime.now(),
				originLoc, destinationLoc, weight, volume, route.getPriority(),
				duration, legs);

		// add to delivery events field
		deliveryRequests.add(request);
		
		addEvent();

		// TODO log in file
		// TODO add to reports: revenue, expenditure

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
		
		addEvent();
		return price;
		// TODO add event to log
		
	}

	public void logTransportCostUpdate(String origin, String destination,
			String company, String type, double weightCost,
			double volumeCost, int maxWeight, int maxVolume, int duration,
			int frequency, DayOfWeek day, int startTime) {

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
		
		//work out Priority
		String priority;
		if (type.equals("Air")) {
			priority = "Air";
		} else {
			priority = "Standard";
		}

		// get customer price matching the route
		price = getCustomerPrice(originLoc, destinationLoc, origin,
				destination, priority);

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
				r.setStartTime(startTime);
				routeExists = true;
			}
		}

		if (!routeExists) {
			// if it doesn't always exist, create route and add to graph
			Route route = new Route(originLoc, destinationLoc, company, type,
					priority, weightCost, volumeCost, maxWeight, maxVolume,
					duration, frequency, day, startTime, price);
			originLoc.addRoute(route);
		}

		// TODO add event to logfile
		addEvent();
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

	public void discontinueTransportRoute(String origin, String destination,
			String company, String type) {

		Location originLoc = null;

		// find the locations matching the given strings
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).getName().equals(origin)) {
				originLoc = locations.get(i);
			}
		}

		Route toCancel = null;
		// find the matching route out of origin
		for (Route r : originLoc.getRoutes()) {
			if (r.getCompany().equals(company)
					&& r.getDestination().getName().equals(destination)
					&& r.getType().equals(type)) {
				toCancel = r;
			}
		}
		if (toCancel != null) {
			originLoc.removeRoute(toCancel);
			// TODO log in file
			addEvent();
		} else {
			// TODO display error
		}

	}

	//Getters and Setters
	public List<DeliveryRequest> getDeliveryRequests() {
		return deliveryRequests;
	}	
	public List<Location> getLocations() {
		return locations;
	}
	public void addLocation(Location location) {
		locations.add(location);
	}
	
	//REPORT DISPLAYING
	
	public void addTotalRev(double amount){
		totalRev+=amount;
	}
	
	public void addTotalExp(double amount){
		totalExp += amount;
	}
	
	public void addEvent(){
		events+=1;
	}
	
	public double getTotalRev(){
		System.out.println("Total Revenue: $"+totalRev);
		return totalRev;
	}
	
	public double getTotalExp(){
		System.out.println("Total Expenditure: $"+totalExp);
		return totalExp;
	}
	
	public int getTotalEvents(){
		System.out.println("Total Events: "+events);
		return events;
	}
	
}