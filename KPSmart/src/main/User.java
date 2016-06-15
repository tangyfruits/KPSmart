package main;

/**
 * This is a simple class to represent a user for the KPS. It has a username,
 * password and a boolean to represent manager stauts.
 * 
 * @author Donald Tang
 */

public class User {
	private boolean manager;
	private String username;
	private String password;

	/**
	 * Constructor
	 * 
	 * @param username
	 * @param password
	 * @param manager
	 */
	public User(String username, String password, boolean manager) {
		this.username = username;
		this.password = password;
		this.manager = manager;
	}

	/**
	 * Getter
	 * 
	 * @return manager status.
	 */
	public boolean isManager() {
		return manager;
	}

	/**
	 * Setter
	 * 
	 * @param manager
	 */
	public void setManager(boolean manager) {
		this.manager = manager;
	}

	/**
	 * Getter
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
