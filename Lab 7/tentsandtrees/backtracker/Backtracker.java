package tentsandtrees.backtracker;

import java.util.Optional;

/**
 * This class represents the classic recursive backtracking algorithm.
 * It has a solver that can take a valid configuration and return a
 * solution, if one exists.
 * 
 * @author RIT CS
 */
public class Backtracker {
    /** Should debug output be enabled */
    private final boolean debug;

    /** number of configs generated */
    private long numConfigs;

    /**
     * Initialize a new tentsandtrees.backtracker.
     * 
     * @param debug Is debugging output enabled?
     */
    public Backtracker(boolean debug) {
        this.debug = debug;
        this.numConfigs = 0;
        if (this.debug) {
            System.out.println("Backtracker debugging enabled...");
        }
    }
    
    /**
     * A utility routine for printing out various debug messages.
     * 
     * @param msg The type of config being looked at (current, goal, 
     *  successor, e.g.)
     * @param config The config to display
     */
    private void debugPrint(String msg, Configuration config) {
        if (this.debug) {
            System.out.println(msg + ":\n" + config);
        }
    }

    /**
     * Return the number of configs generated using solve()
     *
     * @return number of configs
     */
    public long getNumConfigs() {
        return this.numConfigs;
    }
    
    /**
     * Try find a solution, if one exists, for a given configuration.
     * 
     * @param config A valid configuration
     * @return A solution config, or null if no solution
     */
    public Optional<Configuration> solve(Configuration config) {
        debugPrint("Current config", config);
        if (config.isGoal()) {
            debugPrint("\tGoal config", config);
            return Optional.of(config);
        } else {
            for (Configuration child : config.getSuccessors()) {
                ++this.numConfigs;
                if (child.isValid()) {
                    debugPrint("\tValid successor", child);
                    Optional<Configuration> sol = solve(child);
                    if (sol.isPresent()) {
                        return sol;
                    }
                } else {
                    debugPrint("\tInvalid successor", child);
                }
            }
            // implicit backtracking happens here
        } 
        return Optional.empty();
    }
}
