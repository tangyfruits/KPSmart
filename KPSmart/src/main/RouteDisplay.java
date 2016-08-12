package main;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import event.Route;

public class RouteDisplay {

	private String priority;
	private ArrayList<Route> route;
	private double price;

	public RouteDisplay(String priority, ArrayList<Route> route, double price) {
		this.priority = priority;
		this.route = route;
		this.price = price;
	}

	public int getTotalDuration(LocalDateTime currentTime) {
		int total = 0;
		LocalDateTime current = currentTime;
		for (Route r : route) {
			int dur = getDuration(current, r);
			current = current.plusHours(dur);
			total += dur;
		}
		return total;
	}

	public int getDuration(LocalDateTime currentTime, Route route) {
		DayOfWeek day = currentTime.getDayOfWeek();
		int currentInt = (day.getValue() - 1) * 24 + currentTime.getHour();
		int startInt = (route.getDay().getValue() - 1) * 24 + route.getStartTime();
		int departTime;
		if (startInt > currentInt) {
			int difference = startInt;
			while (difference < 120) {
				difference += route.getFrequency();
			}
			int firstOfWeek = difference - 120;
			departTime = getNextDeparture(firstOfWeek, currentInt, route.getFrequency());
			// return (departTime-currentInt)+route.getDuration();
		} else {
			departTime = getNextDeparture(startInt, currentInt, route.getFrequency());
		}
		int weekend = 0;
		if (departTime + route.getFrequency() > 120) {
			weekend = 48;
		}
		return (departTime - currentInt) + route.getDuration() + weekend;
	}

	public int getNextDeparture(int startInt, int currentInt, int frequency) {
		int departTime = startInt;
		while (currentInt >= departTime) {
			departTime += frequency;
		}
		return departTime;
	}

	public String getPriority() {
		return priority;
	}

	public ArrayList<Route> getRoute() {
		return route;
	}

	public double getPrice() {
		return this.price;
	}

	public boolean equals(RouteDisplay r) {
		if (!this.priority.equals(r.getPriority()) || this.route.size() != r.getRoute().size()) {
			return false;
		}
		for (int i = 0; i < route.size(); i++) {
			if (!route.get(i).equals(r.getRoute().get(i))) {
				return false;
			}
		}
		return true;
	}
}
