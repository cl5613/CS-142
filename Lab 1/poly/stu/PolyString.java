package poly.stu;

/**
 * This class can return a string representation of a polynomial of order n,
 * in the form:
 * <pre>
 * x^n + x^n-1 + ... x^1 + constant
 * </pre>
 *
 * @author RIT CS
 */
public class PolyString {

    /**
     * Unused constructor, made private to avoid javadoc generation.
     */
    private PolyString() {
    }

    /**
     * The displayed variable name
     */
    public final static String VARIABLE_NAME = "x";

    /**
     * Get the string representation of the polynomial.  For example:
     * <pre>
     * poly=[1]: "1"
     * poly=[3, -1]: "-x + 3"
     * poly=[0, 3]: "3x + 0"
     * poly=[2, -1, -2, 1]: "x^3 + -2x^2 + -x + 2"
     * poly=[-5, 0, 0, 3, 3, 1]: "x^5 + 3x^4 + 3x^3 + -5"
     * </pre>
     *
     * @param poly A list representing the polynomial, in reverse order.
     * @rit.pre poly is not an empty array.  Minimally it will contain
     *      a constant term.
     * @return A string representation of the polynomial.
     */
    public static String getString(int[] poly) {
        StringBuilder result = new StringBuilder();

        // step backwards through list from highest to lowest order term
        for (int exp=poly.length-1; exp>=0; --exp) {
            int coeff = poly[exp];
            // include constant term, but don't want 1x or -1x
            if (exp == 0 || Math.abs(coeff) > 1) {
                result.append(coeff);
            }
            // include non-constant, non-zero terms
            if (exp > 0 && coeff != 0) {
                if (coeff == -1) {
                    result.append("-"); // ... + -x ...
                }
                result.append(VARIABLE_NAME);
                if (exp > 1) {    // don't want x^1
                    result.append("^").append(exp);
                }
                result.append(" + ");  // ok because constant always shows at end
            }
        }

        return result.toString();
    }
}
