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
 * Compilation: javac-introcs RingBuffer.java
 * Execution: java-introcs RingBuffer
 * Dependencies: None
 *
 * Construct a RingBuffer object with methods to return the size, check if it
 * is full or empty, add a double at the last place, return and delete the
 * first entry, and return the first entry. Main method is used to test the
 * code
 * 
 * The template below comes from "RingBuffer.java" on the course assignment,
 * ftp://ftp.cs.princeton.edu/pub/cs126/guitar/RingBuffer.java
 *   
 ****************************************************************************/

public class RingBuffer {
    private double[] rb;          // array of items in the buffer
    private int first;            // index for the next dequeue or peek
    private int last;             // index for the next enqueue
    private int size;             // number of items in the buffer
    
    // creates an empty ring buffer, with given max capacity
    public RingBuffer(int capacity) {
        rb = new double[capacity];
    }
    
    // returns the size, or number of items currently in the buffer
    public int size() {
        return size;
    }
    
    // checks if the buffer is empty or not by checking if the size is zero or 
    // not
    public boolean isEmpty() {    
        if (size == 0)
            return true;
        else 
            return false;
    }
    
    // checks if the buffer is full by checking if the size equals array capacity
    public boolean isFull() {     
        if (size == rb.length)
            return true;
        else 
            return false;
    }
    
    // adds an item x to the end of the ring buffer
    public void enqueue(double x) {
        
        // if the buffer is full, throw an error
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        
        // set the last entry equal to x
        rb[last] = x;
        
        // shift last by 1 and make it 0 once it gets to the end
        last = (last + 1) % rb.length;
        size++;
    }
    
    // deletes the item from the front of the buffer and returns it
    public double dequeue() {
        
        // if the buffer is empty, throw an error
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        
        // keep the first entry and set it to zero
        double front = rb[first];
        rb[first] = 0;
        size--;
        
        // shift first by 1 and make it 0 once it gets to the end
        first = (first + 1) % rb.length;
        
        return front;
    }
    
    // returns the item from the front but does not delete it
    public double peek() {
        
        // if the buffer is empty, throw an error
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        
        return rb[first];
    }
    
    // a simple test of the constructor and methods in RingBuffer
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(N);
        for (int i = 1; i <= N; i++) {
            buffer.enqueue(i);
        }       
        double t = buffer.dequeue();
        buffer.enqueue(t);
        System.out.println("Size after wrap-around is " + buffer.size());
        while (buffer.size() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        System.out.println(buffer.peek());
    }
    
}