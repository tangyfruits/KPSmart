package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import event.CustomerPrice;
import event.DeliveryRequest;
import event.DiscontinueRoute;
import event.Leg;
import event.Route;

public class Main {

	// FIELDS
	private ArrayList<Location> locations;
	private ArrayList<User> accounts;
	private User currentUser;
	private HashMap<TuplePriority, ArrayList<Integer>> amountOfMailDeliveryTimes;
	private HashMap<Tuple, ArrayList<Double>> amountOfMail;
	private ArrayList<DeliveryRequest> deliveryRequests;

	private LogWriter writer;
	private File file;

	private int events;
	private double totalExp;
	private double totalRev;

	
	// CONSTRUCTOR
	public Main() {

		locations = new ArrayList<Location>();
		accounts = new ArrayList<User>();
		deliveryRequests = new ArrayList<DeliveryRequest>();
		file = new File("accounts.txt");
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String username = sc.next();
				String password = sc.next();
				String manager = sc.next();
				boolean b = false;
				if (manager.equals("true")) {
					b = true;
				}
				accounts.add(new User(username, password, b));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// for (User u : m.accounts) {
		// System.out.println(u.getUsername() + u.getPassword() +
		// u.isManager());
		// }
		amountOfMailDeliveryTimes = new HashMap<>();
		amountOfMail = new HashMap<>();

		// TODO fix file - will be listed in config file with user accounts
		// etc!!
		try {
			writer = new LogWriter(new File("abc.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// read from encrypted file and add them in!

		// read from encrypted file,create User objects and add them in!
		// if ( accounts.containsValue("String") &&
		// accounts.get("String").equals("password);
		// currentUser = new User();
		// want to look into apache shiro tbh but everyone will have to install
		// maven. Apache shiro is a really good framework for logins
	}

	
	// USER ACCOUNT METHODS
	public boolean login(String username, String password) {
		for (User u : accounts) {
			if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
				currentUser = u;
				return true;
			}
		}
		return false;
	}
	public boolean edit(String password) {
		boolean b = false;
		for (User u : accounts) {
			if (u.getUsername().equals(currentUser.getUsername())
					&& u.getPassword().equals(currentUser.getPassword())) {
				u.setPassword(password);
				b = true;
			}
		}
		currentUser.setPassword(password);
		try {
			file.delete();
			file.createNewFile();
			FileWriter writer = new FileWriter("accounts.txt", true);
			for (int i = 0; i < accounts.size() - 1; i++) {
				User u = accounts.get(i);
				writer.write(u.getUsername() + " " + u.getPassword() + " " + Boolean.toString(u.isManager()) + "\n");
			}
			User u = accounts.get(accounts.size() - 1);
			writer.write(u.getUsername() + " " + u.getPassword() + " " + Boolean.toString(u.isManager()));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}
	public boolean delete() {
		boolean b = false;
		System.out.println(accounts.size());
		for (User u : accounts) {
			if (u.getUsername().equals(currentUser.getUsername())
					&& u.getPassword().equals(currentUser.getPassword())) {
				accounts.remove(u);
				logout();
				b = true;
				break;
			}
		}
		try {
			file.delete();
			file.createNewFile();
			FileWriter writer = new FileWriter("accounts.txt", true);
			for (int i = 0; i < accounts.size() - 1; i++) {
				User u = accounts.get(i);
				writer.write(u.getUsername() + " " + u.getPassword() + " " + Boolean.toString(u.isManager()) + "\n");
			}
			User u = accounts.get(accounts.size() - 1);
			writer.write(u.getUsername() + " " + u.getPassword() + " " + Boolean.toString(u.isManager()));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;

	}
	public void add(User u) {
		try {
			FileWriter writer = new FileWriter("accounts.txt", true);
			writer.write("\n" + u.getUsername() + " " + u.getPassword() + " " + Boolean.toString(u.isManager()));
			writer.flush();
			writer.close();
			accounts.add(u);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void logout() {
		currentUser = null;
	}
	public User getCurrentUser() {
		return currentUser;
	}
	public void addUserToList(User u) {
		accounts.add(u);
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public ArrayList<User> getAccounts() {
		return accounts;
	}

	// LOGGERS
	public DeliveryRequest logDeliveryRequest(LocalDateTime logTime, String origin, String destination,
			ArrayList<Leg> legs, double weight, double volume, String priority, int duration, boolean initial) {


		// find the locations matching the given strings
		Location originLoc = getLocation(origin);
		Location destinationLoc = getLocation(destination);

		// create Delivery request
		DeliveryRequest request = new DeliveryRequest(LocalDateTime.now(), originLoc, destinationLoc, weight, volume,
				priority, duration, legs);

		// add to delivery events field
		addToAverageDeliveryTimes(origin, destination, duration, priority);
		addToAmountOfMail(origin, destination, weight, volume);
		deliveryRequests.add(request);

		// if (!initial) {
		// //log in file and add to reports
		// try {
		// writer.writeDeliveryRequest(request);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// get total cost and rev
		double cost = 0;
		double price = 0;
		for (Leg l : legs) {
			cost += l.getCost();
			price += l.getPrice();
		}

		addTotalExp(cost);
		addTotalRev(price);
		addEvent();

		return request;

	}
	public Route logTransportCostUpdate(String origin, String destination, String company, String type,
			double weightCost, double volumeCost, int maxWeight, int maxVolume, int duration, int frequency,
			DayOfWeek day, int startTime, boolean initial) {

		// find the Locations matching the given strings, if they are already in
		// the graph
		Location originLoc = getLocation(origin);
		Location destinationLoc = getLocation(destination);

		// if Locations don't exist yet, add them to the graph
		if (originLoc == null) {
			originLoc = new Location(origin);
			addLocation(originLoc);
		}
		if (destinationLoc == null) {
			destinationLoc = new Location(destination);
			addLocation(destinationLoc);
		}

		// work out Priority
		String priority;
		if (type.equals("Air")) {
			priority = "Air";
		} else {
			priority = "Standard";
		}

		// get customer price matching the route
		CustomerPrice price = null;
		price = getCustomerPrice(originLoc, destinationLoc, origin, destination, priority);

		// check if route already exists, if it does, update it
		Boolean routeExists = false;
		for (int k = 0; k < originLoc.getRoutes().size(); k++) {
			Route r = originLoc.getRoutes().get(k);
			if (r.getDestination().equals(destinationLoc) && r.getCompany().equals(company)
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

		Route route = null;
		if (!routeExists) {
			// if it doesn't always exist, create route and add to graph
			route = new Route(originLoc, destinationLoc, company, type, priority, weightCost, volumeCost,
					maxWeight, maxVolume, duration, frequency, day, startTime, price);
			originLoc.addRoute(route);
		}

	/* Log Customer Price */
		//log in file and add to reports
		addEvent();
//		if (!initial) {
//			try {
//				writer.writeRoute(route);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		return route;
		
	}
	public CustomerPrice logCustomerPriceUpdate(String origin, String destination, String priority, double weightCost,
			double volumeCost, boolean initial) {

		// find the locations matching the given strings, if they are already in
		// the graph
		Location originLoc = getLocation(origin);
		Location destinationLoc = getLocation(destination);

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
			if (c.getDestination().equals(destinationLoc) && c.getPriority().equals(priority)) {
				c.setVolumeCost(volumeCost);
				c.setWeightCost(weightCost);

				// log in file and add to reports
				addEvent();
				// if (!initial) {
				// try {
				// writer.writeCustomerPrice(c);
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// }
				return c;
			}
		}

		// if it doesn't exist, create it, add it to the relevant Location
		CustomerPrice price;
		price = new CustomerPrice(originLoc, destinationLoc, priority, weightCost, volumeCost);
		originLoc.addPrice(price);

		// log in file and add to reports
		addEvent();
		// if (!initial) {
		// try {
		// writer.writeCustomerPrice(price);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		return price;
	}
	public DiscontinueRoute discontinueTransportRoute(String origin, String destination, String company, String type,
			boolean initial) {

		Location originLoc = getLocation(origin);
		Location destinationLoc = getLocation(destination);

		Route toCancel = null;
		// find the matching route out of origin
		for (Route r : originLoc.getRoutes()) {
			if (r.getCompany().equals(company) && r.getDestination().getName().equals(destination)
					&& r.getType().equals(type)) {
				toCancel = r;
			}
		}

		DiscontinueRoute disconRoute = new DiscontinueRoute(originLoc, destinationLoc, company, type);
		if (toCancel != null) {
			originLoc.removeRoute(toCancel);
			// if (!initial) {
			// try {
			// writer.writeDiscontinue(disconRoute);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			addEvent();
			return disconRoute;
		} else {
			// TODO display error
			return null;
		}

	}

	
	// GETTERS
	// Locations
	public ArrayList<Location> getLocations() {
		return locations;
	}
	public Location getLocation(String name) {
		Location location = null;
		for (Location loc : locations) {
			if (loc.getName().equals(name)) {
				location = loc;
			}
		}
		return location;
	}
	// Routes
	public ArrayList<RouteDisplay> getPossibleRoutes(String origin, String destination, double weight, double volume) {

		// find the locations matching the given strings
		Location originLoc = getLocation(origin);
		Location destinationLoc = getLocation(destination);

		// route selection
		AStar astar = new AStar(locations, originLoc, destinationLoc);
		ArrayList<ArrayList<Route>> routes = astar.twoListsOfRoutes(weight, volume);

		// set up list to pass to GUI
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

			if (domesticCities.contains(origin) && domesticCities.contains(destination)) {
				overallPriority = overallPriority + "Domestic ";
			} else {
				overallPriority = overallPriority + "International ";
			}

			String priority = "Air";
			for (Route r : list) {
				if (!r.getPriority().equals("Air")) {
					priority = "Standard";
				}
			}

			overallPriority = overallPriority + priority;

			// calculate customer price
			double price = 0.0;
			for (Route k : list) {
				price += (weight * k.getPrice().getWeightCost() + volume * k.getPrice().getVolumeCost());
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
	public ArrayList<Route> getRoutes(String origin, String destination){
		Location originLoc = getLocation(origin);

		ArrayList<Route> disconRoutes = new ArrayList<>();
		for (Route r : originLoc.getRoutes()) {
			if (r.getDestination().getName().equals(destination)) {
				disconRoutes.add(r);
			}
		}
		return disconRoutes;
	}
	// Customer Prices
	public CustomerPrice getCustomerPrice(Location originLoc, Location destinationLoc, String origin,
			String destination, String priority) {
		// check if there's already a price for the (origin, destination,
		// priority)
		CustomerPrice customerPrice = null;
		for (int k = 0; k < originLoc.getPrices().size(); k++) {

			if (originLoc.getPrices().get(k).getDestination().equals(destinationLoc)
					&& originLoc.getPrices().get(k).getPriority().equals(priority)) {
				customerPrice = originLoc.getPrices().get(k);
			}
		}
		// if there's no customer price, request one
		// TODO replace console input with GUI
		if (customerPrice == null) {
			return null;
		}
		return customerPrice;
	}
	// Delivery Requests
	public List<DeliveryRequest> getDeliveryRequests() {
		return deliveryRequests;
	}
	public DeliveryRequest getDeliveryDetails(String origin, String destination, double weight, double volume,
			RouteDisplay route) {

		// get duration
		int duration = route.getTotalDuration(LocalDateTime.now());
	
		// translate route list into legs
		ArrayList<Leg> legs = new ArrayList<>();
		for (Route r : route.getRoute()) {
			double freightCost = weight * r.getWeightCost() + volume * r.getVolumeCost();
			double customerPrice = weight * r.getPrice().getWeightCost() + volume * r.getPrice().getVolumeCost();
			legs.add(new Leg(r.getOrigin(), r.getDestination(), r.getType(), r.getCompany(), freightCost,
					customerPrice));
		}

		return logDeliveryRequest(LocalDateTime.now(),origin,destination, legs, weight,volume,route.getPriority(),duration, false);
	}
	
	// SETTERS + Adders
	public void addLocation(Location location) {
		locations.add(location);
	}
	
	
	// REPORTS
	// Amount of Mail
	public HashMap<Tuple, ArrayList<Double>> getAmountOfMail() {
		return amountOfMail;
	}
	public HashMap<TuplePriority, ArrayList<Integer>> getAmountOfMailDeliveryTimes() {
		return amountOfMailDeliveryTimes;
	}
	public void addToAmountOfMail(String origin, String destination, double weight, double volume) {
		boolean success = false;
		for (Tuple t : amountOfMail.keySet()) {
			if (t.getOrigin().equals(origin) && t.getDestination().equals(destination)) {
				ArrayList<Double> amountList = amountOfMail.get(t);
				double i = amountList.get(0);
				i = i + weight;
				double j = amountList.get(1);
				j = j + volume;
				double k = amountList.get(2);
				k++;
				amountList.clear();
				amountList.add(i);
				amountList.add(j);
				amountList.add(k);
				amountOfMail.put(t, amountList);
				success = true;
			}
		}
		if (!success) {
			Tuple t = new Tuple(origin, destination);
			ArrayList<Double> weightAndVolume = new ArrayList<>();
			weightAndVolume.add(weight);
			weightAndVolume.add(volume);
			weightAndVolume.add(1.0);
			amountOfMail.put(t, weightAndVolume);
		}
		mailReportPrint();
	}
	private void mailReportPrint() {
		for (Tuple t : amountOfMail.keySet()) {
			ArrayList<Double> amountList = amountOfMail.get(t);
			System.out.println(t.getOrigin() + " to " + t.getDestination() + ". Total Weight: " + amountList.get(0)
					+ " Total Volume:" + amountList.get(1) + " Total Instances: " + amountList.get(2));
		}
	}

	// Average Delivery Times
	public void addToAverageDeliveryTimes(String origin, String destination, int duration, String priority) {
		boolean success = false;
		for (TuplePriority t : amountOfMailDeliveryTimes.keySet()) {
			if (t.getOrigin().equals(origin) && t.getDestination().equals(destination)) {
				ArrayList<Integer> totalAndCount = amountOfMailDeliveryTimes.get(t);
				totalAndCount.set(0, totalAndCount.get(0) + duration);
				totalAndCount.set(1, totalAndCount.get(1) + 1);
				amountOfMailDeliveryTimes.put(t, totalAndCount);
				success = true;
			}
		}
		if (!success) {
			TuplePriority t = new TuplePriority(origin, destination, priority);
			ArrayList<Integer> totalAndCount = new ArrayList<>();
			totalAndCount.add(duration);
			totalAndCount.add(1);
			amountOfMailDeliveryTimes.put(t, totalAndCount);
		}
	}
	public Integer averageDeliveryTime(String origin, String destination, String priority) {
		for (TuplePriority t : amountOfMailDeliveryTimes.keySet()) {
			if (t.getOrigin().equals(origin) && t.getDestination().equals(destination)
					&& t.getPriority().equals(priority)) {
				ArrayList<Integer> totalAndCount = amountOfMailDeliveryTimes.get(t);
				return totalAndCount.get(0) / totalAndCount.get(1);
			}
		}
		return -1;
	}
	
	// Little Reports
	public void addTotalRev(double amount) {
		totalRev += amount;
	}
	public void addTotalExp(double amount) {
		totalExp += amount;
	}
	public void addEvent() {
		events += 1;
	}
	public double getTotalRev() {
		System.out.println("Total Revenue: $" + totalRev);
		return totalRev;
	}
	public double getTotalExp() {
		System.out.println("Total Expenditure: $" + totalExp);
		return totalExp;
	}
	public int getTotalEvents() {
		System.out.println("Total Events: " + events);
		return events;
	}
}