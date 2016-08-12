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

		DeliveryRequest mail = new DeliveryRequest(time, from, to, 40.0, 20.0, "International Air", 30, legs, "User");
		log.writeDeliveryRequest(mail);
	}

	@Test
	public void testWriteRoute1() throws Exception {
		Location from = new Location("Wellington");
		Location to = new Location("Christchurch");
		CustomerPrice price = new CustomerPrice(from, to, "Low ass Priority", 20.0, 10.0, LocalDateTime.now(), "User");

		Route cost = new Route(from, to, "NZ Post", "Sea", "Local Standard", 4.00, 6.00, 400, 150, 60, 12,
				DayOfWeek.THURSDAY, 12, price, LocalDateTime.now(), "User");
		log.writeRoute(cost);
	}

	@Test
	public void testWriteRoute2() throws Exception {
		CustomerPrice price = new CustomerPrice(new Location("place"), new Location("otherplace"), "Super Imp", 30.54,
				20.0, LocalDateTime.now(), "User");
		Route route = new Route(new Location("Auckland"), new Location("Dunedin"), "UPS", "Land", "Local Standard",
				20.8, 56.2, 43, 123, 33, 2, DayOfWeek.TUESDAY, 20, price, LocalDateTime.now(), "User");
		log.writeRoute(route);
	}

	@Test
	public void testWriteCustomerPrice1() throws Exception {
		CustomerPrice price = new CustomerPrice(new Location("StartPlace"), new Location("EndPlace"),
				"Stand LOcal brah", 3.0, 5.0, LocalDateTime.now(), "User");
		log.writeCustomerPrice(price);
	}

	@Test
	public void testWriteCustomerPrice2() throws Exception {
		CustomerPrice price = new CustomerPrice(new Location("place"), new Location("otherplace"), "Super Imp", 30.54,
				20.0, LocalDateTime.now(), "User");
		log.writeCustomerPrice(price);
	}

	@Test
	public void testWriteDiscontinue() throws Exception {
		DiscontinueRoute disc = new DiscontinueRoute(new Location("Christchurch"), new Location("Wellington"),
				"NZ Post", "Sea", LocalDateTime.now(), "User");
		log.writeDiscontinue(disc);
	}

	@Test
	public void writeFile1() {
		log = new LogWriter(file1, true);

		Location dud = new Location("Dunedin");
		Location chc = new Location("Christchurch");
		Location wlg = new Location("Wellington");
		Location hlz = new Location("Hamilton");
		Location akl = new Location("Auckland");
		Location la = new Location("Los Angeles");
		Location bud = new Location("Budapest");

		CustomerPrice c1 = new CustomerPrice(dud, chc, "Standard", 3, 34, LocalDateTime.now(), "shelley");
		CustomerPrice c2 = new CustomerPrice(chc, dud, "Air", 5, 11, LocalDateTime.now(), "peter");
		CustomerPrice c3 = new CustomerPrice(chc, wlg, "Air", 235, 24, LocalDateTime.now(), "donald");
		CustomerPrice c4 = new CustomerPrice(wlg, chc, "Standard", 22, 232, LocalDateTime.now(), "pri");
		CustomerPrice c5 = new CustomerPrice(wlg, hlz, "Standard", 31, 123, LocalDateTime.now(), "peter");
		CustomerPrice c6 = new CustomerPrice(chc, akl, "Standard", 11, 22, LocalDateTime.now(), "shelley");
		CustomerPrice c7 = new CustomerPrice(hlz, akl, "Standard", 4, 4, LocalDateTime.now(), "donald");
		CustomerPrice c8 = new CustomerPrice(akl, wlg, "Standard", 25, 13, LocalDateTime.now(), "kaszandra");
		CustomerPrice c9 = new CustomerPrice(akl, dud, "Air", 37, 21, LocalDateTime.now(), "peter");
		CustomerPrice c10 = new CustomerPrice(akl, bud, "Standard", 27, 4, LocalDateTime.now(), "donald");
		CustomerPrice c11 = new CustomerPrice(akl, la, "Air", 8, 8, LocalDateTime.now(), "donald");
		CustomerPrice c12 = new CustomerPrice(wlg, akl, "Air", 14, 4, LocalDateTime.now(), "kaszandra");
		CustomerPrice c13 = new CustomerPrice(chc, wlg, "Standard", 32, 21, LocalDateTime.now(), "peter");
		CustomerPrice c14 = new CustomerPrice(chc, dud, "Standard", 2, 22, LocalDateTime.now(), "donald");

		Route r1 = new Route(dud, chc, "BopPop Post", "Sea", "Standard", 5, 14, 200, 650, 43, 23, DayOfWeek.MONDAY, 6,
				c1, LocalDateTime.now(), "donald");
		Route r2 = new Route(chc, dud, "NZ POST man", "Air", "Air", 15, 8, 450, 300, 32, 22, DayOfWeek.TUESDAY, 11, c2,
				LocalDateTime.now(), "kaszandra");
		Route r3 = new Route(chc, wlg, "NZ POST buh", "Air", "Air", 25, 4, 650, 200, 22, 13, DayOfWeek.THURSDAY, 2, c3,
				LocalDateTime.now(), "peter");
		Route r4 = new Route(wlg, chc, "Heella Post", "Sea", "Standard", 4, 45, 120, 600, 36, 48, DayOfWeek.WEDNESDAY,
				3, c4, LocalDateTime.now(), "pri");
		Route r5 = new Route(wlg, hlz, "NZ POST boy", "Land", "Standard", 42, 6, 200, 300, 25, 41, DayOfWeek.MONDAY, 18,
				c5, LocalDateTime.now(), "shelley");
		Route r6 = new Route(chc, akl, "BopPop Post", "Sea", "Standard", 457, 2, 220, 300, 15, 5, DayOfWeek.MONDAY, 9,
				c6, LocalDateTime.now(), "shelley");
		Route r7 = new Route(hlz, akl, "*FreshPost*", "Land", "Standard", 6, 6, 300, 200, 47, 16, DayOfWeek.FRIDAY, 22,
				c7, LocalDateTime.now(), "pri");
		Route r8 = new Route(akl, wlg, "BopPop Post", "Land", "Standard", 26, 28, 200, 300, 35, 53, DayOfWeek.THURSDAY,
				17, c8, LocalDateTime.now(), "kaszandra");
		Route r9 = new Route(akl, dud, "New Ze POST", "Air", "Air", 26, 26, 650, 300, 2, 11, DayOfWeek.MONDAY, 21, c9,
				LocalDateTime.now(), "donald");
		Route r10 = new Route(akl, bud, "NZ POST bo", "Sea", "Standard", 22, 30, 650, 520, 14, 44, DayOfWeek.WEDNESDAY,
				15, c10, LocalDateTime.now(), "donald");
		Route r11 = new Route(akl, la, "BopPop Post", "Air", "Air", 16, 16, 200, 300, 43, 14, DayOfWeek.WEDNESDAY, 8,
				c11, LocalDateTime.now(), "donald");
		Route r12 = new Route(wlg, akl, "Fresh Post", "Land", "Standard", 37, 23, 390, 300, 2, 24, DayOfWeek.MONDAY, 14,
				c12, LocalDateTime.now(), "pri");
		Route r13 = new Route(chc, wlg, "Jeff's Van", "Sea", "Standard", 26, 20, 200, 200, 27, 12, DayOfWeek.THURSDAY,
				16, c13, LocalDateTime.now(), "peter");
		Route r14 = new Route(chc, dud, "Fresh Post", "Land", "Standard", 18, 6, 650, 210, 15, 24, DayOfWeek.WEDNESDAY,
				20, c14, LocalDateTime.now(), "kaszandra");
		Route r15_6 = new Route(chc, akl, "Fresh Post", "Land", "Standard", 16, 3, 200, 580, 10, 7, DayOfWeek.FRIDAY, 7,
				c6, LocalDateTime.now(), "peter");
		Route r16_14 = new Route(chc, dud, "*FreshPost*", "Sea", "Standard", 25, 14, 200, 550, 24, 11, DayOfWeek.FRIDAY,
				5, c14, LocalDateTime.now(), "shelley");
		Route r17_1 = new Route(dud, chc, "Jeff's Van", "Land", "Standard", 25, 3, 300, 540, 10, 10, DayOfWeek.THURSDAY,
				5, c1, LocalDateTime.now(), "peter");
		Route r18_9 = new Route(akl, dud, "Jeff's Van", "Air", "Air", 11, 13, 300, 500, 20, 2, DayOfWeek.FRIDAY, 6, c9,
				LocalDateTime.now(), "sam");
		Route r19_12 = new Route(wlg, akl, "Johnny Mac", "Air", "Air", 25, 9, 420, 320, 3, 12, DayOfWeek.FRIDAY, 3, c12,
				LocalDateTime.now(), "peter");
		Route r20_13 = new Route(chc, wlg, "Jeff's Van", "Land", "Standard", 22, 5, 450, 320, 16, 6, DayOfWeek.THURSDAY,
				7, c13, LocalDateTime.now(), "shelley");
		Route r21_13 = new Route(chc, wlg, "FreshPaste", "Land", "Standard", 37, 6, 240, 390, 6, 4, DayOfWeek.FRIDAY, 8,
				c13, LocalDateTime.now(), "donald");

		log.writeCustomerPrice(c1);
		log.writeCustomerPrice(c2);
		log.writeRoute(r1);
		log.writeCustomerPrice(c3);
		log.writeRoute(r2);
		log.writeRoute(r3);
		log.writeRoute(r16_14);
		log.writeCustomerPrice(c4);
		log.writeRoute(r4);
		log.writeCustomerPrice(c5);
		log.writeRoute(r5);
		log.writeRoute(r6);
		log.writeCustomerPrice(c6);
		log.writeCustomerPrice(c7);
		log.writeRoute(r7);
		log.writeCustomerPrice(c8);
		log.writeRoute(r8);
		log.writeRoute(r9);
		log.writeCustomerPrice(c9);
		log.writeRoute(r10);
		log.writeRoute(r11);
		log.writeCustomerPrice(c10);
		log.writeCustomerPrice(c11);
		log.writeRoute(r12);
		log.writeRoute(r13);
		log.writeRoute(r14);
		log.writeCustomerPrice(c12);
		log.writeCustomerPrice(c13);
		log.writeRoute(r19_12);
		log.writeRoute(r20_13);
		log.writeRoute(r15_6);
		log.writeCustomerPrice(c14);
		log.writeRoute(r17_1);
		log.writeRoute(r18_9);
		log.writeRoute(r21_13);

	}

	@Test
	public void extraHelp() {
		Location wlg = new Location("Wellington");
		Location akl = new Location("Auckland");

		CustomerPrice pa = new CustomerPrice(wlg, akl, "Air", 1, 1, LocalDateTime.now(), "User");
		CustomerPrice ps = new CustomerPrice(wlg, akl, "Standard", 2, 5, LocalDateTime.now(), "User");
		Route ra = new Route(wlg, akl, "UPS", "Air", "Air", 4, 4, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, pa,
				LocalDateTime.now(), "User");

		LogWriter writer = new LogWriter(new File("logfile.xml"), true);
		writer.writeCustomerPrice(pa);
		writer.writeCustomerPrice(ps);
		writer.writeRoute(ra);
	}
}
