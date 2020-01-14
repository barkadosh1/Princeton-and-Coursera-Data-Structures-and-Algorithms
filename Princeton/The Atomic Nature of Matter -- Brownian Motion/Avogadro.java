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
 * Compilation: javac-introcs Avogadro.java
 * Execution: java-introcs Avogadro
 * Dependencies: StdIn.java, StdOut.java
 * 
 * This program reads in a file from StdIn. It reads each value until the file
 * is empty, meanwhile keeping a count of every time it reads a value. It also 
 * keeps a running sum of the converted square of each value read in. The sum
 * and the count of values read in are then used to calculate the self 
 * diffusion. Lastly it uses that value with several other constants to 
 * calculate and print the Boltzmann and avogadro constants.
 *  
 * 
 ****************************************************************************/



public class Avogadro {
    
    private static double temperature = 297.0; // absolute temperature
    private static double viscosity = 9.135e-4; // viscosity of water
    private static double radius = 0.5e-6; // radius of bead
    private static double R = 8.31457; // universal gas constant
    private static double deltat = 0.5; // time between position measurements 
    private static double meterspixels = 0.175e-6; // pixels-meters conversion
        
    // main reads values from StdIn and then does all calculations
    public static void main(String[] args) {
         
        int count = 0;
        double sum = 0.0;
        
        /* Reads values and keeps running sum of squared radial displacements
         * and count of number of displacements read until the file being read 
         * is empty */ 
        while (!StdIn.isEmpty()) {
            double value = StdIn.readDouble();
            double valuesquared = value*value*meterspixels*meterspixels;
            sum = sum + valuesquared;
            count++;
        }
        
         double selfdiffusion = sum/(2*count);
         double D = (selfdiffusion)/(2*deltat);    
        
         double k = (6*D*Math.PI*viscosity*radius)/(temperature);
         double avogadro = R/k;
          
         StdOut.printf("Boltzmann = %.4e", k);
         StdOut.println();
         StdOut.printf("Avogadro = %.4e", avogadro);
    }
}  