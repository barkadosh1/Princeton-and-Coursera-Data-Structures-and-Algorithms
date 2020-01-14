/*************************************************************************
  *  
  * Name: Bar Kadosh
  * Netid: bkadosh
  * Precept: P02C
  * Written: 09/29/2014
  * 
  * This program takes two input arguments, T and deltat. T represents the total
  * time that the universe will be simulated for. deltat represents the amount of
  * time that is used for each increment. It changes the time by adding deltat to
  * the current time each time and then calculates the net forces, velocities, etc.
  * for that specific time. The program is run through the terminal window, so
  * we give it the option to read a data file called planets.txt. It reads the 
  * values it needs from that file and then simulates the revolutions of each
  * body in the universe for the specified amount of time T. At the end, it will
  * print out the number of bodies in the system, the radius of the universe window, 
  * the final x and y positions, final x and y velocities, mass, and image
  * name for each body in the system in that order like this:
  * 
  * 5
  * 2.50e+11
  * 1.4959e+11 -1.6531e+09  3.2949e+02  2.9798e+04  5.9740e+24    earth.gif
  * -2.2153e+11 -4.9263e+10  5.1805e+03 -2.3640e+04  6.4190e+23     mars.gif
  * 3.4771e+10  4.5752e+10 -3.8269e+04  2.9415e+04  3.3020e+23  mercury.gif
  * 5.9426e+05  6.2357e+06 -5.8569e-02  1.6285e-01  1.9890e+30      sun.gif
  * -7.3731e+10 -7.9391e+10  2.5433e+04 -2.3973e+04  4.8690e+24    venus.gif
  * 
  * The program will then provide an animation/simulation for a visual of what
  * actually occurs in the system, along with audio playing in the background.
  *
  *************************************************************************/

public class NBody { 
    public static void main(String[] args) { 
        
// read the number of bodies and the value of the radius
        int N = StdIn.readInt();
        double R = StdIn.readDouble();
        double G = 6.67e-11;
        
        // declare and initialize six arrays
        double[] px    = new double[N];
        double[] py    = new double[N];
        double[] vx    = new double[N];
        double[] vy    = new double[N];
        double[] mass  = new double[N];
        String[] image = new String[N];
        
        // reading the data from text file, and repeating until it reads each line
        for (int i = 0; i < N; i++) {
            px[i]      =  StdIn.readDouble();  
            py[i]      =  StdIn.readDouble();  
            vx[i]      =  StdIn.readDouble();  
            vy[i]      =  StdIn.readDouble();  
            mass[i]    =  StdIn.readDouble(); 
            image[i]   =  StdIn.readString(); 
        }
        
        StdDraw.setXscale(-R, R); 
        StdDraw.setYscale(-R, R);     
        
        double T = Double.parseDouble(args[0]);
        double deltat = Double.parseDouble(args[1]);
        
        StdAudio.play("2001.mid"); //Will provide the background audio
        
        /* Below is the start of the main time loop, which will run all over again
         * after each time-frame, so all other loops will be within this one. t 
         * refers to the time in which the velocity, force, etc. are being 
         * calculated for that time specifically */
        for (double t = 0; t < T; t += deltat) {
            double[] fx    = new double[N];
            double[] fy    = new double[N];   
            
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (i == j) continue;
                    double deltax = (px[j] - px[i]);
                    double deltay = (py[j] - py[i]);
                    double r = Math.sqrt((deltax) * (deltax) + (deltay) * (deltay));
                    double F = (G * mass[i] * mass[j]) / (r * r);
                    double forcex = F * deltax / r;
                    double forcey = F * deltay / r;
                    fx[i] = fx[i] + forcex;
                    fy[i] = fy[i] + forcey;
                }
            }
            for (int i = 0; i < N; i++) {
                //I am taking the x and y accelerations for each body
                double ax = fx[i]/mass[i];  
                double ay = fy[i]/mass[i];
                
                /*It now takes the accelerations for the first body to solve for 
                 * the velocities of the first body until it repeats and does the
                 * same thing for the next body */
                vx[i] = vx[i] + (deltat * ax);
                vy[i] = vy[i] + (deltat * ay);
                
                /*It now takes the velocities for the first body to solve for 
                 * the coordinates in space of the first body until it repeats
                 * and does the same thing for the next body */      
                px[i] = px[i] + (deltat * vx[i]);
                py[i] = py[i] + (deltat * vy[i]);
            }
            
            StdDraw.picture(0, 0, "starfield.jpg"); 
            for (int i = 0; i < N; i++) {
                StdDraw.picture(px[i], py[i], image[i]); /* Draws image at its 
                 * current position */ 
            }
            StdDraw.show(10);
        }
        
        //After all the iterations occur in the time loop, we print some final values
        StdOut.printf("%d\n", N);
        StdOut.printf("%.2e\n", R);
        for (int i = 0; i < N; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }
    }
}


