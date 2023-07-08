package tentsandtrees.main;

import tentsandtrees.backtracker.Backtracker;
import tentsandtrees.backtracker.Configuration;
import tentsandtrees.backtracker.TentConfig;

import java.io.IOException;
import java.util.Optional;

/**
 * The main program for the TentsAndTrees puzzle.
 * 
 * To run with a puzzle file and debugging enabled:<br>
 * <tt><br>
 *     java TentsAndTrees tents1.txt true<br>
 * </tt><br>
 * To run with a puzzle file and debugging disabled<br>
 * <tt><br>
 *     java TentsAndTrees tents1.txt false
 * </tt><br>
 *
 * @author RIT CS
 */
public class TentsAndTrees {
    /**
     * The main method.
     *
     * @param args The command line arguments (name of input file)
     */
    public static void main(String[] args) {
        // check for file name and debug flag on command line
        if (args.length != 2) {
            System.err.println("Usage: java TentsAndTrees input-file debug");
        } else {
            try {
                // construct the initial configuration from the file based
                Configuration init = new TentConfig(args[0]);

                System.out.println("Initial config:\n" + init);

                // create the backtracker with the debug flag
                boolean debug = args[1].equals("true");
                Backtracker bt = new Backtracker(debug);

                // start the clock
                double start = System.currentTimeMillis();

                // attempt to solve the puzzle
                Optional<Configuration> sol = bt.solve(init);

                // compute the elapsed time
                System.out.println("Elapsed time: " +
                        (System.currentTimeMillis() - start) / 1000.0 + " seconds.");

                // display the number of configs generated
                System.out.println("Number of configs generated: " + bt.getNumConfigs());

                // indicate whether there was a solution, or not
                if (sol.isPresent()) {
                    System.out.println("Solution:\n" + sol.get());
                } else {
                    System.out.println("No solution!");
                }
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}