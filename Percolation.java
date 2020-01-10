/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: This program creates a class with several very useful methods. 
 *  On a general level it is capable of creating an N by N grid, and running an 
 *  experiment to check whether a particular grid will percolate or not, which is 
 *  when it has a route from top to bottom. The open method takes a blocked site 
 *  and makes it open. It also takes any surrounding sites that are open and unites 
 *  the two sites. The isOpen method checks whether a site is open, while the 
 *  isClosed method checks whether a site is closed. The numberOfOpenSites method 
 *  simply counts how many sites are open. The CoordtoInt method is a private method 
 *  I implimented intended to take 2D coordinates and change them into 1D integers 
 *  that make it easier to introduce virtual points. The percolates method simply 
 *  checks whether the top virtual site connects to the bottom virtual site, and 
 *  in doing so, it is checking whether the system ultimately percolates. The main 
 *  method simply verifies that all of these methods function as I intended.
 ******************************************************************************/

// Importing needed packages
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    
    private int size; // represents the side length of the grid
    private int [][] grid; // double array representing the grid
    private int virtualTop; // virtual top site above top row
    private int virtualBottom; // virtual bottom site below bottom row
    private WeightedQuickUnionUF myuf; // my WeightedQuickFindUF object
    
   // Creates N by N grid, with all sites initially blocked and with top row
   // connected to top virtual site, and bottom row to bottom virtual site 
   public Percolation(int N) {                
       
       // Creates an exception if try to make a grid without any sites
       if (N <= 0) throw new IllegalArgumentException();
       size = N;
       
       // Creates N by N double array, and initializes all sites to 0 (blocked)
       grid = new int [size][size]; 
       
       // Values below guarantee no overlap in value with any other site
       // when converting from 2D to 1D
       virtualTop = size*size; // Creates a virtual top site
       virtualBottom = size*size + 1; // Creates a virtual bottom site
       
       myuf = new WeightedQuickUnionUF(size*size+2); // size include N by N grid 
                                           // sites and includes 2 virtual points
       
       // This for loop links all sites on top row with virtual top site
       // This for loop also links all sites on bottom row with virtual bottom site
       for (int i = 0; i < size; i++) {
           myuf.union(virtualTop, coordToInt(0, i)); 
           myuf.union(virtualBottom, coordToInt(size-1, i));
       }      
   }
   
   // opens the site if it is not open, and based on the location of the site
   // it checks if the surrounding sites are open. If so, it unites the two
   public void open(int row, int col) {   
       
       // creates exception if a site outside of N by N grid is called
       if ((row < 0 || row >= size) || (col < 0 || col >= size)) {
           throw new IndexOutOfBoundsException();
       }
       
       if (isOpen(row, col) != true) {
           
           grid[row][col] = 1; // opens a site if it is closed
           
           // Checks for top left corner of grid
           if (row == 0 && col == 0) {
               if (grid[row][col + 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col + 1));
               }
               
               if (grid[row + 1][col] == 1)  {    
                   myuf.union(coordToInt(row, col), coordToInt(row + 1, col));
               }
           }
           
           // Checks for bottom right corner of grid
           else if (row == size - 1 && col == size - 1) {               
               if (grid[row - 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row - 1, col));
               }
               
               if (grid[row][col - 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col - 1));
               }
           }
           
           // Checks for top right corner of grid
           else if (row == 0 && col == size - 1) {         
               if (grid[row + 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row + 1, col));
               }
               
               if (grid[row][col - 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col - 1));
               }
           }
           
           // Checks for bottom left corner of grid
           else if (row == size - 1 && col == 0) {
               if (grid[row][col + 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col + 1));
               }
               
               if (grid[row - 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row - 1, col));
               }
           }
           
           // Checks for top row, excluding corners
           else if (row == 0) {                   
               if (grid[row + 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row + 1, col)); 
               } 
               
               if (grid[row][col - 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col - 1));
               }
               
               if (grid[row][col + 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col + 1));  
               }
           }
           
           // Checks for bottom row, excluding corners 
           else if (row == size - 1) {          
               if (grid[row - 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row - 1, col));
               }
               
               if (grid[row][col - 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col - 1));
               }
               
               if (grid[row][col + 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col + 1)); 
               }
           }
           
           // Checks for left-most column, exluding corners
           else if (col == 0) {
               if (grid[row - 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row - 1, col));
               }
               
               if (grid[row + 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row + 1, col)); 
               }
               if (grid[row][col + 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col + 1));  
               }
           }
           
           // Checks for right-most column, excluding corners
           else if (col == size - 1) {
               if (grid[row - 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row - 1, col));
               }
               
               if (grid[row + 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row + 1, col)); 
               }
               if (grid[row][col - 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col - 1)); 
               }
           }
            
           // Checks sites that don't qualify for cases above (all sites not on outer edge)
           else {
               if (grid[row - 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row - 1, col));
               }
               
               if (grid[row + 1][col] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row + 1, col));
               }
               
               if (grid[row][col - 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col - 1)); 
               }
               
               if (grid[row][col + 1] == 1) {
                   myuf.union(coordToInt(row, col), coordToInt(row, col + 1));
               }
           }       
       }
   }
       
   // is the site (row, col) open?
   public boolean isOpen(int row, int col) { 
       
       // creates exception if a site outside of N by N grid is called
       if ((row < 0 || row >= size) || (col < 0 || col >= size)) {
           throw new IndexOutOfBoundsException();
       }
       return (grid[row][col] == 1); // returns true if the site is open    
   }
       
   // is the site (row, col) full?
   public boolean isFull(int row, int col) {
       
       // creates exception if a site outside of N by N grid is called
       if ((row < 0 || row >= size) || (col < 0 || col >= size)) { 
           throw new IndexOutOfBoundsException();
       }
       // returns true if the site is full
       return myuf.connected(virtualTop, coordToInt(row, col)) && isOpen(row, col); 
   }
       
   // returns the number of open sites by incrementing the count 
   // every time an open site is found
   public int numberOfOpenSites() {          
       int count = 0; 
       for (int i = 0; i < size; i++) {
           for (int j = 0; j < size; j++) {
               if (isOpen(i, j) == true) count++;                                       
           }
       }
       return count;
   }
   
   // converts 2D points into a 1D integer value, so it is easier to keep track of
   private int coordToInt(int row, int col) {
       return (size*row + col); // this method differentiates (1,0) from (0,1)
                                // Gives unique integer value to each coordinate
   }
   
   // This method verifies whether the system percolates or not      
   public boolean percolates() {    
  
       // returns true if the virtual bottom and virtual top are connected
       return myuf.connected(virtualTop, virtualBottom); 
   }
 
   // This main method makes sure that each of the other methods functions
   public static void main(String[] args) {   
       
       int N = 11;
       Percolation newPerc = new Percolation(N); // verifies the percolation class
     
       // verifies that open works by opening up series of sites that will percolate
       newPerc.open(0, 1);
       newPerc.open(1, 1);
       newPerc.open(2, 1);
       newPerc.open(3, 1);
       newPerc.open(4, 1);
       newPerc.open(5, 1);
       newPerc.open(5, 2);
       newPerc.open(6, 2);
       newPerc.open(6, 3);
       newPerc.open(7, 3);
       newPerc.open(8, 3);
       newPerc.open(9, 3);
       newPerc.open(9, 2);
       newPerc.open(10, 2);

       StdOut.println(newPerc.grid[1][1]); // verifies that it opened site
       
       StdOut.println(newPerc.isOpen(1, 1)); // verifies that isOpen functions
       StdOut.println(newPerc.isFull(1, 1)); // verifies that isFull functions
       
       StdOut.println(newPerc.numberOfOpenSites()); // correctly counts open sites
       StdOut.println(newPerc.coordToInt(3, 4)); // converts from 2D to 1D
       
       StdOut.println(newPerc.percolates()); // Verifies whether it percolates 
   }  
}