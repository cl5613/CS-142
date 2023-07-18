package dendron;

import dendron.treenodes.*;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Operations that are done on a Dendron code parse tree.
 *
 * @author Chen Lin
 */
public class ParseTree {

    /** Identifier expression */
    public static final String ASSIGN = ":=";
    /** Print statement */
    public static final String PRINT = "#";
    /** Program */
    private Program root;

    /**
     * Parse the entire list of program tokens. The program is a
     * sequence of actions (statements), each of which modifies something
     * in the program's set of variables. The resulting parse tree is
     * stored internally.
     * @param tokens the token list (Strings). This list may be destroyed
     *                by this constructor.
     */
    public ParseTree( List< String > tokens ) {
        root = new Program();
        while (!tokens.isEmpty()) {
            root.addAction(parseActionNode(tokens));
        }
    }

    /**
     * Parse ActionNodes
     * @param tokens the token list (String)
     * @return each ActionNode
     */
    public ActionNode parseActionNode(List<String> tokens) {
        if (tokens.get(0).equals(ASSIGN)) {
            tokens.remove(0);
            if (tokens.isEmpty()) {
                Errors.report(Errors.Type.PREMATURE_END, null);
            }
            String ident = tokens.remove(0);
            if (ident.matches("^[a-zA-Z].*")) {
                ExpressionNode rhs = parseExpressionNode(tokens);
                Assignment assignment = new Assignment(ident, rhs);
                return assignment;
            }
            else {
                Errors.report(Errors.Type.ILLEGAL_VALUE, ident);
            }

        } else if (tokens.get(0).equals(PRINT)) {
            tokens.remove(0);
            ExpressionNode printee = parseExpressionNode(tokens);
            Print print = new Print(printee);
            return print;
        }
        else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, tokens.get(0));
        }
        return null;
    }

    /**
     * Parse ExpressionNodes
     * @param tokens the token list (String)
     * @return Each ExpressionNode
     */
    public ExpressionNode parseExpressionNode(List<String> tokens) {
        if (tokens.isEmpty()) {
            Errors.report(Errors.Type.PREMATURE_END, null);
        }
        else if (BinaryOperation.OPERATORS.contains(tokens.get(0))) {
            String operator = tokens.remove(0);
            ExpressionNode lhs = parseExpressionNode(tokens);
            ExpressionNode rhs = parseExpressionNode(tokens);
            BinaryOperation binaryOperation = new BinaryOperation(operator, lhs, rhs);
            return binaryOperation;
        }
        else if (UnaryOperation.OPERATORS.contains(tokens.get(0))) {
            String operator = tokens.remove(0);
            ExpressionNode rhs = parseExpressionNode(tokens);
            UnaryOperation unaryOperation = new UnaryOperation(operator, rhs);
            return unaryOperation;
        }
        else if (tokens.get(0).matches( "-?[0-9]+" )) {
            int num = Integer.parseInt(tokens.remove(0));
            Constant constant = new Constant(num);
            return constant;
        }
        else if ((tokens.get(0).matches("^[a-zA-Z].*"))) {
            String variable = tokens.remove(0);
            Variable var = new Variable(variable);
            return var;
        }
        return null;
    }

    /**
     * Print the program the tree represents in a more typical
     * infix style, and with one statement per line.
     * @see ActionNode#infixDisplay()
     */
    public void displayProgram() {
        root.infixDisplay();
    }

    /**
     * Run the program represented by the tree directly
     * @see ActionNode#execute(Map)
     */
    public void interpret() {
        System.out.println("Interpreting the parse tree...");
        Map<String, Integer> symTab = new HashMap<>();
        root.execute(symTab);
        System.out.println("Interpretation complete");
        Errors.dump(symTab);
    }

    /**
     * Build the list of machine instructions for
     * the program represented by the tree.
     *
     * @param out where to print the Soros instruction list
     */
    public void compileTo( PrintWriter out ) {
        root.compile(out);
    }
}
