package dendron.treenodes;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An ActionNode used to represent a sequence of other ActionNodes.
 * The main use of this node type is to be the root of the entire program tree.
 *
 * @author RIT CS
 */
public class Program {

    private List< ActionNode > actions = null;

    /**
     * Initialize this instance as an empty sequence of ActionNode
     * children.
     */
    public Program() {
        this.actions = new ArrayList<>();
    }

    /**
     * Add a child of this Program node.
     * @param newNode the node representing the action that will execute last
     */
    public void addAction( ActionNode newNode ) {
        this.actions.add( newNode );
    }

    /**
     * Execute each ActionNode in this object, from
     * first-added to last-added.
     * @param symTab the table of variable values
     */
    public void execute( Map< String, Integer > symTab ) {
        for ( ActionNode an: this.actions ) {
            an.execute( symTab );
        }
    }

    /**
     * Show the infix displays of all children on standard output.
     * The order is first-added to last-added.
     */
    public void infixDisplay() {
        for ( ActionNode an: this.actions ) {
            an.infixDisplay();
            System.out.println();
        }
    }

    /**
     * Emit onto a stream the text of the Soros assembly language instructions
     * for the entire program.
     * @param out where the instructions will be written (usually System.out)
     */
    public void compile( PrintWriter out ) {
        for ( ActionNode node: this.actions ) node.compile( out );
    }

}
