package dendron.treenodes;

import dendron.Errors;
import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * A calculation represented by a binary operator and its two operands.
 * @author Chen Lin
 */

public class BinaryOperation extends Object implements ExpressionNode {

    /** The operator symbol used for addition */
    public static final String ADD = "+";
    /** The operator symbol used for subtraction */
    public static final String SUB = "-";
    /** The operator symbol used for multiplication */
    public static final String MUL = "*";
    /** The operator symbol used for division */
    public static final String DIV = "/";
    /** Container of all legal binary operators, for use by parsers */
    public static final Collection<String> OPERATORS = Arrays.asList(ADD, SUB, MUL, DIV);
    /** operator */
    public String operator;
    /** leftChild */
    public ExpressionNode leftChild;
    /** rightChild */
    public ExpressionNode rightChild;

    /**
     * Create a new BinaryOperation node.
     * @param operator the string rep. of the operation
     * @param leftChild the left operand
     * @param rightChild the right operand
     * @precondition OPERATORS.contains( operator ), leftChild != null, rightChild != null
     */
    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode rightChild) {
        this.operator = operator;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Compute the result of evaluating both operands and applying the operator to them.
     * @param symTab symbol table, needed to evaluate the child trees
     * @return the result of the computation
     */
    public int evaluate(Map<String, Integer> symTab) {
        int left = leftChild.evaluate(symTab);
        int right = rightChild.evaluate(symTab);

        switch (this.operator) {
            case ADD:
                return left + right;
            case SUB:
                return left - right;
            case MUL:
                return left * right;
            case DIV:
                if (right == 0) {
                    Errors.report(Errors.Type.DIVIDE_BY_ZERO, null);
                } else {
                    return left / right;
                }
                break;
        }
        return 0;
    }

    /**
     * Print, on standard output, the infixDisplay of the two child nodes separated
     * by the operator and surrounded by parentheses. Blanks are inserted throughout.
     */
    public void infixDisplay() {
        System.out.print("(" + " ");
        leftChild.infixDisplay();
        System.out.print(operator + " ");
        rightChild.infixDisplay();
        System.out.print(")" + " ");
    }


    /**
     * Emit onto a stream the text of the Soros assembly language instructions 
     * that, when executed, computes the result of this operation.
     * 
     * @param out the output stream for the compiled code — usually System.out
     */
    public void compile(PrintWriter out) {
        leftChild.compile(out);
        rightChild.compile(out);
        if (operator.equals(ADD)) {
            out.println(Soros.ADD);
        }
        if (operator.equals(SUB)) {
            out.println(Soros.SUBTRACT);
        }
        if (operator.equals(MUL)) {
            out.println(Soros.MULTIPLY);
        }
        if (operator.equals(DIV)) {
            out.println(Soros.DIVIDE);
        }
    }
}
