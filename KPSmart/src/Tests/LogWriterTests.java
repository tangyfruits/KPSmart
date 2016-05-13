package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.LogWriter;
import event.*;

public class LogWriterTests {
	
	LogWriter writer;
	
	@Before
	public void setupLogWriter() {
		writer = new LogWriter("logfile.xml");
	}
	
	
	@Test
	public void testLogFileExists() {
		fail("Not yet implemented");
	}

	@Test
	public void testWriteDelivery() {
		fail("Not yet implemented");
	}

	@Test
	public void testWriteRoute() {
		fail("Not yet implemented");
	}

	@Test
	public void testWriteCustomerPrice() {
		fail("Not yet implemented");
	}

	@Test
	public void testWriteDiscontinue() {
		DiscontinueEvent disc = new DiscontinueEvent("NZ Post", "Wellington", "Christchurch", "Sea");
		writer.writeDiscontinue(disc);
	}

}
