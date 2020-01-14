/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  SCA
 ******************************************************************************/

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.DirectedCycle;

public class ShortestCommonAncestor {
    private Digraph tree; 
    private final int size;
    private int root;
    
   // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) throw new java.lang.NullPointerException();
        
        Digraph tree_copy = new Digraph(G);
        tree = tree_copy; 
        
        size = tree.V();
        
        int count = 0;
        
        for (int i = 0; i < size; i++) {
            if (tree.outdegree(i) == 0) {
                root = i;
                count++;
            }          
        }
        
        DirectedCycle dc = new DirectedCycle(G);
        
        if (count != 1) throw new java.lang.IllegalArgumentException();
        if (dc.hasCycle()) throw new java.lang.IllegalArgumentException();
        
    }

   // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (v < 0 || v >= size) throw new java.lang.IndexOutOfBoundsException();
        if (w < 0 || w >= size) throw new java.lang.IndexOutOfBoundsException(); 
        BreadthFirstDirectedPaths pathOne = new BreadthFirstDirectedPaths(tree, v);
        BreadthFirstDirectedPaths pathTwo = new BreadthFirstDirectedPaths(tree, w);
        
        int distOne = 0;
        int distTwo = 0;
        int champ = Integer.MAX_VALUE;
        int sum = Integer.MAX_VALUE;
        
        for (int i = 0; i < size; i ++) {
            if (pathOne.hasPathTo(i) == true && pathTwo.hasPathTo(i) == true) {
                sum = pathOne.distTo(i) + pathTwo.distTo(i);
            }
            
            if (sum < champ) {
                champ = sum;
            }         
        }
        
        return champ;
    }

   // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {

        if (v < 0 || v >= size) throw new java.lang.IndexOutOfBoundsException();
        if (w < 0 || w >= size) throw new java.lang.IndexOutOfBoundsException();     
        BreadthFirstDirectedPaths pathOne = new BreadthFirstDirectedPaths(tree, v);
        BreadthFirstDirectedPaths pathTwo = new BreadthFirstDirectedPaths(tree, w);
        
        int distOne = 0;
        int distTwo = 0;
        int ancestor = -1;
        int champ = Integer.MAX_VALUE;
        int sum = Integer.MAX_VALUE;
        
        for (int i = 0; i < size; i ++) {
            if (pathOne.hasPathTo(i) == true && pathTwo.hasPathTo(i) == true) {
                sum = pathOne.distTo(i) + pathTwo.distTo(i);
            }
            
            if (sum < champ) {
                champ = sum;
                ancestor = i;
            }         
        }
        
        return ancestor;
        
    }

    
   // length of shortest ancestral path of vertex subsets A and B
    public int length(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        
        if (subsetA == null) throw new java.lang.NullPointerException();
        if (subsetB == null) throw new java.lang.NullPointerException();
        
        int countOne = 0;
        for (Integer i : subsetA) {
            if (i < 0 || i >= size) throw new java.lang.IndexOutOfBoundsException();
            countOne++;
        }
        
        if (countOne == 0) throw new java.lang.IllegalArgumentException(); 
        
        int countTwo = 0; 
        for (Integer i : subsetB) {
            if (i < 0 || i >= size) throw new java.lang.IndexOutOfBoundsException();  
            countTwo++;
        }
        
        if (countTwo == 0) throw new java.lang.IllegalArgumentException(); 
        
        BreadthFirstDirectedPaths pathOne = new BreadthFirstDirectedPaths(tree, subsetA);
        BreadthFirstDirectedPaths pathTwo = new BreadthFirstDirectedPaths(tree, subsetB);
        
        int distOne = 0;
        int distTwo = 0;
        int champ = Integer.MAX_VALUE;
        int sum = Integer.MAX_VALUE;
        
        for (int i = 0; i < size; i ++) {
            if (pathOne.hasPathTo(i) == true && pathTwo.hasPathTo(i) == true) {
                sum = pathOne.distTo(i) + pathTwo.distTo(i);
            }
            
            if (sum < champ) {
                champ = sum;
            }         
        }
        
        return champ;
    }

   // a shortest common ancestor of vertex subsets A and B
    public int ancestor(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        
        if (subsetA == null) throw new java.lang.NullPointerException();
        if (subsetB == null) throw new java.lang.NullPointerException();
        
        int countOne = 0;
        for (Integer i : subsetA) {
            if (i < 0 || i >= size) throw new java.lang.IndexOutOfBoundsException(); 
            countOne++;
        }
        
        if (countOne == 0) throw new java.lang.IllegalArgumentException();
        
        int countTwo = 0;
        for (Integer i : subsetB) {
            if (i < 0 || i >= size) throw new java.lang.IndexOutOfBoundsException(); 
            countTwo++;
        }
        
        if (countTwo == 0) throw new java.lang.IllegalArgumentException();
        
        BreadthFirstDirectedPaths pathOne = new BreadthFirstDirectedPaths(tree, subsetA);
        BreadthFirstDirectedPaths pathTwo = new BreadthFirstDirectedPaths(tree, subsetB);
        
        int distOne = 0;
        int distTwo = 0;
        int ancestor = -1;
        int champ = Integer.MAX_VALUE;
        int sum = Integer.MAX_VALUE;
        
        for (int i = 0; i < size; i ++) {
            if (pathOne.hasPathTo(i) == true && pathTwo.hasPathTo(i) == true) {
                sum = pathOne.distTo(i) + pathTwo.distTo(i);
            }
            
            if (sum < champ) {
                champ = sum;
                ancestor = i;
            }         
        }
        
        return ancestor;
    }
    

   // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
    
}