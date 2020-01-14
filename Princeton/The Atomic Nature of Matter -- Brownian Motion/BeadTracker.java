/*****************************************************************************
 * Name: Bink Sitawarin
 * Netid: chawins
 * Precept: P06
 * Written: 01/08/2015
 * 
 * Partner's name: Bar Kadosh
 * Partner's login: bkadosh
 * Partner's precept: P02C  
 * 
 * Compilation: javac-introcs BeadTracker.java
 * Execution: java-introcs BeadTracker
 * Dependencies: BlobFinder.java, Blob.java, StdOut.java
 * 
 * Takes an integer P, a double value tau, a double value delta, and a sequence 
 * of JPEG filenames as command-line arguments. It then identifies the beads 
 * in each JPEG image using BlobFinder, and prints out the radial displacement 
 * that each bead moves from one frame to the next. 
 * 
 ****************************************************************************/
 

public class BeadTracker {
    
     // takes an integer P, a double value tau, a double value delta, and a 
     // sequence of JPEG filenames as command-line arguments, identifies the 
     // beads in each JPEG image, and prints out the radial displacement that 
     // each bead moves from one frame to the next
     public static void main(String[] args) {
        
         // take 3 arguments from command-line as mass of beads, threshold for
         // luminance, and max distance a bead travels repectively
         int P = Integer.parseInt(args[0]);
         double tau = Double.parseDouble(args[1]);
         double delta = Double.parseDouble(args[2]);
             
         // go through each frame one by one
         for (int i = 3; i < args.length - 1; i++) {
             
             Picture frame = new Picture(args[i + 1]);
             Picture prevframe = new Picture(args[i]);
             
             // create BlobFinder object for two consecutive frames
             BlobFinder blobFinder = new BlobFinder(frame, tau);
             BlobFinder prevblobFinder = new BlobFinder(prevframe, tau);
             
             // get arrays of beads from the two frames
             Blob[] blobs = blobFinder.getBeads(P);
             Blob[] prevblobs = prevblobFinder.getBeads(P);
             
             // go through every bead in the later frame
             for (int j = 0; j < blobs.length; j++) {
                 
                 double minDistance = Double.POSITIVE_INFINITY;
                 
                 // compare distance from the bead in the later frame to all
                 // the beads in the previous frame, and keep the smallest one
                 for (int k = 0; k < prevblobs.length; k++) {
                     
                     double distance = blobs[j].distanceTo(prevblobs[k]);
                                             
                     if (minDistance > distance)
                         minDistance = distance;
                 }
                 
                 // if minimal distance is less than delta, print it
                 if (minDistance <= delta) {
                     StdOut.printf("%.4f", minDistance);
                     StdOut.println();
                 }
             }                                           
         } 
    }
}
