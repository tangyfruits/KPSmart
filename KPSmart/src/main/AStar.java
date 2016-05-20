package main;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
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

	public Location start;
	public Location goal;

	public AStar(Location start, Location goal) {
		this.start = start;
		this.goal = goal;
	}

	public ArrayList<ArrayList<Route>> listOfRoutes(double weight, double volume) {
		Location cheapestLocation = cheapestRouteAlgorithm(weight, volume);
		Location priorityLocation = highestPriorityAlgorithm(weight, volume);
		ArrayList<Route> cheapestRoutes = new ArrayList<>();
		ArrayList<Route> priorityRoutes = new ArrayList<>();
		while (cheapestLocation.getFrom() != null) {
			for (Route r : cheapestLocation.getFrom().getRoutes()) {
				//need to change this because only checks if there is one instead of multiple
				if (r.getDestination().getName().equals(cheapestLocation.getName())) {
					cheapestRoutes.add(r);
				}
			}
			cheapestLocation = cheapestLocation.getFrom();
		}
		while (priorityLocation.getFrom() != null) {
			for (Route r : priorityLocation.getFrom().getRoutes()) {
				//need to change this because only checks if there is one instead of multiple
				if (r.getDestination().getName().equals(priorityLocation.getName())) {
					priorityRoutes.add(r);
				}
			}
			priorityLocation = priorityLocation.getFrom();
		}
		ArrayList<ArrayList<Route>> list = new ArrayList<ArrayList<Route>>();
		list.add(cheapestRoutes);
		list.add(priorityRoutes);
		return list;

	}

	public Location cheapestRouteAlgorithm(double weight, double volume) {
		Queue<Tuple> fringe = new PriorityQueue<Tuple>();
		double estimate = 0;
		start.setVisited(true);
		Tuple startTuple = new Tuple(start, null, 0, estimate);
		fringe.offer(startTuple);

		while (fringe.peek() != null) {
			Tuple myTuple = fringe.poll();
			if (myTuple.start.isVisited() == false) {
				myTuple.start.setVisited(true);
				myTuple.start.setFrom(myTuple.from);
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

	public Location highestPriorityAlgorithm(double weight, double volume) {
		Queue<TuplePriority> fringe = new PriorityQueue<TuplePriority>();
		double estimate = 0;
		start.setVisited(true);
		TuplePriority startTuple = new TuplePriority(start, null, 0, estimate, "Air");
		fringe.offer(startTuple);

		while (fringe.peek() != null) {
			TuplePriority myTuple = fringe.poll();
			if (myTuple.start.isVisited() == false) {
				myTuple.start.setVisited(true);
				myTuple.start.setFrom(myTuple.from);
				myTuple.start.setCostSoFar(myTuple.costSoFar);
			}
			if (myTuple.start == goal) {
				break;
			}
			for (Route r : myTuple.start.getRoutes()) {
				Location neighbour = null;
				if (myTuple.start == r.getOrigin()) {
					neighbour = r.getDestination();
					neighbour.setPriority(r.getPriority());
				}
				if (myTuple.start == r.getDestination()) {
					neighbour = r.getOrigin();
					neighbour.setPriority(r.getPriority());
				}
				if (!neighbour.isVisited()) {
					double costToNeigh = myTuple.costSoFar + r.getCost(weight, volume);
					double estTotal = costToNeigh;
					fringe.offer(new TuplePriority(neighbour, myTuple.start, costToNeigh, estTotal,
							neighbour.getPriority()));
				}

			}
		}
		return goal;
	}

}