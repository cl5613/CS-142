package edu.rit.cs.chess;

import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

/**
 * King class
 * @author Chen Lin
 */

public class King extends Piece{

    /**
     * Create a King piece
     * @param name the piece's name (will start with 'k' or 'K')
     * @param pos the piece's starting position on the board
     * @param board the Game board on which the pieces is placed
     */
    public King(String name, Coordinates pos, Game board) {
        super(name, pos, board);
    }

    /**
     * Kings move like Queens, except they are restricted to a distance of one square.
     * This method checks whether the destination specifies a move that is legal according to
     * (modified) Chess rules, and if there is a clear path to the destination (for non-jumpers).
     * @param newPos intended destination coordinates
     * @return ActionResult.OK if everything is good, otherwise an ActionResult
     * that either states "The way is blocked: currentPos -> newPos" or "Illegal piece move newPos"
     */
    public ActionResult isLegalMove(Coordinates newPos) {
        if (Math.abs(getPos().delta(newPos).row()) == 1 && Math.abs(getPos().delta(newPos).column()) == 1
                || ((Math.abs(getPos().delta(newPos).row()) == 1 && Math.abs(getPos().delta(newPos).column()) == 0)
                || (Math.abs(getPos().delta(newPos).row()) == 0 && Math.abs(getPos().delta(newPos).column()) == 1))) {
            if (getBoard().isClearPath(getPos(), newPos)) {
                return ActionResult.OK;
            }
            else {
                return new ActionResult("The way is blocked: currentPos -> newPos");
            }
        }
        return new ActionResult("Illegal piece move newPos");
    }
}
