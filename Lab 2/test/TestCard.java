package test;

import war.Card;
import war.Rank;
import war.Suit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test framework for the war.Card class.
 *
 * @author RIT CS
 */
public class TestCard {
    @Test
    public void testFaceUp() {
        Card c1 = new Card(Rank.FOUR, Suit.CLUB);
        assertFalse(c1.isFaceUp());
        c1.setFaceUp();
        assertTrue(c1.isFaceUp());
        c1.setFaceDown();
        assertFalse(c1.isFaceUp());

        Card c2 = new Card(Rank.JACK, Suit.HEART);
        c2.setFaceUp();
        assertTrue(c2.isFaceUp());
        assertFalse(c1.isFaceUp());
    }

    @Test
    public void testBeats() {
        Card c1 = new Card(Rank.TEN, Suit.SPADE);
        Card c2 = new Card(Rank.JACK, Suit.DIAMOND);
        Card c3 = new Card(Rank.NINE, Suit.SPADE);
        Card c4 = new Card(Rank.TEN, Suit.HEART);
        Card c5 = new Card(Rank.ACE, Suit.CLUB);

        assertFalse(c1.beats(c2));
        assertTrue(c2.beats(c1));
        assertTrue(c1.beats(c3));
        assertFalse(c1.beats(c4));
        assertTrue(c2.beats(c3));
        assertTrue(c5.beats(c2));
    }

    @Test
    public void testEquals() {
        Card c1 = new Card(Rank.ACE, Suit.SPADE);
        Card c2 = new Card(Rank.FIVE, Suit.DIAMOND);
        Card c3 = new Card(Rank.ACE, Suit.CLUB);

        assertEquals(c1, c1);
        assertNotEquals(c1, c2);
        assertEquals(c1, c3);
        assertNotEquals(c1, "A♧");
        assertNotEquals("A♧", c1);
    }

    @Test
    public void testToString() {
        Card c1 = new Card(Rank.THREE, Suit.CLUB);
        Card c2 = new Card(Rank.TEN, Suit.DIAMOND);
        Card c3 = new Card(Rank.QUEEN, Suit.HEART);
        Card c4 = new Card(Rank.ACE, Suit.SPADE);

        assertEquals("3♠(D)", c1.toString());
        assertEquals("10♢(D)", c2.toString());
        assertEquals("Q♥(D)", c3.toString());
        assertEquals("A♧(D)", c4.toString());

        c1.setFaceUp();
        assertEquals("3♠(U)", c1.toString());
        c2.setFaceUp();
        assertEquals("10♢(U)", c2.toString());
        c3.setFaceUp();
        assertEquals("Q♥(U)", c3.toString());
        c4.setFaceUp();
        assertEquals("A♧(U)", c4.toString());
    }
}
