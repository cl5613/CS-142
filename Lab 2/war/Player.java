package war;

/**
 * Represents a single player in the game.
 * @author Chen Lin
 */

public class Player {
    /** their pile of cards */
    private Pile pile;
    /** is this player the winner of the game */
    private boolean winner;

    /**
     * Create the player with their id.
     * @param id this player's id
     */
    public Player(int id) {
        winner = false;
        pile = new Pile("P" + id);
    }

    /**
     * Add a single card to the bottom of the player's pile.
     * @param card the card to add
     */
    public void addCard(Card card) {
        pile.addCard(card);
    }

    /**
     * Add the collection of cards from the incoming pile to the bottom.
     * of player's pile, in order.
     * @param cards the imcoming pile of cards to add to this player's pile
     */
    public void addCards(Pile cards) {
        for (Card card : cards.getCards()) {
            addCard(card);
        }
    }

    /**
     * Are there any cards left in this player's pile?
     * @return whether there are cards in the player's pile or not.
     */
    public boolean hasCard() {
        return this.pile.hasCard();
    }

    /**
     * Remove a card from the top of the pile.
     * @return the newly removed card from the top of the pile
     */
    public Card drawCard() {
        return pile.drawCard(true);
    }

    /**
     * Declare this player to be the winner.
     */
    public void setWinner() {
        this.winner = true;
    }

    /**
     * Is this player the winner?
     * @return whether this player was the winner or not
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * Returns a string representation of this player's pile.
     * @return the string described above
     */
    @Override
    public String toString() {
        return this.pile + "";
    }

}
