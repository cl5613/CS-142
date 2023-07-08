package edu.rit.cs.chess;

import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

/**
 * Knight class
 * @author Chen Lin
 */
public class Knight extends Piece{

    /**
     * Create a Knight piece
     * @param name the piece's name (will start with 'n' or 'N')
     * @param pos the piece's starting position on the board
     * @param board the Game board on which the pieces is placed
     */
    public Knight(String name, Coordinates pos, Game board) {
        super(name, pos, board);
    }

    /**
     * A knight is limited to eight destinations relative to its current position.
     * Their move paths look like an "L": two cells in one rectilinear direction,
     * a right-angle turn, and then one cell in that direction. Knights can jump over other pieces.
     * They do not capture the pieces over which they jump. This method checks whether the destination
     * specifies a move that is legal according to (modified) Chess rules, and if there is a clear
     * path to the destination (for non-jumpers).
     * @param newPos intended destination coordinates
     * @return ActionResult.OK if everything is good, otherwise an ActionResult
     * that either states "The way is blocked: currentPos -> newPos" or "Illegal piece move newPos"
     */
    public ActionResult isLegalMove(Coordinates newPos) {
        if ( (Math.abs(getPos().delta(newPos).row()) == 2 && Math.abs(getPos().delta(newPos).column()) == 1)
        || (Math.abs(getPos().delta(newPos).row()) == 1 && Math.abs(getPos().delta(newPos).column()) == 2) ) {
            return ActionResult.OK;
        }
        return new ActionResult("Illegal piece move newPos");
    }
}
