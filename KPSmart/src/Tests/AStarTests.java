package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.AStar;
import main.CustomerPrice;
import main.Location;
import main.Route;

public class AStarTests {
	
	Location loc1 = new Location("Wellington");
	Location loc2 = new Location("Auckland");
	Location loc3 = new Location("Lower Hutt");
	
	AStar astar = new AStar(loc1, loc2);
	AStar astarNull = new AStar(null, null);
	
	@Test
	public void testParameters() {
		assertEquals(astar.start, loc1);
		assertEquals(astar.goal, loc2);
	}
	
	/*@Test
	public void testNull(){
		assertNull(astarNull.algo(9, 1));
	}*/
	
	@Test
	public void testNotEqual(){
		Location loc5 = new Location("Chch");
		Location loc6 = astar.algo2(2.0, 2.0);
		assertNotEquals(loc5, loc6);
	}
	
	@Test
	public void testAlgo1(){
		System.out.println("testAlgo1");
		Location locEnd = astar.algo2(2.0, 1.0);
		while(locEnd != null){
			System.out.println(locEnd.getName());
			locEnd=locEnd.fromLocation;
		}
		System.out.println();
	}
	
	@Test
	public void testAlgo2(){
		System.out.println("testAlgo2");
		CustomerPrice cp = new CustomerPrice(loc1, loc2, "", 1.0, 1.0);
		Route r1 = new Route(loc1, loc2, "", "", "", 1.0, 1.0, 10, 10, 5, 5, "", cp);
		loc1.addRoute(r1);
		Location locEnd = astar.algo2(2.0, 1.0);
		int count=0;
		while(locEnd != null){
			System.out.println(count+": "+locEnd.getName());
			locEnd=locEnd.fromLocation;
			count++;
		}
		System.out.println();
	}
	
	@Test
	public void testAlgo3(){
		System.out.println("testAlgo3");
		CustomerPrice cp = new CustomerPrice(loc1, loc2, "", 1.0, 1.0);
		Route r1 = new Route(loc1,loc2,"","","",1.0,1.0,10,10,5,5,"",cp);
		//CustomerPrice cp2 = new CustomerPrice(loc3, loc4, "", 2.0, 2.0);
		Route r2 = new Route(loc1,loc3,"","","",1.0,1.0,19,10,5,5,"",cp);	
		loc1.addRoute(r1);
		loc2.addRoute(r2);
		
		System.out.println(loc2.getRoutes().toString());
		
		loc2 = astar.algo2(2.0, 2.0);
		System.out.println("past algo");
		int count=0;
		while(loc2 != null){
			System.out.println(count+": "+loc2.getName());
			loc2=loc2.fromLocation;
			count++;
		}
	}
	@Test
	public void testAlgo4(){
		System.out.println("testAlgo4");
		Location locEnd = astar.highestPriorityAlgorithm(2.0, 1.0);
		while(locEnd != null){
			System.out.println(locEnd.getName());
			locEnd=locEnd.fromLocation;
		}
		System.out.println();
	}
	
	@Test
	public void testAlgo5(){
		System.out.println("testAlgo5");
		CustomerPrice cp = new CustomerPrice(loc1, loc2, "", 1.0, 1.0);
		Route r1 = new Route(loc1, loc2, "", "", "", 1.0, 1.0, 10, 10, 5, 5, "", cp);
		loc1.addRoute(r1);
		Location locEnd = astar.highestPriorityAlgorithm(2.0, 1.0);
		int count=0;
		while(locEnd != null){
			System.out.println(count+": "+locEnd.getName());
			locEnd=locEnd.fromLocation;
			count++;
		}
		System.out.println();
	}
	
	@Test
	public void testAlgo6(){
		System.out.println("testAlgo6");
		CustomerPrice cp = new CustomerPrice(loc1, loc2, "", 1.0, 1.0);
		Route r1 = new Route(loc1,loc2,"","","",1.0,1.0,10,10,5,5,"",cp);
		//CustomerPrice cp2 = new CustomerPrice(loc3, loc4, "", 2.0, 2.0);
		Route r2 = new Route(loc1,loc3,"","","",1.0,1.0,19,10,5,5,"",cp);	
		loc1.addRoute(r1);
		loc2.addRoute(r2);
		
		System.out.println(loc2.getRoutes().toString());
		
		loc2 = astar.highestPriorityAlgorithm(2.0, 2.0);
		System.out.println("past algo");
		int count=0;
		while(loc2 != null){
			System.out.println(count+": "+loc2.getName());
			loc2=loc2.fromLocation;
			count++;
		}
	}

}
