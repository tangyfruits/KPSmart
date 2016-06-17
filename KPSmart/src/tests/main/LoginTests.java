package tests.main;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.Main;
import main.User;

/**
 * This is a series of JUnit tests for the Login capabilities of the Kelburn
 * Postal Smart system.
 * 
 * @author Donald Tang
 *
 */
public class LoginTests {
	
	static private File config = new File(".config");
	
	@BeforeClass
	public static void setup() {
		// clear config file
		if (config.isFile()) {
			try {
				FileWriter fw = new FileWriter(config);
				fw.write("");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				config.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Add essential Users
		Main m = new Main();
		m.add(new User("donald", "password123", true));
		m.add(new User("shelley", "password123", true));
		m.add(new User("peter", "password123", true));
		m.add(new User("kaszandra", "password123", true));
		m.add(new User("priyanka", "password123", true));
	}

	@AfterClass
	public static void tearDown() {
		Main m = new Main();
		m.add(new User("donald", "password123", true));
		m.add(new User("shelley", "password123", true));
		m.add(new User("peter", "password123", true));
		m.login("david", "password123");
		m.delete();
	}
	
	@Test
	public void testCorrectLogin_01() {
		Main m = new Main();
		assertTrue(m.login("donald", "password123"));

	}
	@Test
	public void testCorrectLogin_02() {
		Main m = new Main();
		assertTrue(m.login("peter", "password123"));

	}
	@Test
	public void testCorrectLogin_03() {
		Main m = new Main();
		assertTrue(m.login("shelley", "password123"));

	}

	@Test
	public void testIncorrectLogin_01() {
		Main m = new Main();
		assertFalse(m.login("admin", "password123"));

	}
	@Test
	public void testIncorrectLogin_02() {
		Main m = new Main();
		assertFalse(m.login("hacker", "password123"));

	}
	@Test
	public void testIncorrectLogin_03() {
		Main m = new Main();
		assertFalse(m.login("admin", "admin"));

	}

	@Test
	public void testNoUser_01() {
		Main m = new Main();
		assertNull(m.getCurrentUser());
	}

	@Test
	public void testLogoutUser_01() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.logout();
		assertNull(m.getCurrentUser());

	}
	@Test
	public void testLogoutUser_02() {
		Main m = new Main();
		m.login("peter", "password123");
		m.logout();
		assertNull(m.getCurrentUser());
	}
	@Test
	public void testLogoutUser_03() {
		Main m = new Main();
		m.login("donald", "password123");
		m.logout();
		assertNull(m.getCurrentUser());
	}

	@Test
	public void testEditUser_01() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.editPassword("password987");
		assertTrue(m.getCurrentUser().getPassword().equals("password987"));
	}
	@Test
	public void testEditUser_02() {
		Main m = new Main();
		m.login("shelley", "password987");
		m.editPassword("password987");
		assertTrue(m.getCurrentUser().getPassword().equals("password987"));
	}
	@Test
	public void testEditUser_03() {
		Main m = new Main();
		m.login("shelley", "password987");
		m.editPassword("password123");
		assertTrue(m.getCurrentUser().getPassword().equals("password123"));
	}
	@Test
	public void testEditUser_04() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.editManager("peter", false);
		assertFalse(m.findUser("peter").isManager());
	}
	@Test
	public void testEditUser_05() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.editManager("donald", false);
		assertFalse(m.findUser("donald").isManager());
	}
	@Test
	public void testEditUser_06() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.editManager("donald", false);
		assertFalse(m.findUser("donald").isManager());
	}

	@Test
	public void testDeleteUser_01() {
		Main m = new Main();
		m.login("peter", "password123");
		m.delete();
		assertNull(m.getCurrentUser());
	}
	@Test
	public void testDeleteUser_02() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.delete();
		assertNull(m.getCurrentUser());
	}
	@Test
	public void testDeleteUser_03() {
		Main m = new Main();
		m.login("donald", "password123");
		m.delete();
		assertNull(m.getCurrentUser());
	}

	@Test
	public void testTryAddUser_01() {
		Main m = new Main();
		m.add(new User("donald", "password123", true));
		assertTrue(m.login("donald", "password123"));
	}
	@Test
	public void testTryAddUser_02() {
		Main m = new Main();
		m.add(new User("peter", "password123", true));
		assertTrue(m.login("peter", "password123"));
	}
	@Test
	public void testTryAddUser_03() {
		Main m = new Main();
		m.add(new User("shelley", "password123", true));
		assertTrue(m.login("shelley", "password123"));
	}
	@Test
	public void testTryAddUser_04() {
		Main m = new Main();
		m.add(new User("david", "password123", true));
		assertTrue(m.login("david", "password123"));
	}
	@Test
	public void testFindUser_01() {
		Main m = new Main();
		m.login("donald", "password123");
		assertTrue(m.getCurrentUser() == m.findUser("donald"));
	}
	@Test
	public void testFindUser_02() {
		Main m = new Main();
		m.login("peter", "password123");
		assertTrue(m.getCurrentUser() == m.findUser("peter"));
	}
	@Test
	public void testFindUser_03() {
		Main m = new Main();
		m.login("shelley", "password123");
		assertTrue(m.getCurrentUser() == m.findUser("shelley"));
	}
	@Test
	public void testFindUser_04() {
		Main m = new Main();
		m.login("pri", "password123");
		assertTrue(m.getCurrentUser() == m.findUser("pri"));
	}
	@Test
	public void testFindUser_05() {
		Main m = new Main();
		m.login("kaszandra", "password123");
		assertTrue(m.getCurrentUser() == m.findUser("kaszandra"));
	}
	

}
