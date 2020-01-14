/*************************************************************************
  *  
  * 
  * Name: Bink Sitawarin
  * Netid: chawins
  * Precept: P06
  * Written: 10/13/2014
  * 
  * Partner's name: Bar Kadosh
  * Partner's login: bkadosh
  * Partner's precept: P02C
  * 
  * This program will take two strings of characters. The program can read these
  * strings from an input text file, or the person using the program can just 
  * provide the strings to be used. The program then computes the optimal sequence 
  * alignment of the two DNA strings given. By computing their edit distance, we get
  * a better idea of the similarity of two genetic sequences. We use three methods 
  * in this program: penalty, min, and main. Using the code in these methods, we can
  * use gaps to fill in spots where the two strings don't align, but we add a 
  * penalty to the edit distance when that happens. We also pay a penalty when 
  * characters in the two strings don't match. The program then retraces its steps 
  * and prints the optimal alignment and each individual penalty. Basically, the 
  * program prints the edit distance, and then prints the two strings with any 
  * added gaps, along with the penalties next to each pair of characters.
  * 
  * 
  * Dependancies: StdIn, StdOut
  * Compilation: javac-introcs EditDistance.java
  * Execution: java-introcs EditDistance < input.txt
  *
  *************************************************************************/




public class EditDistance {
    
    // Defining constant values that we will use throughout the program
    private static int MATCH = 0;
    private static int MISMATCH = 1;
    private static int GAP = 2;
    
    /* We will use this method to return the penalty for aligning char a and char b 
     * We will get different values depending on whether a is equal to b. If a
     * is equal to b we return MATCH, and if they are not equal, we return
     * MISMATCH */
    public static int penalty(char a, char b) {
        if(a == b)
            return MATCH;
        return MISMATCH; 
    }
    
    /* We will use this method to return the minimum of 3 integers. It takes the 
     * 3 integers, and returns the minimum one to the main method. The reason why 
     * we want the minimum, is because we are searching for the minimum path, so this
     * method plays a very important role */
    public static int min(int a, int b, int c)  {
        return Math.min(Math.min(a, b), c);
    }
    
    // read 2 strings from standard input.
    // compute and print the edit distance between them.
    // output an optimal alignment and associated penalties.   
    public static void main(String[] args) {
        
        // read two strings from input
        String xString = StdIn.readString(); 
        String yString = StdIn.readString(); 
        
        // declare opt
        int M = xString.length();
        int N = yString.length();
        int opt[][] = new int[M + 1][N + 1];
        
        // Here we are building up the matrix opt[][]
        for(int i = M; i >= 0; i--) {
            for(int j = N; j >= 0; j--) {
                
                // set the bottom right to 0
                if (i == M && j == N) {
                    opt[M][N] = 0;
                }
                
                /* the ones on the bottom edge can only come from the one on
                 * their right */
                else if (i == M) {
                    opt[i][j] = opt[i][j+1] + GAP;
                }
                
                // the ones on the right edge can only come from the one below it
                else if (j == N) {
                    opt[i][j] = opt[i+1][j] + GAP;
                }
                
                // this accounts for the rest of the array
                else {
                    opt[i][j] = min (opt[i+1][j+1] + 
                                     penalty(xString.charAt(i), yString.charAt(j)), 
                                     opt[i+1][j] + GAP, opt[i][j+1] + GAP);
                }
                
            }
        }
        
        StdOut.println("Edit distance = " + opt[0][0]);
        
       
        /* At this part of the program, we are now going to retrace and print the 
         * steps that conrtibute to this most optimal alignment */
        
        /* With this while loop we are accounting for all parts except for the right and bottom edges 
         * of the matrix */
        int j = 0;
        int i = 0;
        while (i < M && j < N) {
            
            // Recalculating penalty each time
            int pen = penalty(xString.charAt(i),yString.charAt(j));
            
            if (opt[i][j] == opt[i+1][j+1] + pen) {
                StdOut.println(xString.charAt(i++) + " " + yString.charAt(j++) 
                                   + " " + pen);
            }
            else if (opt[i][j] == opt[i+1][j] + GAP) {
                StdOut.println(xString.charAt(i++) + " - " + GAP);
            }
            else if (opt[i][j] == opt[i][j+1] + GAP) {
                StdOut.println( "- " + yString.charAt(j++) + " " + GAP);
            }
        }
        
        // This while loop accounts for backtracking the right edge of the matrix
        while (i < M) {
            StdOut.println(xString.charAt(i++) + " - " + GAP);
        }
        
        // This while loop accounts for backtracking the bottom edge of the matrix
        while (j < N) {
            StdOut.println("- " + yString.charAt(j++) + " " + GAP);
        }
    }
}