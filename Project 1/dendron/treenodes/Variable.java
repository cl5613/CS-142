package dendron.treenodes;

import dendron.Errors;
import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Map;

/**
 * The ExpressionNode for a simple variable
 * @author Chen Lin
 */
public class Variable extends Object implements ExpressionNode{

    /** variable */
    private String name;

    /**
     * Set the name of this new Variable. Note that it is not wrong for more than one Variable
     * node to refer to the same variable. Its actual value is stored in a symbol table.
     * @param name the name of this variable
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Print on standard output the Variable's name.
     */
    public void infixDisplay() {
        System.out.print(name + " ");
    }

    /**
     * Evaluate a variable by fetching its value
     * @param symTab the table containing all variables' values
     * @return this variable's current value in the symbol table
     */
    public int evaluate(Map<String, Integer> symTab) {
        if (!symTab.containsKey(name)) {
            Errors.report(Errors.Type.UNINITIALIZED, name);
        }
        return symTab.get(name);
    }

    /**
     * Emit onto a stream the text of the Soros assembly language instructions
     * that, when executed, pushes the value of the variable on the stack.
     * @param out the output stream for the compiled code &mdash; usually System.out
     */
    public void compile(PrintWriter out) {
        out.println(Soros.LOAD + " " + name);
    }
}
