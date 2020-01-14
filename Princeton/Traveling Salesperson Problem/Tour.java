/*****************************************************************************
 * Name: Bink Sitawarin
 * Netid: chawins
 * Precept: P06
 * Written: 11/22/2014
 * 
 * Partner's name: Bar Kadosh
 * Partner's login: bkadosh
 * Partner's precept: P02C  
 * 
 * Compilation: javac-introcs Tour.java
 * Execution: java-introcs Tour
 * Dependencies: Point.java, StdOut.java 
 * 
 * This builds a class Tour that contains many useful methods. The first Tour 
 * constructor creates an empty tour, while the second creates a tour with four 
 * points. The method show prints the points of the tour to standard output, 
 * while the method draw illustrates the path of the tour. The method size 
 * returns the amount of points in the tour, while the method distance returns
 * the total distance travelled in the tour or in the points being looked at. 
 * The method insertNearest inserts a point p into the tour near the point 
 * where it will be closest to distance wise, while the method insertSmallest
 * inserts a point p into the tour such that it will cause the smallest increase
 * in the total distance travelled on the tour. The main method is just used 
 * for testing. 
 * 
 * 
 * The template below comes from the course assignment page for TSP at:
 * http://www.cs.princeton.edu/courses/archive/fall14/cos126/
 * assignments/tsp.html
 ****************************************************************************/

public class Tour {
    
    // Node class
    private class Node {
        private Point p; // point in tour
        private Node next; // node following the current node being looked at
    }
    private Node first; // first node in tour
    
    
    // This simply creates an empty tour, where first equals an empty node
    public Tour() {
        first = new Node();
    }
    
    /* This creates a 4 point tour used for testing purposes. We assign each 
     * node with a point and then use pointers to get a->b->c->d->a */
    public Tour(Point a, Point b, Point c, Point d) {
        
        first = new Node();
        Node nodeb = new Node();
        Node nodec = new Node();
        Node noded = new Node();
        
        first.p = a;
        nodeb.p = b;
        nodec.p = c;
        noded.p = d;
        
        first.next = nodeb;
        nodeb.next = nodec;
        nodec.next = noded;
        noded.next = first;
    }
    
    /* We incriment using .next and then print the tour to standard output, 
     * until it returns to the original point */
    public void show() {
        
        if (first.p == null) return; // so program won't crash if tour is empty
        
        Node current = first;
        
        do {
            StdOut.println(current.p);
            current = current.next;
        } 
        while (current != first);
    }
    
    // Same concept as show(), it draws the tour taken to standard draw
    public void draw() {
        
        if (first.p == null) return; // so program won't crash if tour is empty
        
        Node current = first;
        
        do {
            current.p.drawTo(current.next.p);
            current = current.next;
        } 
        while(current != first);
    }
    
    // returns number of points that are part of the tour
    public int size() {
        
        if (first.p == null) return 0; // returns size of 0 if tour is empty
        
        Node current = first;
        int size = 0;
        
        do {
            current = current.next;
            size++;
        } 
        while(current != first);
        
        return size;
    }
    
    /* returns the total distance of the tour through each point and 
     * returning to its first point */
    public double distance() {
        
        if (first.p == null) return 0; // returns zero if tour is empty
        if (first.next.p == null) return 0; // returns 0 if only 1 point
        
        Node current = first;
        double distance = 0;
        
        do {
            distance += current.p.distanceTo(current.next.p);
            current = current.next;
        } 
        while(current != first);
        
        return distance;
    }
    
    /* This method inserts a point p. It decides where to place point p by 
     * traversing on the tour and finding the distance from the new point being
     * inserted to the point already in the tour. Whichever distance is the 
     * smallest is where that point gets inserted */
    public void insertNearest(Point p) {
        
        double mindistance = Double.POSITIVE_INFINITY;
        double distance = 0;
        
        Node newNode = new Node();
        newNode.p = p;
        Node current = first;
        Node temp = new Node();
        
        /* If empty, assigns point being inserted as the new node 
         * and also as the next one pointed to */
        if (first.p == null) {
            first = newNode;
            first.next = first;
            return;
        }
        
        do {
            distance = current.p.distanceTo(newNode.p);
            
            if (distance < mindistance) {
                mindistance = distance;
                temp = current;
            }
            
            current = current.next;
        } 
        while(current != first);
        
        newNode.next = temp.next;
        temp.next = newNode;   
    }
    
    /* This method evaluates where the point should be inserted in order to 
     * create the smallest increase in the distance of the tour by subtracting 
     * the distance between two points and then adding the distances from each 
     * of those points to the inserted point, all from and to original 
     * distance */
    public void insertSmallest(Point p) {
        
        double mindistance = Double.POSITIVE_INFINITY;
        double distance = 0;
        double totalDistance = distance();
        
        Node newNode = new Node();
        newNode.p = p;
        Node current = first;
        Node temp = new Node();
        
        /* If empty, assigns point being inserted as the new node 
         * and also as the next one pointed to */
        if (first.p == null) {
            first = newNode;
            first.next = first;
            return;
        }
        
        do {
            distance = totalDistance - current.p.distanceTo(current.next.p) 
                + current.p.distanceTo(newNode.p) 
                + current.next.p.distanceTo(newNode.p);
            
            if (distance < mindistance) {
                mindistance = distance;
                temp = current;
            }  
            current = current.next;
        } 
        while(current != first);
        
        newNode.next = temp.next;
        temp.next = newNode;
    }
    
    // main method for testing
    public static void main(String[] args) {
        
        StdDraw.setXscale(0, 600);
        StdDraw.setYscale(0, 600);
        
        // define 4 points forming a square
        Point a = new Point(100.0, 100.0);
        Point b = new Point(500.0, 100.0);
        Point c = new Point(500.0, 500.0);
        Point d = new Point(100.0, 500.0);
        
        // Set up a Tour with those four points
        // The constructor should link a->b->c->d->a
        Tour squareTour = new Tour(a, b, c, d);
        
        // Output the Tour
        squareTour.show();
        squareTour.draw();
        StdOut.println(squareTour.size() + " " + squareTour.distance());
    }
}