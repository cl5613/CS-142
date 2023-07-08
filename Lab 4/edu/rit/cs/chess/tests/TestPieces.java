package edu.rit.cs.chess.tests;

import edu.rit.cs.chess.Game;
import edu.rit.cs.util.Coordinates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A JUnit class to test the moves of individual chess pieces.
 *
 * @author RIT CS
 * January 2021
 */
public class TestPieces {

    private static String TEST_DIR = System.getProperty( "user.dir" ) +
                                     File.separator +
                                     "Tests" +
                                     File.separator;

    private static String TEST_BOARD = TEST_DIR + "board13x13.txt";

    private Game game = null;

    /**
     * Convert an array of integer pair arrays to an array of Coordinates.
     * @param locs the 2D array of plain integers
     * @return an array where each pair is converted to a Coordinates object
     */
    private static Coordinates[] makeCoordArray( int[][] locs ) {
        return Arrays.stream( locs )
                     .map( Coordinates::new )
                     .toArray( Coordinates[]::new );
    }

    /**
     * Test a series of moves
     * @param good coordinate array as 2D array of integers
     *      A series of moves that should work.
     *      The first coordinate pair is where the piece exists before we start.
     * @param bad
     *      A bunch of destinations from the final good position that should
     *      not work.
     */
    private void tryMoves( int[][] good, int[][] bad ) {

        // Test the good moves.

        Coordinates[] goodPath = TestPieces.makeCoordArray( good );
        for ( int move = 0; move < goodPath.length - 1; ++move ) {
            final Coordinates source = goodPath[ move ];
            final Coordinates dest = goodPath[ move + 1 ];
            assertTrue(
                    this.game.makeMove( source, dest ).ok,
                    source + "-->" + dest
            );
        }

        // Test the bad moves (starting at last good spot).

        Coordinates[] badSpots = TestPieces.makeCoordArray( bad );
        Coordinates lastGood = goodPath[ goodPath.length - 1 ];
        for ( Coordinates dest: badSpots ) {
            assertFalse(
                    this.game.makeMove( lastGood, dest ).ok,
                    lastGood + "-->" + dest
            );
        }
    }

    /**
     * (Re)create a Game object from the test file given in
     * the static string variable above.
     */
    @BeforeEach
    public void begin() {
        String dir = System.getProperty( "user.dir" );
        assertDoesNotThrow( () -> {
            this.game = new Game( TEST_BOARD );
        } );
    }

    @Test
    public void moveBishop() {
        this.tryMoves(
                new int[][]{
                        {2,2}, {0,0}, {8,8} /* where Pawn is */,
                        {5,11}, {10,6}, {5,11}
                },
                new int[][]{
                        {1,7}, {10,8}, {10,4}, {8,6}, {12,6}
                }
        );
    }

    @Test
    public void moveRook() {
        tryMoves(
                new int[][]{
                        {2,5}, {0,5}, {0,8}, {2,8} /* where Queen is */,
                        {2,2} /* where Bishop is */,
                        {3,2} /* where Knight is */,
                        {5,2}
                },
                new int[][]{
                        {5,5}, {2,1}, {0,5}, {12,9}, {6,0}
                }
        );
    }

    @Test
    public void moveKnight() {
        tryMoves(
                new int[][] {
                        {3,2}, {1,1},
                        {3,2}, {1,3},
                        {2,5} /* where Rook is */, {1,3},
                        {3,2}, {2,0},
                        {3,2}, {4,0},
                        {3,2}
                },
                new int[][] {
                        {2,1},{1,0},
                        {2,2},{1,2},
                        {2,3},{1,4},
                        {3,3},{3,4}, // !!!! 3,3 fails?
                        {4,3},{5,4},
                        {4,2},{5,2},
                        {4,1},{5,0},
                        {3,1},{3,0}
                }
        );
    }

    @Test
    public void moveQueen() {
        tryMoves(
                new int[][] {
                        {2,8}, {0,8}, {1,9}, {4,12}, {4,11}, {4,8},
                        {5,8}, {8,8} /* where Pawn is */,
                        {9,7}, {12,4}, {5,4} /* where King is */
                },
                new int[][] {
                        {1,0} /* can't jump Knight */,
                        {3,5}, {4,6}, {6,6}, {7,5}, {7,3}, {6,2}, {4,2}, {3,3}
                }
        );
    }

    @Test
    public void moveKing() {
        tryMoves(
                new int[][] {
                        {5,4}, {5,3}, {5,4}, {6,4},
                        {5,4}, {4,4}, {3,4}, {2,4}, {2,5} /* where Rook is */
                },
                new int[][] {
                        {0,3}, {0,4}, {0,5}, {0,6}, {0,7},
                        {1,7}, {2,7}, {3,7},
                        {4,7}, {4,6}, {4,5}, {4,4}, {4,3},
                        {3,3}, {2,3}, {1,3}
                }
        );
    }

    @Test
    public void movePawn() {
        tryMoves(
                new int[][] {
                        {8,8}, {7,8}, {6,8}, {5,8}, {4,8}, {3,8},
                        {2,8} /* where Queen is */
                },
                new int[][] {
                        {0,6}, {0,7}, {0,8}, {0,9}, {0,10},
                        {1,6}, {1,7},        {1,9}, {1,10},
                        {2,6}, {2,7}, {2,8}, {2,9}, {2,10},
                        {3,6}, {3,7}, {3,8}, {3,9}, {3,10},
                        {4,6}, {4,7}, {4,8}, {4,9}, {4,10},
                        }
        );
    }

}
