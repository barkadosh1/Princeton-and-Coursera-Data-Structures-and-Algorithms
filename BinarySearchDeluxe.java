/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: This program has two very useful methods. The first is called
 *  firstIndexOf, and it essentially functions as a binary search. The main
 *  difference is that it is designed to continue going if it finds the "key"
 *  it is looking for, if the same key is present in the array at an earlier 
 *  index. This is because this method is intended to return the first index 
 *  that contains the key being search for. This is done using a binary search
 *  implementation coupled with the usage of the comparator from Term.
 *  lastIndexOf has a very similar function and implementation. The difference is
 *  that it is intended to return the last index that contains the key being
 *  searched for, as opposed to the first. They both establish mid, lo, and hi
 *  values and alter these values in order to simulate a binary search each
 *  time a comparison reveals new information. They are only intended to stop
 *  if the key found doesn't have the same key in the index before it in the case
 *  of firstIndexOf, or in the index after it in the case of lastIndexOf.
 * 
 ******************************************************************************/

// imports needed packages
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;

public class BinarySearchDeluxe {
    
    // Returns the index of the first key in a[] that equals the search key,
    // or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, 
                                         Comparator<Key> comparator) {
        if (key == null) throw new NullPointerException();
        if (a == null) throw new NullPointerException();
        if (comparator == null) throw new NullPointerException();
        
        int hi = a.length - 1; // initializes high to last index
        int lo = 0; // initializes low to first index
        int mid = 0; // initializes mid to 0
        
        while (lo <= hi) {   
            mid = lo + (hi - lo) / 2; // recalculates mid each loop
            
            // shifts value of low up if first half is ruled out
            if (comparator.compare(key, a[mid]) > 0) {                              
                lo = mid + 1; 
            }
            
            // shifts value of hi down if second half is ruled out
            else if (comparator.compare(key, a[mid]) < 0) {        
                hi = mid - 1;        
            } 
            
            // if mid is the first index, and contains the key, return mid 
            else if (mid == 0 && comparator.compare(key, a[mid]) == 0) return mid;
            
            // if mid and index before it have key, rule out second half
            else if ((comparator.compare(key, a[mid]) == 0) && 
                     (comparator.compare(key, a[mid - 1]) == 0)) hi = mid - 1;  

            // if mid has key, but not index before it, return mid
            else return mid; 
        }
        return -1; // return -1 if mid was never returned
    }
    
    // Returns the index of the last key in a[] that equals the search key, 
    // or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key,
                                        Comparator<Key> comparator) {
        if (key == null) throw new NullPointerException();
        if (a == null) throw new NullPointerException();
        if (comparator == null) throw new NullPointerException();
        
        int hi = a.length - 1; // initializes high to last index
        int lo = 0; // initializes low to first index
        int mid = 0; // initializes mid to 0
        
        while (lo <= hi) {     
            mid = lo + (hi - lo) / 2; // recalculates mid each loop
            
            // shifts value of low up if first half is ruled out
            if (comparator.compare(key, a[mid]) > 0) {        
                lo = mid + 1;             
            }
            
            // shifts value of hi down if second half is ruled out
            else if (comparator.compare(key, a[mid]) < 0) {
                hi = mid - 1;
            }
            
            // if mid is the last index, and contains the key, return mid 
            else if ((mid == a.length - 1) && 
                     comparator.compare(key, a[mid]) == 0) return mid;
            
            // if mid and index after it have key, rule out first half
            else if ((comparator.compare(key, a[mid]) == 0) && 
                     (comparator.compare(key, a[mid + 1]) == 0)) {
                lo = mid + 1;           
            }
            
            // if mid has key, but not index after it, return mid
            else return mid;          
        }     
        return -1; // return -1 if mid was never returned      
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        
        // Creating several terms for testing
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
        
        // creating arrays of terms for testing
        Term [] ab = {a, b, c, d, e, f, g, h};
        Term [] ac = {a, b, c, d, e, f, g, h, i};
        
        // creating one of each comparator for testing
        Comparator<Term> values = Term.byReverseWeightOrder();
        Comparator<Term> valuesTwo = Term.byPrefixOrder(8);
        
        // checking that firstIndexOf returns what I expect it to
        StdOut.println(firstIndexOf(ab, e, values));
        StdOut.println(firstIndexOf(ab, j, values)); 
        StdOut.println(firstIndexOf(ab, e, valuesTwo));
        StdOut.println(firstIndexOf(ac, e, valuesTwo));
        StdOut.println(firstIndexOf(ab, j, valuesTwo));
        
        // checking that lastIndexOf returns what I expect it to
        StdOut.println(lastIndexOf(ab, e, values));
        StdOut.println(lastIndexOf(ac, e, values));
        StdOut.println(lastIndexOf(ab, j, values));
        StdOut.println(lastIndexOf(ab, e, valuesTwo));
        StdOut.println(lastIndexOf(ac, e, valuesTwo));
        StdOut.println(lastIndexOf(ab, j, valuesTwo));
        
        // running another test to verify both firstIndexOf and lastIndexOf
        Term s = new Term("X", 1);
        Term [] dd = {s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, 
            s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s};
        StdOut.println(firstIndexOf(dd, s, valuesTwo));
        StdOut.println(lastIndexOf(dd, s, valuesTwo));  
    }   
}

