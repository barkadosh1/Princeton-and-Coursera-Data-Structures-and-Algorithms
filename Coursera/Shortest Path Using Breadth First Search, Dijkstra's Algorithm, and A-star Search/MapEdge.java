// MapEdge is my implementation entirely 

package roadgraph;
import geography.GeographicPoint;

// A class that represents the edge between 2 nodes
public class MapEdge {

	private GeographicPoint start;
	private GeographicPoint end;
	private String streetname;
	private String streettype;
	private double distance;
	private boolean accident;

	// Creates a MapEdge with the start and end nodes, street name, street type, and distance
	public MapEdge()
	{
		start = new GeographicPoint (0.0, 0.0);
		end = new GeographicPoint (0.0, 0.0);
		streetname = "";
		streettype = "";
		distance = 0.0;
		accident = false;
	}
	
	// assigns values to the vertices of the edge
	public void addNodes(GeographicPoint one, GeographicPoint two)
	{
		start = one;
		end = two;
	}
	
	// assigns a name type to the street of the edge
	public void addStreet(String name, String type)
	{
		streetname = name;
		streettype = type;
	}
	
	// asssigns a distance value to the edge
	public void addDistance(double dist)
	{
		distance = dist;
	}
	
	// returns the start vertex
	public GeographicPoint getStart()
	{
		return start;
	}
	// returns the end vertex
	public GeographicPoint getEnd()
	{ 
		return end;
	}
	
	// returns the name of the edge
	public String getName()
	{
		return streetname;
	}
	
	// returns the type of the edge
	public String getType()
	{
		return streettype;
	}
	
	// returns the distance of the edge
	public Double getDist()
	{
		return distance;
	}
	
	public void changeAccident()
	{
		accident = true;
	}
	
	public boolean getAccident() {
		return accident;
	}
	


}
