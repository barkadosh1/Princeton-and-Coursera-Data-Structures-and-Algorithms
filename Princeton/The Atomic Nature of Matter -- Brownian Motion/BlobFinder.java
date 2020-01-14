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
  * Compilation: javac-introcs BlobFinder.java
  * Execution: java-introcs BlobFinder
  * Dependencies: Queue.java, Luminance.java, Blob.java, StdOut.java
  * 
  * What this program does is that it finds the blobs in the picture 
  * using the luminance threshold tau. It does this by going through the pixels
  * and when it finds a foreground pixel it calls dfs (depth first searching) 
  * method which finds what other pixels are in that blob. All pixels in that
  * process are marked as visited. That blob is then added to a queue of type
  * Blob. The method countBeads method counts how many of the blobs are of
  * at least size P, with P being the value given when the method is called.
  * The getBeads method searches through the blobs and stores all of the beads 
  * in a Blob array. The main was written to take as command-line arguments P, 
  * tau, and the name of a JPEG file and to then print out the number of blobs 
  * and the number of beads with at least P pixels
  * 
  * 
  * The template comes from the assignment page for atomic matter: http://www.
  * cs.princeton.edu/courses/archive/fall14/cos126/assignments/atomic.html
  *  
  ****************************************************************************/


public class BlobFinder {
    
    private Picture image; // picture file
    private double threshold; // luminance cutoff for background/foreground
    private boolean[][] visited; // array of pixels that have been visited 
    private Queue<Blob> blobs; // Queue of the different blobs found 
    private int width, height; // height and width of picture file in pixels
    
    // finds blobs in the picture using the luminance threshold tau
    public BlobFinder(Picture picture, double tau) {
        
        threshold = tau;
        image = picture;
        width = picture.width();
        height = picture.height();
        blobs = new Queue<Blob>();
             
        visited = new boolean[width][height];
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((Luminance.lum(image.get(i, j)) >= threshold) 
                        && !visited[i][j]) {
                    Blob blob = new Blob();
                    dfs(blob, i, j);
                    blobs.enqueue(blob);               
                }
            }
        }   
    }
    
    // locates all of the foreground pixels in the same blob as the 
    // foreground pixel (i, j)
    private void dfs(Blob blob, int i, int j) {
        
        if (i < 0 || i >= width) return;
        if (j < 0 || j >= height) return;
        if (visited[i][j]) return; 
        visited[i][j] = true;
        if (Luminance.lum(image.get(i, j)) < threshold) return;
        else {
            blob.add(i, j); 
            dfs(blob, i+1, j);
            dfs(blob, i, j+1);
            dfs(blob, i, j-1);
            dfs(blob, i-1, j);
        }
    }
    
    // counts the number of beads (blobs of sufficient pixel size)
    private int countBeads(int pixels) {
        
        int n = blobs.length();
        int beads = 0;
        for (int i = 0; i < n; i++) {
            Blob blob = blobs.dequeue();
            if (blob.mass() >= pixels) {
                beads++;
            }
            blobs.enqueue(blob);
        }
        return beads; 
    }
    
    // searches through the blobs and stores all of the beads 
    // (blobs of sufficient size) in a Blob array 
    public Blob[] getBeads(int P) {
        
        int N = countBeads(P);
        Blob[] blobarray = new Blob[N];
        
        int n = blobs.length();
        int j = 0;
        
        for (int i = 0; i < n; i++) {
            Blob blob = blobs.dequeue();
            if (blob.mass() >= P) {
                blobarray[j] = blob;
                j++;
            }
            blobs.enqueue(blob);
        }
        return blobarray;
    }
    
    // takes as command-line arguments P, tau, and the name of a JPEG file and 
    // prints out the number of blobs and the number of beads with at least P 
    // pixels
    public static void main(String[] args) {
        
        int P = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        Picture frame = new Picture(args[2]);
        
        BlobFinder blobFinder = new BlobFinder(frame, tau);
        
        int numBeads = blobFinder.countBeads(P);
        Blob[] beads = blobFinder.getBeads(P);
        
        StdOut.println(numBeads + " Beads:"); 
        for (int i = 0; i < numBeads; i++) {
            StdOut.println(beads[i]);
        }
        StdOut.println();
        
        int numBlobs = blobFinder.countBeads(1);
        Blob[] blobs = blobFinder.getBeads(1);
        
        StdOut.println(numBlobs + " Blobs:"); 
        for (int i = 0; i < numBlobs; i++) {
            StdOut.println(blobs[i]);
        }
        
    } 
}