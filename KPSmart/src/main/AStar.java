package main;
import java.util.PriorityQueue;
import java.util.Queue;


public class AStar {
	/*
	 * A* algorithm (fast version)
	 * 
	 * fringe = priority queue, ordered by total cost to Goal estimate(node,
	 * goal) must be admissible and consistent
	 * 
	 * Initialise: for all nodes visited <- false, pathFrom <- null
	 * enqueue([start, null, 0, estimate(start, goal )], fringe),
	 * 
	 * Repeat until fringe is empty: [node, from, costToHere, totalCostToGoal]
	 * <- dequeue(fringe) If not node.visited then node.visited <-true,
	 * node.pathFrom<-from, node.cost<-costToHere If node = goal then exit for
	 * each edge to neigh out of node if not neigh.visited then costToNeigh <-
	 * costToHere + edge.weight estTotal <- costToNeigh + estimate(neighbour,
	 * goal ) fringe.enqueue([neighbour, node, costToNeigh, estTotal])
	 */

	Location start;
	Location goal;
	Queue<Tuple> fringe = new PriorityQueue<Tuple>();

	public AStar(Location start, Location goal) {
		this.start = start;
		this.goal = goal;
	}

	public Location algo(double weight, double volume) {
		double estimate = 0;
		start.setVisited(true);
		Tuple startTuple = new Tuple(start, null, 0, estimate);
		fringe.offer(startTuple);

		while (fringe.peek() != null) {
			Tuple myTuple = fringe.poll();
			if (myTuple.start.isVisited() == false) {
				myTuple.start.setVisited(true);
				myTuple.start.fromLocation = myTuple.from;
				myTuple.start.setCostSoFar(myTuple.costSoFar);
			}
			if (myTuple.start == goal) {
				break;
			}
			for (Route r : myTuple.start.getRoutes()) {
				Location neighbour = null;
				if (myTuple.start == r.getOrigin()) {
					neighbour = r.getDestination();
					
				}
				if (myTuple.start == r.getDestination()) {
					neighbour = r.getOrigin();
				}
				if (!neighbour.isVisited()) {
					double costToNeigh = myTuple.costSoFar + r.getCost(weight, volume);
					double estTotal = costToNeigh;
					fringe.offer(new Tuple(neighbour, myTuple.start, costToNeigh, estTotal));
				}

			}
		}

		return goal;

	}
}