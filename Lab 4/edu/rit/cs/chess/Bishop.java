package edu.rit.cs.chess;

import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

/**
 * Bishop class
 * @author Chen Lin
 */
public class Bishop extends Piece{

    /**
     * Create a Bishop piece.
     * @param name the piece's name (will start with 'b' or 'B')
     * @param pos the piece's starting position on the board
     * @param board the Game board on which the pieces is placed
     */
    public Bishop(String name, Coordinates pos, Game board) {
        super(name, pos, board);
    }

    /**
     * Bishops may only move diagonally. In other words, the absolute values of the
     * changes in the row and column positions must be the same. This method checks
     * whether the destination specifies a move that is legal according to
     *(modified) Chess rules, and if there is a clear path to the destination (for non-jumpers).
     * @param newPos intended destination coordinates
     * @return ActionResult.ok if everything is good, otherwise and ActionResult that either states
     *  "The way is blocked: currentPos -> newPos" or "Illegal piece move newPos"
     */
    public ActionResult isLegalMove(Coordinates newPos){
        if (Math.abs(getPos().delta(newPos).row()) == Math.abs(getPos().delta(newPos).column())) {
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
