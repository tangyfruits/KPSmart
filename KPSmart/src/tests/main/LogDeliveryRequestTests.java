package tests.main;

import static org.junit.Assert.*;

import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import main.CustomerPrice;
import main.DeliveryRequest;
import main.Main;
import main.Route;
import main.RouteDisplay;

import org.junit.Test;

/**
 * Will need to manually input customerPrices in console to run these.
 * 
 * @author Shelley
 *
 */
public class LogDeliveryRequestTests {

	// Find a route - only one existing
	@Test
	public void test1() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 1, 15, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Auckland", 15, 1);
		// should find one route
		assertNotNull(r.get(0));
		// should find only one
		assertEquals(1, r.size());
	}

	// find a route - only one - multi
	@Test
	public void test17() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logCustomerPriceUpdate("Auckland", "Paris", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Auckland", "Paris", "UPS", "Air", "Air",
				10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Auckland", 15, 1);
		// should find 2 routes
		assertNotNull(r.get(0));
		assertEquals(1, r.size());
	}

	// Find one of each priority - single routes
	@Test
	public void test10() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Standard", 15,
				14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);

		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost",
				"Standard", "Standard", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY,
				15);

		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Auckland", 15, 1);
		// should find 2 routes
		assertNotNull(r.get(0));
		assertNotNull(r.get(1));
		assertEquals(2, r.size());
	}

	// Find one of each priority - multi routes
	@Test
	public void test16() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Standard", 15,
				14);
		main.logCustomerPriceUpdate("Auckland", "Paris", "Air", 15, 14);
		main.logCustomerPriceUpdate("Auckland", "Paris", "Standard", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Auckland", "Paris", "UPS", "Air", "Air",
				10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost",
				"Standard", "Standard", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY,
				15);
		main.logTransportCostUpdate("Auckland", "Paris", "NZPost", "Standard",
				"Standard", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Paris", 15, 1);
		// should find 2 routes
		assertNotNull(r.get(0));
		assertNotNull(r.get(1));
		assertEquals(2, r.size());
	}

	// Check Priority
	@Test
	public void test11() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Standard", 15,
				14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost",
				"Standard", "Standard", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY,
				15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Auckland", 15, 1);
		// should find one of each domestic priority
		assertEquals("Domestic Standard", r.get(0).getPriority());
		assertEquals("Domestic Air", r.get(1).getPriority());
	}

	// Check Priority
	@Test
	public void test12() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Paris", "Air", 15, 14);
		main.logCustomerPriceUpdate("Wellington", "Paris", "Standard", 15, 14);
		main.logTransportCostUpdate("Wellington", "Paris", "UPS", "Air", "Air",
				10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Paris", "NZPost",
				"Standard", "Standard", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY,
				15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Paris", 15, 1);
		// should find one of each domestic priority
		assertEquals("International Standard", r.get(0).getPriority());
		assertEquals("International Air", r.get(1).getPriority());
	}

	// Check Priority for multi-leg routes
	@Test
	public void test14() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Auckland", "Paris", "Air", 10, 12);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Standard", 15,
				14);
		main.logTransportCostUpdate("Auckland", "Paris", "UPS", "Air", "Air",
				10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost",
				"Standard", "Standard", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY,
				15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Paris", 15, 1);
		// should find one only
		assertEquals(1, r.size());
		//
		assertEquals("International Standard", r.get(0).getPriority());
	}

	// Check Priority for multi-leg routes
	@Test
	public void test15() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Auckland", "Paris", "Air", 10, 12);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Auckland", "Paris", "UPS", "Air", "Air",
				10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost", "Air",
				"Air", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Paris", 15, 1);
		// should find one only
		assertEquals(1, r.size());
		//
		assertEquals("International Air", r.get(0).getPriority());
	}

	// Check Price - single routes
	@Test
	public void test13() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Paris", "Air", 10, 12);
		main.logCustomerPriceUpdate("Wellington", "Paris", "Standard", 15, 14);
		main.logTransportCostUpdate("Wellington", "Paris", "UPS", "Air", "Air",
				10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Paris", "NZPost",
				"Standard", "Standard", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY,
				15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Paris", 15, 1);
		// should be 15*15+14*1
		assertEquals(239, r.get(0).getPrice(), 0);
		// should be 10*15+12*1
		assertEquals(162, r.get(1).getPrice(), 0);
	}

	// Check price - multi routes
	@Test
	public void test18() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Standard", 10,
				12);
		main.logCustomerPriceUpdate("Auckland", "Paris", "Air", 13, 20);
		main.logCustomerPriceUpdate("Auckland", "Paris", "Standard", 16, 19);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Auckland", "Paris", "UPS", "Air", "Air",
				10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost",
				"Standard", "Standard", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY,
				15);
		main.logTransportCostUpdate("Auckland", "Paris", "NZPost", "Standard",
				"Standard", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Paris", 1, 1);
		// should be 10+12+16+19
		assertEquals(57, r.get(0).getPrice(), 0);
		// should be 15+14+13+20
		assertEquals(62, r.get(1).getPrice(), 0);
	}

	// check adding new Delivery Request
	@Test
	public void test19() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Auckland", "Paris", "Air", 10, 12);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Auckland", "Paris", "UPS", "Air", "Air",
				10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost", "Air",
				"Air", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Paris", 15, 1);
		// should have no requests so far
		assertEquals(0, main.getDeliveryRequests().size());
		DeliveryRequest req = main.logDeliveryRequest("Wellington", "Paris",
				15, 1, r.get(0));
		// should add a request
		assertEquals(1, main.getDeliveryRequests().size());
	}

	// check legs are created correctly
	@Test
	public void test20() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Auckland", "Paris", "Air", 10, 12);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Auckland", "Paris", "UPS", "Air", "Air",
				10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost", "Air",
				"Air", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Paris", 1, 1);
		DeliveryRequest req = main.logDeliveryRequest("Wellington", "Paris", 1,
				1, r.get(0));
		// check Legs
		assertEquals("Wellington", req.getLegs().get(0).getOrigin().toPretty());
		assertEquals("Auckland", req.getLegs().get(0).getDestination()
				.toPretty());
		assertEquals("Air", req.getLegs().get(0).getType());
		assertEquals("NZPost", req.getLegs().get(0).getCompany());
		assertEquals(29, req.getLegs().get(0).getCustomerPrice(), 0);
		assertEquals(4, req.getLegs().get(0).getFreightCost(), 0);
		assertEquals("Auckland", req.getLegs().get(1).getOrigin().toPretty());
		assertEquals("Paris", req.getLegs().get(1).getDestination().toPretty());
		assertEquals("Air", req.getLegs().get(1).getType());
		assertEquals("UPS", req.getLegs().get(1).getCompany());
		assertEquals(22, req.getLegs().get(1).getCustomerPrice(), 0);
		assertEquals(22, req.getLegs().get(1).getFreightCost(), 0);
	}

	// check details of delivery request once created
	// check legs are created correctly
	@Test
	public void test21() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Auckland", "Paris", "Air", 10, 12);
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Auckland", "Paris", "UPS", "Air", "Air",
				10, 12, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		main.logTransportCostUpdate("Wellington", "Auckland", "NZPost", "Air",
				"Air", 1, 3, 50, 50, 12, 18, DayOfWeek.MONDAY, 15);
		ArrayList<RouteDisplay> r = main.getPossibleRoutes("Wellington",
				"Paris", 1, 1);
		int dur = r.get(0).getTotalDuration(LocalDateTime.now());
		DeliveryRequest req = main.logDeliveryRequest("Wellington", "Paris", 1,
				1, r.get(0));
		assertNotNull(req.getLogTime());
		assertEquals("Wellington", req.getOrigin().toPretty());
		assertEquals("Paris", req.getDestination().toPretty());
		assertEquals(1, req.getWeight(),0);
		assertEquals(1, req.getVolume(),0);
		assertEquals("International Air", req.getPriority());
		assertEquals(dur, req.getDuration());
		assertEquals(2, req.getLegs().size());
		}

	// test Duration if logged before initial departure
	@Test
	public void test2() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 20, 30, 15, 24, DayOfWeek.WEDNESDAY, 12);
		ArrayList<Route> routes = new ArrayList<>();
		routes.add(main.getLocations().get(0).getRoutes().get(0));
		RouteDisplay route = new RouteDisplay("Air", routes, 2);
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 11, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// shoudl be 15+1 = 16(duration =15 + starts at 12 and is currently 11)
		int dur = route.getTotalDuration(ldt);
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
		RouteDisplay route = new RouteDisplay("Air", routes, 2);
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 13, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 15+23 = 38(duration =15 + 23 hours till next departure)
		int dur = route.getTotalDuration(ldt);
		assertEquals(38, dur);
	}

	// test duration if logged in same hour(ie after)
	@Test
	public void test9() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 20, 30, 15, 24, DayOfWeek.MONDAY, 12);
		ArrayList<Route> routes = new ArrayList<>();
		routes.add(main.getLocations().get(0).getRoutes().get(0));
		RouteDisplay route = new RouteDisplay("Air", routes, 2);
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 12, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 15+23 = 38(duration =15 + 23 hours till next departure)
		int dur = route.getTotalDuration(ldt);
		assertEquals(39, dur);
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
		RouteDisplay route = new RouteDisplay("Air", routes, 2);
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 13, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 15+23 +9 +10 = 57(duration =15 + 23 hours till next
		// departure+9hhours till second departure +10 duration)
		int dur = route.getTotalDuration(ldt);
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
		RouteDisplay route = new RouteDisplay("Air", routes, 2);
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 13, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 15+23 +9 +10 = 57(duration =15 + 23 hours till next
		// departure+9hhours till second departure +10 duration
		int dur = route.getTotalDuration(ldt);
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
		RouteDisplay route = new RouteDisplay("Air", routes, 2);
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 11, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 15+1+9+10 = 35(duration =15 + 1 hour till next departure+9
		// till second departure +10 duration)
		int dur = route.getTotalDuration(ldt);
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
		RouteDisplay route = new RouteDisplay("Air", routes, 2);
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 23, 11, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 1+15+4+10
		int dur = route.getTotalDuration(ldt);
		assertEquals(30, dur);
	}

	// test duration including weekend
	@Test
	public void test8() {
		Main main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air",
				"Air", 10, 12, 20, 30, 15, 24, DayOfWeek.MONDAY, 12);
		main.logTransportCostUpdate("Wellington", "Auckland", "PostHaste",
				"Air", "Air", 10, 12, 20, 30, 10, 120, DayOfWeek.FRIDAY, 12);
		ArrayList<Route> routes = new ArrayList<>();
		routes.add(main.getLocations().get(0).getRoutes().get(0));
		routes.add(main.getLocations().get(0).getRoutes().get(1));
		RouteDisplay route = new RouteDisplay("Air", routes, 2);
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 4, 26, 13, 12);
		LocalDateTime ldt = LocalDateTime.ofInstant(cal.toInstant(),
				ZoneId.systemDefault());
		// should be 1+15+4+10
		int dur = route.getTotalDuration(ldt);
		assertEquals(249, dur);
	}

}
