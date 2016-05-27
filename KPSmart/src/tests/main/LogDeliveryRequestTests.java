package tests.main;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.util.ArrayList;

import main.CustomerPrice;
import main.Main;
import main.Route;

import org.junit.Test;

/**
 * Will need to manually input customerPrices in console to run these. 
 * @author Shelley
 *
 */
public class LogDeliveryRequestTests {

	//Find Routes
	@Test
	public void test1() {
		Main main = setUpRoutes();
		ArrayList<ArrayList<Route>> r = main.getPossibleRoutes("Wellington", "Auckland", 3, 2);
		//should find one route
		assertNotNull(r.get(0));
		//should find only one
		assertNotNull(r.get(1));
		System.out.println(r.size());
	}
	
	// remove a route that doesn't exist
	@Test
	public void test2() {
		
	}
	
	// helper method
	public Main setUpRoutes() {
		Main main = new Main();
		CustomerPrice price = main.logCustomerPriceUpdate("Wellington",
				"Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air",
				"Air", 3.5, 4.5, 15, 50, 12, 18, DayOfWeek.MONDAY,15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost", "air",
				"Air", 2.5, 2.8, 8, 12, 12, 32, DayOfWeek.MONDAY,15);
		main.logTransportCostUpdate("Wellington", "Auckland", "USPS", "air",
				"Air", 2.5, 2.8, 8, 12, 12, 32, DayOfWeek.MONDAY,15);
		return main;

	}
}
