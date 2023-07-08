package edu.rit.cs.labgraph.tests;

import edu.rit.cs.labgraph.Edge;
import edu.rit.cs.labgraph.FlowGraph;
import static edu.rit.cs.labgraph.FlowGraph.CAP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercise the {@link FlowGraph} class.
 * This is <em>not</em> a thorough test.
 * r
 * @author RIT CS
 */
@TestMethodOrder( MethodOrderer.Alphanumeric.class )
public class TestFlowGraph {

    public final static String A = "A";
    public final static String B = "B";
    public final static String C = "C";
    public final static String D = "D";
    public final static List< String > allNodes = List.of( A, B, C, D );

    private FlowGraph graph;
    private Edge eAB;
    private Edge eAC;
    private Edge eBC;
    private Edge eDB;
    private Edge eDC;
    public List< Edge > allEdges;

    private void checkFlowIs( long f ) {
        for ( Edge e: this.allEdges ) {
            String in = e.getInNode();
            String out = e.getOutNode();
            assertEquals( graph.getAvailableFlow( in, out ), f );
            assertEquals( graph.getAvailableFlow( out, in ), 2*CAP - f );
        }
    }

    @BeforeEach
    public void buildGraph() {
        this.graph = new FlowGraph();
        this.eAB = this.graph.getEdge( A, B );
        this.eAC = this.graph.getEdge( A, C );
        this.eBC = this.graph.getEdge( B, C );
        this.eDB = this.graph.getEdge( D, B );
        this.eDC = this.graph.getEdge( D, C );
        this.allEdges = List.of(
                this.eAB, this.eAC, this.eBC, this.eDB, this.eDC
        );
    }

    @Test
    public void t00_sourceAndSink() {
        assertEquals( this.graph.getSource(), A );
        assertEquals( this.graph.getSink(), D );
    }

    @Test
    public void t01_neighborTest() {
        assertEquals( this.graph.getEdgesAt( A ), Set.of( eAB, eAC ) );
        assertEquals( this.graph.getEdgesAt( B ), Set.of( eBC, eAB, eDB ) );
        assertEquals( this.graph.getEdgesAt( C ), Set.of( eBC, eAC, eDC ) );
        assertEquals( this.graph.getEdgesAt( D ), Set.of( eDB, eDC ) );
    }

    @Test
    public void t02_initialSetup() {
        assertEquals( eAB.getOtherEnd( A ), B );
        assertEquals( eAB.getOtherEnd( B ), A );
        assertEquals( eAC.getOtherEnd( A ), C );
        assertEquals( eAC.getOtherEnd( C ), A );
        assertEquals( eBC.getOtherEnd( B ), C );
        assertEquals( eBC.getOtherEnd( C ), B );
        assertEquals( eDB.getOtherEnd( D ), B );
        assertEquals( eDB.getOtherEnd( B ), D );
        assertEquals( eDC.getOtherEnd( D ), C );
        assertEquals( eDC.getOtherEnd( C ), D );
    }

    @Test
    public void t03_noFlow() {
        checkFlowIs( CAP );
    }

    @Test
    public void t04_halfFlow() {
        for ( Edge e: this.allEdges ) {
            e.changeFlow( e.getInNode(), e.getOutNode(), 2L );
        }
        checkFlowIs( 2L );
    }

    @Test
    public void t05_halfFlowReverse() {
        for ( Edge e: this.allEdges ) {
            e.changeFlow( e.getInNode(), e.getOutNode(), 2L );
        }
        for ( Edge e: this.allEdges ) {
            e.changeFlow( e.getInNode(), e.getOutNode(), -4L );
        }
        checkFlowIs( 6L );
    }

    @Test
    public void t10_BFS() {
        Optional< List< String > > searchResult = this.graph.doBFS();
        assertTrue( searchResult.isPresent() );
        List< String > path = searchResult.get();
        assertTrue (
                path.equals( List.of( A, B, D ) ) ||
                path.equals( List.of( A, C, D ) )
        );
    }

    private void checkBFSResult( String... nodes ) {
        Optional<List<String>> searchResult = this.graph.doBFS();
        assertTrue(searchResult.isPresent());
        List<String> path = searchResult.get();
        assertEquals(List.of(nodes), path);
    }

    @Test
    public void t11_BFSOneWay() {
        this.eAB.changeFlow( A, B, CAP );
        // The next step shouldn't be necessary but
        // without it the flow graph is illegal.
        this.eDB.changeFlow( B, D, CAP );
        this.checkBFSResult( A, C, D );
    }

    @Test
    public void t12_BFSOtherWay() {
        this.eAC.changeFlow( C, A, -CAP );
        // The next step shouldn't be necessary but
        // without it the flow graph is illegal.
        this.eDC.changeFlow( D, C, -CAP );
        this.checkBFSResult( A, B, D );
    }

    @Test
    public void t13_BFSLonger() {
        this.eAB.changeFlow( A, B, CAP );
        this.eBC.changeFlow( B, C, CAP );
        this.eDC.changeFlow( D, C, -CAP );
        this.checkBFSResult( A, C, B, D );
    }

    @Test
    public void t14_BFSReverse() {
        this.eAC.changeFlow( C, A, -CAP );
        this.eBC.changeFlow( B, C, -CAP );
        this.eDB.changeFlow( B, D, CAP );
        this.checkBFSResult( A, B, C, D );
    }

}
