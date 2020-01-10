/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: This program is intended to investigate the threshold question 
 *  relating to percolation. We run T experiments on an N by N grid, in which we 
 *  investigate at what value (open sites divided by total sites) the system
 *  percolates. By using the mean, stddev, confidenceLow, and confidenceHigh 
 *  methods, we are able to investigate several statistical values relating
 *  this threshold value. This program is extremely useful in that it allows
 *  us to approach a problem that can only be investigated in such a manner.
 ******************************************************************************/


// Importing needed packages
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

// Runs T experiments on an N by N grid, and uses an array to keep track of 
// each threshold value for each of the experiments 
public class PercolationStats {
    
    private double [] thresholds; // array of thresholds
    private double experiments; // number of experiments
    private int size; // size of one side of grid

   // perform T independent experiments on an N-by-N grid
   public PercolationStats(int N, int T) {  
       if (N <= 0 || T <= 0) throw new IllegalArgumentException();
       size = N;
       experiments = T;
       thresholds = new double [T];
       
       // Creates array with a threshold for each experiment
       for (int i = 0; i < T; i++) {
           
           Percolation newGrid = new Percolation(size);
           int sites = 0; 
           
           // opens up random sites until it percolates
           while (newGrid.percolates() == false) {        
               newGrid.open(StdRandom.uniform(0, size), StdRandom.uniform(0, size));     
           }
           
           // records the threshold value it percolated at for each experiment
           thresholds [i] = (double) newGrid.numberOfOpenSites()/(size*size);          
       }
   }
   
   // sample mean of percolation threshold
   public double mean() {                    
       return StdStats.mean(thresholds);
   }
   
   // sample standard deviation of percolation threshold
   public double stddev() {                  
       return StdStats.stddev(thresholds);
   }
   
   // low  endpoint of 95% confidence interval
   public double confidenceLow() {           
       double confidenceLow = mean() - (1.96*stddev())/Math.sqrt(experiments);
       return confidenceLow;           
   }
       
   // high endpoint of 95% confidence interval
   public double confidenceHigh() {          
       double confidenceHigh = mean() + (1.96*stddev())/Math.sqrt(experiments);
       return confidenceHigh; 
   }
   
   // This main method is used to verify that each of the other methods works
   public static void main(String[] args) {
       int N = 320;
       int T = 160;
       
       // verifies that PercolationStats works
       
       Stopwatch timer = new Stopwatch(); // new Stopwatch
       
       PercolationStats newTrial = new PercolationStats(N, T); 
       
       StdOut.println(newTrial.mean()); // verifies mean
       StdOut.println(newTrial.stddev()); // verifies stddev
       StdOut.println(newTrial.confidenceLow()); // verifies lower confidence
       StdOut.println(newTrial.confidenceHigh()); // verifies higher confidence 
       
       double elapsed = timer.elapsedTime(); // measures elapsed time
       StdOut.println(elapsed);
   }  
}

