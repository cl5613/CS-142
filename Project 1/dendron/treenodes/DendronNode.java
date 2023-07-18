package dendron.treenodes;

import java.io.PrintWriter;

/**
 * The top-level abstraction for all nodes in the Dendron parse tree.
 *
 * All nodes are capable of being displayed as part of an infix-format string,
 * and of emitting machine instructions so that they can be executed later.
 *
 * @author RIT CS
 */
public interface DendronNode {
    /**
     * Show the code rooted at this node, using infix format,
     * on standard output.
     */
    void infixDisplay();

    /**
     * Emit onto a stream the text of the Soros assembly language instructions
     * that, when executed, represents the intent of this {@link DendronNode}
     * and its descendants.
     *
     * @param out the output stream for the compiled code &mdash;
     *            usually {@link System#out}
     */
    void compile( PrintWriter out );
}


