package dendron.treenodes;

import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Map;

/**
 * An expression node representing a constant, i.e., literal value
 * @author Chen Lin
 */

public class Constant extends Object implements ExpressionNode{

    /** a constant */
    private final int value;

    /**
     * Store the integer value in this new Constant.
     * @param value the integer it will hold
     */
    public Constant(int value) {
        this.value = value;
    }

    /**
     * Print this Constant's value on standard output.
     */
    public void infixDisplay() {
        System.out.print(value + " ");
    }

    /**
     * Evaluate the constant
     * @param symTab symbol table, if needed, to fetch variable values
     * @return this Constant's value
     */
    public int evaluate(Map<String, Integer> symTab) {
        return value;
    }

    /**
     * Emit onto a stream the text of the Soros assembly language instructions that,
     * when executed, saves the constant on the stack.
     * @param out the output stream for the compiled code &mdash; usually System.out
     */
    public void compile(PrintWriter out) {
        out.println(Soros.PUSH + " " + value);

    }
}
