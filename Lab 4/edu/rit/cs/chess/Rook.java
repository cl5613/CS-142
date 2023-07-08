package edu.rit.cs.chess;

import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

/**
 * Rook class
 * @author Chen Lin
 */
public class Rook extends Piece{

    /**
     * Create a Rook piece.
     * @param name the piece's name (will start with 'r' or 'R')
     * @param pos the piece's starting position on the board
     * @param board the Game board on which the pieces is placed
     */
    public Rook(String name, Coordinates pos, Game board) {
        super(name, pos, board);
    }

    /**
     * Rooks may only move rectilinearly: forward, backward, or to either side. In other words,
     * their column or row positions may change, but not both. This method checks whether the destination
     * specifies a move that is legal according to (modified) Chess rules, and if there is a clear path
     * to the destination (for non-jumpers).
     * @param newPos intended destination coordinates
     * @return ActionResult.OK if everything is good, otherwise an ActionResult that
     * either states "The way is blocked: currentPos -> newPos" or "Illegal piece move newPos"
     */
    public ActionResult isLegalMove(Coordinates newPos){
        if ((getPos().column() != newPos.column()  && getPos().row() == newPos.row())
        || (getPos().column() == newPos.column()  && getPos().row() != newPos.row())) {

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
