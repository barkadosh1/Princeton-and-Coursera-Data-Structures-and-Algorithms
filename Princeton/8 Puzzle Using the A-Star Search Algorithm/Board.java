/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: The Board program I created has several useful methods. The first
 *  is the Board constructor, which takes a 2D array and converts it into a 1D
 *  array of all the tile values. The next method is the tileAt method, which 
 *  returns the tile value at the given i (row) and j (column) values. The size
 *  method simply returns the size (N) of one side of the grid. The hamming method
 *  is built to return the number of tiles that are not in the right place (meaning
 *  that they aren't in the place they would be in when the final solution is 
 *  reached. The manhattan function is a bit different, in that it finds the 
 *  distance/number of moves each tile is out of place (sum of how many rows and 
 *  columns the tile must move to be in place) and returns the sum of of those
 *  distances for all tiles. The isGoal method simply tells us if the board we
 *  are currently looking at is the solution (meaning that all tiles are in place).
 *  The isSolvable method lets us know whether the puzzle is even capable of being
 *  solved (and the method depends on whether N is even or odd). The equals method
 *  reveals whether two tiles are equal (same class, length, tiles in same indices, 
 *  etc.). The neighbors method finds the location of the 0, swaps the 0 
 *  with each of its 4 nodes (4 different boards are created -- 1 for each swap),
 *  and it returns a stack of boards with each neighbor board in the stack. Finally,
 *  the toString method simply returns a string representing the board in the format
 *  that we want it represented in. The main method simply runs some tests to verify
 *  that the methods function.
 ******************************************************************************/

// imports needed packages
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

// Creates Board class
public class Board {    
    private final int [] tileOneD; // instance variable of one dimensional array
                                   // created from 2D array, containing all tiles
    private final int N; // Size of either row or column (we have N by N grid)
    
    // construct a board from an N-by-N array of tiles
    // (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles) {
        N = tiles[0].length; // length of a row/column   
        tileOneD = new int[N*N]; // initializes one-D array of size N^2
        
        // converts 2D array into a 1D array
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) { 
                tileOneD[N*i + j] = tiles[i][j]; 
            }
        }
    }
    
    // returns tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j) {
        if (tileOneD[N*i + j] == 0) return 0;
        if (i < 0 || i > N-1) throw new IndexOutOfBoundsException();
        if (j < 0 || j > N-1) throw new IndexOutOfBoundsException();
        return tileOneD[N*i + j]; 
    }
    
    // returns board size N 
    public int size() {
        return (int) Math.sqrt(tileOneD.length); // N^2 items, so N = square 
                                                 // root of # of items (length)
    }
    
    // returns number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N*N; i++) {
            if (tileOneD[i] == 0) continue;
            if (tileOneD[i] != i + 1) count++;
        }
        return count;
    }
    
    // returns sum of Manhattan distances between tiles and goal
    public int manhattan() { 
        // initializing several values to zero
        int rowOne = 0;
        int colOne = 0;
        int rowTwo = 0;
        int colTwo = 0;
        int sum = 0;
        int distance = 0;
        
        for (int i = 0; i < N*N; i++) {
            if (tileOneD[i] == 0) continue;
            // my method for getting column and row of current location of key,
            // and of the location it should be at (if not in place)
            colOne = i % N;
            rowOne = (i - colOne)/N;
            colTwo = (tileOneD[i] - 1) % N;
            rowTwo = (tileOneD[i] - 1 - colTwo)/N;
            
            // takes distance, and creates a sum of distances
            distance = Math.abs(rowOne-rowTwo) + Math.abs(colOne - colTwo);
            sum = sum + distance;          
        }
        return sum;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        // returns true if all tiles are in place (checked by hamming)
        if (hamming() == 0) return true; 
        return false;
    }
    
    // is this board solvable?
    public boolean isSolvable() {
        
        int inv = 0; 
        int blank = 0; 
        int total = 0;
        
        // if N by N grid has an odd value for N
        if (N % 2 != 0) {
            for (int i = 0; i < N*N; i++) {
                if (tileOneD[i] == 0) continue;
                
                // use j = i, because we should never be checking behind i
                for (int j = i; j < N*N; j++) {
                    if (tileOneD[j] == 0) continue;
                    // increments inv every time it finds an inversion
                    // where the the tile at j is less than i
                    else if (tileOneD[j] < tileOneD[i]) inv++;
                }
            }
            return (inv % 2 == 0); // solvable if even number of inversions
        }
        
        // if N by N grid has an even value for N
        else {
            for (int i = 0; i < N*N; i++) {
                // searching for where the 0 value appears
                if (tileOneD[i] == 0) {
                    blank = (i - (i % N))/N; // calculates what row 0 is in
                    continue;
                }
                
                // use j = i, because we should never be checking behind i
                for (int j = i; j < N*N; j++) {
                    if (tileOneD[j] == 0) continue;
                    // increments inv every time it finds an inversion
                    // where the the tile at j is less than i
                    else if (tileOneD[j] < tileOneD[i]) inv++;
                }
            }
            
            total = inv + blank; 
            return (total % 2 != 0); // solvable if sum of number of inversions and
                                     // and row where 0 appears is odd
            
        }
        
    }
    
    // checks if this board this board equals y
    // ** CITE: I referenced textbook p. 103 to model my code in this method
    public boolean equals(Object y) {
        if (this == y) return true;         
        if (y == null) return false;         
        if (this.getClass() != y.getClass()) return false;
        
        Board that = (Board) y; 
        if (this.size() != that.size()) return false; 
        
        for (int i = 0; i < N*N; i++) {
            if (this.tileOneD[i] != that.tileOneD[i]) return false;
        }
        
        return true;
    }
    
    // creates a stack with all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        
        int zero = 0;
        
        // this loop finds where the 0 key is (creating neighbors depend on this)
        for (int i = 0; i < N*N; i++) {
            if (tileOneD[i] == 0) {
                zero = i;
                continue;
            }
        }
        
        // column and row where 0 appears
        int col = zero % N;
        int row = (zero - col)/N;
        
        // only runs code for first neighbor if 0 is not in the left most column
        if (col != 0) {
            // recreate 2D array (needed to create a board for stack of boards)
            int [][] firstTwoD = new int[N][N]; 
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    firstTwoD[i][j] = tileOneD[N*i + j];
                }
            }
            
            // switches first neighbor into index where 0 is, and vice versa
            firstTwoD[row][col] = firstTwoD[row][col - 1];
            firstTwoD[row][col - 1] = 0;
            
            // creates a board with this modified 2D array
            Board firstBoard = new Board(firstTwoD);
            
            // pushes this first board/neighbor into the neighbors stack
            neighbors.push(firstBoard);
        }
        
        // only runs code for second neighbor if 0 is not in the right most column
        if (col != N-1) {
            // recreate 2D array (needed to create a board for stack of boards)
            int [][] secondTwoD = new int[N][N]; 
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    secondTwoD[i][j] = tileOneD[N*i + j];
                }
            }
            
            // switches second neighbor into index where 0 is, and vice versa
            secondTwoD[row][col] = secondTwoD[row][col + 1];
            secondTwoD[row][col + 1] = 0;
            
            // creates a board with this modified 2D array
            Board secondBoard = new Board(secondTwoD);
            
            // pushes this second board/neighbor into the neighbors stack
            neighbors.push(secondBoard);
        }
        
        // only runs code for third neighbor if 0 is not in the top most row
        if (row != 0) {
            // recreate 2D array (needed to create a board for stack of boards)
            int [][] thirdTwoD = new int[N][N]; 
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    thirdTwoD[i][j] = tileOneD[N*i + j];
                }
            }
            
            // switches third neighbor into index where 0 is, and vice versa
            thirdTwoD[row][col] = thirdTwoD[row - 1][col];
            thirdTwoD[row - 1][col] = 0;
            
            // creates a board with this modified 2D array
            Board thirdBoard = new Board(thirdTwoD);
            
            // pushes this third board/neighbor into the neighbors stack
            neighbors.push(thirdBoard);
        }
        
        // only runs code for fourth neighbor if 0 is not in the bottom most row
        if (row != N-1) {
            // recreate 2D array (needed to create a board for stack of boards)
            int [][] fourthTwoD = new int[N][N]; 
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    fourthTwoD[i][j] = tileOneD[N*i + j];
                }
            }
            
            // switches fourth neighbor into index where 0 is, and vice versa
            fourthTwoD[row][col] = fourthTwoD[row + 1][col];
            fourthTwoD[row + 1][col] = 0;
            
            // creates a board with this modified 2D array
            Board fourthBoard = new Board(fourthTwoD);
            
            // pushes this fourth board/neighbor into the neighbors stack
            neighbors.push(fourthBoard);
        }
        
        return (Iterable<Board>) neighbors;
    }
    
    // string representation of this board (in the output format specified below)
    // ** CITE: I used the toString example code from the assignment/checklist page
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    // unit testing (required)
    public static void main(String[] args) {
       
        // The following lines just create several two-D arrays
        int [][] m = {
            {1, 6, 3, 2},
            {7, 4, 8, 13},
            {9, 10, 11, 0},
            {5, 14, 15, 12}
        };
        
        
        int [][] n = {
            {1, 2, 3, 6},
            {5, 4, 7, 8},
            {9, 10, 11, 0},
            {13, 15, 14, 12}
        };
        
        
        int [][] a = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
        };
        
        int [][] b = {
            {1, 0, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        
        int [][] c = {
            {1, 0, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        
        int [][] f = {
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        
        
        int [][] w = {
            {2, 1, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        
        
        // Creates boards out of 2D arrays. Verifies our Board constructor
        Board k = new Board(f);
        Board l = new Board(w);     
        Board test = new Board(m);       
        Board testTwo = new Board(n);   
        Board control = new Board(a);       
        Board testThree = new Board(b);       
        Board testFour = new Board(c);
        
        // the following lines test that tileAt, size, hamming, manhattan, and 
        // isGoal all function as I intended them to
        StdOut.println(test.tileAt(2, 0));
        StdOut.println(test.size());
        StdOut.println(test.hamming());
        StdOut.println(test.manhattan());
        StdOut.println(test.isGoal());
        
        StdOut.println(testTwo.tileAt(2, 0));
        StdOut.println(testTwo.size());
        StdOut.println(testTwo.hamming());
        StdOut.println(testTwo.manhattan());
        StdOut.println(testTwo.isGoal());  
        
        StdOut.println(control.tileAt(2, 0));
        StdOut.println(control.size());
        StdOut.println(control.hamming());
        StdOut.println(control.manhattan());
        StdOut.println(control.isGoal());
        
        // The following lines verify that isSolvable functions as I intended
        StdOut.println(test.isSolvable());
        StdOut.println(testTwo.isSolvable());
        StdOut.println(control.isSolvable());
        StdOut.println(testThree.isSolvable());
        
        // The following three lines verify that equals functions as I intended
        StdOut.println(test.equals(testTwo));
        StdOut.println(test.equals(test));
        StdOut.println(testThree.equals(testFour));
        
        // Verifies that my toString functions/ outputs the correct string format
        StdOut.println(testFour.toString());
        
        // Verifies that my neighbors method returns the correct neighbors
        StdOut.println(l.neighbors());
        StdOut.println(l.neighbors());     
        
    }  
}