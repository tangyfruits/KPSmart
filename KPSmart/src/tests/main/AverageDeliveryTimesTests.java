package tests.main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import main.Main;
import main.TuplePriority;

public class AverageDeliveryTimesTests {

	@Test
	public void test1() {
		Main m = new Main();
		m.addToAverageDeliveryTimes("Wellington", "Auckland", 5, "Air");

		m.getAmountOfMailDeliveryTimes();
		HashMap<TuplePriority, ArrayList<Integer>> amountOfMailDeliveryTimes = m.getAmountOfMailDeliveryTimes();
		Set<TuplePriority> set = amountOfMailDeliveryTimes.keySet();
		TuplePriority[] array = set.toArray(new TuplePriority[set.size()]);
		TuplePriority t = array[0];
		assertTrue(t.getOrigin().equals("Wellington") && t.getDestination().equals("Auckland")
				&& t.getPriority().equals("Air") && m.averageDeliveryTime("Wellington", "Auckland", "Air") == 5);
	}

	@Test
	public void test2() {
		Main m = new Main();
		m.addToAverageDeliveryTimes("Wellington", "Auckland", 5, "Air");
		m.addToAverageDeliveryTimes("Wellington", "Auckland", 10, "Air");
		m.getAmountOfMailDeliveryTimes();
		HashMap<TuplePriority, ArrayList<Integer>> amountOfMailDeliveryTimes = m.getAmountOfMailDeliveryTimes();
		Set<TuplePriority> set = amountOfMailDeliveryTimes.keySet();
		TuplePriority[] array = set.toArray(new TuplePriority[set.size()]);
		TuplePriority t = array[0];
		assertTrue(t.getOrigin().equals("Wellington") && t.getDestination().equals("Auckland")
				&& t.getPriority().equals("Air") && m.averageDeliveryTime("Wellington", "Auckland", "Air") == 7);

	}

	@Test
	public void test3() {
		Main m = new Main();
		m.addToAverageDeliveryTimes("Wellington", "Auckland", 5, "Air");
		m.addToAverageDeliveryTimes("Wellington", "Auckland", 10, "Air");
		m.addToAverageDeliveryTimes("Wellington", "Auckland", 15, "Air");
		m.getAmountOfMailDeliveryTimes();
		HashMap<TuplePriority, ArrayList<Integer>> amountOfMailDeliveryTimes = m.getAmountOfMailDeliveryTimes();
		Set<TuplePriority> set = amountOfMailDeliveryTimes.keySet();
		TuplePriority[] array = set.toArray(new TuplePriority[set.size()]);
		TuplePriority t = array[0];
		assertTrue(t.getOrigin().equals("Wellington") && t.getDestination().equals("Auckland")
				&& t.getPriority().equals("Air") && m.averageDeliveryTime("Wellington", "Auckland", "Air") == 10);

	}

	@Test
	public void test4() {
		Main m = new Main();
		m.addToAverageDeliveryTimes("Wellington", "Auckland", 5, "Air");
		m.addToAverageDeliveryTimes("Wellington", "Hamilton", 35, "Air");
		m.addToAverageDeliveryTimes("Wellington", "Hamilton", 15, "Air");
		m.getAmountOfMailDeliveryTimes();
		HashMap<TuplePriority, ArrayList<Integer>> amountOfMailDeliveryTimes = m.getAmountOfMailDeliveryTimes();
		Set<TuplePriority> set = amountOfMailDeliveryTimes.keySet();
		TuplePriority[] array = set.toArray(new TuplePriority[set.size()]);
		TuplePriority t = array[1];
		assertTrue(t.getOrigin().equals("Wellington") && t.getDestination().equals("Hamilton")
				&& t.getPriority().equals("Air") && m.averageDeliveryTime("Wellington", "Hamilton", "Air") == 25);

	}

	@Test
	public void test5() {
		Main m = new Main();
		m.addToAverageDeliveryTimes("Wellington", "Auckland", 5, "Air");
		m.addToAverageDeliveryTimes("Wellington", "Hamilton", 35, "Air");
		m.addToAverageDeliveryTimes("Wellington", "Hamilton", 15, "Air");
		m.getAmountOfMailDeliveryTimes();
		HashMap<TuplePriority, ArrayList<Integer>> amountOfMailDeliveryTimes = m.getAmountOfMailDeliveryTimes();
		Set<TuplePriority> set = amountOfMailDeliveryTimes.keySet();
		TuplePriority[] array = set.toArray(new TuplePriority[set.size()]);
		TuplePriority t = array[1];
		assertTrue(t.getOrigin().equals("Wellington") && t.getDestination().equals("Hamilton")
				&& t.getPriority().equals("Air") && m.averageDeliveryTime("Wellington", "Auckland", "Air") == 5);

	}
}
