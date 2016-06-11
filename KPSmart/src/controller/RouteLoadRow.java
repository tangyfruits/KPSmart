package controller;

public class RouteLoadRow {
	private String origin;
	private String destination;
	private String totalWeight;
	private String totalVolume;
	private String totalCount;
	
	public RouteLoadRow(String origin, String destination, String totalWeight, String totalVolume, String totalCount){
		this.origin = origin;
		this.destination = destination;
		this.totalWeight = totalWeight;
		this.totalVolume = totalVolume;
		this.totalCount = totalCount;
	}
	
	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public String getTotalVolume() {
		return totalVolume;
	}

	public String getTotalCount() {
		return totalCount;
	}
}
