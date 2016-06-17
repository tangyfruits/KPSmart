package tests.main;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.Location;
import main.LogWriter;
import event.*;

public class LogWriterTest {
	
	// Console Logging
	private static boolean LOGS = true;
	
	// VARIABLES
	static File file1 = new File("KPSmart/src/tests/writer_test_1.xml");
	static File file2 = new File("KPSmart/src/tests/writer_test_2.xml");
	static File file3 = new File("KPSmart/src/tests/writer_test_3.xml");
	static LogWriter log;
	
	// HELPERS
	@BeforeClass
	public static void setup() throws Exception {
		if (file1.isFile()) {
			file1.delete();
		}
		
		if (file2.isFile()) {
			file2.delete();
		}
		
		if (file3.isFile()) {
			file3.delete();
		}
		
		log = new LogWriter(file3);
	}
	@AfterClass
	public static void tearDown() {
		boolean veto = true;
		boolean kill3 = false;
		
		if (file1.isFile() && !veto) {
			file1.delete();
		}
		
		if (file2.isFile() && !veto) {
			file2.delete();
		}
		if (file3.isFile() && kill3 && !veto) {
			file3.delete();
		}
	}
	
	// TESTS
	// Log File Existence Checkers
	@Test
	public void testEnsureLogExists1() throws Exception {
		LogWriter log;
		log = new LogWriter(file1);
		log.ensureLogExists();
		assertTrue(file1.isFile());
	}
	@Test
	public void testEnsureLogExists2() throws Exception {		
		file2.createNewFile();

		LogWriter log;
		log = new LogWriter(file2);
		log.ensureLogExists();
		assertTrue(file2.isFile());		
	}
	@Test
	public void testEnsureLogExists3() throws Exception {
		log.ensureLogExists();
		assertTrue(file3.isFile());
	}
	
	// Writers to file3
	@Test
	public void testWriteDeliveryRequest() throws Exception {
		Location from = new Location("Fresh");
		Location to = new Location("Clean");
		LocalDateTime time = LocalDateTime.of(2016, 1, 1, 0, 0);
		ArrayList<Leg> legs = new ArrayList<Leg>();
		
		legs.add(new Leg(from, to, "Seas", "POst dot co", 40.0, 28.4));
		
		DeliveryRequest mail = new DeliveryRequest(time, from, to, 40.0, 20.0, "International Air", 30, legs);
		log.writeDeliveryRequest(mail);
	}
	@Test
	public void testWriteRoute1() throws Exception {
		Location from = new Location("Wellington");
		Location to =  new Location("Christchurch");
		CustomerPrice price = new CustomerPrice(from, to, "Low ass Priority", 20.0, 10.0);
		
		Route cost = new Route(from, to, "NZ Post", "Sea", "Local Standard", 
							   4.00, 6.00, 400, 150,
							   60, 12, DayOfWeek.THURSDAY, 12, price);
		log.writeRoute(cost);
	}
	@Test
	public void testWriteRoute2() throws Exception {
		CustomerPrice price = new CustomerPrice(new Location("place"), new Location("otherplace"), "Super Imp", 30.54, 20.0);
		Route route = new Route(new Location("Auckland"), new Location("Dunedin"), "UPS", "Land", 
				"Local Standard", 20.8, 56.2, 43,123,33, 2, DayOfWeek.TUESDAY, 20, price);
		log.writeRoute(route);
	}
	@Test
	public void testWriteCustomerPrice1() throws Exception {
		CustomerPrice price = new CustomerPrice(new Location("StartPlace"), new Location("EndPlace"), "Stand LOcal brah", 3.0, 5.0);
		log.writeCustomerPrice(price);
	}
	@Test
	public void testWriteCustomerPrice2() throws Exception {
		CustomerPrice price = new CustomerPrice(new Location("place"), new Location("otherplace"), "Super Imp", 30.54, 20.0);
		log.writeCustomerPrice(price);
	}
	@Test
	public void testWriteDiscontinue() throws Exception {
		DiscontinueRoute disc = new DiscontinueRoute(new Location("Christchurch"), new Location("Wellington"), "NZ Post", "Sea");
		log.writeDiscontinue(disc);
	}

	

}
