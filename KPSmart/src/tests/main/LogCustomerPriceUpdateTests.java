package tests.main;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import main.Location;
import main.Main;

import org.junit.Test;

import event.CustomerPrice;
import event.Route;

/**
 *
 * @author Shelley
 *
 */
public class LogCustomerPriceUpdateTests {

	// add when neither Location exists
	@Test
	public void test1() {
		Main main = new Main();
		CustomerPrice price = main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false,
				LocalDateTime.now(), "User");
		// test price has been added to origin
		assertEquals(1, main.getLocations().get(0).getPrices().size());
	}

	// add when neither Location exists
	@Test
	public void test1a() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// test both locations have been added
		assertEquals(2, main.getLocations().size());
	}

	// add when origin already exists
	@Test
	public void test2a() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check there are 2 locations existing
		assertEquals(2, main.getLocations().size());
		// add a second route with same origin
		main.logCustomerPriceUpdate("Wellington", "Hastings", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check a third location has been added
		assertEquals(3, main.getLocations().size());
	}

	// add when origin already exists
	@Test
	public void test2b() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check there is 1 price existing
		assertEquals(1, main.getLocations().get(0).getPrices().size());
		// add a second route with same origin
		main.logCustomerPriceUpdate("Wellington", "Hastings", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check there is a second price added
		assertEquals(2, main.getLocations().get(0).getPrices().size());
	}

	// add when dest already exists
	@Test
	public void test3a() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check there are 2 locations existing
		assertEquals(2, main.getLocations().size());
		// add a second route with same origin
		main.logCustomerPriceUpdate("Hastings", "Wellington", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check a third location has been added
		assertEquals(3, main.getLocations().size());
	}

	// add when dest already exists
	@Test
	public void test3b() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check there is 1 price existing
		assertEquals(1, main.getLocations().get(0).getPrices().size());
		// add a second route with same origin
		main.logCustomerPriceUpdate("Hastings", "Auckland", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check there is a price out of the new Location
		assertEquals(1, main.getLocations().get(2).getPrices().size());
		// check there is still only price from the original location
		assertEquals(1, main.getLocations().get(0).getPrices().size());
	}

	// add when 2 locations are the same but Priority differs
	@Test
	public void test4() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check there is 1 price existing
		assertEquals(1, main.getLocations().get(0).getPrices().size());
		// add a second route with same origin
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Standard", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check there is a second price
		assertEquals(2, main.getLocations().get(0).getPrices().size());
	}

	// updating a customer price
	@Test
	public void test5a() {
		Main main = new Main();
		CustomerPrice price = main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false,
				LocalDateTime.now(), "User");
		// check there is one route to start with
		assertEquals(1, main.getLocations().get(0).getPrices().size());
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 2, 4, false, LocalDateTime.now(), "User");
		// check there is still only one route
		assertEquals(1, main.getLocations().get(0).getPrices().size());
	}

	// updating a customer price
	@Test
	public void test5b() {
		Main main = new Main();
		CustomerPrice price = main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false,
				LocalDateTime.now(), "User");
		// check the initial values
		assertEquals(1.4, main.getLocations().get(0).getPrices().get(0).getVolumeCost(), 0);
		assertEquals(1.2, main.getLocations().get(0).getPrices().get(0).getWeightCost(), 0);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 2, 4, false, LocalDateTime.now(), "User");
		// check new values
		assertEquals(4, main.getLocations().get(0).getPrices().get(0).getVolumeCost(), 0);
		assertEquals(2, main.getLocations().get(0).getPrices().get(0).getWeightCost(), 0);
	}

	// add the same price a second time - shouldn't add
	@Test
	public void test6() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check there is one price
		assertEquals(1, main.getLocations().get(0).getPrices().size());
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 1.2, 1.4, false, LocalDateTime.now(), "User");
		// check there is still only one price
		assertEquals(1, main.getLocations().get(0).getPrices().size());

	}

	// add route when price exists
	// should not ask for input from console, because a price already exists
	@Test
	public void test7() {
		Main main = new Main();
		CustomerPrice price = main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14, false,
				LocalDateTime.now(), "User");
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air", 3.5, 4.5, 15, 50, 12, 18, DayOfWeek.MONDAY,
				12, false, LocalDateTime.now(), "User");
		assertEquals(price, main.getLocations().get(0).getRoutes().get(0).getPrice());
	}

	// updated price is updated in routes
	@Test
	public void test8() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14, false, LocalDateTime.now(), "User");
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air", 3.5, 4.5, 15, 50, 12, 18, DayOfWeek.MONDAY,
				13, false, LocalDateTime.now(), "User");
		// check initial values
		assertEquals(15, main.getLocations().get(0).getRoutes().get(0).getPrice().getWeightCost(), 0);
		assertEquals(14, main.getLocations().get(0).getRoutes().get(0).getPrice().getVolumeCost(), 0);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 10, 12, false, LocalDateTime.now(), "User");
		// check updated values
		assertEquals(10, main.getLocations().get(0).getRoutes().get(0).getPrice().getWeightCost(), 0);
		assertEquals(12, main.getLocations().get(0).getRoutes().get(0).getPrice().getVolumeCost(), 0);
	}

	// add a route when a different priority price exists
	// should request input from console
	@Test
	public void test9() {
		Main main = new Main();
		CustomerPrice price = main.logCustomerPriceUpdate("Wellington", "Auckland", "Standard", 15, 14, false,
				LocalDateTime.now(), "User");
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air", 3.5, 4.5, 15, 50, 12, 18, DayOfWeek.MONDAY,
				14, false, LocalDateTime.now(), "User");
	}
}