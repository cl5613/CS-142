package test;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import war.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test framework for the war.Player class.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPlayer {
    @Test
    @Order(1)
    public void testPlayerAddCard() {
        Player p1 = new Player(1);
        assertEquals("P1 pile: ", p1.toString());
        assertFalse(p1.hasCard());

        p1.addCard(new Card(Rank.EIGHT, Suit.SPADE));
        p1.addCard(new Card(Rank.ACE, Suit.CLUB));
        p1.addCard(new Card(Rank.FOUR, Suit.HEART));
        p1.addCard(new Card(Rank.TEN, Suit.DIAMOND));

        assertEquals("P1 pile: 8♧(D) A♠(D) 4♥(D) 10♢(D) ", p1.toString());
        assertTrue(p1.hasCard());
    }

    @Test
    @Order(2)
    public void testPlayerAddCards() {
        Player p2 = new Player(2);
        assertEquals("P2 pile: ", p2.toString());
        assertFalse(p2.hasCard());

        Pile pile = new Pile("Test");
        pile.addCard(new Card(Rank.EIGHT, Suit.SPADE));
        pile.addCard(new Card(Rank.ACE, Suit.CLUB));
        pile.addCard(new Card(Rank.FOUR, Suit.HEART));
        pile.addCard(new Card(Rank.TEN, Suit.DIAMOND));
        p2.addCards(pile);

        assertEquals("P2 pile: 8♧(D) A♠(D) 4♥(D) 10♢(D) ", p2.toString());
        assertTrue(p2.hasCard());
    }

    @Test
    @Order(3)
    public void testPlayerDrawCards() {
        Player p1 = new Player(1);
        p1.addCard(new Card(Rank.EIGHT, Suit.SPADE));
        p1.addCard(new Card(Rank.ACE, Suit.CLUB));
        p1.addCard(new Card(Rank.FOUR, Suit.HEART));
        p1.addCard(new Card(Rank.TEN, Suit.DIAMOND));

        Card c1 = p1.drawCard();
        assertEquals("8♧(U)", c1.toString());
        assertEquals("P1 pile: A♠(D) 4♥(D) 10♢(D) ", p1.toString());

        Card c2 = p1.drawCard();
        assertEquals("A♠(U)", c2.toString());
        assertEquals("P1 pile: 4♥(D) 10♢(D) ", p1.toString());
    }

    @Test
    @Order(4)
    public void testPlayerWinner() {
        Player p1 = new Player(1);
        assertFalse(p1.isWinner());
        p1.setWinner();
        assertTrue(p1.isWinner());
    }
}
