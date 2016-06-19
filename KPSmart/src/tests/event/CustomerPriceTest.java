package tests.event;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import event.CustomerPrice;
import main.Location;

public class CustomerPriceTest {
	
	CustomerPrice price;
	
	// Constructor
	@Before
	public void testCustomerPrice() {
		Location from = new Location("Somewhere over the Mediterrainian Sea");
		Location to = new Location("French Guinea");
		
		price = new CustomerPrice(from, to, "Hella urgent", 333.2, .20, LocalDateTime.now(), "User");
	}
	
	// Getters
	@Test
	public void testGetOrigin() {
		assertEquals("Somewhere over the Mediterrainian Sea", price.getOrigin().getName());
	}
	@Test
	public void testGetDestination() {
		assertEquals("French Guinea", price.getDestination().getName());
	}
	@Test
	public void testGetPriority() {
		assertEquals("Hella urgent", price.getPriority());
	}
	@Test
	public void testGetWeightCost() {
		assertEquals(333.2, price.getWeightCost(), 0.00001);
	}
	@Test
	public void testGetVolumeCost() {
		assertEquals(.20, price.getVolumeCost(), 0.001);
	}
	public void testGetEventType() {
		assertEquals("price", price.getEventType());
	}
	
	// Setters
	@Test
	public void testSetWeightCost() {
		price.setWeightCost(235235.234);
		assertEquals(235235.234, price.getWeightCost(), 0.00001);
	}
	@Test
	public void testSetVolumeCost() {
		price.setVolumeCost(383838.234234);
		assertEquals(383838.234234, price.getVolumeCost(), 0.000001);
	}

	// toString
	@Test
	public void testToString() {
		String thing = "CustomerPrice [origin=" + price.getOrigin() + 
					   ", destination=" + price.getDestination() + 
					   ", priority=" + price.getPriority() + 
					   ", weightCost=" + price.getWeightCost() + 
					   ", volumeCost=" + price.getVolumeCost() + "]";
		//System.out.println(thing);
		//System.out.println(price.toString());
		assertEquals(thing, price.toString());
	}
}
