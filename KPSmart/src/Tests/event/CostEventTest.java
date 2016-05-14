package Tests.event;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import event.CostEvent;

public class CostEventTest {

	CostEvent cost;

	@Before
	public void testCostEvent() {
		cost = new CostEvent("Start", "End", "Company", "Type", "Priority", 4.0, 8.0, 400, 800, 100, 12, "Monday");
	}
	
	@Test
	public void testGetOrigin() {
		assertEquals("Start", cost.getOrigin());
	}
	@Test
	public void testGetDestination() {
		assertEquals("End", cost.getDestination());
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
		assertEquals("Monday", cost.getDay());
	}

	@Test
	public void testSetOrigin() {
		cost.setOrigin("thing");
		assertEquals("thing", cost.getOrigin());
	}
	@Test
	public void testSetDestination() {
		cost.setDestination("thisthingtho");
		assertEquals("thisthingtho", cost.getDestination());
	}
	@Test
	public void testSetCompany() {
		cost.setCompany("PepsiCola");
		assertEquals("PepsiCola", cost.getCompany());
	}
	@Test
	public void testSetType() {
		cost.setType("AnotherThing");
		assertEquals("AnotherThing", cost.getType());
	}
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
		cost.setDay("AnotherDay");
		assertEquals("AnotherDay", cost.getDay());
	}

}
