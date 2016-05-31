package tests.main;

import java.io.File;
import java.io.FileWriter;
import java.time.DayOfWeek;
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
	static String filename1 = "test_1.xml";
	static String filename3 = "logfile.xml";
	static File file1 = new File(filename1);
	static File file2 = new File("test_2.xml");
	static File file3 = new File(filename3);
	static LogWriter log;
	
	// HELPER
	@BeforeClass
	@AfterClass
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
		
		log = new LogWriter(filename3);
	}
	
	// TESTS
	@Test
	public void eXtRaSHiT() {
		FileWriter lalala;
		try {
			lalala = new FileWriter("test_sheeit.xml", true);
			lalala.write("\nMANG FUCK THIS SHIIIIT");
			lalala.write("SERILSUY WAT THE FUK\n");
			lalala.write("SUC-FUCKIN-CESS BOOOOOY! UGH!\n");
			lalala.write("https://www.youtube.com/watch?v=66EBzbzzObY\n");
			lalala.write("wate that doesn't work anymore\n");
			lalala.write("https://soundcloud.com/chancetherapper/smoke-break-feat-future\n");
			lalala.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// Things that check the log file actually exists
	@Test
	public void testEnsureLogExists1() throws Exception {
		LogWriter log;
		log = new LogWriter(filename1);
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
	
	// Writers
	@Test
	public void testWriteDelivery() throws Exception {
		if (LOGS) {System.out.println("\n----------mail");}
		ArrayList<LegEvent> legs = new ArrayList<LegEvent>();
		legs.add(new LegEvent("new", "old", "Seas", "POst dot co", 40.0, 28.4));
		MailEvent mail = new MailEvent("Wednesday", legs, 40.0, 20.0, "International Air", 37.2, 552.3, 30);
		log.writeDelivery(mail);
	}
	@Test
	public void testWriteRoute1() throws Exception {
		if (LOGS) {System.out.println("\n----------cost1");}
		CostEvent cost = new CostEvent("Wellington", "Christchurch", "NZ Post", "Sea", "Local Standard", 
				4.00, 6.00, 400, 150, 6, 12,"Thursday");
		log.writeRoute(cost);
	}
	@Test
	public void testWriteRoute2() throws Exception {
		if (LOGS) {System.out.println("\n----------cost2");}
		CustomerPrice price = new CustomerPrice(new Location("place"), new Location("otherplace"), "Super Imp", 30.54, 20.0);
		Route route = new Route(new Location("Auckland"), new Location("Dunedin"), "UPS", "Land", 
				"Local Standard", 20.8, 56.2, 43,123,33, 2, DayOfWeek.TUESDAY, 20, price);
		log.writeRoute(route);
	}
	@Test
	public void testWriteCustomerPrice1() throws Exception {
		if (LOGS) {System.out.println("\n----------price1");}
		PriceEvent price = new PriceEvent("StartPlace", "EndPlace", "Stand LOcal brah", 3.0, 5.0);
		log.writeCustomerPrice(price);
	}
	@Test
	public void testWriteCustomerPrice2() throws Exception {
		if (LOGS) {System.out.println("\n----------price2");}
		CustomerPrice price = new CustomerPrice(new Location("place"), new Location("otherplace"), "Super Imp", 30.54, 20.0);
		log.writeCustomerPrice(price);
	}
	@Test
	public void testWriteDiscontinue() throws Exception {
		if (LOGS) {System.out.println("\n----------disc.");}
		DiscontinueRoute disc = new DiscontinueRoute("NZ Post", "Wellington", "Christchurch", "Sea");
		log.writeDiscontinue(disc);
	}
}
