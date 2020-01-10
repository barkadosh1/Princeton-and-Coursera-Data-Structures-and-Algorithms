/******************************************************************************
 *  Name:    Tal Bass
 *  NetID:   tabass
 *  Precept: P04
 *
 *  Partner Name:    Bar Kadosh
 *  Partner NetID:   bkadosh
 *  Partner Precept: P03
 * 
 *  Description:  KdTreeST is a symbol table which implements a 2D tree. The
 *  tree alternates sorting the points and their respective values by the x and
 *  y coordinates. The class has a private inner node class that represents each
 *  node in the tree. Each node contains the point, value, rectangle, and
 *  references to the children node. The class also has a constructor KdTreeST(),
 *  an isEmpty() method which checks if the symbol table is empty, and a size()
 *  method which returns the size of the symbol table (how many points there 
 *  are). Two private helper methods compare() are implemented to help the other
 *  methods. compare is essentially a compareTo() method that alternates 
 *  comparing the x and y coordinates based on the count. There are also methods
 *  put(), get(), and contains() which put in a point to the symbol table, get
 *  the qiven point's value, and check if the symbol table contains a certain
 *  point. Each of these three methods have private helper methods. The method
 *  points() returns all the points of the symbol table in an iterable. Range()
 *  returns all of the points contained within a given rectangle in an iterable. 
 *  Nearest() returns the point nearest a given query point. * 
 ******************************************************************************/

// importing needed packages 
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

// creating our class
public class KdTreeST<Value> {
    private Node root, machamp; // instance variables: root Node, and machamp is
                                // champion node with minimum distance in nearest
    private Queue<Point2D> in; // instance variable for queue of all points in ST
    private int size; // instance variable for the size of our 2D tree
    private double dist; // instance variable for minimum distance
    
    // Creates our Node Class
    private class Node implements Comparable<Node> {
        // the point
        private Point2D p;
        // the symbol table maps the point to this value
        private Value value;  
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect; 
        // the left/bottom subtree
        private Node lb;    
        // the right/top subtree
        private Node rt;
        
        // Node constructor
        public Node(Point2D p, Value value, double[] rect) {
            this.p = p;
            this.value = value;
            this.rect = new RectHV(rect[0], rect[1], rect[2], rect[3]);
        }
        
        // compareTo method for comparing points
        public int compareTo(Node that) {
            return this.p.compareTo(that.p);
        }
    }
    
    // constructor creates an empty symbol table of points 
    public KdTreeST() {
        // simply indicates that the current size is 0
        size = 0;
    }
    
    // is the symbol table empty? 
    public boolean isEmpty() {
        // returns that it is empty if size is 0
        return (size == 0);
    }
    
    // number of points 
    public int size() {
        // returns the value of size instance variable
        return size;
    }
    
    // Implements one of our compare methods for comparing points for get
    private int compare(Node n, Point2D p, int count) {
        // if count is even (horizontal) returns -1, 1, or 0 depending on case
        if ((count % 2) == 0) {
            // if y of p is less than y of point of n
            if (p.y() < n.p.y()) return -1;
            // if y of p is greater than y of point of n
            else if (p.y() >= n.p.y() && p.x() != n.p.x()) return 1;
            // if they are equal
            else return 0;
        }
        
        // if count is odd (vertical) returns -1, 1, or 0 depending on case
        else {
            // if x of p is less than x of point of n
            if (p.x() < n.p.x()) return -1;
            // if x of p is greater than x of point of n 
            else if (p.x() >= n.p.x() && p.y() != n.p.y()) return 1;
            // if they are equal
            else return 0;
        }
    }
    
