package tests.main;

import static org.junit.Assert.*;

import java.time.DayOfWeek;

import org.junit.Test;

import main.Main;

public class CriticalTests {

	private Main main;
	private String o,d,p;
	private double w,v;

	public void setUp(){
		main = new Main();
		main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 15, 14, false);
		main.logTransportCostUpdate("Wellington", "Auckland", "UPS", 
				"Air", 10, 12, 20, 30, 15, 24, DayOfWeek.MONDAY, 12, false);
		o = "Wellington";
		d = "Auckland";
		p = "Air";
		w = 2.5;
		v = 1.5;
		
	}
	
	@Test
	public void test1() {
		setUp();
		main.addToCriticalRoutes(o, d, p, w, v);
		System.out.println(main.getCriticalRoutes().remove(0));
	}
	
	@Test
	public void test2() {
		setUp();
		main.addToCriticalRoutes(o, d, p, w, v);
		System.out.println(main.getCriticalRoutes().remove(0));
	}
	
	@Test
	public void test3() {
		setUp();
		main.addToCriticalRoutes(o, d, p, w, v);
		System.out.println(main.getCriticalRoutes().remove(0));
	}
	
	@Test
	public void test4() {
		setUp();
		main.addToCriticalRoutes(o, d, p, w, v);
		System.out.println(main.getCriticalRoutes().remove(0));
	}
	
	@Test
	public void test5() {
		setUp();
		main.addToCriticalRoutes(o, d, p, w, v);
		System.out.println(main.getCriticalRoutes().remove(0));
	}

}
