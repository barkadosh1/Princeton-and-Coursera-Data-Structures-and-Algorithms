/*****************************************************************************
 * Name: Bink Sitawarin
 * Netid: chawins
 * Precept: P06
 * Written: 11/17/2014
 * 
 * Partner's name: Bar Kadosh
 * Partner's login: bkadosh
 * Partner's precept: P02C 
 * 
 * Compilation: javac-introcs MarkovModel.java
 * Execution: java-introcs MarkovModel
 * Dependencies: StdRandom.java, StdOut.java, ST.java, StringBuilder.java
 *
 * This builds a class MarkovModel that contains many useful methods. The 
 * constructor creates the MarkovModel and keeps a frequency count of each
 * character. The order() method returns the order k, while the first freq() 
 * method would return, if called, the number of occurences of kgram in the 
 * text. The second freq() method returns the amount of times a character c
 * follows a kgram. The rand() method uses probabilities to return a random
 * character c following a given k-gram. Finally, the gen() method appends
 * a random character on to the initial k-gram and continues to append a 
 * character for each new k-gram until a string of length T is returned. The
 * main method is just used for testing. 
 * 
 * The template below comes from the course assignment page for Markov Model:
 * http://www.cs.princeton.edu/courses/archive/fall14/cos126/assignments/
 * markov.html
 ****************************************************************************/

public class MarkovModel {
    
    private final ST<String, int[]> st; // symbol table with key of string
                                             // and value of Integer array
    private final int order; // order (length) of k-gram
    private int BIT = 128; // Constant total number of characters
    
    /* Constructor creates a Markov model of order k from given text, and 
     * assumes that text has length at least k. It keeps a tally of the
     * frequency of each character */
    public MarkovModel(String text, int k) {
        
        st = new ST<String, int[]>();
        order = k;
        
        String s = text;
        s = s + s.substring(0, order);
        
        for (int i = 0; i < s.length() - order; i++) {
            
            String kgram = s.substring(i, i + order);
            if (st.contains(kgram)) {
                int[] freq = st.get(kgram);
                freq[s.charAt(i + order)]++;
            } 
            else {
                st.put(kgram, new int[BIT]);
                
                int[] freq = st.get(kgram);
                for (int j = 0; j < BIT; j++) {
                    freq[j] = 0;
                }
                freq[s.charAt(i + order)]++;
            } 
        }
    }
    
    // returns the order k of Markov model
    public int order() {
        return order;
    }
    
    /* returns number of occurrences of kgram in text and throws an exception 
     * if kgram is not of length k */
    public int freq(String kgram) {
        if (kgram.length() != order) {
            throw new RuntimeException("order does not match");
        }
        
        int freq = 0;
        for (int i = 0; i < BIT; i++) {
            freq += st.get(kgram)[i];
        }
        
        return freq;
    }
    
    /* returns number of times that a character c follows that kgram and 
     * throws an exception if kgram is not of length k */
    public int freq(String kgram, char c) {
        if (kgram.length() != order) {
            throw new RuntimeException("order does not match");
        }
        
        return st.get(kgram)[c];
    }
    
    /* returns random character following given kgram based on probabilities
     * and throws an exception if kgram is not of length k or if there is no 
     * such kgram */
    public char rand(String kgram) {
        if (kgram.length() != order) {
            throw new RuntimeException("order does not match");
        }
        if (!st.contains(kgram)) {
            throw new RuntimeException("string does not exist");
        }
        
        double[] prob = new double[BIT];
        double totalFreq = freq(kgram);
        
        for (int i = 0; i < BIT; i++) {
            prob[i] = (double) freq(kgram, (char) i) / totalFreq;
        }
        
        return (char) StdRandom.discrete(prob);
    }
    
    /* Generates a String of length T characters by simulating a trajectory 
    * through the corresponding Markov chain. The first k characters of the 
    * newly generated String should be the argument kgram, and the rest are
    * randomly generated based on probabilities. We throw an exception if 
    * kgram is not of length k and assume that T is at least k */
    public String gen(String kgram, int T) { 
        if (kgram.length() != order) {
            throw new RuntimeException("order does not match");
        }
        
        StringBuilder text = new StringBuilder(kgram);
        for (int i = 0; i < T - kgram.length(); i++) {
            text.append(rand(text.substring(i, i + order)));
        }
        
        return text.toString();
    }
    
    // Main method is used for testing
    public static void main(String[] args) {
        MarkovModel mod1 = new MarkovModel("i am sam. sam i am", 3);
        StdOut.println("freq(\"sam\", ' ')    = " + mod1.freq("sam", ' '));
        StdOut.println("freq(\"sam\", '.')    = " + mod1.freq("sam", '.'));
        StdOut.println("freq(\"mi \")         = " + mod1.freq("mi "));
        StdOut.println("freq(\"sam\")         = " + mod1.freq("sam"));
        StdOut.println();
        
        String text = "now is the time. now is the time to eat. " 
            + "now is the time to live.";
        MarkovModel mod2 = new MarkovModel(text, 7);
        StdOut.println("freq(\"now is \", ' ') = " + mod2.freq("now is ", ' '));
        StdOut.println("freq(\"now is \", 't') = " + mod2.freq("now is ", 't'));
        StdOut.println("freq(\"now is \")      = " + mod2.freq("now is "));
    }
}