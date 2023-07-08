package tentsandtrees.backtracker;

import java.io.*;
import java.util.*;

/**
 *  The full representation of a configuration in the TentsAndTrees puzzle.
 *  It can read an initial configuration from a file, and supports the
 *  Configuration methods necessary for the Backtracker solver.
 *
 *  @author RIT CS
 *  @author Chen Lin
 */
public class TentConfig implements Configuration {
    /** the grid of characters */
    private char[][] field;
    /** the number of rows */
    private int[] rowLook;
    /** the number of columns */
    private int[] colLook;
    /** square number */
    private int DIM;
    /** current row */
    private int currentRow;
    /** current col */
    private int currentCol;

    // INPUT CONSTANTS
    /** An empty cell */
    public final static char EMPTY = '.';
    /** A cell occupied with grass */
    public final static char GRASS = '-';
    /** A cell occupied with a tent */
    public final static char TENT = '^';
    /** A cell occupied with a tree */
    public final static char TREE = '%';

    // OUTPUT CONSTANTS
    /** A horizontal divider */
    public final static char HORI_DIVIDE = '-';
    /** A vertical divider */
    public final static char VERT_DIVIDE = '|';

    /**
     * Construct the initial configuration from an input file whose contents
     * are, for example:<br>
     * <tt><br>
     * 3        # square dimension of field<br>
     * 2 0 1    # row looking values, top to bottom<br>
     * 2 0 1    # column looking values, left to right<br>
     * . % .    # row 1, .=empty, %=tree<br>
     * % . .    # row 2<br>
     * . % .    # row 3<br>
     * </tt><br>
     * @param filename the name of the file to read from
     * @throws IOException if the file is not found or there are errors reading
     */
    public TentConfig(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        DIM = Integer.parseInt(in.readLine());
        rowLook = new int[DIM];
        colLook = new int[DIM];
        this.field = new char[DIM][DIM];
        String[] fields = in.readLine().split("\\s+");
        for (int row = 0; row < DIM; ++row) {
            rowLook[row] = Integer.parseInt(fields[row]);
        }

        String[] num = in.readLine().split("\\s+");
        for (int col = 0; col < DIM; ++col) {
            colLook[col] = Integer.parseInt(num[col]);
        }

        for (int row = 0; row < DIM; ++row) {
            fields = in.readLine().split("\\s+");
            for (int col = 0; col < DIM; ++col) {
                this.field[row][col] = fields[col].charAt(0);
            }
        }
        in.close();
    }

    /**
     * Copy constructor.  Takes a config, other, and makes a full "deep" copy
     * of its instance data.
     * @param other the config to copy
     */
    private TentConfig(TentConfig other, int currentRow, int currentCol, char c) {
        this.DIM = other.DIM;
        this.currentRow = other.currentRow;
        this.currentCol = other.currentCol;
        this.field = new char[other.DIM][other.DIM];

        for (int i = 0; i < this.DIM; i++) {
            System.arraycopy(other.field[i], 0, this.field[i], 0, this.DIM);
        }
        this.field[currentRow][currentCol] = c;
    }

    /**
     * Get the collection of successors from the current one
     * @return All successors, valid and invalid
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        List<Configuration> successors = new LinkedList<>();
        int row = currentRow;
        int col = currentCol + 1;
        if (col == DIM) {
            col = 0;
            row += 1;
        }
        if (this.field[row][col + 1]  == TREE) {
            TentConfig tree = new TentConfig(this, row, col, TREE);
            tree.field[row][col] = TREE;
            successors.add(tree);
        }
        else {
            TentConfig grass = new TentConfig(this, row, col, GRASS);
            grass.field[row][col] = GRASS;
            successors.add(grass);

            TentConfig tent = new TentConfig(this, row, col, TENT);
            tent.field[row][col] = TENT;
            successors.add(tent);
        }
        return successors;
    }

    /**
     * Is the current configuration valid or not?
     * @return true if valid, false otherwise
     */
    @Override
    public boolean isValid() {
        char type = this.field[currentRow][currentCol];
        if (type == TREE) {
            if (currentRow < DIM - 1 && currentRow - 1 >= 0 && currentCol < DIM - 1 && currentCol - 1 >= 0
            && this.field[currentRow - 1][currentCol - 1] == TENT) {
                return false;
            }
            if (this.currentRow - 1 > -1 & this.currentCol - 1 > -1) {
                // check if tree from left, right, top, bottom exists
                if (field[currentRow + 1][currentCol] == TREE || field[currentRow][currentCol + 1] == TREE
                        || field[currentRow - 1][currentCol] == TREE || field[currentRow][currentCol - 1] == TREE) {
                    return false;
                }
                // check if tree from leftTop, rightTop, leftBottom, right Bottom exists
                if (field[currentRow + 1][currentCol + 1] == TREE || field[currentRow - 1][currentCol - 1] == TREE
                        || field[currentRow - 1][currentCol + 1] == TREE || field[currentRow + 1][currentCol - 1] == TREE) {
                    return false;
                }
                //check if tree is near the tent or not
                if (field[currentRow + 1][currentCol] == TENT || field[currentRow][currentCol + 1] == TENT
                        || field[currentRow - 1][currentCol] == TENT || field[currentRow][currentCol - 1] == TENT) {
                    return true;
                }
            }
        }
        /* int[] upRow = this.rowLook;
        int[] downRow = this.rowLook;
        for (int col = this.col - 1; col >= 0; --col) {
            upRow -= 1;
            downRow += 1;
            if ((this.field[this.row][col] == TENT) ||
                    (upRow >= 0 && this.field[upRow][col] == TENT) ||
                    (downRow < this.DIM && this.field[downRow][col] == TENT)) {
                return false;
            }
        } */
        return true;
    }

    /**
     * Is the current configuration a goal?
     * @return true if goal, false otherwise
     */
    @Override
    public boolean isGoal() {
        return (this.currentRow == DIM - 1 && this.currentCol == DIM - 1);

    }

    /**
     * to string method
     * @return the output
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(" ");
        for (int i = 0; i < DIM * 2 - 1; i++) {
            string.append(HORI_DIVIDE);
        }
        for (int row = 0; row < DIM; row++) {
            string.append('\n');
            string.append(VERT_DIVIDE);
            for (int col = 0; col < DIM; col++) {
                string.append(this.field[row][col]);
                if (col != DIM - 1) {
                    string.append(' ');
                }
            }
            string.append(VERT_DIVIDE);
            string.append(rowLook[row]);
        }
        string.append('\n');
        string.append(' ');
        for (int i = 0; i < DIM * 2 - 1; i++) {
            string.append(HORI_DIVIDE);
        }
        string.append('\n');
        for (int col = 0; col < DIM; col++) {
            string.append(' ');
            string.append(colLook[col]);
        }
        return string.toString();
    }
}
