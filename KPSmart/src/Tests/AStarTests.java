package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.AStar;
import main.CustomerPrice;
import main.Location;
import main.Route;

public class AStarTests {
	
	Location loc1 = new Location("Lower Hutt");
	Location loc2 = new Location("Wellington");
	Location loc3 = new Location("Auckland ");
	Location loc4 = new Location("Hamilton");
	Location loc5 = new Location("Tauranga");
	Location loc6 = new Location("Upper Hutt");
	Location loc7 = new Location("Turangi");
	Location loc8 = new Location("Kuratau");
	Location loc9 = new Location("Lake Taupo");
	
	AStar astar = new AStar(loc1, loc2);
	AStar astarNull = new AStar(null, null);
	
	@Test
	public void testParameters() {
		assertEquals(astar.start, loc1);
		assertEquals(astar.goal, loc2);
	}

	
	@Test
	public void testAlgo1(){
		System.out.println("testAlgo1");
		CustomerPrice cp1 = new CustomerPrice(loc1, loc2, "Land", 1.0, 1.0);
		CustomerPrice cp2 = new CustomerPrice(loc1, loc2, "Air", 2.0, 2.0);
		CustomerPrice cp3 = new CustomerPrice(loc1, loc3, "Land", 1.0, 1.0);
		CustomerPrice cp4 = new CustomerPrice(loc1, loc3, "Air", 2.0, 2.0);
		CustomerPrice cp5 = new CustomerPrice(loc2, loc3, "Land", 1.0, 1.0);
		CustomerPrice cp6 = new CustomerPrice(loc2, loc3, "Air", 2.0, 2.0);
		Route r1 = new Route(loc1, loc2, "", "", "Land", 1.0, 1.0, 99, 99, 5, 5, "", cp1);
		Route r2 = new Route(loc1, loc2, "", "", "Air", 1.0, 1.0, 99, 99, 5, 5, "", cp2);
		Route r3 = new Route(loc1, loc3, "", "", "Land", 1.0, 1.0, 99, 99, 5, 5, "", cp3);
		Route r4 = new Route(loc1, loc3, "", "", "Air", 1.0, 1.0, 99, 99, 5, 5, "", cp4);
		Route r5 = new Route(loc2, loc3, "", "", "Land", 1.0, 1.0, 99, 99, 5, 5, "", cp5);
		Route r6 = new Route(loc2, loc3, "", "", "Air", 1.0, 1.0, 99, 99, 5, 5, "", cp6);
		loc1.addRoute(r1);
		loc1.addRoute(r2);
		loc1.addRoute(r3);
		loc1.addRoute(r4);
		ArrayList<ArrayList<Route>> bestRoutes = bestRoutes(loc1, loc3, 2, 2);
		for(Route r : bestRoutes.get(0)){
			System.out.println(bestRoutes.get(0).size());
			System.out.println(r.getOrigin().getName() + " to " + r.getDestination().getName());
		}
		System.out.println(bestRoutes.get(0).get(0).getOrigin().getName());
		System.out.println(bestRoutes.get(0).get(0).getDestination().getName());
		assertTrue(bestRoutes.get(0).get(0).getOrigin().getName().equals("Lower Hutt") && bestRoutes.get(0).get(0).getDestination().getName().equals("Auckland"));
	}
	

	
	//helper methods
	public ArrayList<ArrayList<Route>> bestRoutes(Location origin, Location destination, double weight, double volume) {
		ArrayList<ArrayList<Route>> listOfListOfRoutes = new ArrayList<ArrayList<Route>>();
		Route directRoute = getDirectRoute(destination, destination, weight, volume);
		if (directRoute == null) {
			AStar astar = new AStar(origin, destination);
			return astar.listOfRoutes(weight, volume);
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
