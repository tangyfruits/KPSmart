package tests.main;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Main;

public class CriticalTests {

	private Main main;
	private String o,d,p;
	private double w,v;

	public void setUp(){
		main = new Main();
		o = "From";
		d = "To";
		p = "totes important";
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
