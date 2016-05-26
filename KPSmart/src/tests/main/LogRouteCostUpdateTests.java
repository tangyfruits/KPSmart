package tests.main;

import static org.junit.Assert.*;

import java.util.List;

import main.Location;
import main.Main;
import main.Route;

import org.junit.Test;

/**
 * Will need to manually input customerPrices in console to run these. 
 * @author Shelley
 *
 */
public class LogRouteCostUpdateTests {

	//add Locations that don't already exist
	@Test 
	public void test1() {
		Main main = setUpRoutes();
		assertEquals(2, main.getLocations().size());
	}

	@Test
	public void test2a() {
		Main main = setUpRoutes();
		List<Location> locs = main.getLocations();
		Location origin = null;
		for (int i = 0; i < locs.size(); i++) {
			if (locs.get(i).getName().equals("Wellington")) {
				origin = locs.get(i);
			}
		}
		assertEquals(locs.get(0).getName(), origin.getName());
		assertEquals(locs.get(0).getRoutes().get(0).getOrigin().getName(), origin.getName());
	}

	@Test
	public void test2b() {
		Main main = setUpRoutes();
		List<Location> locs = main.getLocations();
		Location dest = null;
		for (int i = 0; i < locs.size(); i++) {
			if (locs.get(i).getName().equals("Auckland")) {
				dest = locs.get(i);
			}
		}
		assert (dest.getName().equals("Auckland"));
	}

	// add when origin doesn't already exist
	@Test
	public void test3() {
		Main main = setUpRoutes();
		main.logTransportCostUpdate("Wellington", "Hastings", "UPS", "air",
				"Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		List<Location> locs = main.getLocations();
		// has added origin, but not dest again
		assertEquals(3, locs.size());
	}

	// add when destination doesn't already exist
	@Test
	public void test4() {
		Main main = setUpRoutes();
		main.logTransportCostUpdate("Hastings", "Auckland", "UPS", "air",
				"Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		List<Location> locs = main.getLocations();
		// has added dest but not origin again
		assertEquals(3, locs.size());
	}

	// test when origin destination and company are same, but type differs
	@Test
	public void test5() {
		Main main = setUpRoutes();
		// check there is only one route out of origin
		assertEquals(1, main.getLocations().get(0).getRoutes().size());
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "land",
				"Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		// check there are still only 2 locations
		assertEquals(2, main.getLocations().size());
		// check a second route has been added
		assertEquals(2, main.getLocations().get(0).getRoutes().size());
	}

	// test when origin destination and type are same, but company differs
	@Test
	public void test6() {
		Main main = setUpRoutes();
		// check only one route
		assertEquals(1, main.getLocations().get(0).getRoutes().size());
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost", "air",
				"Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		// check there are still only two locations
		assertEquals(2, main.getLocations().size());
		// check a second route hass been added
		assertEquals(2, main.getLocations().get(0).getRoutes().size());
	}

	// update existing route
	@Test
	public void test7a() {
		Main main = setUpRoutes();
		// check that there is only one route out of origin
		assertEquals(1, main.getLocations().get(0).getRoutes().size());
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air",
				"Domestic Air", 5.8, 15, 15, 50, 12, 18, "Tuesday");
		// check there is still only one route
		assertEquals(1, main.getLocations().get(0).getRoutes().size());
	}

	// update existing route
	@Test
	public void test7b() {
		Main main = setUpRoutes();
		Route routeStart = main.getLocations().get(0).getRoutes().get(0);
		// check the initial values
		assertEquals(15, routeStart.getMaxWeight(), 0);
		assertEquals(50, routeStart.getMaxVolume(), 0);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air",
				"Domestic Air", 3.5, 4.5, 20, 60, 12, 18, "Tuesday");
		// check the route has the new values
		assertEquals(20, routeStart.getMaxWeight(), 0);
		assertEquals(60, routeStart.getMaxVolume(), 0);
	}

	// update existing route
	@Test
	public void test7c() {
		Main main = setUpRoutes();
		Route routeStart = main.getLocations().get(0).getRoutes().get(0);
		// check the initial values
		assertEquals(3.5, routeStart.getWeightCost(), 0);
		assertEquals(4.5, routeStart.getVolumeCost(), 0);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air",
				"Domestic Air", 5.8, 15, 15, 50, 12, 18, "Tuesday");
		// check the route has the new values
		assertEquals(5.8, routeStart.getWeightCost(), 0);
		assertEquals(15, routeStart.getVolumeCost(), 0);
	}

	// update existing route
	@Test
	public void test7d() {
		Main main = setUpRoutes();
		Route routeStart = main.getLocations().get(0).getRoutes().get(0);
		// check the initial values
		assertEquals(12, routeStart.getDuration(), 0);
		assertEquals(18, routeStart.getFrequency(), 0);
		assertEquals("Tuesday", routeStart.getDay());
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air",
				"Domestic Air", 3.5, 4.5, 15, 50, 20, 60, "Wednesday");
		// check the route has the new values
		assertEquals(20, routeStart.getDuration(), 0);
		assertEquals(60, routeStart.getFrequency(), 0);
		assertEquals("Wednesday", routeStart.getDay());
	}

	// add the same route again - shouldn't add
	@Test
	public void test8() {
		Main main = setUpRoutes();
		// check there is only one route
		assertEquals(1, main.getLocations().get(0).getRoutes().size());
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air",
				"Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		assertEquals(2, main.getLocations().size());
		// check there is still only one route
		assertEquals(1, main.getLocations().get(0).getRoutes().size());
	}

	// HELPER
	public Main setUpRoutes() {
		Main main = new Main();
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air",
				"Domestic Air", 3.5, 4.5, 15, 50, 12, 18, "Tuesday");
		return main;
	}

}