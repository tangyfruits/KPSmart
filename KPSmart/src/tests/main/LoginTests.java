package tests.main;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Main;
import main.User;

public class LoginTests {

	@Test
	public void test01() {
		Main m = new Main();
		assertTrue(m.login("donald", "password123"));

	}

	@Test
	public void test02() {
		Main m = new Main();
		assertTrue(m.login("peter", "password123"));

	}

	@Test
	public void test03() {
		Main m = new Main();
		assertTrue(m.login("shelley", "password123"));

	}

	@Test
	public void test04() {
		Main m = new Main();
		assertFalse(m.login("admin", "password123"));

	}

	@Test
	public void test05() {
		Main m = new Main();
		assertFalse(m.login("hacker", "password123"));

	}

	@Test
	public void test06() {
		Main m = new Main();
		assertFalse(m.login("admin", "admin"));

	}

	@Test
	public void test07() {
		Main m = new Main();
		assertNull(m.getCurrentUser());

	}

	@Test
	public void test08() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.logout();
		assertNull(m.getCurrentUser());

	}

	@Test
	public void test09() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.logout();
		assertNull(m.getCurrentUser());
	}

	@Test
	public void test10() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.edit("password987");
		assertTrue(m.getCurrentUser().getPassword().equals("password987"));
	}

	@Test
	public void test11() {
		Main m = new Main();
		m.login("shelley", "password987");
		m.edit("password123");
		assertTrue(m.getCurrentUser().getPassword().equals("password123"));

	}

	@Test
	public void test12() {
		Main m = new Main();
		m.login("shelley", "password987");
		assertNull(m.getCurrentUser());
	}

	@Test
	public void test13() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.delete();
		assertNull(m.getCurrentUser());
	}

	@Test
	public void test14() {
		Main m = new Main();
		User u = new User("David", "password123", true);
		m.add(u);
		assertTrue(m.login("David", "password123"));
	}

}
