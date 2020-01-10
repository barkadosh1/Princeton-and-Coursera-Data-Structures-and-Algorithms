/*****************************************************************************
 * Name: Bink Sitawarin
 * Netid: chawins
 * Precept: P06
 * Written: 01/08/2015
 * 
 * Partner's name: Bar Kadosh
 * Partner's login: bkadosh
 * Partner's precept: P02C  
 * 
 * Compilation: javac-introcs Blob.java
 * Execution: java-introcs Blob
 * Dependencies: None
 * 
 * Blob contains several useful methods. The blob constructor creates an empty
 * blob. The add method adds a pixel to the blob while simultaneously keeping
 * a count of the the pixels added. The mass method then uses that count to
 * print the total number of pixels added. The distanceTo method returns the
 * distance between the center of mass of the current blob and the center of
 * mass of a blob b. Lastly, the toString method returns a string containing
 * the current blob's mass and center of mass. 
 * 
 * The template comes from the assignment page for atomic matter: http://www.
 * cs.princeton.edu/courses/archive/fall14/cos126/assignments/atomic.html 
 * 
 ****************************************************************************/

public class Blob {
    
    private int numPoints; // number of points 
    private double xCenter, yCenter; // x and y centers of masses
    
    // constructs an empty blob
    public Blob() {
        numPoints = 0;
        xCenter = 0;
        yCenter = 0;  
    }
    
    // adds a pixel (i, j) to the blob and keeps count of pixels added
    public void add(int i, int j) {       
        xCenter = (xCenter*numPoints + i)/(numPoints + 1);
        yCenter = (yCenter*numPoints + j)/(numPoints + 1); 
        numPoints++;
    }
    
    // returns the number of pixels added (equivalent to the mass)
    public int mass() {        
        return numPoints;
    }
    
    // returns distance between centers of masses of this blob and blob b
    public double distanceTo(Blob b) {
        
        double deltaX = xCenter - b.xCenter;
        double deltaY = yCenter - b.yCenter;
        
        double distance = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        
        return distance;
    }

    // returns string containing this blob's mass and center of mass
    public String toString() {        
        return String.format("%2d (%8.4f, %8.4f)", numPoints, xCenter, yCenter);
    }
}