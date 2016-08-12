package tests.event;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import event.CustomerPrice;
import event.Route;
import main.Location;

public class RouteTest {

	Location from;
	Location to;
	Route cost;
	CustomerPrice price;

	// Constructor
	@Before
	public void testRoute() {
		from = new Location("Christchurch");
		to = new Location("Auckland");
		price = new CustomerPrice(from, to, "More Priority", 3000.4, 4000.0, LocalDateTime.now(), "User");

		cost = new Route(from, to, "Company", "Type", "Priority", 4.0, 8.0, 400, 800, 100, 12, DayOfWeek.MONDAY, 6,
				price, LocalDateTime.now(), "User");
	}

	// Methods
	@Test
	public void testEquals() {
		Route r = new Route(from, to, "Company", "Type", "Priorityblalalal", 4.0, 8.0, 400, 800, 100, 12,
				DayOfWeek.MONDAY, 6, new CustomerPrice(from, to, "lolol", 0.0, 1.0, LocalDateTime.now(), "User"),
				LocalDateTime.now(), "User");
		assertTrue(cost.equals(r));
	}

	@Test
	public void testEquals2() {
		Route r = new Route(from, to, "Company2", "Type", "Priority", 4.0, 8.0, 400, 800, 100, 12, DayOfWeek.WEDNESDAY,
				6, new CustomerPrice(from, to, "lolol", 0.0, 1.0, LocalDateTime.now(), "User"), LocalDateTime.now(),
				"User");

		assertFalse(cost.equals(r));
	}

	@Test
	public void testEquals3() {
		Location from = new Location("John");
		Location to = new Location("Hamish");

		Route r = new Route(from, to, "Company", "Type", "Priority", 4.0, 8.0, 400, 800, 100, 12, DayOfWeek.MONDAY, 6,
				new CustomerPrice(from, to, "lolol", 0.0, 1.0, LocalDateTime.now(), "User"), LocalDateTime.now(),
				"User");

		assertFalse(cost.equals(r));
	}

	@Test
	public void testGetCost() {
		assertEquals(643.6, cost.getCost(40.3, 60.3), 0.0000001);
	}

	// Getters
	@Test
	public void testGetOrigin() {
		assertEquals("Christchurch", cost.getOrigin().getName());
	}

	@Test
	public void testGetDestination() {
		assertEquals("Auckland", cost.getDestination().getName());
	}

	@Test
	public void testGetCompany() {
		assertEquals("Company", cost.getCompany());
	}

	@Test
	public void testGetType() {
		assertEquals("Type", cost.getType());
	}

	@Test
	public void testGetPriority() {
		assertEquals("Priority", cost.getPriority());
	}

	@Test
	public void testGetWeightCost() {
		assertEquals(4.0, cost.getWeightCost(), 0.001);
	}

	@Test
	public void testGetVolumeCost() {
		assertEquals(8.0, cost.getVolumeCost(), 0.001);
	}

	@Test
	public void testGetMaxWeight() {
		assertEquals(400, cost.getMaxWeight());
	}

	@Test
	public void testGetMaxVolume() {
		assertEquals(800, cost.getMaxVolume());
	}

	@Test
	public void testGetDuration() {
		assertEquals(100, cost.getDuration());
	}

	@Test
	public void testGetFrequency() {
		assertEquals(12, cost.getFrequency());
	}

	@Test
	public void testGetDay() {
		assertEquals("Monday", cost.getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
	}

	@Test
	public void testGetStartTime() {
		assertEquals(cost.getStartTime(), 6);
	}

	@Test
	public void testGetPrice() {

		assertEquals(price, cost.getPrice());
	}

	@Test
	public void testGetEventType() {
		assertEquals("cost", cost.getEventType());
	}

	// Setters
	@Test
	public void testSetPriority() {
		cost.setPriority("pirsdfsd");
		assertEquals("pirsdfsd", cost.getPriority());
	}

	@Test
	public void testSetWeightCost() {
		cost.setWeightCost(234.00);
		assertEquals(234.00, cost.getWeightCost(), 0.001);
	}

	@Test
	public void testSetVolumeCost() {
		cost.setVolumeCost(2584.0);
		assertEquals(2584.0, cost.getVolumeCost(), 0.001);
	}

	@Test
	public void testSetMaxWeight() {
		cost.setMaxWeight(8634);
		assertEquals(8634, cost.getMaxWeight());
	}

	@Test
	public void testSetMaxVolume() {
		cost.setMaxVolume(1);
		assertEquals(1, cost.getMaxVolume());
	}

	@Test
	public void testSetDuration() {
		cost.setDuration(954);
		assertEquals(954, cost.getDuration());
	}

	@Test
	public void testSetFrequency() {
		cost.setFrequency(95493);
		assertEquals(95493, cost.getFrequency());
	}

	@Test
	public void testSetDay() {
		cost.setDay(DayOfWeek.WEDNESDAY);
		assertEquals("Wednesday", cost.getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
	}
}
