package spelling;

import java.util.TreeSet;

/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class DictionaryBST implements Dictionary 
{
   private TreeSet<String> dict; 
   private int size;
   
   // DictionaryBST is my implementation
   public DictionaryBST () {
   
	   dict = new TreeSet<String>();
	   size = 0;
   }
    
    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
   
   // addWord is my implementation
    public boolean addWord(String word) {
    	String lower_word = word.toLowerCase();
    	if (dict.contains(lower_word)) return false;
    	else {
    		dict.add(lower_word);
    		size++;
    		return true;
    	}
    }


    /** Return the number of words in the dictionary */
    public int size()
    {
        return size;
    }

    /** Is this a word according to this dictionary? */
    
    // isWord is my implementation
    public boolean isWord(String s) {
        if (dict.contains(s.toLowerCase())) return true;
        else return false;
    }

}
