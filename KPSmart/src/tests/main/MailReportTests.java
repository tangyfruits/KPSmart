package tests.main;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import main.Main;
import main.Tuple;

public class MailReportTests {

	@Test
	public void test1() {
		Main m = new Main();
		m.addToAmountOfMail("Wellington", "Auckland", 2.0, 3.0);
		HashMap<Tuple, ArrayList<Double>> amountOfMailDeliveryTimes = m.getAmountOfMail();
		Set<Tuple> set = amountOfMailDeliveryTimes.keySet();
		Tuple[] array = set.toArray(new Tuple[set.size()]);
		Tuple t = array[0];
		assertTrue(t.getOrigin().equals("Wellington") && t.getDestination().equals("Auckland")
				&& amountOfMailDeliveryTimes.get(t).get(0) == 2.0 && amountOfMailDeliveryTimes.get(t).get(1) == 3.0);
	}

	@Test
	public void test2() {
		Main m = new Main();
		m.addToAmountOfMail("Wellington", "Auckland", 2.0, 3.0);
		m.addToAmountOfMail("Wellington", "Auckland", 5.0, 6.0);
		HashMap<Tuple, ArrayList<Double>> amountOfMailDeliveryTimes = m.getAmountOfMail();
		Set<Tuple> set = amountOfMailDeliveryTimes.keySet();
		Tuple[] array = set.toArray(new Tuple[set.size()]);
		Tuple t = array[0];
		assertTrue(t.getOrigin().equals("Wellington") && t.getDestination().equals("Auckland")
				&& amountOfMailDeliveryTimes.get(t).get(0) == 7.0 && amountOfMailDeliveryTimes.get(t).get(1) == 9.0);
	}

	@Test
	public void test3() {
		Main m = new Main();
		m.addToAmountOfMail("Wellington", "Auckland", 2.0, 3.0);
		m.addToAmountOfMail("Wellington", "Whangarei", 5.0, 6.0);
		HashMap<Tuple, ArrayList<Double>> amountOfMailDeliveryTimes = m.getAmountOfMail();
		Set<Tuple> set = amountOfMailDeliveryTimes.keySet();
		Tuple[] array = set.toArray(new Tuple[set.size()]);
		Tuple t = array[1];
		assertTrue(t.getOrigin().equals("Wellington") && t.getDestination().equals("Whangarei")
				&& amountOfMailDeliveryTimes.get(t).get(0) == 5.0 && amountOfMailDeliveryTimes.get(t).get(1) == 6.0);
	}
}
