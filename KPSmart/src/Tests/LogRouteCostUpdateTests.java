package Tests;

import static org.junit.Assert.*;

import java.util.List;

import main.Location;
import main.Main;
import main.Route;

import org.junit.Test;

public class LogRouteCostUpdateTests {

	//add Locations that don't already exist
	@Test 
	public void test1() {
		Main main = setUpRoutes();
		assertEquals(2, main.getLocations().size());
	}	
	
	@Test
	public void test2() {
		Main main = setUpRoutes();
		List<Location> locs = main.getLocations();
		Location origin = null;
		Location dest = null;
		for(int i =0; i<locs.size(); i++) {
			if(locs.get(i).getName().equals("Wellington")){
				origin = locs.get(i);
			}
			if(locs.get(i).getName().equals("Auckland")){
				dest = locs.get(i);
			}
		}
		assertEquals("Wellington", origin.getName());
		assert(dest.getName().equals("Auckland"));
	}
	
	//add when origin doesn't already exist
	@Test
	public void test3() {
		Main main = setUpRoutes();
		main.logTransportCostUpdate("Wellington", "Hastings", "UPS", "air", "Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		List<Location> locs = main.getLocations();
		assertEquals(3,locs.size());
	}
	
	//add when destination doesn't already exist
	@Test
	public void test4() {
		Main main = setUpRoutes();
		main.logTransportCostUpdate("Hastings", "Auckland", "UPS", "air", "Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		List<Location> locs = main.getLocations();
		assertEquals(3,locs.size());
	}
	
	//test when origin destination and company are same, but type differs
	@Test 
	public void test5() {
		Main main = setUpRoutes();
		assertEquals(1,main.getLocations().get(0).getRoutes().size());
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "land", "Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		assertEquals(2, main.getLocations().size());
		assertEquals(2,main.getLocations().get(0).getRoutes().size());
	}
	
	//test when origin destination and type are same, but company differs
	@Test 
	public void test6() {
		Main main = setUpRoutes();
		assertEquals(1,main.getLocations().get(0).getRoutes().size());
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost", "air", "Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		assertEquals(2, main.getLocations().size());
		assertEquals(2,main.getLocations().get(0).getRoutes().size());
	}	
	
	//update existing route
	@Test
	public void test7() {
		Main main = setUpRoutes();
		Route routeStart = main.getLocations().get(0).getRoutes().get(0);
		Location destStart = routeStart.getDestination();
		String compStart = routeStart.getCompany();
		String typeStart = routeStart.getType();
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air", "Domestic Air", 5.8, 15, 15, 50, 12, 18, "Tuesday");
		Route routeFinal = main.getLocations().get(0).getRoutes().get(0);
		assertEquals(routeFinal.getDestination().getName(), destStart.getName());
		assertEquals(routeFinal.getCompany(), compStart);
		assertEquals(routeFinal.getType(), typeStart);
	}
	
	//add the same route again - shouldn't add
	@Test
	public void test8() {
		Main main = setUpRoutes();
		assertEquals(1,main.getLocations().get(0).getRoutes().size());
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air", "Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		assertEquals(2, main.getLocations().size());
		assertEquals(1,main.getLocations().get(0).getRoutes().size());
	}
	
	//HELPER
	public Main setUpRoutes() {
		Main main = new Main();
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air", "Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		return main;
	}
	
}
