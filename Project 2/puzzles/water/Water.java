package puzzles.water;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Water {

    public static void main(String[] args) {
        for (String arg : args) {
            if (Integer.parseInt(arg) < 0) {
                System.err.println("Usage: java Water amount bucket1 bucket2...");
                System.exit(0);
            }
        }
        if (args.length < 2) {
            System.out.println(("Usage: java Water amount bucket1 bucket2 ..."));
        }
        else {
            int[] capacities = new int[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                capacities[i - 1] = Integer.parseInt(args[i]);
            }
            Configuration water = new WaterConfiguration(Integer.parseInt(args[0]), capacities);
            System.out.println("Amount=" + args[0]);

            for (int i = 1; i < args.length; i++) {
                System.out.println("Bucket " + i + "=" + args[i]);
            }
            System.out.println();
            System.out.println("Amount: " + args[0] + ", " + "Buckets:" + Arrays.toString(capacities));

            Solver solver = new Solver(water);
            Optional<List<Configuration>> path = solver.BFS();

            if (path.isEmpty()) {
                System.out.println("Total configs:" + solver.getTotalCount());
                System.out.println("Unique configs:" + solver.getUniqueCount());
                System.out.println("No Solution!");
            }
            else {
                int step = 0;
                System.out.println("Total configs:" + solver.getTotalCount());
                System.out.println("Unique configs:" + solver.getUniqueCount());
                for (Configuration configuration: path.get()) {
                    System.out.println("Step" + " " + step + ":" + " " + configuration);
                    ++step;
                }
            }
        }
    }
}
