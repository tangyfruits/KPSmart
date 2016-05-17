package Tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
//import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.CustomerPrice;
import main.Location;
import main.LogWriter;
import main.Route;
import event.*;

public class LogWriterTest {
	
	// VARIABLES
	static String filename1 = "test_1.xml";
	static String filename3 = "test_logfile.xml";
	static File file1 = new File(filename1);
	static File file2 = new File("test_2.xml");
	static File file3 = new File(filename3);
	static LogWriter log;
	
	// SETUPS
	
	@BeforeClass
	public static void setupLogWriter() {
		try {
			log = new LogWriter(filename3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// */
	@BeforeClass
	public static void clearFiles() {
		if (file1.isFile()) {
			file1.delete();
		}
		
		if (file2.isFile()) {
			file2.delete();
		}
		
		if (file3.isFile()) {
			file3.delete();
		}
	}
	
	// TESTS
	@Test
	public void extraSHit() {
		FileWriter lalala;
		try {
			lalala = new FileWriter("test_sheeit.xml", true);
			lalala.write("MANG FUCK THIS SHIIIIT\n");
			lalala.write("SERILSUY WAT THE FUK\n");
			lalala.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// /*
	@Test
	public void testEnsureLogExists1() {
		LogWriter log;
		try {
			log = new LogWriter(filename1);
			log.ensureLogExists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(file1.isFile());
	}
	@Test
	public void testEnsureLogExists2() {		
		try {
			file2.createNewFile();
		} catch (IOException e) { 
			e.printStackTrace();
		}	
		
		LogWriter log;
		try {
			log = new LogWriter(file2);
			log.ensureLogExists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		assertTrue(file2.isFile());		
	}
	@Test
	public void testEnsureLogExists3() {
		log.ensureLogExists();
		assertTrue(file3.isFile());
	}
	
	// Writes
	@Test
	public void testWriteDelivery() {
		
		ArrayList<Leg> legs = new ArrayList<Leg>();
		legs.add(new Leg("new", "old"));
		
		MailEvent mail = new MailEvent("Wednesday", legs, 40.0, 20.0, "International Air", 37.2, 552.3, 30);
		try {
			log.writeDelivery(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWriteRoute1() {
		CostEvent cost = new CostEvent("Wellington", "Christchurch", "NZ Post", "Sea", "Local Standard", 
				4.00, 6.00, 400, 150, 6, 12,"Thursday");
		try {
			log.writeRoute(cost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testWriteRoute2() {
		CustomerPrice price = new CustomerPrice(new Location("place"), new Location("otherplace"), "Super Imp", 30.54, 20.0);
		Route route = new Route(new Location("Auckland"), new Location("Dunedin"), "UPS", "Land", 
				"Local Standard", 20.8, 56.2, 43,123,33, 2, "Saturday", price);
		try {
			log.writeRoute(route);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWriteCustomerPrice1() {
		PriceEvent price = new PriceEvent("StartPlace", "EndPlace", "Stand LOcal brah", 3.0, 5.0);
		try {
			log.writeCustomerPrice(price);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testWriteCustomerPrice2() {
		CustomerPrice price = new CustomerPrice(new Location("place"), new Location("otherplace"), "Super Imp", 30.54, 20.0);
		try {
			log.writeCustomerPrice(price);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWriteDiscontinue() {
		DiscontinueEvent disc = new DiscontinueEvent("NZ Post", "Wellington", "Christchurch", "Sea");
		try {
			log.writeDiscontinue(disc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// */
}
