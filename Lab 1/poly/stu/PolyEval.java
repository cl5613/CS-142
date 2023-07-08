package poly.stu;

/**
 * This class can evaluate a polynomial, and determine whether the polynomial
 * is zero or not.
 *
 * @author Chen Lin
 */

public class PolyEval {

    /**
     * Unused constructor, made private to avoid javadoc generation.
     */
    private PolyEval() {
    }

    /**
     * Evaluates a polynomial for a supplied value for x.  For example:
     * <pre>
     * poly=[1], x=0: 1.0
     * poly=[3, -1], x=4.5: -1.5
     * poly=[0, 3], x=-2: -6.0
     * poly=[2, -1, -2, 1], x=2: 0.0
     * poly=[-5, 0, 0, 3, 3, 1], x=-3.9: -391.16669
     * </pre>
     *
     * @param poly A list representing the polynomial, in reverse order.
     * @param x The value for x.
     * @return The result of the evaluation.
     * Poly is not an empty array.  Minimally it will contain
     * a constant term.
     */
    public static double evaluate(int[] poly, double x) {
        int counter = 0;
        double result = 0;
        for (double i : poly) {
            result = result + i * Math.pow(x, counter);
            ++counter;
        }
        return result;
    }

    /**
     * Tells whether a polynomial is zero or not.  For example:
     * <pre>
     * poly=[0]: True
     * poly=[1]: False
     * poly=[0, 3]: False
     * poly=[0,0,0,4]: False
     * </pre>
     *
     * @param poly A native array representing the polynomial, in reverse order.
     * @return True if it is zero, False otherwise.
     * Poly is not an empty array.  Minimally it will contain
     * a constant term.
     */
    public static boolean isZero(int[] poly) {
        if (poly.length == 1) {
            if (poly[0] == 0) {
                return true;
            }
        }
        return false;
    }}
