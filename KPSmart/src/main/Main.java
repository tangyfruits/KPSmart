package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	private List<Location> locations;
	
	public Main() {
		locations = new ArrayList<Location>();
	}
	
	public CustomerPrice logCustomerPriceUpdate(String origin, String destination, 
			String priority, double weightCost, double volumeCost){
		
		//find the locations matching the given starings, if they are already in the graph
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
		
		//if locations don't exist yet, add them to the graph
		if (originLoc == null) {
			originLoc = new Location(origin);
			addLocation(originLoc);
		}
		if (destinationLoc == null) {
			destinationLoc = new Location(destination);
			addLocation(destinationLoc);
		}
		
		//check if customer price already exists, if so, update it
		Boolean priceExists = false;
		for(int i = 0; i<originLoc.getPrices().size(); i++){
			CustomerPrice c = originLoc.getPrices().get(i);
			if(c.getDestination().equals(destinationLoc) && c.getPriority().equals(priority)){
				c.setVolumeCost(volumeCost);
				c.setWeightCost(weightCost);
				priceExists = true;
				return c;
			}
		}
		
		//if it doesn't exist, create it, add it to the relevant Location
		CustomerPrice price;
		if (!priceExists) {
			price = new CustomerPrice(originLoc, destinationLoc, priority,
					weightCost, volumeCost);
			originLoc.addPrice(price);
			return price;
		}		
		return null;
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
		Boolean routeExists = false;
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
					.equals(destinationLoc)
					&& originLoc.getPrices().get(k).getPriority()
							.equals(priority)) {
				customerPrice = originLoc.getPrices().get(k);
			}
		}
		//if there's no customer price, request one
		//TODO replace console input with GUI
		if (customerPrice == null) {
			double custWeightCost = -1;
			double custVolCost = -1;
			    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			    System.out.print("Enter weightCost");
			    try{
		            custWeightCost = Double.parseDouble(input.readLine());
		        } catch (IOException e) {
				}
			    System.out.print("Enter volumeCost");
			    try{
		            custVolCost = Double.parseDouble(input.readLine());
			    }catch(IOException e) {
				}
		      
			logCustomerPriceUpdate(origin, destination, priority, custWeightCost, custVolCost);
		}
		return customerPrice;
	}
	
	public List<Location> getLocations() {
		return locations;
	}
	
	public void addLocation(Location location) {
		locations.add(location);
	}
}
