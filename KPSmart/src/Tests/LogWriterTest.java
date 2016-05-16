package Tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.CustomerPrice;
import main.Location;
import main.LogWriter;
import main.Route;
import event.*;

public class LogWriterTest {
	
	LogWriter writer;
	
	@Test
	public void testEnsureLogExists1() {
		String fileName = "thing.xml";
		File file = new File(fileName);
		assertFalse(file.isFile());
		LogWriter log = new LogWriter(fileName);
		log.ensureLogExists();
		
		assertTrue(file.isFile());
	}
	
	@Test
	public void testEnsureLogExists2() {
		String fileName = "thing.xml";
		File file = new File(fileName);
		
		try {
			file.createNewFile();
			assertTrue(file.isFile());
			LogWriter log = new LogWriter(fileName);
			log.ensureLogExists();
			
			assertTrue(file.isFile());
			
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	
	@Before
	public void setupLogWriter() {
		writer = new LogWriter("logfile.xml");
	}
	
	
	@Test
	public void testWriteDelivery() {
		
		ArrayList<Leg> legs = new ArrayList<Leg>();
		legs.add(new Leg("new", "old"));
		
		MailEvent mail = new MailEvent("Wednesday", legs, 40.0, 20.0, "International Air", 37.2, 552.3, 30);
		writer.writeDelivery(mail);
	}

	@Test
	public void testWriteRoute1() {
		CostEvent cost = new CostEvent("Wellington", "Christchurch", "NZ Post", "Sea", "Local Standard", 
				4.00, 6.00, 400, 150, 6, 12,"Thursday");
		writer.writeRoute(cost);
	}
	
	@Test
	public void testWriteRoute2() {
		CustomerPrice price = new CustomerPrice(new Location("place"), new Location("otherplace"), "Super Imp", 30.54, 20.0);
		Route route = new Route(new Location("Auckland"), new Location("Dunedin"), "UPS", "Land", 
				"Local Standard", 20.8, 56.2, 43,123,33, 2, "Saturday", price);
		writer.writeRoute(route);
	}

	@Test
	public void testWriteCustomerPrice1() {
		PriceEvent price = new PriceEvent("StartPlace", "EndPlace", "Stand LOcal brah", 3.0, 5.0);
		writer.writeCustomerPrice(price);
	}
	
	@Test
	public void testWriteCustomerPrice2() {
		CustomerPrice price = new CustomerPrice(new Location("place"), new Location("otherplace"), "Super Imp", 30.54, 20.0);
		writer.writeCustomerPrice(price);
	}

	@Test
	public void testWriteDiscontinue() {
		DiscontinueEvent disc = new DiscontinueEvent("NZ Post", "Wellington", "Christchurch", "Sea");
		writer.writeDiscontinue(disc);
	}
}
