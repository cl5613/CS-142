package edu.rit.cs.chess;

/*
 * This tiny skeleton of code is put here only to get the code we
 * give you to compile.
 * You basically must implement this class from scratch.
 */
import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;

/**
 * Piece class
 * @author Chen Lin
 */
public abstract class Piece extends Object {
    /** name of the piece */
    private String name;
    /** the position of that piece */
    private Coordinates pos;
    /** game board */
    private Game board;

    /**
     * Initialize the piece-type-independent state for a new Chess piece.
     * @param name name given to the piece -- preferably one or two characters
     * @param pos starting position on the board
     * @param board reference to the game board
     */
    public Piece(String name, Coordinates pos, Game board) {
        this.name = name;
        this.pos = pos;
        this.board = board;
    }

    /**
     * Execute a move of this piece, if it is legal. If the move is illegal, the piece stays put.
     * @param newPos the destination coordinates
     * @precondition newPos is within the bounds of the board
     * @return information about the success or failure of the move.
     */
    public ActionResult makeMove( Coordinates newPos ) {
        ActionResult result = isLegalMove(newPos);
        if (result.ok) {
            setPos(newPos);
        }
        return result;
    }

    /**
     * Board accessor, used by concrete Piece classes to check move validity
     * @return the board with which the piece is associated
     */
    protected Game getBoard() {
        return this.board;
    }

    protected abstract ActionResult isLegalMove(Coordinates newPos);

    /**
     * @return the name provided for this piece
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * This method is used by concrete subclasses in determining legality of moves.
     * @return the current position of this piece on the board
     */
    protected Coordinates getPos() {
        return this.pos;
    }

    /**
     * Set/change the location of this piece on the board.
     * This method is used by makeMove(Coordinates). No checks are done; the method always succeeds.
     * @param newPos newPos is within the bounds of the board
     */
    protected void setPos(Coordinates newPos) {
        this.pos = newPos;
    }
}
