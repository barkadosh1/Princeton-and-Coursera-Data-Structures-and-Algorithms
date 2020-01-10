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
 * Compilation: javac-introcs TextGenerator.java
 * Execution: java-introcs TextGenerator k T < input.txt
 * Dependencies: MarkovModel.java, StdOut.java, StdIn.java
 * 
 * What this program does is that it takes two command line arguments, the 
 * first being k, which is order of the k-gram, and the second being T, which
 * is the desired length of the randomly generated string. It also reads in an 
 * input text file and reads all the characters.It then starts with
 * the k-gram and after creating a MarkovModel instance, uses gen() to 
 * append a random value based on probablities to the k-gram. It then shifts
 * the k-gram over one to the right giving a new k-gram which then appends 
 * another random character based on probabilities for that k-gram. At the
 * end, the program uses StdOut to print the random string.
 *
 *  
 ****************************************************************************/

 
public class TextGenerator { 
    
    private static MarkovModel markov; // markov model
    
    /* This main method takes the command line arguments, reads in the input
     * text, then creates a MarkovModel instance and then uses gen() to append
     * a random value after each new k gram, which creates the final random 
     * string */
    public static void main(String[] args) {
        
        int k = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        String text = StdIn.readAll();
        
        markov = new MarkovModel(text, k);
        
        String kgram = text.substring(0, k);
        String randomText = markov.gen(kgram, T);
        
        StdOut.println(randomText);  
    } 
}


