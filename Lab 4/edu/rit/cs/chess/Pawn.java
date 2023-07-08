package edu.rit.cs.chess;


import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

/**
 * Pawn class
 * @author Chen Lin
 */

public class Pawn extends Piece{
    /**
     * Create a Pawn piece
     * @param name the piece's name (will start with 'p' or 'P')
     * @param pos the piece's starting position on the board
     * @param board the Game board on which the pieces is placed
     */
    public Pawn(String name, Coordinates pos, Game board) {
        super(name, pos, board);
    }

    /**
     * Pawns may only move forward one position. "Forward" means decreasing the row number by one.
     * This method checks whether the destination specifies a move that is legal according to (modified)
     * Chess rules, and if there is a clear path to the destination (for non-jumpers).
     *
     * @param newPos intended destination coordinates
     * @return ActionResult.ok if everything is good, otherwise an
     * ActionResult that either states
     * "The way is blocked: currentPos -> newPos" or "Illegal piece move newPos"
     */
    public ActionResult isLegalMove(Coordinates newPos) {
        if (getPos().row() - 1 == newPos.row() && getPos().column() == newPos.column()) {
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
