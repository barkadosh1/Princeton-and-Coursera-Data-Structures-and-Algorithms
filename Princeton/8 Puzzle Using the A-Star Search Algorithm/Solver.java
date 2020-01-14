/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A 
 *  
 *  Description: The Solver program uses the A* algorithm t complete its goals.
 *  The first part of this Solver class is a SearchNode class and constructor, 
 *  which associates board, previous search node, number of moves, and manhattan 
 *  value with the search node that it creates. I also created a private comparator 
 *  method called Priority, which comapres two Search Nodes by their priority value,
 *  which is the sum of the number of moves to get to that board and the manhattan
 *  sum associated with that board. It returns a 1, -1, or 0, depending on if the
 *  priorities are equal, or which one is greater than the other. Then there is the
 *  Solver method. This method creates a minimum priority queue, with the Priority
 *  comparator. It removes the board with the smallest priority, and then enqueues
 *  the neighbors of that dequeued board. In the process, it will keep track of 
 *  each board involved int the progression through the previous reference of a 
 *  Search Node. Finally it will go through this progression and push this 
 *  progression into our solution Stack, including the actual solution board, which
 *  should be the last board pushed on to the stack. The next method is moves, which
 *  simply returns the size of the solution Stack minus one, which is equivalent
 *  to the number of moves needed to arrive at the final solution. Lastly,
 *  the solution method takes our solution Stack and copies it over to a new stack,
 *  which is so that solution Stack never gets modified. This new stack is then 
 *  popped, with each popped value pushed into a new stack. This is so we return 
 *  the stack in the desired order. Finally, that stack is returned. The main 
 *  method simply runs tests to verify that all methods function.
 ******************************************************************************/

// imports needed packages
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.Stack;
import edu.princeton.cs.algs4.StdOut;

// Creates our Solver class 
public class Solver {
    
    private Stack<Board> solutionStack; // Stack containing progression of Boards
    // from the initial board to final solution
    private SearchNode solution; // last SearchNode returned when solution reached
    
    // Creates our SearchNode class
    private class SearchNode {    
        private Board board; // board associated with search node
        private int moves; // number of from initial to current board
        private SearchNode previous; // represents search node before current
        private int manhattan; // manhattan sum computed for the current board
        
        // constructor for Search Node class. Assigns values for search node
        public SearchNode(Board board, SearchNode previous) {
            this.board = board;
            if (previous == null) moves = 0;
            else moves = previous.moves + 1;
            this.previous = previous;
            manhattan = board.manhattan();
        }
    }
    
    // Creates Priority comparator that compares search nodes by priority values
    private static class Priority implements Comparator<SearchNode> {
        
        // compare calculates priority values and returns proper value
        // depending on which one is larger, or if they are equal
        public int compare(SearchNode a, SearchNode b) {
            int aPriority = a.moves + a.manhattan;
            int bPriority = b.moves + b.manhattan;
            if (aPriority > bPriority) return 1;
            if (aPriority < bPriority) return -1;         
            return 0;
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        
        // throws exceptions if not solvable or initial is null
        if (initial.isSolvable() == false) throw new IllegalArgumentException();
        if (initial == null) throw new NullPointerException();
        
        // Creates a Priority comparator
        Comparator<SearchNode> comparator = new Priority();
        
        // creates minimum PQ, that compares priority values of search nodes
        MinPQ<SearchNode> minpq = new MinPQ<SearchNode>(comparator);
        
        // Creates our initial search node and inserts it into our min PQ 
        SearchNode first = new SearchNode(initial, null);
        minpq.insert(first);
        
        // initializes our solution Stack of boards instance variable
        solutionStack = new Stack<Board>();
        
        // initialize values before while loop
        boolean isGoal = false;
        int steps = 0;
        
        // only break out of while loop when search node dequeued is associated
        // with the board equivalent to the solution
        while (isGoal == false) {
            // dequeues minimum and assigns it to solution for now
            solution = minpq.delMin();
            
            // assigns true or false to isGoal depending on if the item just 
            // dequeued contains the solution board
            isGoal = solution.board.isGoal();
            
            // We use a for each loop because we need this to work regardless of
            // if client uses stack or queue 
            // Inserts neigbors into minPQ, unless any are null or equal the 
            // previous search node
            for (Board b : solution.board.neighbors()) {                      
                if (solution.previous != null) {
                    if (b.equals(solution.previous.board)) continue;
                }
                SearchNode neighbor = new SearchNode(b, solution);
                minpq.insert(neighbor);
            }
        } 
        
        // pushes solution into solution stack
        solutionStack.push(solution.board); 
        
        // creates search node for search node before current solution search node
        SearchNode prev = solution.previous;
        
        // only run this portion if the prev search node is not null
        if (prev != null) {
            // Use previous to go through search nodes, and as long as the search
            // node doesn't equal the solution, remain in the while loop
            while ((prev.board).equals(initial) == false) {
                // puts the previous board prev.board in the solution stack
                solutionStack.push(prev.board);
                // changes prev to now be the next previous search node
                prev = prev.previous;
            }
            
            // this last line pushes the final solution board into the stack
            solutionStack.push(prev.board);
        }    
    }
    
    // min number of moves to solve initial board
    public int moves() {
        return (solutionStack.size() - 1);
    }
    
    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        // creates two new stacks of boards
        Stack<Board> e = new Stack<Board>();
        Stack<Board> f = new Stack<Board>();
        
        // copies Solutionstack to e, without altering solutionStack
        for (Board d : solutionStack) e.push(d);
        
        // copies contents of e into f in reverse order 
        while (e.isEmpty() == false) f.push(e.pop());
        
        return f;
    }
    
    // unit testing
    public static void main(String[] args) {
        
        // Here we are creating several arrays of boards
        int [][] array = {
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        
        int [][] c = {
            {1, 2, 3},
            {4, 5, 0},
            {7, 8, 6}
        };
        
        int [][] eee = {
            {1,  2,  3,  4,  5,  6,  7,  8,  9, 10}, 
            {11, 12, 13, 14, 15, 16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25, 26, 27, 28, 29, 30},
            {31, 32, 33, 34, 35, 36, 37, 38, 39, 40},
            {41, 42, 43, 44, 45, 46, 47, 48, 49, 50},
            {51, 52, 53, 54, 55, 56, 57, 58, 59, 60},
            {61, 62, 63, 64, 65, 66, 67, 68, 69, 70},
            {71, 72, 73, 74, 75, 76, 77, 78, 79, 80},
            {81, 82, 83, 84, 85, 86, 87, 88, 89, 90},
            {91, 92, 93, 94, 95, 96, 97, 98, 99, 0} 
        };
        
        // Creates new boards from arrays
        Board test = new Board(array);
        Board testtwo = new Board(c);
        Board testthree = new Board(eee);
        
        // Verifies that our Solver method works
        Solver solve = new Solver(test);
        Solver solvetwo = new Solver(testtwo);
        Solver solvethree = new Solver(testthree);
        
        // tests that our moves and solution methods work
        StdOut.println(solve.moves());
        StdOut.println(solve.solution());
        StdOut.println(solve.moves());
        
        StdOut.println(solvetwo.moves());
        StdOut.println(solvetwo.solution());
        StdOut.println(solvetwo.moves());
        
        StdOut.println(solvethree.moves());
        StdOut.println(solvethree.solution());
        StdOut.println(solvethree.moves());    

    }    
}