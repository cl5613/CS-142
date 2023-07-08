package war;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents a pile of cards.
 * @author Chen Lin
 */

public class Pile {
    /** the name of the pile */
    private final String name;
    /** the collection of cards */
    private final ArrayList<Card> cards;
    /** the random number generator (static) */
    private static Random rng = new Random();

    /**
     * Create the pile of cards for a given seed.
     * @param name name of the pile
     */
    public Pile(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    /**
     * Create and set the seed for the random number generator.
     * @param seed the see value
     */
    public static void setSeed(long seed) {
        rng.setSeed(seed);
    }

    /**
     * Shuffle the cards and set them all to face down.
     */
    public void shuffle() {
        Collections.shuffle(cards, rng);
        for (Card card: cards) {
            card.setFaceDown();
        }
        System.out.println("Shuffling " + this.name + " pile");
    }

    /**
     * Add a card to the bottom of the pile.
     * @param card the card to add
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Returns the collection of cards in the pile's current state.
     * @return all the cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Get the next top card from the pile.
     * @param faceUp should the card be face up when drawn
     * @return the card that was at the top of the pile
     */
    public Card drawCard(boolean faceUp) {
        if (cards.get(0).isFaceUp()) {
            shuffle();
            System.out.println(this.toString());
        }
        Card card = cards.remove(0);
        if (faceUp) {
            card.setFaceUp();
        }
        return card;
    }

    /**
     * Is there at least one card in the pile?
     * @return whether there is a card in the pile or none
     */
    public boolean hasCard() {
        return cards.size() > 0;
    }

    /**
     * Remove all cards from the pile by clearing it out.
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Returns a string representation of the pile in the format:
     * "{name} pile: first-card second-card ..."
     * @return the string described above
     */
    @Override
    public String toString() {
        String c = this.name + " pile:" + " ";
        for (Card card: cards) {
            c += card.toString() + " " ;
        }
        return c;
    }

}
