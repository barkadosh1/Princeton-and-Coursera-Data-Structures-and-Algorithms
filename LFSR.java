/*************************************************************************
 * Name: Bar Kadosh
 * NetID: bkadosh 
 * Precept: P02C
 * Written: 11/04/2014
 *
 * Dependencies : StdOut (just for testing)
 * 
 * Description  : What this program does is that it several different methods
 * within the LFSR class. The constructor is used to initialize the instance 
 * variables and to then assign each character of the string of bits into
 * an array. The toString() method simply converts the output into a string
 * before it is used as output. The step method simulates one step of an LFSR,
 * meaning that it XOR's the leftmost bit with a bit in a specified tap 
 * (depending on the tap given as an argument), then shifts all the bits left,
 * and then places the XOR bit all the way to the right. The generate method
 * does k steps of this and returns a k-bit integer, which is simply done
 * with a for loop and the equation r = 2*r + bit. This is basically what this
 * program does and what you do with these methods is up to the programer.
 *  
 * 
 *************************************************************************/

public class LFSR {
   
// Here I am just declaring my instance variables 
    private int N;       // This is the total number of bits in the LFSR
    private int[] reg;   /* This declares the arrary in which reg[i] = ith bit of 
                          * LFSR, in my case reg[0] is the leftmost bit */
    private int tap;     /* This specifies the index at which the tap is 
                          * placed when XORing, but since I am working from left 
                          * to right, some math will be done with tap later on
                          * to manipulate at which index the tap is placed */
    
    /* This is the constructor, used to create LFSR with the initial seed and tap, 
     * whatever they may be. tap is created through t */
    public LFSR(String seed, int t) {
        N = seed.length();
        tap = t;
        reg = new int[N];
        for (int i = 0; i < N; i++) {
            reg[i] = seed.charAt(i);
            if (reg[i] == 48) reg[i] = 0; // Since I was getting values of 48 
            if (reg[i] == 49) reg[i] = 1; /* and 49, I used these two lines to 
                                           * change them into 0's and 1's */
        }
    }
    
    /* This method simulates one step of XOR and shifting everything to the left and 
     * then replacing the most right index with the XOR value and returns the new 
     * bit as 0 or 1 */
    public int step() {
        int bit = reg[0] ^ reg[N-1-tap]; /* The reason why I have used N-1-tap as 
                                          * my tap index is because I am working 
                                          * from left to right, rather than the 
                                          * normal, which is right to left. I 
                                          * accounted for that by making my tap 
                                          * index N-1-tap */
        for (int i = 0; i < N-1; i++) {
            reg[i] = reg[i+1];
        }
        reg [N-1] = bit;
        return bit;
    }
    
    /* This method simulates k steps and returns a k-bit integer method, which is 
     * simply done with a for loop and the equation r = 2*r + bit. */
    public int generate(int k) {
        int r = 0;
        for (int i = 0; i < k; i++) {
            int bit = step();
            r = 2*r + bit;
        }
        return r;
    }
    
// This method simply returns a string representation of the LFSR
    public String toString() {
        String s = "";
        for (int i = 0; i < N; i++) {
            s = s + reg[i];
        }
        return s;
    }
    
    
    
    /* I use this method just to test all of the methods in LFSR to make sure they 
     * are working properly */
    public static void main(String[] args)  {
        LFSR lfsr; 
        lfsr = new LFSR("01101000010100010000", 16); // tests constructor 
        for (int i = 0; i < 10; i++) {
            int r = lfsr.generate(8); /* this directly tests generate and 
                                       * indirectly tests step, as step is 
                                       * called in generate */
            StdOut.println(lfsr + " " + r); /* printing it tests that toString() 
                                             * actually returned a string */
            
        }
    }
}

