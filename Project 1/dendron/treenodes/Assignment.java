package dendron.treenodes;

import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Map;

/**
 * An ActionNode that represents the assignment of the value of an expression to a variable.
 * @author Chen Lin
 */
public class Assignment extends Object implements ActionNode{
    /** the name of the variable that is getting a new value */
    private final String ident;
    /** the expression on the "right-hand side" (RHS) of the assignment statement */
    private final ExpressionNode rhs;

    /**
     * Set up an Assignment node. Note that the identifier is not turned into a Variable node.
     * The reason is that the variable's value is not needed; instead it is a destination for
     * a computation. This use is not compatible with Variable's mission.
     *
     * @param ident the name of the variable that is getting a new value
     * @param rhs the expression on the "right-hand side" (RHS) of the assignment statement
     */
    public Assignment(String ident, ExpressionNode rhs) {
        this.ident = ident;
        this.rhs = rhs;
    }

    /**
     * Evaluate the RHS expression and assign the result value to the variable.
     * @param symTab the table where variable values are stored
     */
    public void execute(Map<String, Integer> symTab) {
        symTab.put(ident, rhs.evaluate(symTab));
    }

    /**
     * Show this assignment on standard output as a variable followed by an assignment arrow (":=")
     * followed by the infix form of the RHS expression.
     */
    public void infixDisplay() {
        System.out.print(ident+ " " + ":=" + " ");
        rhs.infixDisplay();
    }

    /**
     * Emit onto a stream the text of the Soros assembly language instructions that, when executed, perform an assignment.
     * @param out the output stream for the compiled code &mdash; usually System.out
     */
    public void compile(PrintWriter out) {
        this.rhs.compile(out);
        out.println(Soros.STORE + " " + ident);
    }
}
