package puzzles.common.solver;

import java.util.*;

public class Solver {
    /** start */
    private final Configuration start;
    /** total number of configurations */
    private int count = 0;
    /** total number of unique configurations */
    private int uniqueCount = 0;

    public Solver(Configuration start) {
        this.start = start;
    }

    /** BFS Algorithm */
    public Optional<List<Configuration>> BFS() {
        List<Configuration> queue = new LinkedList<>();
        queue.add(start);
        Map<Configuration, Configuration> predecessors = new HashMap<>();
        predecessors.put(start, start);
        Configuration curr = null;

        while (!queue.isEmpty()) {
            curr = queue.remove(0);
            if (curr.isGoal()) {
                break;
            }
            curr.getSuccessors();
            for (Configuration config: curr.getSuccessors()) {
                ++count;
                if(!predecessors.containsKey(config)) {
                    ++uniqueCount;
                    predecessors.put(config, curr);
                    queue.add(config);
                }
            }
        }
        List<Configuration> path = new LinkedList<>();
        assert curr != null;
        if (curr.isGoal()) {
            Configuration currNode = curr;
            while (!currNode.equals(start)) {
                path.add(0, currNode);
                currNode = predecessors.get(currNode);
            }
            path.add(0, start);
        }

        if (path.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(path);
        }
    }

    /**
     * total configurations
     * @return total configurations
     */
    public int getTotalCount() {
        return this.count;
    }

    /**
     * total unique configurations
     * @return total unique configurations
     */
    public int getUniqueCount() {
        return this.uniqueCount;
    }

}
