package puzzles.jam.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.model.JamConfig;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * the main function to run the Jam
 * @author Chen Lin
 */
public class Jam {

    /**
     * main function
     * @param args the arguments
     * @throws IOException exception
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Jam filename");
            System.exit(-1);
        }
        else {
            JamConfig jamConfig = new JamConfig(args[0]);
            Solver solver = new Solver(jamConfig);
            Optional<List<Configuration>> path = solver.BFS();

            System.out.println("File: " + args[0]);
            System.out.print(jamConfig);
            System.out.println("Total configs:" + solver.getTotalCount());
            System.out.println("Unique configs:" + solver.getUniqueCount());

            if (path.isEmpty()) {
                System.out.println("No Solution!");
            }
            if (path.isPresent()) {
                int step = 0;
                for (Configuration configuration : path.get()) {
                    System.out.println("Step "+ step + ": ");
                    System.out.println(configuration);
                    ++step;
                }
            }
        }
    }
}