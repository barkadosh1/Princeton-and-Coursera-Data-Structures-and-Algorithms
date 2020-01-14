/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A 
 * 
 *  Description: This program creates our Term data type, and it also creates
 *  our comparators. The Term constructor essentially assigns two values to a
 *  Term type: a String query, and a long weight. My byReverseWeight and
 *  byPrefixOrder methods create comparators, each of which will function to
 *  do comparisons based on its own unique criteria. The byReverseWeight will 
 *  compare them by the weight value assigned to each term (in descending order),
 *  while the byPrefixOrder compares them lexographically by their associated
 *  query String. This is exactly what my helper methods function to do -- they
 *  do the actual comparing. The ReverseWeightOrder just checks which term has
 *  the higher weight value (or equal), and returns a 0, 1, or -1 accordingly.
 *  The PrefixOrder helper method functions the exact same, except for that it
 *  compares lexographically, and it calls the CompareTo method. It also filters
 *  the Strings so that they are only r characters long or shorter. The compareTo
 *  method then simply uses the built-in compareTo method to again return a 0,
 *  1, or -1 depending on the situation. Lastly, the toString method simply 
 *  returns the weight and the query values of the Term, with an indentation in
 *  between.
 ******************************************************************************/

// imports needed packages
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;  


public class Term implements Comparable<Term> {
    
    private String search; // this instance variable will represent the query
    private long value; // this instance variable will represent the weight
    
    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null) throw new NullPointerException();
        if (weight < 0) throw new IllegalArgumentException();
        
        this.search = query; 
        this.value = weight;
    }
    
    // helper class I created
    // creates comparator that compares terms based on their weight
    private static class ReverseWeightOrder implements Comparator<Term> {
        
        public int compare(Term a, Term b) {
            if (a.value > b.value) return -1;
            if (a.value < b.value) return 1;         
            return 0;
        }
    }
    
    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder(); // calls upon my helper class above
    }
    
    // helper class I created to compare lexicographically using first r characters
    private static class PrefixOrder implements Comparator<Term> {     
        
        private int characters; // instance variable for number of characters
        
        // PrefixOrder constructor
        public PrefixOrder(int r) {
            characters = r;
        }
        
        public int compare(Term c, Term d) {
            
            String one = c.search;
            String two = d.search;
            
            // Takes full string if it is shorter than r/characters
            if (one.length() < characters) {
                one = one;
            } 
            
            // otherwise, takes substring with r characters
            else one = one.substring(0, characters);
            
            // Takes full string if it is shorter than r/characters
            if (two.length() < characters) {
                two = two;
            }
            
            // otherwise, takes substring with r characters
            else two = two.substring(0, characters);
            
            return one.compareTo(two);
        }
    }
    
    // Compares the two terms in lexicographic order but using only the first 
    // r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) { 
        if (r < 0) throw new IllegalArgumentException();
        return new PrefixOrder(r); // calls upon my helper class above
    }
    
    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        if (this.search.compareTo(that.search) > 0) return 1;
        if (this.search.compareTo(that.search) < 0) return -1;
        return 0;
    }
    
    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return (value + "\t" + search); 
    } 
    
    // unit testing (required)
    public static void main(String[] args) { 
        
        // verify that my Term constructor functions
        Term a = new Term("I don't like to Dougie", 8);
        Term b = new Term("Gustavo has a mustache", 2);
        Term c = new Term("Bronheim is weak", 1); 
        Term d = new Term("Millman is a Milkman", 7);
        Term e = new Term("PJ is tall", 4);
        Term f = new Term("PJ is tall", 5);
        Term g = new Term("Hamza is my friend", 3);
        Term h = new Term("Sarang!", 6);
        
        // verifies that my toString method functions
        StdOut.println(h.toString());
        
        // verifies that my byReverseWeightOrder and byPrefixOrder methods work
        // also tests my two helper methods/classes as well
        Comparator<Term> j = byReverseWeightOrder();
        Comparator<Term> k = byPrefixOrder(8); 
        
        // Lastly, this tests that my lexicographic compareTo method functions
        StdOut.println(d.compareTo(e));   
    }
}