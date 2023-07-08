package movies;

import cs.Genre;
import cs.TitleType;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * The subclass of the IMDB abstract class that implements all the required
 * abstract query methods.
 *
 * @author RIT CS
 * @author Chen Lin
 */
public class MyIMDB extends IMDB {
    /** The minimum number of votes a movie needs to be considered for top ranking */
    private final static int MIN_NUM_VOTES_FOR_TOP_RANKED = 1000;

    /**
     * Create IMDB using the small or large dataset.
     *
     * @param small true if the small dataset is desired, otherwise the large one
     * @throws FileNotFoundException
     */
    public MyIMDB(boolean small) throws FileNotFoundException {
        super(small);
    }

    /**
     * Find all movies of a certain type that contain the words as a substring
     * (case sensitive). This routine should use the protected movieList to perform the search.
     *
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param words the words as a string that the movie title must contain to match
     * @return the collection of movies that match (order determined by order in file)
     */
    @Override
    public Collection<Movie> getMovieTitleWithWords(String type, String words) {
        List<Movie> result = new LinkedList<>();

        TitleType titleType = TitleType.valueOf(type);
        for(Movie movie: movieList) {
            if (movie.getTitleType() == titleType && movie.getTitle().contains(words)) {
                result.add(movie);
            }
        }
        return result;
    }

    /**
     * Find a movie by a certain ID (a unique tConst string). This routine has a precondition that
     * IMDB's convertMovieMapToList has already been called, and the map, movieMap, has been created.
     * This routine must use movieMap to perform the lookup as an expected O(1) operation.
     *
     * @param ID the movie's tConst string ID
     * @return the matching Movie object, or null if not found
     */
    @Override
    public Movie findMovieByID(String ID) {
        if (this.movieMap.containsKey(ID)) {
            return movieMap.get(ID);
        }
        return null;
    }

    /**
     * Find movies of a certain type for a specific year that are a certain genre.
     * The movies the are returned should be ordered alphabetically by title.
     *
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param year the year
     * @param genre the genre, e.g. "Crime", "Drama", etc.
     * @return the movies ordered alphabetically by title
     */
    @Override
    public Collection<Movie> getMoviesByYearAndGenre(String type, int year, String genre) {
        // we use Movie's natural order comparison which is to order Movie's of a
        // type by title and then year
        Set<Movie> result = new TreeSet<>();
        Genre genre1 = Genre.valueOf(genre);
        TitleType titleType = TitleType.valueOf(type);
        for (Movie movie: movieList) {
            if (movie.getTitleType() == titleType && movie.getGenres().contains(genre1) && movie.getYear() == year) {
                result.add(movie);
            }
        }
        return result;
    }

    /**
     * Get the movies of a certain type that have a runtime inclusively in the range between
     * lower and upper inclusively. The movies returned should be ordered by descending runtime
     * length, followed by alphabetically by the movie title in case of a tie.
     *
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param lower the lower bound runtime (inclusive)
     * @param upper the upper bound runtime (inclusive)
     * @return the movies ordered by descending run time length, then alphabetical by title
     * in case of a tie
     */
    @Override
    public Collection<Movie> getMoviesByRuntime(String type, int lower, int upper) {
        // we use a comparator which orders Movie's of a type by descending runtime
        // and then title
        Set<Movie> result = new TreeSet<>(new MovieComparatorRuntime());
        for (Movie movie: this.movieList) {
            if (movie.getTitleType() == TitleType.valueOf(type) && movie.getRuntimeMinutes() >= lower && movie.getRuntimeMinutes() <= upper) {
                result.add(movie);
            }
        }
        return result;
    }

    /**
     * Get the movies of a certain type with the most votes. The movies returned should be ordered
     * by descending number of votes, followed by alphabetically by the movie title in case of a tie.
     * @param num number of movies to list
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @return the movies ordered by descending number of votes, then alphabetical by title
     * in case of a tie
     */
    @Override
    public Collection<Movie> getMoviesMostVotes(int num, String type) {
        // use a comparator that orders Movie's of a type by descending number
        // of votes
        List<Movie> result = new LinkedList<>();
        Set<Movie> result1 = new TreeSet<>(new MovieComparatorVotes());
        for (Movie movie: movieList) {
            if (movie.getTitleType() == TitleType.valueOf(type)) {
                result1.add(movie);
            }
        }
        for (Movie movie: result1) {
            if (result.size() < num) {
                result.add(movie);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * For a range of inclusive years, get the num top rated movies for each year.
     * The collection returned should have the movies for each year ordered by
     * descending rating, and using the movie title alphabetically to resolve ties.
     *
     * @param num number of top movies
     * @param type the movie type, e.g. "MOVIE", "TV_SHOW", etc.
     * @param start the start year (inclusive)
     * @param end the end year (inclusive)
     * @return the map is keyed by year from start to end inclusive, and the values for each year
     * are the movies ordered by descending rating, using the movie title alphabetically to resolve ties.
     */
    @Override
    public Map<Integer, List<Movie>> getMoviesTopRated(int num, String type, int start, int end) {
        Map<Integer, List<Movie>> map = new TreeMap<>();
        Set<Rating> movieTreeSet = new TreeSet<>();

        for (Movie movie : this.movieList) {
            if (movie.getRating().getNumVotes() >= MIN_NUM_VOTES_FOR_TOP_RANKED
                    && movie.getTitleType() == TitleType.valueOf(type)
                    && movie.getYear() >= start && movie.getYear() <= end) {
                movieTreeSet.add(movie.getRating());
            }
        }
        for (int i = start; i <= end;  ++i) {
            map.put(i , new LinkedList<>());
        }

        for (Rating rating: movieTreeSet) {
            Movie currentMovie = movieMap.get(rating.getID());
            if (map.get(currentMovie.getYear()).size() < num) {
                map.get(currentMovie.getYear()).add(currentMovie);
            }
        }
        return map;
    }
}