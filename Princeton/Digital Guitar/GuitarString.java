
/*****************************************************************************
 * Name: Bink Sitawarin
 * Netid: chawins
 * Precept: P06
 * Written: 11/09/2014
 * 
 * Partner's name: Bar Kadosh
 * Partner's login: bkadosh
 * Partner's precept: P02C 
 * 
 * Compilation: javac-introcs GuitarString.java
 * Execution: java-introcs GuitarString
 * Dependencies: RingBuffer.java, Math
 *
 * Contruct a GuitarString object with methods to fill it with random numbers
 * between -0.5 and 0.5, to proceed one step forward, to return the first
 * entry, and to return the number of steps proceeded.
 * 
 * The template below comes from "GuitarString.java" on the course assignment,
 * ftp://ftp.cs.princeton.edu/pub/cs126/guitar/GuitarString.java
 *
 ****************************************************************************/
 
public class GuitarString {
    
    private RingBuffer buffer; // ring buffer
    private int count; // count of how many times tic is called
    private int SAMPLING_RATE = 44100; // Sampling rate constant
    
    
    /* with our sampling rate constant and given frequency, we create a guitar 
     * string of length N and enqueue all of it with zeroes */
    public GuitarString(double frequency) {
        
        int N = (int) Math.ceil(SAMPLING_RATE/frequency);
        buffer = new RingBuffer(N);
        
        // set all entries in the RingBuffer to 0
        for (int i = 0; i < N; i++) {     
            buffer.enqueue(0.0);
        }
    }
    
    // creates a guitar string with size & initial values given by the array
    public GuitarString(double[] init) {
        
        int N = init.length;       
        buffer = new RingBuffer(N);
        
        // set all entries coresponding to the array init
        for (int i = 0; i < N; i++) {
            buffer.enqueue(init[i]);
        }
    }
    
    /* plucks the guitar string by first taking the length of the array, and then 
     * dequeueing each 0.0 and enqueuing in the place of each 0.0 a random value 
     * between -0.5 (included) and 0.5 (not included) */
    public void pluck() {
        
        int N = buffer.size();        
        for (int i = 0; i < N; i++) {
            
            // get rid of previous entry
            buffer.dequeue();
            
            // set an entry to a random number between -0.5 to 0.5
            double r = Math.random();
            if (r < 0.5) buffer.enqueue(r);
            else buffer.enqueue(r - 1);
        }
        
    } 
    
    /* this adavances our simulation one time step by deleting the first sample
     * from the buffer and adding to the end the average of the first two samples, scaled 
     * by an energy decay factor of 0.996 */
    public void tic() {
        
        double x = buffer.dequeue();
        double y = buffer.peek();
        double karplus_strong = (0.996 * 0.5) * (x + y);
        buffer.enqueue(karplus_strong);
        count++;
        
    }
    
    // returns the current sample at the front of the ring buffer
    public double sample() {
        return buffer.peek();        
    }
    
    // this returns the amount of times that tic was called
    public int time() {
        return count;   
    }
    
    // this is simply a test method
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        double[] samples = { .2, .4, .5, .3, -.2, .4, .3, .0, -.1, -.3 };  
        GuitarString testString = new GuitarString(samples);
        for (int i = 0; i < N; i++) {
            int t = testString.time();
            double sample = testString.sample();
            System.out.printf("%6d %8.4f\n", t, sample);
            testString.tic();
        }
    }
    
}