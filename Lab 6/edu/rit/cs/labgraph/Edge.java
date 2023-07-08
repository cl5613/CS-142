package edu.rit.cs.labgraph;

/**
 * A class representing graph edges that are labeled with flow values.
 * This class was custom designed for the Max Flow problem.
 * @author Chen Lin
 */
public class Edge {

    /**
     * How much traffic, or flow, this edge can handle,
     * in either direction (but not in both)
     */
    private final long capacity;

    /**
     * The name of the first node.
     * When flow is assigned, it is positive if the flow comes
     * <em>from</em> this node.
     */
    private final String in;

    /**
     * The name of the second node.
     * When flow is assigned, it is positive if the flow goes
     * <em>toward</em> this node.
     */
    private final String out;

    /**
     * The flow in the direction from in to out.
     * Negative if the flow is in the opposite direction.
     */
    private long flow;

    /**
     * Create a new edge with an initial flow of 0.
     * @param in The name of the first node.
     * When flow is assigned, it is positive if the flow comes
     * <em>from</em> this node.
     * @param out The name of the second node.
     * When flow is assigned, it is positive if the flow comes
     * <em>towards</em> this node.
     * @param capacity the maximum flow that can travel through this edge
     *                 in either direction.
     */
    public Edge( String in, String out, long capacity ) {
        this.in = in;
        this.out = out;
        this.capacity = capacity;
        this.flow = 0;
    }

    /**
     * Find the node at the other end of this edge
     *
     * @param thisEnd the name of the node at one end
     * @precondition thisEnd must be the name of one of this edge's nodes.
     * @return the name of the node at the other end
     *
     */
    public String getOtherEnd(String thisEnd){
        if (thisEnd.equals(in)) {
            return out;
        }
        else {
            return in;
        }
    }

    /**
     * Get the incoming node
     * @return the name of the node associated with in in the constructor
     */
    public String getInNode() {
        return this.in;
    }

    /**
     * Get the outgoing node
     * @return the name of the node associated with out in the constructor
     */
    public String getOutNode() {
        return this.out;
    }

    /**
     * Get the highest amount of flow that can be associated with this edge.
     * @return the value associated with capacity in the constructor
     */
    public long getCapacity() {
        return this.capacity;
    }

    /**
     * Compute which direction of flow the parameters indicate, compared to the order specified in the constructor.
     * NOTE! This method is made public only so it can be tested. It should not be otherwise called from the outside.
     *
     * @param in the name of the node from which flow would come
     * @param out the name of the node toward which flow would travel
     * @return 1 if (in,out) matches the node order in the constructor, or -1 if the order is reversed.
     */
    public int direction(String in, String out) {
        if (in.equals(this.out)) {
            return -1;
        }
        else {
            return 1;
        }
    }

    /**
     * Find out how much more flow can be pushed through this edge.
     * @param in the name of the node from which flow would come
     * @param out the name of the node toward which flow would travel
     * @return capacity - direction(String, String) * existing-flow
     */
    public long availableFlow(String in, String out) {
        return capacity - direction(in, out) * flow;
    }

    /**
     * Add more flow to this edge in the direction of in→out. The new flow will be old-flow + direction(in,out)*delta.
     *
     * @param in the name of the node from which flow would come
     * @param out  the name of the node toward which flow would travel
     * @param delta the change in the amount of flow
     * @precondition The resulting flow, in absolute value, cannot exceed capacity.
     */
    public void changeFlow(String in, String out, long delta) {
        if (flow + direction(in, out) * delta <= capacity) {
            flow += direction(in, out) * delta;
        }
    }

    /**
     * Get the flow currently traveling from in to out.
     * @param in the name of the node from which flow would come
     * @param out the name of the node toward which flow would travel
     * @return the amount of flow, negated if the in→out direction is opposite to the direction specified in the constructor.
     */
    public long getFlow(String in, String out) {
        if (direction(in,out) > 0) {
            return flow;
        }
        return -flow;
    }

    /**
     * Indicate all the information stored in this edge.
     * @return a String in the format "<code>[in==>flow/capacity==>out]</code>"
     */
    @Override
    public String toString() {
        return "[" + this.in +
               "==>" +
               this.direction( this.in, this.out ) * this.flow +
               '/' +
               this.capacity +
               "==>" +
               this.out + ']';
    }

    /**
     * Indicate node names and flow stored in this edge.
     * @return a String in the format "<code>[in==>flow==>out]</code>"
     */
    public String toStringNoMax() {
        return "[" + this.in +
               "==>" +
               this.direction( this.in, this.out ) * this.flow +
               "==>" +
               this.out + ']';
    }

}
