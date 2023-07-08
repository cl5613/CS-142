import movies.Movie;
import movies.MyIMDB;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The main program for the IMDB movie query system.  If run on the command line
 * with no arguments, it uses the large title (data/title.basics.tsv) and ratings
 * (data/title.ratings.tsv) files.  Otherwise with any arguments it uses the small title
 * (data/small.basics.tsv) and ratings (data/small.ratings.tsv) files.
 *
 * The input commands need to be redirected from a file to standard input using the run
 * configuration.  Example input commands are in the input/ directory.
 *
 * @author RIT CS
 */
public class MovieMain {
    // INPUT COMMANDS
    /** find movie titles that contain a substring */
    private final static String CONTAINS = "CONTAINS";
    /** lookup a movie by ID (tconst) */
    private final static String LOOKUP = "LOOKUP";
    /** find movies by year and genre */
    private final static String YEAR_AND_GENRE = "YEAR_AND_GENRE";
    /** find a range of movies with a certain runtime in minutes */
    private final static String RUNTIME = "RUNTIME";
    /** find movies with the top number of votes (from ratings) */
    private final static String MOST_VOTES = "MOST_VOTES";
    /** find the top movies over a range of years */
    private final static String TOP = "TOP";

    /** the concrete class that inherits and implements from the IMDB abstract class */
    private final MyIMDB imdb;

    /**
     * Instantiate MyIDBM with the small or large dataset, then call three methods
     * to handle reading in the files and creating the appropriate internal data
     * structures to represent them.
     *
     * @param small true if the small dataset is to be used, false for the large one
     * @throws FileNotFoundException if the file cannot be found
     */
    public MovieMain(boolean small) throws FileNotFoundException {
        // read in the basics movie dataset
        System.out.println("Reading movies into list...");
        long start = System.currentTimeMillis();
        this.imdb = new MyIMDB(small);
        System.out.println("Elapsed time (s): " + ((System.currentTimeMillis() - start) / 1000.0));

        // convert the list to a map for fast lookup by id
        System.out.println("Converting movie list to map...");
        start = System.currentTimeMillis();
        this.imdb.convertMovieListToMap();
        System.out.println("Elapsed time (s): " + ((System.currentTimeMillis() - start) / 1000.0));

        // create in the ratings dataset
        System.out.println("Reading ratings into map...");
        start = System.currentTimeMillis();
        this.imdb.processRatings();
        System.out.println("Elapsed time (s): " + ((System.currentTimeMillis() - start) / 1000.0));
    }

    /**
     * Used by some commands to get the trailing words (often used for substring titles).
     *
     * @param fields array of strings
     * @param start the index in the array to start at
     * @return the individual words concatenated to a string with spaces in-between
     */
    private String combineFields(String[] fields, int start) {
        StringBuilder words = new StringBuilder();
        for (int i=start; i<fields.length; ++i) {
            words.append(fields[i]);
            if (i != fields.length-1) {
                words.append(" ");
            }
        }
        return words.toString();
    }

    /**
     * Handles the contain command query, e.g.: "CONTAINS MOVIE Starman".
     *
     * @param fields the command
     */
    private void processContains(String[] fields) {
        String words = combineFields(fields, 2);
        for (Movie movie : this.imdb.getMovieTitleWithWords(fields[1], words.toString())) {
            System.out.println(movie);
        }
    }

    /**
     * Handles the lookup command query, e.g.: "LOOKUP tt0081505".
     *
     * @param ID
     */
    private void processLookup(String ID) {
        System.out.println(this.imdb.findMovieByID(ID));
    }

    /**
     * Handle the year-genre command query, e.g. "YEAR_AND_GENRE MOVIE 1945 Crime".
     *
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param year the year
     * @param genre the genre, e.g. "Crime", "Drama", etc.
     */
    private void processYearAndGenre(String type, int year, String genre) {
        for (Movie movie : this.imdb.getMoviesByYearAndGenre(type, year, genre)) {
            System.out.println("\tTITLE: " + movie.getTitle() +
                    ", TYPE: " + movie.getTitleType() +
                    ", YEAR: " + movie.getYear() +
                    ", GENRES: " + movie.getGenres());
        }
    }

