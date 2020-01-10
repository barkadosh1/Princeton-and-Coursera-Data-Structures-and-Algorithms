/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: The Autocomplete program heavily relies on the previous 
 *  two programs. The first thing it does in its constructor is assign the given
 *  array to an immutable instance variable array, and then sorts that array
 *  lexicographically. The allMathes Method uses our firstIndexOf and 
 *  lastIndexOf methods to create a copy array that includes both the terms
 *  recovered from firstIndexOf and lastIndexOf, and any terms with the same
 *  prefix in between. It then sorts them by descending weighted order. The
 *  numberOfMatches method functions almost the same way, but it simply uses 
 *  the indices found through firstIndexOf and lastIndexOf in order to calculate
 *  the total number of matches.
 ******************************************************************************/

// imports needed packages 
import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;

public class Autocomplete {
    private final Term [] natural; // instance variable immutable array of terms
    private Term [] copyOfNatural; // sub array of natural array
    
    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new NullPointerException();
        Arrays.sort(terms); // sorts array lexographically
        natural = terms; // assigns terms to our natural array     
    }
    
    // Returns all terms that start with the given prefix, 
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        
        if (prefix == null) throw new NullPointerException();
        
        // length if prefix string
        int r = prefix.length();
        
        // creates a new Term with the prefix. Weight value is arbitrary 
        Term h = new Term(prefix, 0); 
        
        // creates comparator and BinarySearchDeluxe objects
        Comparator<Term> first = Term.byPrefixOrder(r);
        BinarySearchDeluxe bs = new BinarySearchDeluxe();
        
        // returns first and last items starting witht the prefix above
        int a = bs.firstIndexOf(natural, h, first);
        int b = bs.lastIndexOf(natural, h, first);
        
        // makes a new array with just items that begin with the prefix
        copyOfNatural = Arrays.copyOfRange(natural, a, b+1);
        
        // Creates comparator, and sorts the array by reverse weighted order
        Comparator<Term> descending = Term.byReverseWeightOrder();
        Arrays.sort(copyOfNatural, descending);
        
        // returns empty array if there is no such key in the array
        if (a == -1 && b == -1) {
            Term [] empty = new Term[0];
            return empty; 
        }  
        return copyOfNatural;  
    }
    
    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        
        if (prefix == null) throw new NullPointerException();
        
        // length of prefix, and new term with this prefix (and arbitrary weight)
        int r = prefix.length();
        Term h = new Term(prefix, 0);
        
        // creates comparator and BinarySearchDeluxe objects
        Comparator<Term> first = Term.byPrefixOrder(r);
        BinarySearchDeluxe bs = new BinarySearchDeluxe();
        
        // uses firstIndexOf and lastIndexOf to return number of matches
        int a = bs.firstIndexOf(natural, h, first);
        int b = bs.lastIndexOf(natural, h, first);
        
              
        if (a == -1 && b == -1) {
            return 0;
        }  
        
        return (b + 1 - a); 
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        
        // creates new terms
        Term a = new Term("Bring me water", 1);
        Term b = new Term("Gustavo has a mustache", 2);
        Term c = new Term("Gustavo has a mustache", 3);
        Term d = new Term("Gustavo has a mustache", 4);
        Term e = new Term("Gustavo has a mustache", 4);
        Term f = new Term("PJ is tall", 4);
        Term g = new Term("Roman is my friend", 4); 
        Term h = new Term("Sarang!", 5);
        Term i = new Term("Tall", 6); 
        Term j = new Term("Kevin", 8);
        
        // creates array of terms
        Term [] ab = {c, d, e, f, g, a, b, h};
        
        // creates new Autocomplete
        Autocomplete ac = new Autocomplete(ab);
        
        // tests the numberOfMatches method
        StdOut.println(ac.numberOfMatches("Gustav"));
        
        // tests the allMatches method
        StdOut.println(ac.allMatches("Gustav")[0]);
        StdOut.println(ac.allMatches("Gustav")[1]);
        StdOut.println(ac.allMatches("Gustav")[2]);
        StdOut.println(ac.allMatches("Gustav")[3]); 
        
        StdOut.println(ac.numberOfMatches("zzzz"));
    }
}
