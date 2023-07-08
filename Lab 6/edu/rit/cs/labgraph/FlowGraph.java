package edu.rit.cs.labgraph;

import java.io.*;
import java.util.*;
import java.util.concurrent.Flow;

/**
 * A Graph representation where nodes are just strings that contain no data,
 * and edges are named by their node endpoints, their flow, and their flow
 * capacity.
 * This graph class was custom-designed for the Max Flow Problem.
 *
 * @author RIT CS
 * @author Chen Lin
 */
public class FlowGraph {

    /**
     * The starting node for the {@link #doBFS()} method
     */
    private final String source;

    /**
     * The destination node for the {@link #doBFS()} method
     */
    private final String sink;

    /**
     * The internal graph data structure: a map where each node name
     * is associated with the set of edges connected to it
     */
    private final LinkedHashMap< String, LinkedHashSet< Edge > > adjList;

    /**
     * <em>This constant is only used for testing.</em>
     */
    public static final long CAP = 4L;

    /**
     * Build a fixed graph for testing.
     * <em>This method is here only for testing.</em>
     * <pre>
     *        B
     *      / | \
     *     A  |  D
     *      \ | /
     *        C
     * </pre>
     */
    public FlowGraph() {
        final String A = "A";
        final String B = "B";
        final String C = "C";
        final String D = "D";

        Edge eAB = new Edge( A, B, CAP );
        Edge eAC = new Edge( A, C, CAP );
        Edge eBC = new Edge( B, C, CAP );
        Edge eDB = new Edge( D, B, CAP );
        Edge eDC = new Edge( D, C, CAP );

        this.source = A;
        this.sink = D;
        this.adjList = new LinkedHashMap<>();

        for ( String node: List.of( A, B, C, D ) ) {
            this.adjList.put( node, new LinkedHashSet<>() );
        }
        this.adjList.get( A ).add( eAB );
        this.adjList.get( B ).add( eAB );
        this.adjList.get( A ).add( eAC );
        this.adjList.get( C ).add( eAC );
        this.adjList.get( B ).add( eBC );
        this.adjList.get( C ).add( eBC );
        this.adjList.get( D ).add( eDB );
        this.adjList.get( B ).add( eDB );
        this.adjList.get( D ).add( eDC );
        this.adjList.get( C ).add( eDC );
    }

    /**
     * Build a graph from the edge list in a file. Each line in the file contains two nodes and a maximum flow capacity:
     * Example: Gates Chili 45
     * A BufferedReader is used to read the file contents. Note that each line specifies one edge.
     * But each edge will get attached to the lists of two nodes, without modification.
     *
     * @param graphFileName the name of the edge list file
     * @param source the source of the flow for the maxflow problem
     * @param sink the sink of the flow for the maxflow problem
     * @throws IOException if there is a problem reading the input file
     * @throws GraphException if the data in the file is not correctly formatted
     * or if either the source or the sink is not in the final graph
     */
    public FlowGraph (String graphFileName, String source, String sink) throws IOException, GraphException {
        this.source = source;
        this.sink = sink;
        this.adjList = new LinkedHashMap<>();

        BufferedReader in = new BufferedReader(new FileReader(graphFileName));
        String l;
        while ( ( in.ready() ) ) {
            l = in.readLine();
            String[] i = l.split( " " );
            Edge edge = new Edge(i[0], i[1], Long.parseLong(i[2]));
            for ( String node: List.of(i[0], i[1]) ) {
                if (this.adjList.get(node) == null) {
                    this.adjList.put(node, new LinkedHashSet<>());
                }
                adjList.get(node).add(edge);
            }
        }
    }

    /**
     * Find the shortest path from source to sink, taking into account the available flow on each edge considered.
     * This means that edges whose flows are maxed out in the direction being considered will not be used
     * for the solution path. A breadth-first-search algorithm is used, with no guarantee on order of
     * consideration of edges.
     * @return an Optional containing the list of nodes for the path, from source to sink,
     * or Optional.empty() if no path exists
     */
    public Optional<List<String>> doBFS() {
        List<String> queue = new LinkedList<>();
        queue.add(source);
        Map<String, String> predecessors = new HashMap<>();
        predecessors.put(source, source);
        while (!queue.isEmpty()) {
            String current = queue.remove(0);
            if (current.equals(sink)) {
                break;
            }
            for (Edge edge: getEdgesAt(current)) {
                String other = edge.getOtherEnd(current);
                if (!predecessors.containsKey(other) && edge.availableFlow(current, other) > 0) {
                    predecessors.put(other, current);
                    queue.add(other);
                }
            }
        }
        List<String> path = new LinkedList<>();
        if (predecessors.containsKey(sink)) {
            String currNode = sink;
            while (!currNode.equals(source)) {
                path.add(0, currNode);
                currNode = predecessors.get(currNode);
            }
            path.add(0, source);
        }
        if (path.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(path);
        }
    }

    /**
     * @return the name of the starting node for this graph
     */
    public String getSource() {
        return this.source;
    }

    /**
     * @return the name of the ending node for this graph
     */
    public String getSink() {
        return this.sink;
    }

    /**
     * What edges are connected to this node?
     *
     * @param node the node's name
     * @return A Set of {@link Edge} objects whose in or out node equals
     *         the parameter node
     * @rit.pre node is not null and is in the graph.
     */
    public Set< Edge > getEdgesAt( String node ) {
        return this.adjList.get( node );
    }

    /**
     * Get an edge in this graph
     * @param a the name of one of the edge's nodes
     * @param b the name of one of the edge's nodes
     * @return the Edge object containing the two given nodes
     * @precondition node1 and node2 are distinct, and they are both in the graph.
     */
    public Edge getEdge(String a, String b) {
        for (Edge edge: getEdgesAt(a)) {
            if (edge.getOtherEnd(a).equals(b)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Determine how much remaining flow is available from one node to another in this graph.
     * The calculation is capacity minus current flow if flow is in the same direction as
     * previous→current or capacity plus current flow if flow is in the opposite direction.
     *
     * @param previous the name of the node that would inject more flow into the edge
     * @param current the name of the node that would pull more flow out of the edge
     * @return the amount of flow as described above
     */
    public long getAvailableFlow(String previous, String current) {
        Edge edge = this.getEdge(previous, current);
        return edge.getCapacity() - edge.getFlow(previous, current);

    }

    /**
     * Print, on standard output, a representation of this graph.
     * The format on each line is a single node, followed by a comma-separated list of edges.
     *
     * @param showMax If true, include each edge's flow and capacity; if false, just include each edge's flow.
     */
    public void show(boolean showMax) {
        for (String node: adjList.keySet()) {
            System.out.print(node + ": ");
            for (Edge edge : adjList.get(node)) {
                if (showMax) {
                    System.out.println(edge.toString());
                }
                else {
                    System.out.println(edge.toStringNoMax());
                }
            }
        }
    }
}
