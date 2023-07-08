package war;

/**
 * The main program for the card game, War.  It is run on the command line as:<br>
 * <br>
 * java War cards-per-player seed<br>
 * <br>
 *
 * @author Chen Lin
 */

public class War {
    /** The maximum number of cards a single player can have */
    public final static int MAX_CARDS_PER_PLAYER = 26;
    /** the first player */
    private final Player p1;
    /** the second player */
    private final Player p2;
    /** the current round */
    private int round;

    /**
     * Initialize the game.
     *
     * @param cardsPerPlayer the number of cards for a single player
     */
    public War(int cardsPerPlayer) {
        Pile pile = new Pile("Initial");
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                Card card = new Card(rank, suit);
                pile.addCard(card);
                pile.shuffle();
        }}
        this.p1 = new Player(1);
        this.p2 = new Player(2);
        for (int i = 0; i < cardsPerPlayer; ++i ) {
            p1.addCard(pile.drawCard(false));
            p2.addCard(pile.drawCard(false));
        }
    }

    /** Play a single round of the game */
    private void playRound() {
        System.out.println("Now round " + round + ":");
        Pile trickPile = new Pile("trick");
        while (!(p1.isWinner() && p2.isWinner()) || (p1.hasCard() == true && p2.hasCard() == true)) {
            System.out.println(p1.toString());
            System.out.println(p2.toString());
            Card card1 = p1.drawCard();
            Card card2 = p2.drawCard();
            System.out.println(card1.toString());
            System.out.println(card1.toString());
            trickPile.addCard(card1);
            trickPile.addCard(card2);
            if (card1.beats(card2)) {
                System.out.println("P1 wins this round.");
                p1.addCards(trickPile);
                trickPile.clear();
            }
            else if (card2.beats(card1)) {
                System.out.println("P2 wins this round.");
                p2.addCards(trickPile);
                trickPile.clear();
            }
            else {
                System.out.println("Same rank! No cards being distributed");
            }
        }
    }

    /** Play the full game. */
    public void playGame() {
        round = 0;
        while (!(p1.isWinner() && p2.isWinner())) {
            round += 1;
            playRound();
            if (p1.hasCard() == false && p2.hasCard() == true) {
                p1.setWinner();
            }
            else if (p2.hasCard() == false && p1.hasCard() == true) {
                p2.setWinner();
            }
        }
        System.out.println(p1.toString());
        System.out.println(p2.toString());
        if (p1.isWinner()) {
            System.out.println("P1 wins.");
        }
        else if(p2.isWinner()) {
            System.out.println("P2 wins.");
        }
    }

    /**
     * The main method get the command line arguments, then constructs
     * and plays the game.  The Pile's random number generator and seed
     * need should get set here using Pile.setSeed(seed).
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java War cards-per-player seed");
        } else {
            int cardsPerPlayer = Integer.parseInt(args[0]);
            // must be between 1 and 26 cards per player
            if (cardsPerPlayer <= 0 || cardsPerPlayer > MAX_CARDS_PER_PLAYER) {
                System.out.println("cards-per-player must be between 1 and " + MAX_CARDS_PER_PLAYER);
            } else {
                // set the rng seed
                Pile.setSeed(Integer.parseInt(args[1]));
                War war = new War(cardsPerPlayer);
                war.playGame();
            }
        }
    }
}
