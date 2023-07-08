package movies;

import cs.Genre;
import cs.MovieMaps;
import cs.TitleType;

import java.util.TreeSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represent a movie.
 *
 * @author RIT CS
 * @author Chen Lin
 */
public final class Movie implements Comparable<Movie> {
    /** used in the movie file to indicate there is no entry for a field */
    public final static String NO_FIELD = "\\N";

    /** the unique ID of the movie (tconst string) */
    private final String ID;
    /** the type of movie */
    private final TitleType titleType;
    /** the name of the movie */
    private final String title;
    /** the year the movie was made (start year) */
    private final int year;
    /** the runtime length of the movie in minutes */
    private final int runtimeMinutes;
    /** the genres of this movie */
    private final Set<Genre> genres;
    /** the rating details for this movie */
    private Rating rating;

    /**
     * The public factory method that is responsible for creating a new Movie object.
     * This routine sanitizes the data of the movie and handles translating missing
     * fields to valid values.
     *
     * @param ID the movie ID (tconst String)
     * @param titleType the type of movie, e.g. "movie", "tvShow", etc.
     * @param title the name of the movie
     * @param year the start year of the movie
     * @param runtimeMinutes the runtime length in minutes
     * @param genres the genres if the movie
     * @return a new Movie object
     */
    public static Movie createMovie(String ID, String titleType, String title,
                                    String year, String runtimeMinutes, String genres) {
        // determine the genres
        Set<Genre> genreSet = new TreeSet<>();
        String[] genreFields = genres.equals(Movie.NO_FIELD) ? new String[]{"None"} : genres.split(",");
        for (String genre : genreFields) {
            genreSet.add(MovieMaps.GENRES.get(genre));
        }

        // create and return the new movie
        return new Movie(ID,
                MovieMaps.TITLE_TYPES.get(titleType),
                title,
                year.equals(Movie.NO_FIELD) ? 0 : Integer.parseInt(year),
                runtimeMinutes.equals(Movie.NO_FIELD) ? 0 : Integer.parseInt(runtimeMinutes),
                genreSet);
    }

    /**
     * The private constructor of a movie, meant to be called only by the
     * Movie.createMovie() factory method.
     *
     * @param ID the movie ID (tconst String)
     * @param titleType the type of movie, e.g. "movie", "tvShow", etc.
     * @param title the name of the movie
     * @param year the start year of the movie
     * @param runtimeMinutes the runtime length in minutes
     * @param genres the genres if the movie
     */
    private Movie(String ID, TitleType titleType, String title,
                  int year, int runtimeMinutes, Set<Genre> genres) {
        this.ID = ID;
        this.titleType = titleType;
        this.title = title;
        this.year = year;
        this.runtimeMinutes = runtimeMinutes;
        this.genres = genres;
        this.rating = null;       // to be populated later when ratings are processed
    }

    /**
     * Get the movie's ID
     *
     * @return ID
     */
    public String getID() {
        return this.ID;
    }

    /**
     * Get the movie type.
     *
     * @return movie type
     */
    public TitleType getTitleType() {
        return this.titleType;
    }

    /**
     * Get the title of the movie.
     *
     * @return movie title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get the start year of the movie.
     *
     * @return the year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Get the runtime length of the movie.
     *
     * @return length in minutes
     */
    public int getRuntimeMinutes() {
        return this.runtimeMinutes;
    }

    /**
     * Get the genres of this movie.
     *
     * @return the genres
     */
    public Set<Genre> getGenres() {
        return this.genres;
    }

    /**
     * Get the rating of this movie.
     *
     * @return the rating
     */
    public Rating getRating() {
        return this.rating;
    }

    /**
     * Set the rating of this movie.
     *
     * @param rating the movie's rating
     */
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    /**
     * Return a string representation of the Movie containing the values for all
     * the fields.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Movie{" +
                "ID='" + this.ID + '\'' +
                ", titleType=" + this.titleType +
                ", title='" + this.title + '\'' +
                ", year=" + this.year +
                ", runtimeMinutes=" + this.runtimeMinutes +
                ", genres=" + this.genres +
                ", rating=" + this.rating +
                '}';
    }

    /**
     * Two movies are equal if they have the same title and year.
     *
     * @param other the other movie
     * @return whether they are equal or not
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Movie) {
            Movie otherMovie = (Movie) other;
            result = this.title.equals(otherMovie.title) &&
                    this.year == otherMovie.year;
        }
        return result;
    }

    /**
     * Get the hash code of the Movie using Objects.hash() of the title and year.
     */
    @Override
    public int hashCode() {
        return this.title.hashCode() + this.year;
    }

    /**
     * Movie's are naturally compared alphabetically by title, and then ascending
     * by year.
     *
     * @param other the movie to compare to
     * @return whether this movie is less than, equal, or greater than the other movie
     */
    @Override
    public int compareTo(Movie other) {
        int result = this.getTitle().compareTo(other.getTitle());
        if (result == 0) {
            result = this.getYear() - other.getYear();
        }
        return result;
    }
}
