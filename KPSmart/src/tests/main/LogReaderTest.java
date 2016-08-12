package tests.main;

//import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import event.*;
import main.*;

public class LogReaderTest {

	// FIELDS
	private File file1 = new File("KPSmart/src/tests/reader_test_1.xml");
	private File file2 = new File("KPSmart/src/tests/reader_test_2.xml");
	private File badFile = new File("KPSmart/src/tests/reader_test_3.xml");

	@Test
	public void testParseFile1() throws Exception {
		Main m = new Main();
		LogReader reader = new LogReader(file1, m, false, true);

		reader.parseFile();

		// Print Results
		System.out.println("---------------- SYSTEM: ----------------");
		for (Location loc : m.getLocations()) {
			System.out.println(loc.toPretty());
			for (Route r : loc.getRoutes()) {
				System.out.print("\t");
				System.out.println(r.toPretty());
			}
			for (CustomerPrice p : loc.getPrices()) {
				System.out.print("\t");
				System.out.println(p);
			}
		}
		System.out.println("---------------- END ----------------");
	}

	@Test
	public void testParseFile2() throws Exception {
		Main m = new Main();
		LogReader reader = new LogReader(file2, m, false, true);
		reader.parseFile();
	}

	@Test
	public void testParseBadFile() throws Exception {
		Main m = new Main();
		LogReader reader = new LogReader(badFile, m, false, true);
		reader.parseFile();
	}
}
