package main;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

import event.Route;

public class AStar {

	// FIELDS
	private Location start;
	private Location goal;
	private ArrayList<Location> graph;
	private HashMap<Location, Boolean> visited;
	private HashMap<Location, Double> costs;
	private HashMap<Location, Route> previous;

	// CONSTRUCTOR
	public AStar(ArrayList<Location> graph, Location start, Location goal) {
		this.start = start;
		this.goal = goal;
		this.graph = graph;
		this.visited = new HashMap<>();
		this.costs = new HashMap<>();
		this.previous = new HashMap<>();
		resetGraph();
	}

	// METHODS
	// MAIN METHOD
	public ArrayList<ArrayList<Route>> twoListsOfRoutes(double weight, double volume) {
		ArrayList<ArrayList<Route>> list = new ArrayList<ArrayList<Route>>();
		ArrayList<Route> cheapestRoute = cheapestRouteAlgorithm(weight, volume);
		ArrayList<Route> priorityRoute = airPriorityAlgorithm(weight, volume);
		if (cheapestRoute.size() > 0) {
			list.add(cheapestRoute);
		}
		if (priorityRoute.size() > 0) {
			list.add(priorityRoute);
		}
		return list;
	}

	// Get Cheapest Overall Route
	public ArrayList<Route> cheapestRouteAlgorithm(double weight, double volume) {
		resetGraph();

		// Find Cheapest Route
		Location loc = getNextLocation();
		while (loc != null && loc != goal) {
			// Mark Location Visited
			visited.put(loc, true);

			// Visit its Neighbours
			for (Route route : loc.getRoutes()) {
				Location neightbour = route.getDestination();

				// If Cheapest Start -> Neighbour Route - Add Route to
				// 'previous'
				Double altCost = costs.get(loc) + route.getCost(weight, volume);
				if (altCost < costs.get(neightbour)) {
					costs.put(neightbour, altCost);
					previous.put(neightbour, route);
				}
			}
			loc = getNextLocation();
		}

		// Create Route Array
		ArrayList<Route> list = new ArrayList<Route>();
		Location origin = goal;
		Route route = previous.get(origin);

		while (route != null && origin != start) {
			list.add(0, route);
			origin = route.getOrigin();
			route = previous.get(origin);
		}

		return list;
	}

	// Get Cheapest Air Priority Route
	public ArrayList<Route> airPriorityAlgorithm(double weight, double volume) {
		resetGraph();
		Location loc = getNextLocation();
		while (loc != null && loc != goal) {
			// Mark Location Visited
			visited.put(loc, true);

			// Visit its Neighbours
			for (Route route : loc.getRoutes()) {
				Location neightbour = route.getDestination();

				// Check Only Air Routes
				if (route.getPriority() == "Air") {
					// If Cheapest (Start -> Neighbour) Route
					Double newCost = costs.get(loc) + route.getCost(weight, volume);
					if (newCost < costs.get(neightbour)) {
						costs.put(neightbour, newCost);
						previous.put(neightbour, route);
					}
				}
			}
			loc = getNextLocation();

		}

		ArrayList<Route> list = new ArrayList<Route>();
		Location origin = goal;
		Route route = previous.get(origin);

		while (route != null && origin != start) {
			list.add(0, route);
			origin = route.getOrigin();
			route = previous.get(origin);

		}
		return list;
	}

	// HELPERS
	// Clear Graph
	public void resetGraph() {
		for (Location location : graph) {
			visited.put(location, false);
			costs.put(location, Double.MAX_VALUE);
			previous.put(location, null);
		}
		costs.put(start, 0.0);
	}

	// Get Next Cheapest Unvisited Location
	private Location getNextLocation() {
		Location next = null;
		double minCost = Double.MAX_VALUE;

		for (Location location : graph) {
			double cost = costs.get(location);
			if (!visited.get(location)) {
				if (cost < minCost) {
					next = location;
					minCost = cost;
				}
			}
		}
		return next;
	}
}