    // implements one of our compare methods for comparing points for put and
    // resizing the rectangles as needed
    private int compare(Node n, Point2D p, int count, double[] rect) {
       
        // for case where count is even (horizontal)
        if ((count % 2) == 0) {
            // if y of p is less than y of n
            if (p.y() < n.p.y()) {
                // reassigning ymax
                rect[3] = n.p.y(); 
                return -1;
            }
            // if y of p is greater than y of n
            else if (p.y() > n.p.y()) {
                // reassgning y min
                rect[1] = n.p.y(); 
                return 1;
            }
            // if y of p is equal to y of n
            else return 0;
        }
        
        // for case where count is odd (vertical)
        else {
            // if x of p is less than x of n
            if (p.x() < n.p.x()) {
                // reassign xmax
                rect[2] = n.p.x(); 
                return -1;
            }
            // if x of p is greater than x of n
            else if (p.x() > n.p.x()) {
                // reassign xmin
                rect[0] = n.p.x(); 
                return 1;
            }
            else return 0;
        }
    }
    
    // associate the value val with point p
    public void put(Point2D p, Value val) {   
        if (p == null || val == null) throw new NullPointerException();
        double max = Double.MAX_VALUE;
        // creates rectangle of root
        double[] rect = {-max, -max, max, max};
        size++;
        // calls private helper method
        root = put(root, p, val, 1, rect);
    }
    
    // private recursive helper method for put
    private Node put(Node n, Point2D p, Value val, int count, double[] rect) {
        if (n == null) return new Node(p, val, rect);
        
        int cmp = compare(n, p, count, rect);
        // If it less put it in the left/bottom
        if (cmp < 0) n.lb = put(n.lb, p, val, ++count, rect);
        // If greater than or equal to (but not same points) but it in right/top
        else if (cmp >= 0 && !p.equals(n.p)) n.rt = put(n.rt, p, val, ++count, rect);
        // if they are equal
        else {
            n.value = val;
            // subtract 1 from size (we aren't putting in a point that is the same)
            size--; 
        }
        return n;
    }
    
    // value associated with point p 
    public Value get(Point2D p) {
        if (p == null) throw new NullPointerException();
        return get(root, p, 1);
    }
    // private helper method for get()
    private Value get(Node n, Point2D p, int count) {
        // Return value associated with key in the subtree rooted at n;
        // return null if key not present in subtree rooted at n.
        if (n == null) return null;
        // compare the two
        int cmp = compare(n, p, count);
        // if less look in left/bottom subtree
        if (cmp < 0) return get(n.lb, p, ++count);
        // if greater than/equal (but not the same point) look in right/top subtree
        else if (cmp >= 0 && !p.equals(n.p)) return get(n.rt, p, ++count);
        else return n.value;
    }
    
