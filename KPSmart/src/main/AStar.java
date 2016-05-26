package main;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;



public class AStar {
	/*
	 * A* algorithm (fast version)
	 * 
	 * fringe = priority queue, ordered by total cost to Goal estimate(node,
	 * goal) must be admissible and consistent
	 * 
	 * Initialise: for all nodes visited <- false, pathFrom <- null
	 * enqueue([start, null, 0, estimate(start, goal )], fringe),
	 * 
	 * Repeat until fringe is empty: [node, from, costToHere, totalCostToGoal]
	 * <- dequeue(fringe) If not node.visited then node.visited <-true,
	 * node.pathFrom<-from, node.cost<-costToHere If node = goal then exit for
	 * each edge to neigh out of node if not neigh.visited then costToNeigh <-
	 * costToHere + edge.weight estTotal <- costToNeigh + estimate(neighbour,
	 * goal ) fringe.enqueue([neighbour, node, costToNeigh, estTotal])
	 */
	
	// VARIABLES
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

		/**System.out.println("\n\n----------------------------------------");/**/
		/**System.out.print("PLAN: ");/**/
		/**System.out.println(start.toPretty()+" ---> "+goal.toPretty());	/**/
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
		/**System.out.println("\n------------Cheap------------");/**/
		
		// Find Cheapest Route
		Location loc = getNextLocation();
		/**System.out.println("Visit: "+loc);/**/
		while (loc != null && loc != goal) {
			// Mark Location Visited
			visited.put(loc, true);							
			
			// Visit its Neighbours
			for (Route route : loc.getRoutes()) {
				Location neightbour = route.getDestination();
				
				// If Cheapest Start -> Neighbour Route - Add Route to 'previous'
				Double altCost = costs.get(loc) + route.getCost(weight, volume);
				if (altCost < costs.get(neightbour)) {
					costs.put(neightbour, altCost);
					previous.put(neightbour, route);
				}
			}
			loc = getNextLocation();
			/**System.out.println("Visit: "+loc);/**/
		}
		
		// Create Route Array
		int x = 0;
		/**System.out.println("BACK");/**/
		/**System.out.println(goal + " "+this.goal);/**/
		ArrayList<Route> list = new ArrayList<Route>();			
		Location origin = goal;
		Route route = previous.get(origin);
		while (route != null && origin != start) {
			list.add(route);
			origin = route.getOrigin();
			route = previous.get(origin);
			if (x++ < 40) {
				/**System.out.println("c("+ origin.toPretty() +")");/**/
			}
		}
		
		/**System.out.println("OUT: ");/**/
		for (Route r : list) {
			/**System.out.println(r.toPretty());/**/
		}
		
		return list;
	}
	// Get Cheapest Air Priority Route
	// TODO
	public ArrayList<Route> airPriorityAlgorithm(double weight, double volume) {
		resetGraph();
		/**System.out.println("------------Priority------------");/**/
		
		Location loc = getNextLocation();
		/**System.out.println("Visit: "+loc);/**/
		while (loc != null && loc != goal) {
			// Mark Location Visited
			visited.put(loc, true);	
			
			// Visit its Neighbours
			for (Route route : loc.getRoutes()) {
				Location neightbour = route.getDestination();
				
				// Check Only Air Routes
				if (route.getPriority() == "Air") {
					// If Cheapest (Start -> Neighbour) Route
					Double altCost = costs.get(loc) + route.getCost(weight, volume);
					if (altCost < costs.get(neightbour)) {
						costs.put(neightbour, altCost);
						previous.put(neightbour, route);
					}
				}
			}
			loc = getNextLocation();
			/**System.out.println("Visit: "+loc);/**/
		}

		// Create Route Array
		int x = 0;
		/**System.out.println("BACK");/**/
		ArrayList<Route> list = new ArrayList<Route>();			
		Location origin = goal;
		Route route = previous.get(origin);
		while (route != null && origin != start) {	
			list.add(route);
			origin = route.getOrigin();
			route = previous.get(origin);
			if (x < 40) {
				/**System.out.println("p("+ origin.toPretty() +")");/**/
			}
		}
		
		/**System.out.println("OUT: ");/**/
		for (Route r : list) {
			/**System.out.println(r.toPretty());/**/
		}
		
		return list;
	}
	
	// HELPERS
	// Clear Graph
	public void resetGraph() {
			for (Location location : graph) {	
				visited.put(location, false);
				costs.put(location, Double.MAX_VALUE);
				previous.put(location,  null);
			}
			costs.put(start, 0.0);
		}
	// Get Next Cheapest Unvisited Location
	private Location getNextLocation() {			//System.out.println("Get cheapest Loc");
		Location next = null;
		double minCost = Double.MAX_VALUE;
		
		for (Location location : graph) {
			//System.out.println(" check: "+location);
			double cost = costs.get(location);
			
			if (!visited.get(location)) {
				if (cost < minCost) {
				//System.out.println(" added");
				next = location;
				minCost = cost;
				}
			}
		}
		return next;
	}
	
	
	// TODO check cheapest Air and cheapest Overall aren't the same
	
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
	public Route getCheapestRoute(ArrayList<Route> routes, double weight, double volume) {
		Route cheapest = routes.get(0);
		for (Route r : routes) {
			if (cheapest.getCost(weight, volume) > r.getCost(weight, volume)) {
				cheapest = r;
			}
		}
		return cheapest;
	}

}