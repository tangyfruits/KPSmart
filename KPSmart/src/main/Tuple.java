package main;

public class Tuple implements Comparable<Tuple> {
	Location start;
	Location from;
	double costSoFar;
	double totalCost;

	public Tuple(Location s, Location f, double cSF, double tC) {
		start = s;
		from = f;
		costSoFar = cSF;
		totalCost = tC;
	}

	public int compareTo(Tuple t) {
		if (this.totalCost > t.totalCost) {
			return 1;

		} else if (this.totalCost < t.totalCost) {
			return -1;

		} else
			return 0;
	}

	public Location getStart(){
		return start;
	}
	
	public Location getFrom(){
		return from;
	}
}