    /**
     * Handles the runtime command query, e.g. "RUNTIME MOVIE 237 240".
     *
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param start the start year (inclusive)
     * @param end the end year (inclusive)
     */
    private void processRuntime(String type, int start, int end) {
        for (Movie movie : this.imdb.getMoviesByRuntime(type, start, end)) {
            System.out.println("\tTITLE: " + movie.getTitle() +
                    ", TYPE: " + movie.getTitleType() +
                    ", YEAR: " + movie.getYear() +
                    ", RUNTIME: " + movie.getRuntimeMinutes());
        }
    }

    /**
     * Handle the most-votes command query, e.g. "MOST_VOTES 10 MOVIE".
     *
     * @param num number of movies to list
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     */
    private void processMostVotes(int num, String type) {
        int spot = 1;
        for (Movie movie : this.imdb.getMoviesMostVotes(num, type)) {
            System.out.println("\t" + spot++ + ": " +
                    "TITLE: " + movie.getTitle() +
                    ", TYPE: " + movie.getTitleType() +
                    ", YEAR: " + movie.getYear() +
                    ", VOTES: " + movie.getRating().getNumVotes());
        }
    }

    /**
     * Process the top command query, e.g. "TOP 10 MOVIE 1990 1995".
     * @param num number of top movies
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param start the start year (inclusive)
     * @param end the end year (inclusive)
     */
    private void processTop(int num, String type, int start, int end) {
        Map<Integer, List<Movie>> movies = this.imdb.getMoviesTopRated(num, type, start, end);
        for (int year = start; year <= end; ++year) {
            System.out.println("YEAR: " + year);
            int spot = 1;
            if (movies.containsKey(year)) {
                for (Movie movie : movies.get(year)) {
                    System.out.println("\t" + spot++ + ": " +
                            "TITLE: " + movie.getTitle() +
                            ", TYPE: " + movie.getTitleType() +
                            ", RATING: " + movie.getRating().getRating() +
                            ", VOTES: " + movie.getRating().getNumVotes());
                }
            }
        }
    }

    /**
     * Handles processing of the input commands that are intended to be redirected from
     * a file to standard input.  All commands start with the query command in question,
     * along with various additional fields depending on the command.
     */
    public void mainLoop() {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String line = in.nextLine();
            System.out.println("Processing: " + line);
            String[] fields = line.split("\\s+");
            long start = System.currentTimeMillis();

            // breakdown the query command here and pass control to the appropriate method
            switch (fields[0]) {
                case CONTAINS -> processContains(fields);
                case LOOKUP -> processLookup(fields[1]);
                case YEAR_AND_GENRE -> processYearAndGenre(fields[1], Integer.parseInt(fields[2]), fields[3]);
                case RUNTIME -> processRuntime(fields[1], Integer.parseInt(fields[2]), Integer.parseInt(fields[3]));
                case MOST_VOTES -> processMostVotes(Integer.parseInt(fields[1]), fields[2]);
                case TOP -> processTop(Integer.parseInt(fields[1]), fields[2],
                        Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
                default -> System.out.println("Unrecognized command " + fields[0]);
            }

            System.out.println("Elapsed time (s): " + ((System.currentTimeMillis() - start) / 1000.0));
        }
        in.close();  // <3 Jim
    }

    /**
     * The main method.
     *
     * @param args if a command line arg is present, we run with the small dataset,
     *             otherwise the large.
     * @throws FileNotFoundException if the file cannot be found
     */
    public static void main(String[] args) throws FileNotFoundException{
        // figure out if we are going to run with the small or large dataset
        boolean small = false;
        if (args.length == 1) {
            small = true;
        }

        // initialize and pass control to the input command processor
        MovieMain movieMain = new MovieMain(small);
        movieMain.mainLoop();
    }
}
