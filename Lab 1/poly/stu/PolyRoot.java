package poly.stu;

/**
 * This class can compute the root of a polynomial (whose derivative is
 * non-zero), using Newton's method of successive approximation.
 *
 * @author Chen Lin
 */
public class PolyRoot {
    /**
     * The degree of acceptable error for the root
     */
    public static final double EPSILON = 0.000001;

    /**
     * The initial guess for the root
     */
    public static final double INITIAL_GUESS = 0.1;

    /**
     * The maximum number of iterations to perform for root finding
     */
    public static final int MAX_ITERATIONS = 100;

    /**
     * Compute an estimate for a root of the polynomial using Newton's
     * method.
     *
     * @param poly A native array representing the polynomial, in reverse order.
     *             Poly is not an empty array.  Minimally it will contain
     *             a constant term.
     *             The derivative of poly is non-zero
     *             The evaluation of the derivative of the polynomial at a guessed
     *             root is non-zero.
     * @return An estimated root for the polynomial.
     */
    public static double computeRoot(int[] poly) {
        return newtonsMethod(poly, INITIAL_GUESS, 1);
    }

    /**
     * Compute an estimate for a root of the polynomial, using Newton's
     * method.
     *
     * @param poly A list representing the polynomial, in reverse order.
     * @param x0   The current guess for the root.
     * @param iter The current iteration being performed
     *             poly is not an empty list.  Minimally it will contain
     *             a constant term.
     *             The derivative of poly is non-zero
     *             The evaluation of the derivative of the polynomial at a guessed
     *             root is non-zero.
     * @return An estimated root for the polynomial.
     */
    private static double newtonsMethod(int[] poly, double x0, int iter) {
        double result = PolyEval.evaluate(poly, x0);
        if (Math.abs(result) <= EPSILON || iter > MAX_ITERATIONS) {
            return x0;
        } else {
            int[] derivative = PolyDerive.computeDerivative(poly);
            double slope = PolyEval.evaluate(derivative, x0);
            double x1 = x0 - result / slope;
            return newtonsMethod(poly, x1, ++iter);
        }
    }
}
