package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.LogWriter;
import main.Route;
import event.*;

public class LogWriterTests {
	
	LogWriter writer;
	
	@Before
	public void setupLogWriter() {
		writer = new LogWriter("logfile.xml");
	}
	

	@Test
	public void testWriteDelivery() {
		MailEvent mail = new MailEvent();
		writer.writeDelivery(mail);
	}

	@Test
	public void testWriteRoute1() {
		CostEvent cost = new CostEvent("Wellington", "Christchurch", "NZ Post", "Sea", "Local Standard", 
				4.00, 6.00, 400, 150, 6, 12,"Thursday");
		writer.writeRoute(cost);
	}
	
	public void testWriteRoute2() {
		Route route = new Route();
		writer.writeRoute(route);
	}

	@Test
	public void testWriteCustomerPrice() {
		PriceEvent price = new PriceEvent("StartPlace", "EndPlace", "Stand LOcal brah", 3.0, 5.0);
		writer.writeCustomerPrice(price);
	}

	@Test
	public void testWriteDiscontinue() {
		DiscontinueEvent disc = new DiscontinueEvent("NZ Post", "Wellington", "Christchurch", "Sea");
		writer.writeDiscontinue(disc);
	}
}
