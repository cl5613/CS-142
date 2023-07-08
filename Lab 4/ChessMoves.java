import edu.rit.cs.util.ActionResult;
import edu.rit.cs.util.Coordinates;
import edu.rit.cs.chess.Game;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Top-level class that runs the ChessMoves game
 *
 * @author RIT CS
 */
public class ChessMoves {

    /**
     * What the user types to end the program
     */
    public static final String END_GAME = "quit";

    /**
     * "Universal" new line
     */
    private static final String NL = System.lineSeparator();

    /**
     * If true, the user's input is echoed in the output.
     * This is useful if the input is coming from a file instead of the console.
     */
    private static boolean echo = false;

    /** Failure code for bad command line */
    public static final int BAD_CMD_LINE = 1;

    /** Failure code for game initialization from file */
    public static final int BAD_CONFIG_FILE = 2;

    /**
     * Run the chess moves program.
     * Board configuration comes from a file.
     * User move commands come from standard input.
     * @param args [0]: configuration file name; [1] (opt) echo option
     * @throws FileNotFoundException if the configuration file cannot be opened
     * @throws NumberFormatException if non-numerical coordinates are given
     */
    public static void main( String[] args )
            throws FileNotFoundException // when config file can't be read
    {

        checkCommandLineArgs( args );

        Game game = new Game( args[ 0 ] );

        if ( !game.ok ) {
            // If something went wrong the Game object,
            // the constructor will have displayed an error.
            System.exit( BAD_CONFIG_FILE ); // Exit program.
        }

        playGame( game );
    }

    /**
     * Make sure the arguments passed in on the command line are OK.
     * If not, the program is aborted with a {@link ChessMoves#BAD_CMD_LINE}
     * status.
     * <br/>
     * There is one required argument: the board setup (configuration) file.
     * There is an optional second argument. If it is provided, regardless of
     * its value, all input will be echoed to standard output.
     * This option should be used when user commands are coming from a test
     * file instead of from an actual console.
     *
     * @param args the command line arguments
     */
    private static void checkCommandLineArgs( String[] args ) {
        if ( args.length == 2 ) {
            echo = true; // Echo all user input on the output.
        }

        if ( args.length < 1 || args.length > 2 ) {
            System.out.println(
                    "Usage: java ChessMoves board-setup-file [echo]" );
            System.exit( BAD_CMD_LINE );
        }
    }

    /**
     * The main game loop.
     * User commands are read in.
     * They are verified, and the game object is told to execute them.
     * If a user command is not valid, an error message is printed and
     * the game continues.
     *
     * @param game The main object containing the board and pieces
     */
    private static void playGame( Game game ) {
        Scanner console = new Scanner( System.in ); // User's move commands

        game.showBoard();
        System.out.print( NL + "> " ); // prompt

        // Continue loop until end of file reached, or user quits.
        while ( console.hasNextLine() ) {

            // Read start row, start column, end row, end column.
            //
            String move = console.nextLine();
            if ( echo ) System.out.println( move );
            String[] parts = move.split( "\\s+" );

            if ( parts.length != 4 ) {
                // Special case for quitting below:
                if ( parts.length == 1 &&
                     parts[ 0 ].equalsIgnoreCase( END_GAME ) ) {
                    break;
                }
                else {
                    System.out.println( "Illegal move specification: " + move );
                    continue; // Just have the user try again.
                }
            }

            Coordinates pieceLoc =
                    new Coordinates( parts[ 0 ], parts[ 1 ] );
            Coordinates dest = new Coordinates( parts[ 2 ], parts[ 3 ] );

            ActionResult result = game.makeMove( pieceLoc, dest );
            if ( !result.ok ) {
                System.out.println( result.message() );
            }

            // Get next move.
            game.showBoard();
            System.out.print( NL + "> " ); // prompt
        }
        console.close();
    }
}
