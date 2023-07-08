package edu.rit.cs.grocerystore;

import java.util.Random;

/**
 * A random set of useful values and methods for the GroceryLine lab
 *
 * @author RIT CS
 */
public class Utilities {

    /**
     * Compute a random number.
     * The probability distribution function mimics the first half-cycle
     * of a sine wave, i.e., the part from 0 to &pi;. This makes the mean value
     * also the most likely value. It is scaled, however. See the return
     * description.
     * @param rand the random number generator (instance of {@link Random})
     * @param meanDelay on average, the number returned should be close to this.
     * @return a random value between 0 and 2*meanDelay
     */
    public static double sinePDFDelay( Random rand, double meanDelay ) {
        return 2.0d * Math.acos( 1.0d - 2 * rand.nextDouble() ) * meanDelay
                                                                    / Math.PI;
    }

    /**
     * A special "sentinel" cart sent through the checkout line after all
     * the "real" carts. When the Clerk's run() method sees it, it terminates.
     */
    public static final Cart NO_MORE_CARTS = new Cart();

    /**
     * The factor relating a grocery item to how long it takes to check it out
     */
    public static final long TIME_PER_CART_ITEM = 10;

    /**
     * The lock used by {@link #printf(String, Object...)} and
     * {@link #println(String)}
     */
    private static final Object sync = new Object();

    /**
     * Print a line to standard output, without risk of interruption.
     * @param s the string to print on the line
     */
    public static void println( String s ) {
        synchronized( sync ) {
            System.out.println( s );
        }
    }

    /**
     * Print a line to standard output, without risk of interruption.
     * @param format the printf-style format string to print on the line
     * @param args the values referenced in the format string
     * @see java.io.PrintStream#printf(String, Object...)
     * @see <a href="https://www.cs.rit.edu/usr/local/jdk/docs/api/java.base/java/util/Formatter.html#syntax">Format syntax</a>
     */
    public static void printf( String format, Object... args ) {
        synchronized( sync ) {
            System.out.printf( format, args );
        }
    }
}