    // does the symbol table contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return contains(root, p, 1);
    }
    
    // private helper method for contains
    private boolean contains(Node n, Point2D p, int count) {
        // Return value associated with key in the subtree rooted at n;
        // return null if key not present in subtree rooted at n.
        if (n == null) return false;
        // compare the two
        int cmp = compare(n, p, count);
        // if less, check left/bottom subtree
        if (cmp < 0) return contains(n.lb, p, ++count);
        // if greater/equal to (but not the same point) look in right/top subtree
        else if (cmp >= 0 && !p.equals(n.p)) return contains(n.rt, p, ++count);
        else return true;
    }
    
    // all points in the symbol table 
    public Iterable<Point2D> points() {
        // Queue holds temporary points
        Queue<Node> queue = new Queue<Node>();
        // Queue containing all the points
        Queue<Point2D> points = new Queue<Point2D>();
        queue.enqueue(root);
        // Go through all the points
        while (!queue.isEmpty())
        {
            // If current point in queue is not null, enqueue into points
            Node x = queue.dequeue();
            if (x == null) continue;
            points.enqueue(x.p);
            // Enqueue the two subtrees into queue
            queue.enqueue(x.lb);
            queue.enqueue(x.rt);
        }
        return points;
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        in = new Queue<Point2D>();
        range(root, rect);
        return in;
    }
    
    // private helper method for range
    private void range(Node temp, RectHV rect) {
        if (temp == null) return;
        // If the node's rectangle does not intersect the curent rectangle
        // Do not check the node or the subtrees
        if (!temp.rect.intersects(rect)) return;
        // Check if the current point is contained in the ractangle
        if (rect.contains(temp.p)) in.enqueue(temp.p);
        // Call for lb and rt subtrees
        range(temp.lb, rect);
        range(temp.rt, rect);
    }
    
    // a nearest neighbor to point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        machamp = root;
        dist = Double.MAX_VALUE;
        nearest(root, p, 1);
        if (machamp != null) return machamp.p;
        return null;
    }
    
    // private helper method for nearest
    private void nearest(Node temp, Point2D query, int count) {
        if (temp == null) return;
        // If the distance form the node's rectangle is not less than the current
        // minimum distance, do not check this node or its subtrees
        if (temp.rect.distanceSquaredTo(query) >= dist) return;
        // If the distance between the query point and current point is less than
        // the min distance, update the distance and the new champ, machamp
        if (temp.p.distanceSquaredTo(query) < dist) {
            dist = temp.p.distanceSquaredTo(query);
            machamp = temp;
        }
        // First calls the subtree who's point is rooted on the query side
        if (count % 2 == 0) {
            if (query.y() <= temp.p.y()) {
                nearest(temp.lb, query, ++count);
                nearest(temp.rt, query, ++count);
            }
            else {
                nearest(temp.rt, query, ++count);
                nearest(temp.lb, query, ++count);
            }
        }
        else {
            if (query.x() <= temp.p.x()) {
                nearest(temp.lb, query, ++count);
                nearest(temp.rt, query, ++count);
            }
            else {
                nearest(temp.rt, query, ++count);
                nearest(temp.lb, query, ++count);
            }
        }
    }
    
    // unit testing (required) 
    public static void main(String[] args) {
        // Insert a couple points and check the methods
        KdTreeST<Integer> tree = new KdTreeST<Integer>();
        // Test put
        tree.put(new Point2D(0.5, 0.5), 1);
        tree.put(new Point2D(0.25, 0.25), 2);
        tree.put(new Point2D(0.20, 0.25), 3);
        tree.put(new Point2D(0.75, 0.75), 4);
        tree.put(new Point2D(0.5, 0.25), 5);
        // Test isempty
        System.out.println(tree.isEmpty());
        // Test get
        System.out.println(tree.get(new Point2D(0.5, 0.25)));
        // Test contains
        System.out.println(tree.contains(new Point2D(0.5, 0.25)));
        System.out.println(tree.contains(new Point2D(0.5, 0.1)));
        // Test points
        Iterable<Point2D> points = tree.points();
        System.out.println("Points");
        for (Point2D i : points) System.out.println(i);
        
        // Test range method
        System.out.println("Testing range");
        RectHV rect = new RectHV(0.0, 0.0, 0.4, 0.4);
        Iterable<Point2D> in = tree.range(rect);
        for (Point2D i : in) System.out.println(i);
        
        // Test nearest neighbor
        System.out.println("Testing Nearest Neighbor");
        System.out.println(tree.nearest(new Point2D(0.5, 0.8)));
        
        // Test size
        System.out.println("Check size");
        System.out.println(tree.size());
        tree.put(new Point2D(0.5, 0.5), 2);
        System.out.println(tree.size());
        System.out.println("Points");
        for (Point2D i : tree.points()) System.out.println(i);
        
        // Test for empty trees
        tree = new KdTreeST<Integer>();
        for (Point2D i : tree.points()) System.out.println(i);
        System.out.println("Pass");
        in = tree.range(rect);
        for (Point2D i : in) System.out.println(i);
        System.out.println("Pass 2");
        
        // Test for outside points
        tree.put(new Point2D(-1.0, -1.0), 3);
        System.out.println(tree.get(new Point2D(-1.0, -1.0)));
        tree.put(new Point2D(-8.0, -8.0), 4);
        System.out.println(tree.get(new Point2D(-8.0, -8.0)));

    }
}