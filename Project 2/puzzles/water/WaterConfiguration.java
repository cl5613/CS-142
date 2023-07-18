package puzzles.water;

import puzzles.common.solver.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.*;

public class WaterConfiguration implements Configuration{
    /** the goal amount */
    private static int goal;
    /** Buckets */
    private final int[] Buckets;
    /** Capacities */
    private static int[] capacities;

    public WaterConfiguration(int goal, int[] capacities) {
        WaterConfiguration.goal = goal;
        WaterConfiguration.capacities = capacities;
        Buckets = new int[capacities.length];
    }

    public WaterConfiguration(Configuration waterConfig, int[] bucket) {
        int[] j = new int[capacities.length];
        System.arraycopy(((WaterConfiguration)waterConfig).getBuckets(),0, j,0, capacities.length);
        Buckets = bucket;
    }

    public int[] getBuckets() {
        return Buckets;
    }

    public Collection<Configuration> getSuccessors() {
        HashSet<Configuration> neighbors = new HashSet<>();
        for (int i = 0; i < Buckets.length; i++) {
            int[] bucket = Arrays.copyOf(Buckets, Buckets.length);
            bucket[i] = capacities[i];
            neighbors.add(new WaterConfiguration(this, bucket));

            int[] bucket1 = Arrays.copyOf(Buckets, Buckets.length);
            bucket1[i] = 0;
            neighbors.add(new WaterConfiguration(this, bucket1));

            for (int j = 0; j < Buckets.length; j++) {
                if (i != j) {
                    int[] num = Arrays.copyOf(Buckets, Buckets.length);
                    int pourAmount = Math.min(capacities[j] - num[j], num[i]);
                    num[j] += pourAmount;
                    num[i] -= pourAmount;

                    neighbors.add(new WaterConfiguration(this, num));
                }
            }
        }
        return neighbors;
    }

    @Override
    public boolean isGoal() {
        for (int i = 0; i < Buckets.length; ++i) {
            if (Buckets[i] == goal) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterConfiguration that = (WaterConfiguration) o;
        return Arrays.equals(Buckets, that.Buckets);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(Buckets);
    }

    @Override
    public String toString() {
        return Arrays.toString(Buckets);
    }
}
