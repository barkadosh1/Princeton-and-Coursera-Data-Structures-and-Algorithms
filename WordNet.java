/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  WordNet
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import java.lang.String;

public class WordNet {

    private ST<String, Integer> allNouns;
    private ST<Integer, String> reverseSynset;
    private int N;
    private Digraph hypernym;
    private Queue<String> nouns;

   // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        
        In syn = new In(synsets);
        In hyp = new In(hypernyms);
        
        reverseSynset = new ST<Integer, String>(); 
        allNouns = new ST<String, Integer>();
        
        while (!syn.isEmpty()) {

            String line = syn.readLine();
            String[] splits = line.split(",");
            String splitOne = splits[0]; 
            String splitTwo = splits[1]; 

            
            String[] nounSplit = splitTwo.split(" ");

            reverseSynset.put(Integer.parseInt(splitOne), splitTwo);
            
            
            int i = 0;
            while (i < nounSplit.length) {
                allNouns.put(nounSplit[i], Integer.parseInt(splitOne));
                i++;
            }

        }
        
        N = reverseSynset.size();
        
        hypernym = new Digraph(N);
        

        while (!hyp.isEmpty()) {
            
            String lineTwo = hyp.readLine();
            String[] splitsTwo = lineTwo.split(",");
            int v = Integer.parseInt(splitsTwo[0]); 
            
            int j = 1;
            while (j < splitsTwo.length) {
                hypernym.addEdge(v, Integer.parseInt(splitsTwo[j]));
                j++;
                
            }         
        }

    }

   // all WordNet nouns
    public Iterable<String> nouns() {       
        nouns = new Queue<String>();
        
        for (int i = 0; i < N; i++) {
            String noun = reverseSynset.get(i);
            String[] words = noun.split(" ");
            
            int j = 0;
            while (j < words.length) {
                nouns.enqueue(words[j]);
                j++;
            }       
        }
        return nouns;
    }

   // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new java.lang.NullPointerException();
        if (allNouns.contains(word)) return true;
        else return false; 
    }

   // a synset (second field of synsets.txt) that is a shortest common ancestor
   // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) throw new java.lang.NullPointerException();
        if (!isNoun(noun1) || !isNoun(noun2)) throw new java.lang.IllegalArgumentException();
        ShortestCommonAncestor graph = new ShortestCommonAncestor(hypernym);
        int valOne = allNouns.get(noun1);
        int valTwo = allNouns.get(noun2);
        String sca = "" + graph.ancestor(valOne, valTwo);
        return sca;
    }

   // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) { 
        if (noun1 == null || noun2 == null) throw new java.lang.NullPointerException();
        if (!isNoun(noun1) || !isNoun(noun2)) throw new java.lang.IllegalArgumentException();
        ShortestCommonAncestor graph = new ShortestCommonAncestor(hypernym);
        int valOne = allNouns.get(noun1);
        int valTwo = allNouns.get(noun2);
        return graph.length(valOne, valTwo);
    }

   // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        StdOut.println(wordnet.isNoun("Bar"));
        StdOut.println(wordnet.isNoun("actomyosin"));
        

    }
    
}