package spelling;

import java.util.LinkedList;

/**
 * A class that implements the Dictionary interface using a LinkedList
 *
 */
public class DictionaryLL implements Dictionary 
{

	private LinkedList<String> dict;
	private int size;
	
	// DictionaryLL is my implementation
    public DictionaryLL() {  
    	dict = new LinkedList<String>();
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

    // isWord is my implementation
    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
        if (dict.contains(s.toLowerCase())) return true;
        else return false;
    }

    
}
