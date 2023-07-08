package edu.rit.cs.labgraph.tests;

import edu.rit.cs.labgraph.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Exercise the {@link Edge} class.
 * @author RIT CS
 */
@TestMethodOrder( MethodOrderer.Alphanumeric.class )
public class TestEdge {

    public static final String IN = "incoming";
    public static final String OUT = "outgoing";
    private Edge e1;

    @BeforeEach
    public void init() {
        this.e1 = new Edge( IN, OUT, 10 );
    }

    @Test
    public void t00_directionStuff() {
        assertEquals( e1.getOtherEnd( IN ), OUT );
        assertEquals( e1.getOtherEnd( OUT ), IN );
        assertEquals( e1.direction( IN, OUT ), 1 );
        assertEquals( e1.direction( OUT, IN ), -1 );
    }

    @Test
    public void t01_initialValues() {
        assertEquals( e1.getInNode(), IN );
        assertEquals( e1.getOutNode(), OUT );
        assertEquals( e1.getCapacity(), 10L );
        assertEquals( e1.getFlow( IN, OUT ), 0L );
    }

    @Test
    public void t02_addFlow() {
        e1.changeFlow( IN, OUT, 6L );
        assertEquals( e1.getFlow( IN, OUT ), 6L );
        assertEquals( e1.getFlow( OUT, IN ), -6L );
    }

    @Test
    public void t03_changeFlowPlus() {
        e1.changeFlow( IN, OUT, 6L );
        e1.changeFlow( IN, OUT, 2L );
        assertEquals( e1.getFlow( IN, OUT ), 8L );
        assertEquals( e1.getFlow( OUT, IN ), -8L );
    }

    @Test
    public void t04_changeFlowReversedPlus() {
        e1.changeFlow( OUT, IN, -6L );
        e1.changeFlow( IN, OUT, 2L );
        assertEquals( e1.getFlow( IN, OUT ), 8L );
        assertEquals( e1.getFlow( OUT, IN ), -8L );
    }

    @Test
    public void t05_changeFlowMinus() {
        e1.changeFlow( IN, OUT, 6L );
        e1.changeFlow( IN, OUT, -2L );
        assertEquals( e1.getFlow( IN, OUT ), 4L );
        assertEquals( e1.getFlow( OUT, IN ), -4L );
    }

    @Test
    public void t06_changeFlowReversedMinus() {
        e1.changeFlow( OUT, IN, -6L );
        e1.changeFlow( IN, OUT, -2L );
        assertEquals( e1.getFlow( IN, OUT ), 4L );
        assertEquals( e1.getFlow( OUT, IN ), -4L );
    }

    @Test
    public void t07_changeFlowSwitch() {
        e1.changeFlow( IN, OUT, 6L );
        e1.changeFlow( IN, OUT, -10L );
        assertEquals( e1.getFlow( IN, OUT ), -4L );
        assertEquals( e1.getFlow( OUT, IN ), 4L );
    }

    @Test
    public void t08_changeFlowReversedSwitch() {
        e1.changeFlow( OUT, IN, -6L );
        e1.changeFlow( IN, OUT, -10L );
        assertEquals( e1.getFlow( IN, OUT ), -4L );
        assertEquals( e1.getFlow( OUT, IN ), 4L );
    }

    @Test
    public void t11_initialValues() {
        assertEquals( e1.availableFlow( IN, OUT ), 10L );
        assertEquals( e1.availableFlow( OUT, IN ), 10L );
    }

    @Test
    public void t12_addFlow() {
        e1.changeFlow( IN, OUT, 6 );
        assertEquals( e1.availableFlow( IN, OUT ), 4L );
        assertEquals( e1.availableFlow( OUT, IN ), 16L );
    }

    @Test
    public void t13_changeFlowPlus() {
        e1.changeFlow( IN, OUT, 6 );
        e1.changeFlow( IN, OUT, 2 );
        assertEquals( e1.availableFlow( IN, OUT ), 2L );
        assertEquals( e1.availableFlow( OUT, IN ), 18L );
    }

    @Test
    public void t14_changeFlowReversedPlus() {
        e1.changeFlow( OUT, IN, -6 );
        e1.changeFlow( IN, OUT, 2 );
        assertEquals( e1.availableFlow( IN, OUT ), 2L );
        assertEquals( e1.availableFlow( OUT, IN ), 18L );
    }

    @Test
    public void t15_changeFlowMinus() {
        e1.changeFlow( IN, OUT, 6 );
        e1.changeFlow( IN, OUT, -2 );
        assertEquals( e1.availableFlow( IN, OUT ), 6L );
        assertEquals( e1.availableFlow( OUT, IN ), 14L );
    }

    @Test
    public void t16_changeFlowReversedMinus() {
        e1.changeFlow( OUT, IN, -6 );
        e1.changeFlow( IN, OUT, -2 );
        assertEquals( e1.availableFlow( IN, OUT ), 6L );
        assertEquals( e1.availableFlow( OUT, IN ), 14L );
    }

    @Test
    public void t17_changeFlowSwitch() {
        e1.changeFlow( IN, OUT, 6 );
        e1.changeFlow( IN, OUT, -10 );
        assertEquals( e1.availableFlow( IN, OUT ), 14L );
        assertEquals( e1.availableFlow( OUT, IN ), 6L );
    }

    @Test
    public void t18_changeFlowReversedSwitch() {
        e1.changeFlow( OUT, IN, -6 );
        e1.changeFlow( IN, OUT, -10 );
        assertEquals( e1.availableFlow( IN, OUT ), 14L );
        assertEquals( e1.availableFlow( OUT, IN ), 6L );
    }
}
