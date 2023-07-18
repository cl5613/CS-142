package puzzles.clock;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.List;
import java.util.Optional;

public class Clock {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Clock hours start stop");
        }
        Configuration clock = new ClockConfiguration(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]), Integer.parseInt(args[2]));

        System.out.println("Hours=" + args[0]);
        System.out.println("Start=" + args[1]);
        System.out.println("End=" + args[2]);
        System.out.println();
        System.out.println("Hours: "+ args[0] + ", " + "Start: " + args[1]
                + ", " + "End: " + args[2]);

        Solver solver = new Solver(clock);
        Optional<List<Configuration>> path = solver.BFS();

        if (solver.BFS().isEmpty()) {
            System.out.println("No Solution!");
        }
        else {
            int step = 0;
            System.out.println("Total configs:" + solver.getTotalCount());
            System.out.println("Unique configs:" + solver.getUniqueCount());
            for (Configuration configuration: path.get()) {
                System.out.println("Step" + " " + step + ":" + " " + configuration );
                ++step;
            }
        }
    }
}