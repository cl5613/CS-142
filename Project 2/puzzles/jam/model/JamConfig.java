package puzzles.jam.model;

import puzzles.common.solver.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * JamConfig Class
 * @author Chen Lin
 */

public class JamConfig implements Configuration {
    /** fields */
    public String[][] field;
    /** number of rows */
    public static int numRows;
    /** number of columns */
    public static int numCols;
    /** number of cars */
    public static int numCars;
    /** a list of cars */
    public LinkedList<Car> cars;

    /**
     * Construct the initial configuration from an input file
     * @param filename the file name to read
     * @throws FileNotFoundException if file is not found, throw this error.
     */
    public JamConfig(String filename) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filename));
        JamConfig.numRows = in.nextInt();
        JamConfig.numCols = in.nextInt();
        JamConfig.numCars = in.nextInt();
        this.field = new String[numRows][numCols];
        cars = new LinkedList<>();
        in.nextLine();
        while (in.hasNext()) {
            String nextLine = in.nextLine();
            String[] s;
            s = nextLine.split("\\s+");
            Car car = new Car(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Integer.parseInt(s[4]));
            cars.add(car);

            for(int row = 0; row < numRows; row++) {
                for(int col =0; col < numCols; col++) {
                    if (field[row][col] == null) {
                        field[row][col] = ".";
                    }
                }
            }

            for (Car c : cars) {
                for (int a = c.startRow; a <= c.endRow; a++) {
                    for (int b = c.startCol; b <= c.endCol; b++) {
                        field[a][b] = c.letter;
                    }
                }
            }
        }
    }

    /**
     * JamConfig copy constructor
     * @param newCars a list of cars
     */
    public JamConfig(LinkedList<Car> newCars) {
        field = new String[numRows][numCols];
        cars = new LinkedList<>(newCars);

        for (String[] s: field) {
            Arrays.fill(s, ".");
        }

        for (Car c : cars) {
            for (int i = c.startRow; i <= c.endRow; i++) {
                for (int j = c.startCol; j <= c.endCol; j++) {
                    field[i][j] = c.letter;
                }
            }
        }
    }

    /**
     * Get the collection of successors from the current one
     * @return All successors, valid and invalid
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        HashSet<Configuration> successors = new HashSet<>();

        for (Car car: cars){
            int step = 1;
            int index = cars.indexOf(car);

            if (car.startRow == car.endRow) {
                if (car.endCol + 1 < numCols) {
                    if (field[car.endRow][car.endCol + step].equals(".")) {
                        Car newCar = new Car(car.letter, car.startRow, car.startCol, car.endRow, car.endCol);
                        newCar.startCol += step;
                        newCar.endCol += step;
                        successors.add(new JamConfig(updateCar(cars, newCar, index)));
                    }
                }

                if (car.startCol - 1 >= 0) {
                    if (field[car.startRow][car.startCol - step].equals(".")) {
                        Car newCar = new Car(car.letter, car.startRow, car.startCol, car.endRow, car.endCol);
                        newCar.startCol -= step;
                        newCar.endCol -= step;
                        successors.add(new JamConfig(updateCar(cars, newCar, index)));
                    }
                }
            }

            if (car.startCol == car.endCol) {
                if (car.startRow - 1 >= 0) {
                    if (field[car.startRow - step][car.startCol].equals(".")) {
                        Car newCar = new Car(car.letter, car.startRow, car.startCol, car.endRow, car.endCol);
                        newCar.startRow -= step;
                        newCar.endRow -= step;
                        successors.add(new JamConfig(updateCar(cars, newCar, index)));
                    }
                }
                if (car.endRow + 1 < numRows) {
                    if (field[car.endRow + step][car.endCol].equals(".")) {
                        Car newCar = new Car(car.letter, car.startRow, car.startCol, car.endRow, car.endCol);
                        newCar.startRow += step;
                        newCar.endRow += step;
                        successors.add(new JamConfig(updateCar(cars, newCar, index)));
                    }
                }
            }
        }
        return successors;
    }

    /**
     * update cars
     * @param cars a list of cars
     * @param other other cars
     * @param index index
     * @return a list of new cars
     */
    public LinkedList<Car> updateCar(LinkedList<Car> cars, Car other, int index) {
        LinkedList<Car> newCars = new LinkedList<>(cars);
        newCars.remove(index);
        newCars.add(index, other);
        return newCars;
    }

    /**
     * Is current configuration a goal?
     * @return true if Yes, false otherwise.
     */
    @Override
    public boolean isGoal() {
        for (Car car: cars) {
            if (car.letter.equals("X")) {
                return car.endCol == numCols - 1;
            }
        }
        return false;
    }

    /**
     * to string method
     * @return the output
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for(int row = 0; row < numRows; row++) {
            for(int col =0; col < numCols; col++) {
                string.append(field[row][col]);
                string.append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }

    /**
     * Equals method
     * @param o object
     * @return two fields that equals each other
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JamConfig that = (JamConfig) o;
        return Arrays.deepEquals(field, that.field);
    }

    /**
     * Hash code method
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.field);
    }

    /** car class */
    public static class Car {
        /** start row */
        public int startRow;
        /** start column */
        public int startCol;
        /** end row */
        public int endRow;
        /** end column */
        public int endCol;
        /** letter of the cars */
        public String letter;
        /** cars constructor */
        public Car(String letter, int startRow, int startCol, int endRow, int endCol) {
            this.startRow = startRow;
            this.startCol = startCol;
            this.endCol = endCol;
            this.endRow = endRow;
            this.letter = letter;
        }

        /**
         * equals method
         * @param o other
         * @return true if valid, false if not.
         */
        @Override
        public boolean equals(Object o) {
            if (o instanceof Car) {
                Car other = (Car) o;
                if (this.letter.equals(other.letter)) {
                    if (this.startCol == this.endCol && other.startCol == other.endCol) {
                        return true;
                    }
                    if (this.startRow == this.endRow && other.startRow == other.endRow) {
                        return true;
                    }
                }
                else {
                    return false;
                }
            }
            return false;
        }
    }
}


