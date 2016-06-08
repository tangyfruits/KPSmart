package tests.main;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Main;

public class LoginTests {

	@Test
	public void test1() {
		Main m = new Main();
		assertTrue(m.login("donald", "password123"));
		
	}
	@Test
	public void test2() {
		Main m = new Main();
		assertTrue(m.login("peter", "password123"));
		
	}
	@Test
	public void test3() {
		Main m = new Main();
		assertTrue(m.login("shelley", "password123"));
		
	}
	@Test
	public void test4() {
		Main m = new Main();
		assertFalse(m.login("admin", "password123"));
		
	}
	@Test
	public void test5() {
		Main m = new Main();
		assertFalse(m.login("hacker", "password123"));
		
	}
	@Test
	public void test6() {
		Main m = new Main();
		assertFalse(m.login("admin", "admin"));
		
	}
	@Test
	public void test7() {
		Main m = new Main();
		assertNull(m.getCurrentUser());
		
	}
	@Test
	public void test8() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.logout();
		assertNull(m.getCurrentUser());
		
	}
	@Test
	public void test9() {
		Main m = new Main();
		m.login("shelley", "password123");
		m.logout();
		assertNull(m.getCurrentUser());
	}

}
