package tests.main;

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
	Location loc3 = new Location("Auckland");
	Location loc4 = new Location("Hamilton");
	Location loc5 = new Location("Tauranga");
	Location loc6 = new Location("Upper Hutt");
	Location loc7 = new Location("Turangi");
	Location loc8 = new Location("Kuratau");
	Location loc9 = new Location("Lake Taupo");
	Location loc10 = new Location("Cape Reinga");
	Location loc11 = new Location("Paraparaumu");
	Location loc12 = new Location("Dunedin");

	CustomerPrice cp1 = new CustomerPrice(loc1, loc2, "Land", 1.0, 1.0);
	CustomerPrice cp2 = new CustomerPrice(loc1, loc2, "Air", 2.0, 2.0);
	CustomerPrice cp3 = new CustomerPrice(loc1, loc3, "Land", 1.0, 1.0);
	CustomerPrice cp4 = new CustomerPrice(loc1, loc3, "Air", 2.0, 2.0);
	CustomerPrice cp5 = new CustomerPrice(loc2, loc3, "Land", 1.0, 1.0);
	CustomerPrice cp6 = new CustomerPrice(loc2, loc3, "Air", 2.0, 2.0);
	CustomerPrice cp7 = new CustomerPrice(loc2, loc4, "Land", 1.0, 1.0);
	CustomerPrice cp8 = new CustomerPrice(loc2, loc4, "Air", 2.0, 2.0);
	CustomerPrice cp9 = new CustomerPrice(loc4, loc5, "Land", 1.0, 1.0);
	CustomerPrice cp10 = new CustomerPrice(loc4, loc5, "Air", 2.0, 2.0);
	CustomerPrice cp11 = new CustomerPrice(loc3, loc5, "Land", 1.0, 1.0);
	CustomerPrice cp12 = new CustomerPrice(loc3, loc5, "Air", 2.0, 2.0);
	CustomerPrice cp13 = new CustomerPrice(loc3, loc4, "Land", 1.0, 1.0);
	CustomerPrice cp14 = new CustomerPrice(loc3, loc4, "Air", 2.0, 2.0);
	CustomerPrice cp15 = new CustomerPrice(loc1, loc3, "TEST", 1.0, 1.0);

	Route r1 = new Route(loc1, loc2, "", "", "Land", 1.0, 1.0, 99, 99, 5, 5, "", cp1);
	Route r2 = new Route(loc1, loc2, "", "", "Air", 1.0, 1.0, 99, 99, 5, 5, "", cp2);
	Route r3 = new Route(loc1, loc3, "", "", "Land", 1.0, 1.0, 99, 99, 5, 5, "", cp3);
	Route r4 = new Route(loc1, loc3, "", "", "Air", 1.0, 1.0, 99, 99, 5, 5, "", cp4);
	Route r5 = new Route(loc2, loc3, "", "", "Land", 1.0, 1.0, 99, 99, 5, 5, "", cp5);
	Route r6 = new Route(loc2, loc3, "", "", "Air", 1.0, 1.0, 99, 99, 5, 5, "", cp6);
	Route r7 = new Route(loc2, loc4, "", "", "Land", 1.0, 1.0, 99, 99, 5, 5, "", cp7);
	Route r8 = new Route(loc2, loc4, "", "", "Air", 1.0, 1.0, 99, 99, 5, 5, "", cp8);
	Route r9 = new Route(loc4, loc5, "", "", "Land", 1.0, 1.0, 99, 99, 5, 5, "", cp9);
	Route r10 = new Route(loc4, loc5, "", "", "Air", 1.0, 1.0, 99, 99, 5, 5, "", cp10);
	Route r11 = new Route(loc3, loc5, "", "", "Land", 1.0, 1.0, 99, 99, 5, 5, "", cp11);
	Route r12 = new Route(loc3, loc5, "", "", "Air", 1.0, 1.0, 99, 99, 5, 5, "", cp12);
	Route r13 = new Route(loc3, loc4, "", "", "Land", 1.0, 1.0, 99, 99, 5, 5, "", cp13);
	Route r14 = new Route(loc3, loc4, "", "", "Air", 1.0, 1.0, 99, 99, 5, 5, "", cp14);
	Route r15 = new Route(loc1, loc3, "", "", "TEST", 1.0, 1.0, 99, 99, 5, 5, "", cp15);

	@Test
	public void testDirectRoute1() {
		AStar astar = new AStar(loc1, loc3);
		addRoutesToLocations();
		ArrayList<ArrayList<Route>> bestRoutes = astar.twoListsOfRoutes(2, 2);
		assertTrue(bestRoutes.get(0).get(0).getOrigin().getName().equals("Lower Hutt")
				&& bestRoutes.get(0).get(0).getDestination().getName().equals("Auckland"));
	}

	@Test
	public void testCompoundRoute1() {
		AStar astar = new AStar(loc1, loc5);
		addRoutesToLocations();
		ArrayList<ArrayList<Route>> bestRoutes = astar.twoListsOfRoutes(2, 2);
		System.out.println(bestRoutes.size());
		for (Route r : bestRoutes.get(0)){
			System.out.println(r.getPrice().toString());
		}
		for (Route r : bestRoutes.get(1)){
			System.out.println(r.getPrice().toString());
		}
		System.out.println(bestRoutes.get(0).get(0).getOrigin().getName());
		System.out.println(bestRoutes.get(0).get(0).getDestination().getName());
		System.out.println(bestRoutes.get(0).get(0).getPriority());
		System.out.println(bestRoutes.get(0).get(1).getPriority());
		System.out.println(bestRoutes.get(0).get(2).getPriority());
		System.out.println(bestRoutes.size());
		System.out.println(bestRoutes.get(0).size());
		System.out.println(bestRoutes.get(1).get(0).getPriority());
		System.out.println(bestRoutes.get(1).get(1).getPriority());
		System.out.println(bestRoutes.get(1).get(2).getPriority());
		assertTrue(bestRoutes.get(0).get(0).getOrigin().getName().equals("Auckland")
				&& bestRoutes.get(0).get(0).getDestination().getName().equals("Tauranga"));
	}

	@Test
	public void testCompoundRoute2() {
		AStar astar = new AStar(loc1, loc5);
		addRoutesToLocations();
		ArrayList<ArrayList<Route>> bestRoutes = astar.twoListsOfRoutes(2, 2);
		System.out.println(bestRoutes.get(0).get(1).getOrigin().getName());
		System.out.println(bestRoutes.get(0).get(1).getDestination().getName());
		assertTrue(bestRoutes.get(0).get(2).getOrigin().getName().equals("Lower Hutt")
				&& bestRoutes.get(0).get(2).getDestination().getName().equals("Auckland"));
	}

	// helper methods
	private void addRoutesToLocations() {
		loc1.addRoute(r1);
		loc1.addRoute(r2);
		loc1.addRoute(r3);
		loc1.addRoute(r4);
		loc2.addRoute(r5);
		loc2.addRoute(r6);
		loc2.addRoute(r7);
		loc2.addRoute(r8);
		loc4.addRoute(r9);
		loc4.addRoute(r10);
		loc3.addRoute(r11);
		loc3.addRoute(r12);
		loc3.addRoute(r13);
		loc3.addRoute(r14);
	}

}
