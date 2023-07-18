package puzzles.clock;

import puzzles.common.solver.Configuration;
import puzzles.jam.model.JamConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class ClockConfiguration implements Configuration {
    /** number of hours */
    private final int hours;
    /** the start point */
    private final int start;
    /** the end point */
    private final int end;
    /** the current position */
    private final int currPos;

    public ClockConfiguration(int hours, int start, int end) {
        this.hours = hours;
        this.start = start;
        this.currPos = start;
        this.end = end;
        if (hours < 0 || start < 0 || end < 0 ) {
            System.err.println("Usage: java Clock hours start end");
            System.exit(0);
        }
    }

    public ClockConfiguration(int hours, int start, int end, int currPos) {
        this.hours = hours;
        this.start = start;
        this.currPos = (currPos - 1) % hours + 1;
        this.end = end;
    }

    public Collection<Configuration> getSuccessors(  ) {
        int i;
        int j;
        if (currPos - 1 == 0) {
            i = hours;
        }
        else {
            i = currPos - 1;
        }
        if (currPos + 1 > hours) {
            j = 1;
        }
        else {
            j = currPos + 1;
        }
        Configuration clockConfig = new ClockConfiguration(hours, start, end, i);
        Configuration startConfig = new ClockConfiguration(hours, start, end, j);
        return new ArrayList<>(Arrays.asList(clockConfig, startConfig));
    }

    @Override
    public boolean isGoal() {
        return currPos == end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClockConfiguration that = (ClockConfiguration) o;
        return currPos == that.currPos;
    }

    @Override
    public int hashCode() {
        return currPos;
    }

    @Override
    public String toString() {
        return String.valueOf(currPos);
    }
}
