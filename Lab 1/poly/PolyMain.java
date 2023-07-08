package poly;

import poly.stu.PolyDerive;
import poly.stu.PolyEval;
import poly.stu.PolyRoot;
import poly.stu.PolyString;

import java.util.Scanner;

/**
 * A class that works with polynomials that are provided on the command line,
 * in reverse order.  If the polynomial is empty, it will display a usage
 * message and exit:
 * <pre>
 * $ java Poly
 * Usage: java Poly term0...
 * </pre>
 *
 * <p>
 * Otherwise, various operations described in the main method are performed
 * using the polynomial.
 * </p>
 *
 * @author Chen Lin
 */
public class PolyMain {
    /**
     * The main method:
     * <pre>
     * 1. reads the polynomial from the command line and displays it.
     * 2. prompts the user for a value of x.
     * 3. evaluates the polynomial with the supplied value of x and
     * displays the result of the evaluation.
     * 4. compute/display the derivative of the polynomial.
     * 5. compute/display the root of the polynomial, using Newton's method,
     * if one exists.
     * </pre>
     *
     * @param args The polynomial, in reverse order of terms, whose
     *             coefficients are integers.
     */
    public static void main(String[] args) {
        // check for one or more command line arguments
        if (args.length == 0) {
            System.out.println("Usage: java Poly term0 ...");
            return;
        }

        // read the polynomial in from the command line
        int[] poly = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            // poly = new int[args.length + 1];
            poly[i] = Integer.parseInt(args[i]);
            // System.out.println(poly[i]); // Uncomment if you want to debug.
        }

        // pretty print the polynomial
        System.out.println("f(x) = " + PolyString.getString(poly));

        // get a value for x and evaluate the polynomial with it
        Scanner in = new Scanner(System.in);
        System.out.print("Enter x: ");
        double x = in.nextDouble();
        final double eval = PolyEval.evaluate( poly, x );
        System.out.println( "f(" + x + ") = " + eval );
        in.close();

        // get the derivative of the polynomial and pretty print it
        int[] deriv = PolyDerive.computeDerivative(poly);
        System.out.println("f'(x) = " + PolyString.getString(deriv));

        // get the root of the polynomial, if derivative is not 0
        if (PolyEval.isZero(deriv)) {
            System.out.println("Root: none exist");
        } else {
            System.out.println("Root: " + PolyRoot.computeRoot(poly));
        }
    }
}
