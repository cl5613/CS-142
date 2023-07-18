package puzzles.jam.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Definition for the model of a Jam game.
 * @author Chen Lin
 */
public class JamModel {

    /** the collection of observers of this model */
    private final List<Observer<JamModel, JamClientData>> observers = new LinkedList<>();
    /** the current configuration */
    public JamConfig currentConfig;
    /** file name */
    private String filename;
    /** selectValid */
    private boolean selectValid;
    /** selected row */
    private int sRow;
    /** selected column */
    private int sCol;

    /**
     * JamModel Constructor
     */
    public JamModel() {
    }

    /**
     * hint
     */
    public void hint() {
        String message;

        if (currentConfig.isGoal()) {
            message = "Already Solved!";
        }
        else {
            Solver solver = new Solver(currentConfig);
            Optional<List<Configuration>> path = solver.BFS();
            if (path.isEmpty()) {
                message = "No solution";
            }
            else if (currentConfig.equals(path.get().get(0))) {
                currentConfig = (JamConfig) path.get().get(1);
                path.get().remove(0);
                message = "Next Step";
            }
            else {
                path = solver.BFS();
                currentConfig = (JamConfig) path.get().get(1);
                path.get().remove(0);
                message = "Next Step";
            }
        }
        alertObservers(new JamClientData(message));
    }

    /**
     * load a file (use in game)
     */
    public void load(String filename) {
        String message;
        try {
            this.currentConfig = new JamConfig(filename);
            this.filename = filename;
            String[] name = filename.split("/");
            message = "Loaded: " + name[name.length - 1];
        } catch (FileNotFoundException e) {
            message = "Fail to load: " + filename;
        }
        alertObservers(new JamClientData(message));
    }

    /**
     * select (use in game)
     */
    public void select(int row, int col){
        String message = " ";

        if (currentConfig.isGoal()) {
            message = "Already Solved!";
        }

        else if (!selectValid) {
            if (currentConfig.field[row][col].equals(".")) {
                message = "No Car at (" + row + ", " + col + ")";
            }
            else {
                sRow = row;
                sCol = col;
                selectValid = true;
                message = "Selected (" + sRow + ", " + sCol + ")";
            }
        }
        else {
            if (!currentConfig.field[row][col].equals(".")) {
                message = "Cannot move from (" + sRow + ", " + sCol + ") to (" + row + ", " + col + ")";
                selectValid = false;
            }
            else if (sRow - row > 1 || sCol -col > 1 || row - sRow > 1 || col - sCol > 1){
                message = "Cannot move from (" + sRow + ", " + sCol + ") to (" + row + ", " + col + ")";
                selectValid = false;
            }
            else {
                JamConfig.Car c = null;
                for (JamConfig.Car car: currentConfig.cars) {
                    if (car.letter.equals(currentConfig.field[sRow][sCol])) {
                        c = car;
                    }
                }
                assert c != null;
                if (c.startRow == c.endRow) {
                    if (sRow != row) {
                        message = "Cannot move from (" + sRow + ", " + sCol + ") to (" + row + ", " + col + ")";
                        selectValid = false;
                    }
                    else {
                        if (sCol > col) {
                            int index = currentConfig.cars.indexOf(c);
                            JamConfig.Car newCar = new JamConfig.Car(c.letter, c.startRow, c.startCol, c.endRow, c.endCol);
                            newCar.startCol -= 1;
                            newCar.endCol -= 1;
                            currentConfig = new JamConfig(currentConfig.updateCar(currentConfig.cars, newCar, index));
                            selectValid = false;
                            message = "move from (" + sRow + ", " + sCol + ") to (" + row + ", " + col + ")";

                        }
                        else if (sCol < col) {
                            int index = currentConfig.cars.indexOf(c);
                            JamConfig.Car newCar = new JamConfig.Car(c.letter, c.startRow, c.startCol, c.endRow, c.endCol);
                            newCar.startCol += 1;
                            newCar.endCol += 1;
                            currentConfig = new JamConfig(currentConfig.updateCar(currentConfig.cars, newCar, index));
                            selectValid = false;
                            message = "move from (" + sRow + ", " + sCol + ") to (" + row + ", " + col + ")";
                        }
                    }
                }
                if (c.startCol == c.endCol) {
                    if (sCol != col) {
                        message = "Cannot move from (" + sRow + ", " + sCol + ") to (" + row + ", " + col + ")";
                        selectValid = false;
                    }
                    else {
                        if (sRow > row) {
                            int index = currentConfig.cars.indexOf(c);
                            JamConfig.Car newCar = new JamConfig.Car(c.letter, c.startRow, c.startCol, c.endRow, c.endCol);
                            newCar.startRow -= 1;
                            newCar.endRow -= 1;
                            currentConfig = new JamConfig(currentConfig.updateCar(currentConfig.cars, newCar, index));
                            selectValid = false;
                            message = "move from (" + sRow + ", " + sCol + ") to (" + row + ", " + col + ")";

                        }
                        else if (sRow < row) {
                            int index = currentConfig.cars.indexOf(c);
                            JamConfig.Car newCar = new JamConfig.Car(c.letter, c.startRow, c.startCol, c.endRow, c.endCol);
                            newCar.startRow += 1;
                            newCar.endRow += 1;
                            currentConfig = new JamConfig(currentConfig.updateCar(currentConfig.cars, newCar, index));
                            selectValid = false;
                            message = "move from (" + sRow + ", " + sCol + ") to (" + row + ", " + col + ")";
                        }
                    }
                }
            }
        }
        alertObservers(new JamClientData(message));
    }

    /**
     * reset (use in game)
     */
    public void reset(){
        load(filename);
        alertObservers(new JamClientData("Puzzle Successfully Reset!"));
    }

    /**
     * quit (used in game)
     */
    public void quit() {
        System.exit(0);
    }

    /**
     * toString method
     * @return the output
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("  ");
        for (int i = 0; i < JamConfig.numCols; i++) {
            string.append(" ").append(i);
        }
        string.append("\n  ");
        string.append("--".repeat(Math.max(0, JamConfig.numCols)));
        string.append("\n");
        for (int i = 0; i < JamConfig.numRows; i++) {
            string.append(i).append("|");
            for (String s : currentConfig.field[i]) {
                string.append(" ").append(s);
            }
            string.append("\n");
        }
        return string.toString();
    }

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<JamModel, JamClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(JamClientData data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }
}
