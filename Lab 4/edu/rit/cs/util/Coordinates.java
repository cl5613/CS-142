package edu.rit.cs.util;

import java.util.Objects;

/**
 * A simple class containing a tuple of integers.
 * The tuple is normally interpreted as 2D coordinates, but
 * it can also be a vector from one location to another,
 * as in the return value of {@link Coordinates#delta(Coordinates)},
 * or in the parameter value of {@link Coordinates#plus(Coordinates)}.
 * @author RIT CS
 */
public class Coordinates {
    private final int row;
    private final int column;

    /**
     * Initialize this object with the actual integer values.
     * @param row row number, or y coordinate, increasing DOWNwards
     * @param column column number, or x coordinate, increasing to the right
     */
    @SuppressWarnings( "SpellCheckingInspection" )
    public Coordinates( int row, int column ) {
        this.row = row;
        this.column = column;
    }

    /**
     * Initialize this object with the actual integer values.
     * @param pair array containing the row number, or y coordinate,
     *             increasing Downwards, followed by the
     *             column number, or x coordinate, increasing to the right
     * @rit.pre pair.length == 2
     */
    public Coordinates( int[] pair ) {
        assert pair.length == 2:
                "Coordinate constructor array must be length 2.";
        this.row = pair[ 0 ];
        this.column = pair[ 1 ];
    }

    /**
     * Initialize this object with strings representing the integer values.
     * @see Coordinates#Coordinates(int, int)
     * @param rowStr row number as a string
     * @param colStr column number as a string
     */
    public Coordinates( String rowStr, String colStr ) {
        this( Integer.parseInt( rowStr ), Integer.parseInt( colStr ) );
    }

    /**
     * @return the first value of this tuple
     */
    public int row() { return row; }

    /**
     * @return the second value of this tuple
     */
    public int column() { return column; }

    /**
     * Compute the "difference" between this location and another location.
     * This is useful, in combination with
     * {@link Coordinates#plus(Coordinates)},
     * for writing a loop over a sequence of locations.
     * @param c the other location
     * @return a new Coordinates object containing the row difference and the
     * column difference, i.e., what to add to this to get to the c location.
     */
    public Coordinates delta( Coordinates c ) {

        return new Coordinates( c.row - this.row, c.column - this.column );
    }

    /**
     * Add the "row" and "column" values of the argument to this object.
     * This is useful, in combination with
     * {@link Coordinates#delta(Coordinates)},
     * for writing a loop over a sequence of locations.
     * @param delta contains the increment to the row and column values (+/-)
     * @return a new Coordinates object containing the resulting location
     */
    public Coordinates plus( Coordinates delta ) {
        return new Coordinates( this.row + delta.row,
                                this.column + delta.column
        );
    }

    /**
     * Create a string of the form <code>(row,column)</code>
     * @return a string containing the row and column separated by
     * by a comma and surrounded by parentheses.
     */
    @Override
    public String toString() {
        return "(" + this.row + ',' + this.column + ')';
    }

    /**
     * Does the argument represent the Coordinates of the same location as
     * this object?
     * @param other the object to compare to this one
     * @return true if and only if other is a Coordinates instance containing
     * the same row and column values
     */
    @Override
    public boolean equals( Object other ) {
        if ( this == other ) return true;
        if ( !( other instanceof Coordinates ) ) return false;
        Coordinates that = (Coordinates)other;
        return row == that.row && column == that.column;
    }

    /**
     * Compute a hash value for this object
     * @return the hash provided by the built-in method
     * {@link Objects#hash(Object...)}
     */
    @Override
    public int hashCode() {
        return Objects.hash( row, column );
    }
}
