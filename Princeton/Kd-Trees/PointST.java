/******************************************************************************
 *  Name:    Tal Bass
 *  NetID:   tabass
 *  Precept: P04
 *
 *  Partner Name:    Bar Kadosh
 *  Partner NetID:   bkadosh
 *  Partner Precept: P03
 * 
 *  Description: Our PointST method is a simplified version of our second program,
 *  in which we use a red black search tree to implement our methods. Our PointST
 *  constructor creates an empty RedBlackST, where the key is a Point2D, and the
 *  value is a generic Value. The isEmpty simply checks if the symbol table/bst is
 *  empty or not. The size method checks how many points are in the symbol 
 *  table/bst. The put method simply calls the already implemented put method 
 *  in RedBlackBST, and inserts the point and its associated value into the symbol
 *  table. Similarly, the get method uses the already implemented get method to 
 *  return the value associated with the point the get method is called upon. 
 *  The contains method returns true if the symbol table contains the point we
 *  call it on, and false otherwise. The points method returns an iterable of all
 *  the points that are contained in the symbol table/bst. Our range method is a bit
 *  more complex. It first establishes a minimum and maximum range of values, based
 *  on the rectangle we are working with. It then creates and returns and interable
 *  of all points that are contained/fall within the boundaries of this rectangle.
 *  The nearest method here is a brute-force method, in which we iterate through
 *  every single point in the symbol table. Only if the distance of the point to the 
 *  query point is less than the current minimum do we assign that distance as the
 *  new minimum distance, and that point as the new nearest point. We continue doing
 *  this through all the points, and then return the point at the end that had the
 *  smallest distance. Therefore, that point is the nearest point to the query 
 *  point. Lastly, our main method simply verifies that all of our methods are 
 *  functioning as we intended for them to.
 * 
 ******************************************************************************/

// importing packages needed
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

// creating our class
public class PointST<Value> {
    private RedBlackBST<Point2D, Value> bst; // instance variable initializing our
                                             // red-black BST
    
    // construct an empty symbol table of points and their associated values
    public PointST() {
        bst = new RedBlackBST<Point2D, Value>();
    }
    
    // checks if the symbol table empty 
    public boolean isEmpty() {
        return bst.isEmpty();
    }
    // returns size, or number of points in the bst/symbol table
    public int size() {
        return bst.size();
    }
    
    // inserts point p and its associated value val into the symbol table
    public void put(Point2D p, Value val) {  
        // throws null pointer exception if either val or p is null
        if (p == null || val == null) throw new NullPointerException();
        
        // otherwise, it inserts the point and its associated value into the bst
        bst.put(p, val);
    }
    
    // returns the value associated with point p 
    public Value get(Point2D p) {
        // null pointer exception if the point is null
        if (p == null) throw new NullPointerException();
        
        // calls bst get method to return the value associated with p
        return bst.get(p);
    }
    
    // does the symbol table contain point p? 
    public boolean contains(Point2D p) {
        // null pointer exception if point p is null
        if (p == null) throw new NullPointerException();
        
        // calls bst contains method to return whether p is in the bst
        return bst.contains(p);
    }
    
    // returns an iterable of all points contained in the symbol table 
    public Iterable<Point2D> points() {
        // calls the keys method on bst, which returns all the points as an iterable
        return bst.keys();
    }
    
    // returns an iterable of all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        Queue<Point2D> range = new Queue<Point2D>();
        
        // min and max are the boundary points of the rectangle
        Point2D min = new Point2D(rect.xmin(), rect.ymin());
        Point2D max = new Point2D(rect.xmax(), rect.ymax());
        
        // creates iterable of all points contained within the boundary points above
        for (Point2D i : bst.keys(min, max)) {
            if (i.x() >= rect.xmin() && i.x() <= rect.xmax()) {
                range.enqueue(i);
            }
        }
        
        return range;
    }
    
    // returns nearest neighbor point to point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        
        // initial values for distance and nearest neighbor point
        double dist = Double.MAX_VALUE;
        Point2D min = null;
        
        // assigns smallest distance and nearest neighbor each time by iterating 
        // through all the points and calculating the distance to p
        for (Point2D i : points()) {
            if (i.distanceSquaredTo(p) < dist) {
                min = i;
                dist = i.distanceSquaredTo(p);
            }
        }
        return min;
    }
    
    // main method unit testing (required) 
    public static void main(String[] args) {
        // tests that our constructor works
        PointST<Integer> tree = new PointST<Integer>();
        
        // tests that our put method works for points and their associated values
        tree.put(new Point2D(0.5, 0.5), 1);
        tree.put(new Point2D(0.2, 0.2), 2);
        tree.put(new Point2D(0.20, 0.25), 3);
        tree.put(new Point2D(0.75, 0.75), 4);

        // tests that our get method returns the right value
        System.out.println(tree.get(new Point2D(0.5, 0.5)));
        
        // tests that our contains method returns true or false, depending on if
        // the point is in the symbol table/bst
        System.out.println(tree.contains(new Point2D(0.5, 0.25)));
        System.out.println(tree.contains(new Point2D(0.5, 0.1)));
        
        // tests our size method
        System.out.println(tree.size());
        
        // tests our isEmpty method
        System.out.println(tree.isEmpty());
        
        // tests that our points method actually creates the correct iterable
        Iterable<Point2D> points = tree.points();
        for (Point2D i : points) System.out.println(i);
        
        // tests our range method returns the correct iterable
        System.out.println("Testing range");
        RectHV rect = new RectHV(0.0, 0.0, 0.4, 0.4);
        Iterable<Point2D> in = tree.range(rect);
        for (Point2D i : in) System.out.println(i);
        
        // tests that our nearest neighbor method is working
        System.out.println("Testing Nearest Neighbor");
        System.out.println(tree.nearest(new Point2D(0.51, 0.24)));      
    }
}