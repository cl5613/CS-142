package movies;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * An abstract class that is responsible for representing the "universal" structures
 * that will be used by the subclass, MyIMDB.  It contains routines for
 * loading and converting the IMDB movie dataset files (tsv - tab separated values)
 * into various protected structures that the subclass can access.
 *
 * @author RIT CS
 * @author Chen Lin
 */
public abstract class IMDB {
    /** the small basics dataset file name */
    private final static String SMALL_BASICS_TSV = "small.basics.tsv";
    /** the small ratings dataset file name */
    private final static String SMALL_RATINGS_TSV = "small.ratings.tsv";

    /** the large basics dataset file name */
    private final static String TITLE_BASICS_TSV = "title.basics.tsv";
    /** the large ratings dataset file name */
    private final static String TITLE_RATINGS_TSV = "title.ratings.tsv";

    /** the adult movie tag in the basics dataset */
    private final static String IS_ADULT = "1";

    /** a list of Movie objects */
    protected List<Movie> movieList;
    /** a map of a movie ID (tconst String), to a Movie object */
    protected Map<String, Movie> movieMap;

    /** whether we are working with the small dataset or not */
    private final boolean small;

    // THESE ABSTRACT METHODS MUST BE IMPLEMENTED BY THE MyIMDB SUBCLASS

    /**
     * Find all movies of a certain type that contain the words as a substring
     * (case sensitive).  This routine should use the protected movieList
     * to perform the search.
     *
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param words the words as a string that the movie title must contain to match
     * @return the collection of movies that match (order determined by order in file).
     */
    public abstract Collection<Movie> getMovieTitleWithWords(String type, String words);

    /**
     * Find a movie by a certain ID (a unique tConst string).  This routine has a precondition
     * that IMDB's convertMovieMapToList has already been called, and the map, movieMap,
     * has been created.  This routine must use movieMap to perform the lookup as an
     * expected O(1) operation.
     *
     * @param ID the movie's tConst string ID
     *
     * @rit.pre the movie's string ID must exist
     * @return the matching Movie object, or null if not found
     */
    public abstract Movie findMovieByID(String ID);

    /**
     * Find movies of a certain type for a specific year that are a certain genre.
     * The movies the are returned should be ordered alphabetically by title.
     *
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param year the year
     * @param genre the genre, e.g. "Crime", "Drama", etc.
     * @return the movies ordered alphabetically by title
     */
    public abstract Collection<Movie> getMoviesByYearAndGenre(String type, int year, String genre);

    /**
     * Get the movies of a certain type that have a runtime inclusively in the range between
     * start and end.  The movies returned should be ordered by descending runtime length,
     * followed by alphabetically by the movie title in case of a tie.
     *
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param start the start year (inclusive)
     * @param end the end year (inclusive)
     * @return the movies ordered by descending run time length, then alphabetical by
     * title in case of a tie
     */
    public abstract Collection<Movie> getMoviesByRuntime(String type, int start, int end);

    /**
     * Get the movies of a certain type with the most votes.  The movies returned should
     * be ordered by descending number of votes, followed by alphabetically by the
     * movie title in case of a tie.
     *
     * @param num number of movies to list
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @return the movies ordered by descending number of votes, then alphabetical by
     * title in case of a tie
     */
    public abstract Collection<Movie> getMoviesMostVotes(int num, String type);

    /**
     * For a range of inclusive years, get the num top rated movies for each year.
     * The collection returned should have the movies for each year ordered
     * by descending rating, and using the movie title alphabetically to resolve ties.
     *
     * @param num number of top movies
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param start the start year (inclusive)
     * @param end the end year (inclusive)
     * @return the map is keyed by year from start to end inclusive, and the values
     * for each year are the movies ordered by descending rating, using the movie
     * title alphabetically to resolve ties.
     */
    public abstract Map<Integer, List<Movie>> getMoviesTopRated(int num, String type, int start, int end);

    /**
     * Create the IMDB instance.  Based on whether it is reading the small or large datasets,
     * it will read in the entire basics file, creating each unique Movie object per line, and
     * adding to the movieList.
     *
     * @param small true if using the small dataset, and large if not
     * @throws FileNotFoundException if the basics file is not found
     */
    public IMDB(boolean small) throws FileNotFoundException {
        this.movieList = new LinkedList<>();
        this.movieMap = null;
        this.small = small;

        // determine which file to read
        Scanner in;
        if (this.small) {
            in = new Scanner(new File(SMALL_BASICS_TSV));
        } else {
            in = new Scanner(new File(TITLE_BASICS_TSV));
        }

        in.nextLine();
        while (in.hasNext()) {
            String line = in.nextLine();
            String[] fields = line.split("\t");
            if (!fields[4].equals(IS_ADULT)) {
                Movie movie = Movie.createMovie(fields[0], fields[1], fields[2], fields[5], fields[7], fields[8]);
                this.movieList.add(movie);
            }
        }
        in.close();
    }

    /**
     * Convert the movieList created in IMDB's constructor into movieMap -
     * a HashMap that associates a movie's ID key (tconst string) to a Movie object value.
     * @rit.pre the constructor has run and movieList has been created and populated with
     *  Movie objects
     */
    public void convertMovieListToMap() {
        this.movieMap = new HashMap<>();
        for (Movie movie: this.movieList) {
            this.movieMap.put(movie.getID(), movie);
        }
    }

    /**
     * Read the ratings dataset and update each Movie in movieList with the Rating object.
     * In the event a movie does not have a rating, a new Rating's object should be created
     * for the movie with a rating and numVotes of 0.
     *
     * @throws FileNotFoundException if the file is not found
     */
    public void processRatings() throws FileNotFoundException {
        // determine which dataset to use
        Scanner in;
        if (this.small) {
            in = new Scanner(new File(SMALL_RATINGS_TSV));
        } else {
            in = new Scanner(new File(TITLE_RATINGS_TSV));
        }

        in.nextLine();
        while (in.hasNext()) {
             String rating = in.nextLine();
             String [] fields = rating.split("\t");
             Rating rating1 = new Rating(fields[0], Double.parseDouble(fields[1]), Integer.parseInt(fields[2]));
             if (this.movieMap.containsKey(fields[0])) {
                 this.movieMap.get(fields[0]).setRating(rating1);
             }
        }
        for (Movie movie: movieList) {
            if (movie.getRating() == null) {
                Rating rating = new Rating(movie.getID(), 0, 0);
                movie.setRating(rating);
           }
       }
        in.close();
    }
}
