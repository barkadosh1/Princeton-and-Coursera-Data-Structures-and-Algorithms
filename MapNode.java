// MapNode is my impementation entirely 

package roadgraph;
import geography.GeographicPoint;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

// A class in which I store a set of the MapEdges for each vertex/location/geographic point
public class MapNode implements Comparable<MapNode> {
	
	private GeographicPoint loc;
	private Set<MapEdge> edges;
	private double distance;
	private double theoretical_distance;
	private double total_distance;
	
	public MapNode(GeographicPoint location)
	{
		// assign the location of the vertex and create an empty set of MapEdges
		loc = location;
		edges = new HashSet<MapEdge>();
		distance = Double.POSITIVE_INFINITY;
		theoretical_distance = Double.POSITIVE_INFINITY;
		total_distance = Double.POSITIVE_INFINITY;
	}
	
	// Adds a map edge
	public void addMapEdge(MapEdge edge) {
		edges.add(edge);	
	}
	
	// returns the GeographicPoint/Vertex of the MapNode
	public GeographicPoint loc() {
		return loc;	
	}
	
	// Returns the number of edges associated with this MapNode
	public int edgeCount() {
		return edges.size();	
	}
	
	// Returns the edges associated with this MapNode
	public Set<MapEdge> getNeighbor() {
		return edges;
	}
	
	public void changeDist(double dist) {
		this.distance = dist;
	}
	
	public double getDist() {
		return distance;
	}
	
	public void setTheorDist(MapNode mn) {
		this.theoretical_distance = this.loc().distance(mn.loc());
	}
	
	public double getTheorDist() {
		return theoretical_distance;
	}
	
	public void ChangetotalDist() {
		this.total_distance = this.distance + this.theoretical_distance;
	}
	
	public double getTotalDist() {
		return total_distance;
	} 
	
	public int compareTo(MapNode temp) {
		return ((Double) this.getDist()).compareTo((Double) temp.getDist());
	}
	

    public static Comparator<MapNode> a_star_map_comp = new Comparator<MapNode>() {

        public int compare(MapNode mn1, MapNode mn2) {

          double d1 = mn1.total_distance;
          double d2 = mn2.total_distance;

          return ((Double)d1).compareTo((Double)d2);

        }

    };

}
