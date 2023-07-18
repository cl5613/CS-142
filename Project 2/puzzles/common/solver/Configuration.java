package puzzles.common.solver;

import puzzles.jam.model.JamConfig;

import java.util.Collection;
import java.util.LinkedList;

/**
 * The representation of a single configuration for a puzzle.
 *
 * @author RIT CS
 */
public interface Configuration {
    /**
     * Get the collection of successors from the current one.
     * @return All successors, valid and invalid
     */
    public Collection<Configuration> getSuccessors();

    /**
     * Is the current configuration a goal?
     * @return true if goal; false otherwise
     */
    public boolean isGoal();

}
