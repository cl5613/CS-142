package dendron.treenodes;

import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * A calculation represented by a unary operator and its operand.
 * @author Chen Lin
 */

public class UnaryOperation extends Object implements ExpressionNode{
    /** arithmetic negation operator */
    public static final String NEG = "_";
    /** square root operator */
    public static final String SQRT = "%";
    /** Container of all legal unary operators, for use by parsers */
    public static final Collection<String> OPERATORS = Arrays.asList(NEG, SQRT);
    /** operators */
    private final String operator;
    /** expressions */
    private final ExpressionNode expr;

    /**
     * Create a new UnaryOperation node
     * @param operator  the string rep. of the operation
     * @param expr the operand
     * @precondition OPERATORS.contains( operator ), expr != null
     */
    public UnaryOperation(String operator, ExpressionNode expr) {
        this.operator = operator;
        this.expr = expr;
    }

    /**
     * Compute the result of evaluating the expression and applying the operator to it.
     * @param symTab symbol table, if needed, to fetch variable values (needed to evaluate the child tree)
     * @return the result of the computation
     */
    public int evaluate(Map<String, Integer> symTab) {
        int result = expr.evaluate(symTab);
        if (operator.equals(NEG)) {
            return result * -1;
        }
        if (operator.equals(SQRT)) {
            return (int) Math.sqrt(result);
        }
        return 0;
    }

    /**
     * Print, on standard output, the infixDisplay of the child nodes
     * preceded by the operator and without an intervening blank.
     */
    public void infixDisplay() {
        System.out.print(operator + " ");
        expr.infixDisplay();
    }

    /**
     * Emit onto a stream the text of the Soros assembly language
     * instructions that, when executed, computes the result of this operation.
     * @param out the output stream for the compiled code &mdash; usually System.out
     */
    public void compile(PrintWriter out) {
        expr.compile(out);
        if (operator.equals(NEG)) {
            out.println(Soros.NEGATE);
        }
        if (operator.equals(SQRT)) {
            out.println(Soros.SQUARE_ROOT);
        }
    }
}
