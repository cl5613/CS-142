package war;

/**
 * An enumerated type for the card's rank.
 *
 * @author RIT CS
 */
public enum Rank {
    /** 2 */
    TWO("2", 2),
    /** 3 */
    THREE("3", 3),
    /** 4 */
    FOUR("4", 4),
    /** 5 */
    FIVE("5", 5),
    /** 6 */
    SIX("6", 6),
    /** 7 */
    SEVEN("7", 7),
    /** 8 */
    EIGHT("8", 8),
    /** 9 */
    NINE("9", 9),
    /** 10 */
    TEN("10", 10),
    /** J */
    JACK("J", 11),
    /** Q */
    QUEEN("Q", 12),
    /** K */
    KING("K", 13),
    /** A */
    ACE("A", 14);

    /** the display character for the rank */
    private final String display;
    /** the integer value of the card */
    private final int value;

    /**
     * Create the rank for this card.
     *
     * @param display display character
     * @param value numerical value
     */
    Rank(String display, int value) {
        this.display = display;
        this.value = value;
    }

    /**
     * Get the value for this card.1
     *
     * @return the card's value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Returns the display character for the rank.
     *
     * @return the display character
     */
    @Override
    public String toString() {
        return this.display;
    }
}
