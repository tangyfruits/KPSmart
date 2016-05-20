package main;

public class TuplePriority implements Comparable<Tuple> {
	Location start;
	Location from;
	double costSoFar;
	double totalCost;
	String priority;

	public TuplePriority(Location s, Location f, double cSF, double tC, String Priority) {
		start = s;
		from = f;
		costSoFar = cSF;
		totalCost = tC;
	}

	public int compareTo(Tuple t) {
		if (this.start.getPriority().equals("Air") && !t.start.getPriority().equals("Air")){
			return 1;
		}
		if (this.totalCost > t.totalCost && (this.start.getPriority().equals(t.start.getPriority()))){
			return 1;
		}
		else if (this.totalCost == t.totalCost && (this.start.getPriority().equals(t.start.getPriority()))){
			return 0;
		}
		 else
			return -1;
	}

	public Location getStart() { 
		return start;
	}

	public Location getFrom() {
		return from;
	}
}
