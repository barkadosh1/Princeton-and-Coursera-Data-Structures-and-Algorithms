/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */

package roadgraph;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {

	// hashmap that will store the vertex points and MapNodes
	private HashMap <GeographicPoint, MapNode> hm;

	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		hm = new HashMap<GeographicPoint, MapNode>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	
	// getNumVertices is my implementation 
	public int getNumVertices()
	{
		return hm.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	
	// getVertices is my implementation 
	public Set<GeographicPoint> getVertices()
	{
		// Creating empty hashset
		Set<GeographicPoint> set = new HashSet<>();
		
		// adding each vertex to the hashset
		for (GeographicPoint key : hm.keySet() ) {
		    set.add(key);
		}
		
		// returning the hashset
		return set;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	
	// getNumEdges is my implementation 
	public int getNumEdges()
	{
		int count = 0;

		// for each vertex, I get the list of edges associated with it
		// I then sum the size of each of those lists of edges associated with each vertext
		for (GeographicPoint key : hm.keySet()) {
		    MapNode n = hm.get(key);
		    count = count + n.edgeCount();
		}
		
		// I return the sum
		return count;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	
	// addVertex is my implementation 
	public boolean addVertex(GeographicPoint location)
	{
		if (hm.containsKey(location)) return false;
		else if (location == null) return false;
		
		// only if the location is not null or not already in the graph,
		// then I add the vertex and its associated mapnode to the graph
		else {
			MapNode r = new MapNode(location);
			hm.put(location, r);
			return true;
		}	
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	
	// addEdge is my implementation 
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		
		// If the hashmap already contains the from and to vertices,
		// then I create the MapEdge and assign the relevant values to it
		if (hm.containsKey(from) && hm.containsKey(to)) {
			MapEdge x = new MapEdge();
			x.addNodes(from, to);
			x.addStreet(roadName, roadType);
			x.addDistance(length);
			
			// I add the MapEdge I just created to the MapNode associated with this vertex
			MapNode node = hm.get(from);
			node.addMapEdge(x);

		}



	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	
	// bfs is my implementation 
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		
		// I initialize a queue, hashset, and hashmap
		// The queue will be used to remove the nodes in the correct bfs order
		// the set will be used to keep track of the nodes that have been visited
		// the hash map will document the path taken by storing which nodes are connected
		Queue<GeographicPoint> nodes = new LinkedList<GeographicPoint>();
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		HashMap<MapNode, MapNode> path = new HashMap<MapNode, MapNode>();
		
		nodes.add(start);
		visited.add(start);
		
		LinkedList<GeographicPoint> list = new LinkedList<GeographicPoint>();
		
		while (nodes.peek() != null) {
			GeographicPoint x = nodes.remove();

			// if we reach our goal, then break out of the loop
			if (x == goal) break;
			
			MapNode n1 = hm.get(x);
			
			// creates a set of the neighbor MapEdges for this vertex 
			Set<MapEdge> edges = n1.getNeighbor();
			
		    for (MapEdge edge : edges) {
		    	// gets the GeographicPoint value for the MapNode being studied currently
		         GeographicPoint neighbor = edge.getEnd();
		         
		         // as long as this neigbor has not been visited, I add it to the queue, the visited set,
		         // and I put it along with the node pointing to it into my path hash map 
		         if (!visited.contains(neighbor)) {
			         nodes.add(neighbor);
			         visited.add(neighbor);
			         path.put(hm.get(neighbor), hm.get(x));
		         }

		    }
			
		}
		

		// defines our goal and start MapNodes
		MapNode end = hm.get(goal);
		MapNode beginning = hm.get(start);
		
		
		// Continues looping until we reach our start node
		while (end != beginning) {
			if (path.get(end) == null) return null;
			
			//  add the current last node into the first spot of the linked list
			list.addFirst(end.loc());
			
			// updates the "last" node so that it is now the node before the current last node
			end = path.get(end);
		}

		// finally add the original start node into the beginning of the linked list and return the list
		list.addFirst(beginning.loc());
		return list;
	}
	

	
 
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	
	// dijkstra is my implementation 
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		 

		PriorityQueue<MapNode> nodes = new PriorityQueue<>();
		Set<MapNode> visited = new HashSet<MapNode>();
		HashMap<MapNode, MapNode> path = new HashMap<MapNode, MapNode>();
		
		MapNode end = hm.get(goal);
		MapNode beginning = hm.get(start);
		
		beginning.changeDist(0);
		
		nodes.add(hm.get(start));
		visited.add(hm.get(start));
		
		LinkedList<GeographicPoint> list = new LinkedList<GeographicPoint>();
		
		
		while (nodes.peek() != null) {
			MapNode x = nodes.remove();
			visited.add(x);

			
			if (x.loc().equals(goal)) break;

			Set<MapEdge> edges = x.getNeighbor();
			
		    for (MapEdge edge : edges) {
		         MapNode neighbor = hm.get(edge.getEnd());
		         
		         if (!visited.contains(neighbor)) {
		        	 double edgeDist = edge.getDist();
		        	 double newDist = x.getDist() + edgeDist;
		        	 
		        	 if (newDist < neighbor.getDist()) {
		        		 neighbor.changeDist(newDist); 
				         nodes.add(neighbor);
				         path.put(neighbor, x);
		        	 }

		         }

		    }
		}

		while (end != beginning) {
			if (path.get(end) == null) return null;
			list.addFirst(end.loc());
			end = path.get(end);
		}

		list.addFirst(beginning.loc());
		return list;
		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	
	// aStarSearch is my implementation 
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
	

		PriorityQueue<MapNode> nodes = new PriorityQueue<>(MapNode.a_star_map_comp);
		Set<MapNode> visited = new HashSet<MapNode>();
		HashMap<MapNode, MapNode> path = new HashMap<MapNode, MapNode>();
		
		MapNode end = hm.get(goal);
		MapNode beginning = hm.get(start);
		
		beginning.changeDist(0);
		beginning.setTheorDist(end);
		beginning.ChangetotalDist();
		
		nodes.add(hm.get(start));
		visited.add(hm.get(start));
		
		LinkedList<GeographicPoint> list = new LinkedList<GeographicPoint>();
		
		
		while (nodes.peek() != null) {
			MapNode x = nodes.remove();
			visited.add(x);

			
			if (x.loc().equals(goal)) break;

			Set<MapEdge> edges = x.getNeighbor();
			
		    for (MapEdge edge : edges) {
		         MapNode neighbor = hm.get(edge.getEnd());
		         
		         if (!visited.contains(neighbor)) {
		        	 double edgeDist = edge.getDist();
		        	 double newDist = x.getDist() + edgeDist;
		        	 
		        	 if (newDist < neighbor.getDist()) {
		        		 neighbor.changeDist(newDist); 
		        	 }
		        	 
		        	 if (neighbor.getTheorDist() == Double.POSITIVE_INFINITY) {
		        		 neighbor.setTheorDist(end);
		        	 }
		        	 
		        	 
		        	 if ((neighbor.getTheorDist()+neighbor.getDist()) < neighbor.getTotalDist()) {
		        		 neighbor.ChangetotalDist();
				         nodes.add(neighbor);
				         path.put(neighbor, x);
		        	 }

		         }

		    }
		}

				
		while (end != beginning) {
			if (path.get(end) == null) return null;
			list.addFirst(end.loc());
			end = path.get(end);
		}

		list.addFirst(beginning.loc());
		return list;
		
	}


	

	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		//GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		//GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
	
		
		// You can use this method for testing.  
		
	
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		for (int i =0; i < testroute.size(); i++) {
			System.out.println(testroute.get(i));
		}
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

	
		
	}
	
}
