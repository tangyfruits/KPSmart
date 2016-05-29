package tests.main;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.util.List;

import main.CustomerPrice;
import main.Location;
import main.Main;
import main.Route;

import org.junit.Test;

/**
 *
 * @author Shelley
 *
 */
public class LogDiscontinueRouteTests {

	// remove a route
	@Test
	public void test1() {
		Main main = setUpRoutes();
		// check there are 3 routes out of origin
		assertEquals(3, main.getLocations().get(0).getRoutes().size());
		// discontinue route
		main.discontinueTransportRoute("Wellington", "Auckland", "UPS", "Air");
		// check only 2 routes now
		assertEquals(2, main.getLocations().get(0).getRoutes().size());
	}

	// remove route that doesn't exist
	@Test
	public void test2() {
		Main main = setUpRoutes();
		// check there are 3 routes out of origin
		assertEquals(3, main.getLocations().get(0).getRoutes().size());
		// discontinue route
		main.discontinueTransportRoute("Wellington", "Auckland", "UPS", "Air");
		// check only 2 routes now
		assertEquals(2, main.getLocations().get(0).getRoutes().size());
		// try same route again
		main.discontinueTransportRoute("Wellington", "Auckland", "UPS", "Air");
		// check still 2 routes
		assertEquals(2, main.getLocations().get(0).getRoutes().size());
	}

	// check correct route has been removed
	@Test
	public void test3() {
		Main main = setUpRoutes();
		// check there are 3 routes out of origin
		assertEquals(3, main.getLocations().get(0).getRoutes().size());
		Route r = main.getLocations().get(0).getRoutes().get(0);
		assertEquals("UPS", r.getCompany());
		// discontinue route
		main.discontinueTransportRoute("Wellington", "Auckland", "UPS", "Air");
		// check only 2 routes now
		assertEquals(2, main.getLocations().get(0).getRoutes().size());
		// check route again
		r = main.getLocations().get(0).getRoutes().get(0);
		assertNotEquals("UPS", r.getCompany());
	}

	// remove a route that doesn't exist
	@Test
	public void test4() {
		Main main = setUpRoutes();
		// check there are 3 routes out of origin
		assertEquals(3, main.getLocations().get(0).getRoutes().size());
		// discontinue route
		main.discontinueTransportRoute("Wellington", "Auckland", "PostHaste", "Air");
		// check still 3 routes now
		assertEquals(3, main.getLocations().get(0).getRoutes().size());
	}

	// helper method
	public Main setUpRoutes() {
		Main main = new Main();
		CustomerPrice price = main.logCustomerPriceUpdate("Wellington",
				"Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", 
				"Air", 3.5, 4.5, 15, 50, 12, 18, DayOfWeek.MONDAY,15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost",
				"Air", 2.5, 2.8, 8, 12, 12, 32, DayOfWeek.MONDAY,15);
		main.logTransportCostUpdate("Wellington", "Auckland", "USPS",
				"Air", 2.5, 2.8, 8, 12, 12, 32, DayOfWeek.MONDAY,15);
		return main;

	}

}