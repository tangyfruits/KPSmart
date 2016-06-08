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
	public void test() {
		setUp();
		main.addToCriticalRoutes(o, d, p, w, v);
		System.out.println(main.criticalRoutes.remove(0));
	}

}
