package tests.main;

import static org.junit.Assert.*;

import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import main.CustomerPrice;
import main.Main;
import main.Route;

import org.junit.Test;

/**
 * Will need to manually input customerPrices in console to run these.
 * 
 * @author Shelley
 *
 */
public class LogDeliveryRequestTests {

	// Find Routes
	@Test
	public void test1() {
		Main main = setUpRoutes();
		ArrayList<ArrayList<Route>> r = main.getPossibleRoutes("Wellington",
				"Auckland", 3, 2);
		// should find one route
		assertNotNull(r.get(0));
		// should find only one
		assertNotNull(r.get(1));
		System.out.println(r.size());
	}

	// test Duration if logged before initial departure
	@Test
	public void test2() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 20, 30, 15, 24, DayOfWeek.MONDAY, 12);
		ArrayList<Route> routes = new ArrayList<>();
		routes.add(main.getLocations().get(0).getRoutes().get(0));
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 11, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// shoudl be 15+1 = 16(duration =15 + starts at 12 and is currently 11)
		int dur = main.getTotalDuration(ldt, routes);
		assertEquals(16, dur);
	}

	// test duration if logged after initial departure
	@Test
	public void test3() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 20, 30, 15, 24, DayOfWeek.MONDAY, 12);
		ArrayList<Route> routes = new ArrayList<>();
		routes.add(main.getLocations().get(0).getRoutes().get(0));
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 13, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 15+23 = 38(duration =15 + 23 hours till next departure)
		int dur = main.getTotalDuration(ldt, routes);
		assertEquals(38, dur);
	}

	// test duration for multileg route if logged after initial departure and
	// second departure after arrival
	@Test
	public void test4() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 20, 30, 15, 24, DayOfWeek.MONDAY, 12);
		main.logTransportCostUpdate("Wellington", "Auckland", "PostHaste",
				"Air", "Air", 10, 12, 20, 30, 10, 24, DayOfWeek.WEDNESDAY, 12);
		ArrayList<Route> routes = new ArrayList<>();
		routes.add(main.getLocations().get(0).getRoutes().get(0));
		routes.add(main.getLocations().get(0).getRoutes().get(1));
		System.out.println(routes.get(0).getDuration());
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 13, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 15+23 +9 +10 = 57(duration =15 + 23 hours till next
		// departure+9hhours till second departure +10 duration)
		int dur = main.getTotalDuration(ldt, routes);
		assertEquals(57, dur);
	}

	// test duration for multileg route if logged after initial departure and
	// second departure before arrival
	@Test
	public void test5() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 20, 30, 15, 24, DayOfWeek.MONDAY, 12);
		main.logTransportCostUpdate("Wellington", "Auckland", "PostHaste",
				"Air", "Air", 10, 12, 20, 30, 10, 24, DayOfWeek.TUESDAY, 12);
		ArrayList<Route> routes = new ArrayList<>();
		routes.add(main.getLocations().get(0).getRoutes().get(0));
		routes.add(main.getLocations().get(0).getRoutes().get(1));
		System.out.println(routes.get(0).getDuration());
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 13, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 15+23 +9 +10 = 57(duration =15 + 23 hours till next
		// departure+9hhours till second departure +10 duration
		int dur = main.getTotalDuration(ldt, routes);
		assertEquals(57, dur);
	}

	// test duration for multileg route if logged Before initial departure and
	// second departure after arrival
	@Test
	public void test6() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 20, 30, 15, 24, DayOfWeek.MONDAY, 12);
		main.logTransportCostUpdate("Wellington", "Auckland", "PostHaste",
				"Air", "Air", 10, 12, 20, 30, 10, 24, DayOfWeek.TUESDAY, 12);
		ArrayList<Route> routes = new ArrayList<>();
		routes.add(main.getLocations().get(0).getRoutes().get(0));
		routes.add(main.getLocations().get(0).getRoutes().get(1));
		System.out.println(routes.get(0).getDuration());
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 11, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 15+1+9+10 = 35(duration =15 + 1 hour till next departure+9
		// till second departure +10 duration)
		int dur = main.getTotalDuration(ldt, routes);
		assertEquals(35, dur);
	}

	// test duration for multileg route if logged Before initial departure and
	// second departure after arrival
	@Test
	public void test7() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 20, 30, 15, 24, DayOfWeek.MONDAY, 12);
		main.logTransportCostUpdate("Wellington", "Auckland", "PostHaste",
				"Air", "Air", 10, 12, 20, 30, 10, 6, DayOfWeek.TUESDAY, 1);
		ArrayList<Route> routes = new ArrayList<>();
		routes.add(main.getLocations().get(0).getRoutes().get(0));
		routes.add(main.getLocations().get(0).getRoutes().get(1));
		System.out.println(routes.get(0).getDuration());
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 11, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 1+15+4+10
		int dur = main.getTotalDuration(ldt, routes);
		assertEquals(30, dur);
	}

	// helper method
	public Main setUpRoutes() {
		Main main = new Main();
		CustomerPrice price = main.logCustomerPriceUpdate("Wellington",
				"Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "air",
				"Air", 3.5, 4.5, 15, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost", "air",
				"Air", 2.5, 2.8, 8, 12, 12, 32, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Auckland", "USPS", "air",
				"Air", 2.5, 2.8, 8, 12, 12, 32, DayOfWeek.MONDAY, 15);
		return main;

	}
}
