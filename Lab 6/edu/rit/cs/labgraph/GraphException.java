package edu.rit.cs.labgraph;

/**
 * A special exception class for graph-based lab assignments
 *
 * @author RIT CS
 */
public class GraphException extends Exception {
    /**
     * {@inheritDoc}
     * Create an exception that is just like a basic, checked Exception.
     */
    public GraphException( String reason ) { super( reason ); }
}
