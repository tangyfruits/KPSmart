package main;

public class User {
private boolean manager;
private String username;
private String password;
public User(String u, String p, boolean m){
	this.manager = m;
	this.username = u;
	this.password = p;
}
public boolean isManager() {
	return manager;
}
public void setManager(boolean manager) {
	this.manager = manager;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
}
