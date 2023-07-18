package dendron.treenodes;

import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Map;

/**
 * A node that represents the displaying of the value of an expression on the console
 * @author Chen Lin
 */

public class Print extends Object implements ActionNode {

    /** print prefix */
    public static final String PRINT_PREFIX = "===";

    /** printee expression node */
    public ExpressionNode printee;

    /**
     * Set up a Print node
     * @param printee the expression to be evaluated and printed
     */
    public Print(ExpressionNode printee) {
        this.printee = printee;
    }

    /**
     * Evaluate the expression and display the result on the console.
     * Precede it with three equal signs so it stands out a little.
     * @param symTab the table where variable values are stored
     */
    public void execute(Map<String, Integer> symTab) {
        System.out.println(PRINT_PREFIX + " " + printee.evaluate(symTab));
    }

    /**
     * Show this statement on standard output as the word "Print" followed by the infix form of the expression.
     */
    public void infixDisplay() {
        System.out.print("Print" + " ");
        printee.infixDisplay();
    }

    /**
     * Emit onto a stream the text of the Soros assembly language instructions that, when executed, perform a print action.
     * @param out the output stream for the compiled code &mdash; usually System.out
     */
    public void compile(PrintWriter out) {
        printee.compile(out);
        out.println(Soros.PRINT);
    }
}
