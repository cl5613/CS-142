package war;

/**
 * An enumerated type for the card suits.
 *
 * @author RIT CS
 */
public enum Suit {
    /** Club */
    CLUB ("\u2660"),
    /** Heart */
    HEART("\u2665"),
    /** Diamond */
    DIAMOND("\u2662"),
    /** Spade */
    SPADE("\u2667");

    /** how the suit is displayed */
    private final String display;

    /**
     * Set the suit for this card.
     *
     * @param display the display character
     */
    Suit(String display) {
        this.display = display;
    }

    /**
     * Returns the display character for the suit.
     *
     * @return display character
     */
    @Override
    public String toString() {
        return this.display;
    }
}
