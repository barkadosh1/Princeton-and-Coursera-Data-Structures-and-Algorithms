package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		size = 0;
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
    
    // addWord is my implementation 
	public boolean addWord(String word)
	{
		String lower_word = word.toLowerCase();
		char [] letters = lower_word.toCharArray();
		boolean return_val = false;
	
		TrieNode curr = root;
		TrieNode curr2 = root;
			
		for (int i = 0; i <= letters.length; i++) {			
			if (curr != null) {
				if (i == (letters.length)) {
					if (curr.endsWord() != true) {
						curr.setEndsWord(true);
						size++;
					}
				}
				else {
					curr2 = curr;
					curr = curr.getChild(letters[i]);
				}
			}
			else {
				if (i == (letters.length)) {
					curr2.insert(letters[i-1]).setEndsWord(true);
					size++;
				}
				else {
					curr2 = curr2.insert(letters[i-1]);		
				}
				
				return_val = true;
			}
		}

	    return return_val;
	}
	
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	
	// isWord is my implementation
	public boolean isWord(String s) 
	{
		String lower_word = s.toLowerCase();
		char [] letters = lower_word.toCharArray();
		
		int i = 0;
		TrieNode curr = root;
		
		boolean return_value = false;
		
		while (curr != null && i < letters.length) {
			curr = curr.getChild(letters[i]);
			i++;					
		}
		
		if (curr != null && curr.endsWord() == true) return_value = true;
		return return_value;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     
     // predictCompletions is my implementation
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	List<String> return_list = new LinkedList<String>(); 
    	List<TrieNode> queue = new LinkedList<TrieNode>();
    	 
    	String lower_word = prefix.toLowerCase();
 		char [] letters = lower_word.toCharArray();
 		
 		int i = 0;
 		TrieNode curr = root; 
 		
 		while (curr != null && i < letters.length) {
 			curr = curr.getChild(letters[i]);
 			i++;					
 		}
 		
 		if (curr == null) return return_list;
 		
 		queue.add(curr);
 		
 		while (queue.isEmpty() != true && return_list.size() < numCompletions) {
 			TrieNode new_curr = queue.remove(0);
 			if (new_curr.endsWord() == true) return_list.add(new_curr.getText());
 			Set<Character> neighbors = new_curr.getValidNextCharacters();
 			Iterator<Character> it = neighbors.iterator();
 			while (it.hasNext() == true) queue.add(new_curr.getChild(it.next()));
 		}
    	 
        return return_list;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}