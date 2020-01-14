
/*************************************************************************
  *  
  * Name: Bar Kadosh
  * Netid: bkadosh
  * Precept: P02C
  * Written: 10/06/2014
  * 
  * This program takes one input argument, n, which represents the depth of the
  * reursion. The program then draws one large triangle which will serve as the
  * boundary for all the other triangles. The main method then calls the
  * sierpinski method with the n variable and with an x value of 0.5, y value 
  * of 0.0, and an s value of 0.5. As long as n is not zero, the program will 
  * use these values in the sierpinski method to calculate new points for new 
  * triangles and it then calls the filledTriangle method to draw that triangle.
  * It then returns to the sierpinski method, where it continues with recursion,
  * calling upon itself to draw a triangle to the right of, left of, and above of
  * each triangle and it continues to do this as long as n > 0. It continues to call
  * filledTriangle and to draw these triangles in that pattern until n = 0, at which
  * point it will stop drawing the triangles.
  * 
  * 
  * An n value of 1 will just give one triangle pointed downwards. An n value of 2
  * will give an additional triangle to the left of, right of, and above the first
  * triangle, just with sides half the length. It continues to do this for each 
  * triangle as n is increased and so on. That is the main function of the program.
  * 
  * Dependancies: StdDraw
  * Compilation: javac-introcs Sierpinski.java
  * Execution: java-introcs Sierpinksi n
  *
  *************************************************************************/



public class Sierpinski { 
    
    private static double altitude = (Math.sqrt(3.0)/2.0); /* this variable will 
    represent the ratio for computing the altitude of an equilateral triangle */
    
    /* This method is called upon by sierpinski. When it is called, it calculates
     * the new x and y values for the triangle it is currently about to draw and
     * then places those values in an array. StdDraw.filledPolygon is then used
     * to fill that triangle with black, and it then returns to sierpinski and the
     * process is repeated when filledTriangle is called to draw the next triangle.
     */
    public static void filledTriangle(double x, double y, double s)
    {
        double x0 = x, x1 = x + s*0.5, x2 = x - s*0.5;
        double y0 = y, y1 = y + s*(altitude), 
                         y2 = y + s*(altitude);
        double[] xval = {x0, x1, x2};
        double[] yval = {y0, y1, y2};
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledPolygon(xval, yval);
    }
    
    /* This method, when called by the main method, takes the n argument given, 
     * along with 0.5, 0.0, and 0.5 as x, y, and s values, respectively. It then 
     * takes those values and uses them to calculate new x and y values for each 
     * triangle it will draw. It then calls filledTriangle to draw the actual 
     * triangles. It then uses recursion to call itself 3 seperate times to account 
     * for a triangle to the left of, right of, and above each triangle. It will 
     * continue to do this for all the recursions until n = 0, at which point
     * it will stop drawing triangles.
     */
 
        if (n == 0) return;
        double x0 = x, x1 = x + s*0.5, x2 = x - s*0.5;
        double y0 = y, y1 = y + s*(altitude);
        filledTriangle(x, y, s);
        sierpinski(n-1, x1, y0, s/2);
        sierpinski(n-1, x2, y0, s/2); 
        sierpinski(n-1, x0, y1, s/2);
        }
       
    public static void main(String[] args) 
    {
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.line(0.0, 0.0, 1.0, 0.0);
        StdDraw.line(1.0, 0.0, 0.5, (Math.sqrt(3.0)/2.0));
        StdDraw.line(0.5, (Math.sqrt(3.0)/2.0), 0.0, 0.0);
        int n = Integer.parseInt(args[0]);
        sierpinski(n, 0.5, 0.0, 0.5);
    }  
}
