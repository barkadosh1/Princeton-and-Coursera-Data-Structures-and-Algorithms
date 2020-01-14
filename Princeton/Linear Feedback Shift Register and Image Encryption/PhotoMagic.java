/*************************************************************************
 * Name: Bar Kadosh
 * NetID: bkadosh 
 * Precept: P02C
 * Written: 11/04/2014
 *
 * Dependencies: Picture, Color, LFSR
 * 
 * Description: What this program does is it takes a filename for a picture, 
 * a string which is the seed, and a variable which is the tap. The program
 * calls on the method trasnform which extracts the red/green/blue color 
 * of each pixel in the picture and then XOR's each of the red/green/blue
 * with an 8 bit integer by calling generate. This gives you the new red/green/blue
 * colors and those are used to set a final color at that pixel. The program then
 * iterates over and over again until it does this for each pixel and by doing
 * that, it prints out a new image.
 *  
 *************************************************************************/

import java.awt.Color;

public class PhotoMagic {
    
    /* This method returns a transformed copy of a picture by extracting colors
     * of each pixel and then XORing the red/green/blue components at each pixel
     * with an eight bit integer using LFSR. It uses that value to compute the 
     * new color of each pixel */
    public static Picture transform(Picture picture, LFSR lfsr) {
        for (int x = 0; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++)  {
                Color color = picture.get(x, y);
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int red = r ^ lfsr.generate(8);
                int green = g ^ lfsr.generate(8); 
                int blue =  b ^ lfsr.generate(8);
                Color finalcolor = new Color(red, green, blue);
                picture.set(x, y, finalcolor);
            }
        }
        return picture;
    }
    
    /* The main method reads in 3 command line arguments. The first is the filename
     * of a picture. The second is a string which represents the seed. The third 
     * is the value of the tap, which is defined as t. It then uses LFSR and calls 
     * on transform to transform a copy of this image that is then returned to main 
     * and then displayed. */
    public static void main(String[] args) {                    
        Picture picture = new Picture(args[0]);    
        String seed = args[1];
        int t = Integer.parseInt(args[2]);
        LFSR lfsr = new LFSR(seed, t);
        transform(picture, lfsr);
        picture.show();
        
    }
}