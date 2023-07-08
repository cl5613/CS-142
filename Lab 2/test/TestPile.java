package test;

import org.junit.jupiter.api.*;
import war.Card;
import war.Pile;
import war.Rank;
import war.Suit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test framework for the war.Pile class.
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPile {
    /** Used to test that expected System.out print's happen */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeAll
    public static void seedPile() {
        Pile.setSeed(0);
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    @Order(1)
    public void testEmptyPile() {
        Pile p1 = new Pile("Empty");
        assertFalse(p1.hasCard());
        assertEquals("Empty pile: ", p1.toString());
        assertEquals(new ArrayList<Card>(), p1.getCards());
        p1.clear();
        assertFalse(p1.hasCard());
    }

    @Test
    @Order(2)
    public void testShuffle() {
        Pile p1 = new Pile("Full");
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                p1.addCard(new Card(rank, suit));
            }
        }
        assertEquals("Full pile: 2♠(D) 2♥(D) 2♢(D) 2♧(D) 3♠(D) 3♥(D) 3♢(D) 3♧(D) 4♠(D) 4♥(D) 4♢(D) 4♧(D) 5♠(D) 5♥(D) 5♢(D) 5♧(D) 6♠(D) 6♥(D) 6♢(D) 6♧(D) 7♠(D) 7♥(D) 7♢(D) 7♧(D) 8♠(D) 8♥(D) 8♢(D) 8♧(D) 9♠(D) 9♥(D) 9♢(D) 9♧(D) 10♠(D) 10♥(D) 10♢(D) 10♧(D) J♠(D) J♥(D) J♢(D) J♧(D) Q♠(D) Q♥(D) Q♢(D) Q♧(D) K♠(D) K♥(D) K♢(D) K♧(D) A♠(D) A♥(D) A♢(D) A♧(D) ", p1.toString());
        p1.shuffle();
        assertEquals("Shuffling Full pile" + System.getProperty("line.separator"), outContent.toString());
        assertEquals("Full pile: K♧(D) 8♠(D) 8♥(D) J♠(D) 5♧(D) Q♧(D) A♧(D) 4♥(D) 7♧(D) 3♥(D) 10♢(D) 6♢(D) A♠(D) 4♢(D) 9♧(D) 9♢(D) J♧(D) Q♥(D) Q♢(D) 7♠(D) J♥(D) K♢(D) 2♥(D) 5♠(D) 3♧(D) 5♥(D) J♢(D) Q♠(D) 6♧(D) 5♢(D) 3♢(D) 4♠(D) 10♠(D) 2♧(D) 2♠(D) 3♠(D) 8♧(D) 6♥(D) A♢(D) 10♥(D) 6♠(D) A♥(D) 8♢(D) K♥(D) 7♥(D) 10♧(D) 7♢(D) 4♧(D) 2♢(D) 9♥(D) 9♠(D) K♠(D) ", p1.toString());
    }

    @Test
    @Order(3)
    public void testTwoCardPile() {
        Pile p1 = new Pile("Two");
        p1.addCard(new Card(Rank.FIVE, Suit.DIAMOND));
        p1.addCard(new Card(Rank.KING, Suit.CLUB));

        assertTrue(p1.hasCard());
        assertEquals("Two pile: 5♢(D) K♠(D) ", p1.toString());
        assertEquals(Arrays.asList(new Card(Rank.FIVE, Suit.DIAMOND), new Card(Rank.KING, Suit.CLUB)),
            p1.getCards());

        Card c1 = p1.drawCard(true);
        assertEquals("5♢(U)", c1.toString());
        assertEquals("Two pile: K♠(D) ", p1.toString());

        Card c2 = p1.drawCard(true);
        assertEquals("K♠(U)", c2.toString());
        assertFalse(p1.hasCard());
        assertEquals("Two pile: ", p1.toString());

        p1.addCard(c1);
        p1.addCard(c2);
        assertEquals("Two pile: 5♢(U) K♠(U) ", p1.toString());

        Card c3 = p1.drawCard(false);
        assertEquals("Shuffling Two pile" + System.getProperty("line.separator") +
                "Two pile: K♠(D) 5♢(D) " + System.getProperty("line.separator"),
                outContent.toString());
        assertEquals("K♠(D)", c3.toString());
        assertEquals("Two pile: 5♢(D) ", p1.toString());

        p1.clear();
        assertFalse(p1.hasCard());
    }

    @Test
    @Order(4)
    public void testFourCardPile() {
        Pile p1 = new Pile("Four");
        p1.addCard(new Card(Rank.FIVE, Suit.DIAMOND));
        p1.addCard(new Card(Rank.KING, Suit.CLUB));
        p1.addCard(new Card(Rank.TEN, Suit.HEART));
        p1.addCard(new Card(Rank.ACE, Suit.SPADE));

        assertTrue(p1.hasCard());
        assertEquals("Four pile: 5♢(D) K♠(D) 10♥(D) A♧(D) ", p1.toString());
        assertEquals(Arrays.asList(new Card(Rank.FIVE, Suit.DIAMOND), new Card(Rank.KING, Suit.CLUB),
                new Card(Rank.TEN, Suit.HEART), new Card(Rank.ACE, Suit.SPADE)),
                p1.getCards());

        Card c1 = p1.drawCard(true);
        assertEquals("5♢(U)", c1.toString());
        assertEquals("Four pile: K♠(D) 10♥(D) A♧(D) ", p1.toString());

        Card c2 = p1.drawCard(true);
        assertEquals("K♠(U)", c2.toString());
        assertEquals("Four pile: 10♥(D) A♧(D) ", p1.toString());

        Card c3 = p1.drawCard(true);
        assertEquals("10♥(U)", c3.toString());
        assertEquals("Four pile: A♧(D) ", p1.toString());

        Card c4 = p1.drawCard(true);
        assertEquals("A♧(U)", c4.toString());
        assertFalse(p1.hasCard());
        assertEquals("Four pile: ", p1.toString());

        p1.addCard(c1);
        p1.addCard(c2);
        p1.addCard(c3);
        p1.addCard(c4);
        assertEquals("Four pile: 5♢(U) K♠(U) 10♥(U) A♧(U) ", p1.toString());

        Card c5 = p1.drawCard(false);
        assertEquals("Shuffling Four pile" + System.getProperty("line.separator") +
                        "Four pile: A♧(D) K♠(D) 5♢(D) 10♥(D) " + System.getProperty("line.separator"),
                outContent.toString());
        assertEquals("A♧(D)", c5.toString());
        assertEquals("Four pile: K♠(D) 5♢(D) 10♥(D) ", p1.toString());

        p1.clear();
        assertFalse(p1.hasCard());
    }
}
