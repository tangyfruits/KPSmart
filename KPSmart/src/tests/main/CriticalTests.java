package tests.main;

import static org.junit.Assert.*;
import main.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import event.DeliveryRequest;
import event.Leg;
import event.Route;
import main.Main;

public class CriticalTests {

	private Main main;
	private String o, d, p;
	private double w, v;
	private DeliveryRequest req;
	private ArrayList<Leg> legs;

	public void setUp(double w, double v) {
		main = new Main();
		o = "Wellington";
		d = "Auckland";
		p = "Air";
		this.w = w;
		this.v = v;
		main.logCustomerPriceUpdate(o, d, p, 5, 3, false, LocalDateTime.now(), "User");
		main.logTransportCostUpdate(o, d, "UPS", p, 3, 5, 20, 30, 15, 24, DayOfWeek.MONDAY, 12, false,
				LocalDateTime.now(), "User");
		ArrayList<RouteDisplay> route = main.getPossibleRoutes(o, d, w, v);
		legs = new ArrayList<>();
		for (Route r : route.get(0).getRoute()) {
			double freightCost = w * r.getWeightCost() + v * r.getVolumeCost();
			double customerPrice = w * r.getPrice().getWeightCost() + v * r.getPrice().getVolumeCost();
			legs.add(new Leg(r.getOrigin(), r.getDestination(), r.getType(), r.getCompany(), freightCost,
					customerPrice));
		}
	}

	// check route with one leg gets added - critical route
	@Test
	public void test1() {
		setUp(2, 4);
		main.addToCriticalRoutes(o, d, p, legs);
		assertEquals(1, main.getCriticalRoutes().size());
	}

	// check amounts are correct
	@Test
	public void test2() {
		setUp(2, 4);
		main.addToCriticalRoutes(o, d, p, legs);
		for (TuplePriority t : main.getCriticalRoutes().keySet()) {
			assertEquals(26, main.getCriticalRoutes().get(t).get(0), 0); // cost
			assertEquals(22, main.getCriticalRoutes().get(t).get(1), 0); // price
			assertEquals(4, main.getCriticalRoutes().get(t).get(2), 0); // diff
		}
	}

	// check non-critical route doesn't get added
	@Test
	public void test3() {
		setUp(4, 2);
		main.addToCriticalRoutes(o, d, p, legs);
		assertEquals(0, main.getCriticalRoutes().size());
	}

	// check amounts are correct with multiple requests
	@Test
	public void test4() {
		setUp(2, 4);

		main.addToCriticalRoutes(o, d, p, legs);
		assertEquals(1, main.getCriticalRoutes().size());
		// add more data
		w = 3;
		v = 5;
		ArrayList<RouteDisplay> route = main.getPossibleRoutes(o, d, w, v);
		legs = new ArrayList<>();
		for (Route r : route.get(0).getRoute()) {
			double freightCost = w * r.getWeightCost() + v * r.getVolumeCost();
			double customerPrice = w * r.getPrice().getWeightCost() + v * r.getPrice().getVolumeCost();
			legs.add(new Leg(r.getOrigin(), r.getDestination(), r.getType(), r.getCompany(), freightCost,
					customerPrice));
		}
		main.addToCriticalRoutes(o, d, p, legs);
		for (TuplePriority t : main.getCriticalRoutes().keySet()) {
			assertEquals(30, main.getCriticalRoutes().get(t).get(0), 0); // cost
			assertEquals(26, main.getCriticalRoutes().get(t).get(1), 0); // price
			assertEquals(4, main.getCriticalRoutes().get(t).get(2), 0); // diff
		}
	}

	// check multi-leg routes
	@Test
	public void test5() {
		main = new Main();
		o = "Wellington";
		String s = "Hamilton";
		d = "Auckland";
		p = "Air";
		w = 2;
		v = 4;
		main.logCustomerPriceUpdate(o, s, p, 5, 3, false, LocalDateTime.now(), "User");
		main.logCustomerPriceUpdate(s, d, p, 5, 3, false, LocalDateTime.now(), "User");

		main.logTransportCostUpdate(o, s, "UPS", p, 3, 5, 20, 30, 15, 24, DayOfWeek.MONDAY, 12, false,
				LocalDateTime.now(), "User");
		main.logTransportCostUpdate(s, d, "UPS", p, 3, 5, 20, 30, 15, 24, DayOfWeek.MONDAY, 12, false,
				LocalDateTime.now(), "User");
		ArrayList<RouteDisplay> route = main.getPossibleRoutes(o, d, w, v);
		assertEquals(2, route.get(0).getRoute().size());
		legs = new ArrayList<>();
		for (Route r : route.get(0).getRoute()) {
			double freightCost = w * r.getWeightCost() + v * r.getVolumeCost();
			double customerPrice = w * r.getPrice().getWeightCost() + v * r.getPrice().getVolumeCost();
			legs.add(new Leg(r.getOrigin(), r.getDestination(), r.getType(), r.getCompany(), freightCost,
					customerPrice));
		}

		main.addToCriticalRoutes(o, d, p, legs);
		assertEquals(1, main.getCriticalRoutes().size());
		for (TuplePriority t : main.getCriticalRoutes().keySet()) {
			assertEquals(52, main.getCriticalRoutes().get(t).get(0), 0); // cost
			assertEquals(44, main.getCriticalRoutes().get(t).get(1), 0); // price
			assertEquals(8, main.getCriticalRoutes().get(t).get(2), 0); // diff
		}
	}

}
