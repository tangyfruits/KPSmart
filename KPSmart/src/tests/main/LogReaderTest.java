package tests.main;

//import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import event.*;
import main.*;

public class LogReaderTest {
	
	// FIELDS
	private static final Main main = new Main();
	private static File logfile = new File("KPSmart/src/tests/reader_test_1.xml");
	private static LogReader reader;
	
	// CONSTRUCTOR
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reader = new LogReader(logfile, main, false, true);
	}
	
	
	@Test
	public void testParseFile() throws Exception {
		reader.parseFile();
		System.out.println("---- SYSTEM: ----");
		for (Location loc : main.getLocations()) {
			System.out.println(loc);
			for (Route r : loc.getRoutes()) {
				System.out.print("\t");
				System.out.println(r);
			}
			for (CustomerPrice p : loc.getPrices()) {
				System.out.print("\t");
				System.out.println(p);
			}
		}
	}
}
