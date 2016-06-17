package tests.main;

//import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import main.LogReader;
import main.Main;

public class LogReaderTest {
	
	// FIELDS
	private static final Main main = new Main();
	private static File logfile = new File("KPSmart/src/tests/logfile.xml");
	private static LogReader reader;
	
	// CONSTRUCTOR
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reader = new LogReader(logfile, main);
	}
	
	
	@Test
	public void testParseFile() throws Exception {
		reader.parseFile();
	}

}
