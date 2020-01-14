/*****************************************************************************
 * Name: Bink Sitawarin
 * Netid: chawins
 * Precept: P06
 * Written: 11/09/2014
 * 
 * Partner's name: Bar Kadosh
 * Partner's login: bkadosh
 * Partner's precept: P02C 
 * 
 * Compilation: javac-introcs GuitarHero.java
 * Execution: java-introcs GuitarHero
 * Dependencies: StdAudio.java, StdDraw.java, GuitarString.java, Math
 *
 * Plays 37 different chromatic notes on guitar strings (starting from an A at
 * 110 Hertz and ending on an A at 880 Hertz) when the user types one of the keys 
 * q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' along with the space bar when in the 
 * standard drawing window.
 * 
 * The template below comes from "GuitarHeroLite.java" on the course assignment,
 * http://www.cs.princeton.edu/courses/archive/fall14/cos126/assignments/
 * guitar.html
 *  
 ****************************************************************************/

public class GuitarHero {
    
    static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' "; /* all 
     * possible keys that can be clicked, each of which corresponds to one of
     * the 37 notes between 110 and 880 Hertz */
    static double CONCERT_A = 440.0; // constant defining a concert A as 440 Hertz
    
     /* Creates 37 guitar string notes for the 37 chromatic notes between 
      * 110 and 880 Hertz */
    public static void main(String[] args) {
        
        GuitarString[] note = new GuitarString[37];
        for (int i = 0; i < 37; i++) {  
            note[i] = new GuitarString(CONCERT_A * Math.pow(2, (i-24)/12.0));
        } 
        
        // This is the main input loop
        while (true) {
            
            // This checks if the user has typed a key and processes it
            if (StdDraw.hasNextKeyTyped()) {
                
                // This is the key the user typed
                char key = StdDraw.nextKeyTyped();
                
                // This plucks the string that corresponds to the key pressed
                if (keyboard.indexOf(key) != -1) {
                note[keyboard.indexOf(key)].pluck();
                }
                
            }
            
            // Here, the superposition of the samples is calculated
            double sample = 0.0;
            for (int i = 0; i < 37; i++) {
                sample += note[i].sample();
                note[i].tic();
            }
            
            // The result is sent to standard audio to be played
            StdAudio.play(sample);
            
        }

    }
    
}