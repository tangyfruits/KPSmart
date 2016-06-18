package controller;

public class AverageTimesRow {
	private String origin;
	private String destination;
	private String priority;
	private String time;
	
	public AverageTimesRow(String origin, String destination, String priority, String time){
		this.origin = origin;
		this.destination = destination;
		this.priority = priority;
		this.time = time;
	}
	
	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public String getPriority() {
		return priority;
	}

	public String getTime() {
		return time;
	}


}

