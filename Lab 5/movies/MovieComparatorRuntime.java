package movies;

import java.util.Comparator;

/**
 * A comparator that orders Movie's by descending runtime length,
 * then alphabetically by title.
 *
 * @author RIT CS
 * @author Chen Lin
 */
public class MovieComparatorRuntime implements Comparator<Movie> {
    /**
     * Compare two Movie's by runtime.
     *
     * @param m1 first movie
     * @param m2 second movie
     * @return less than 0 if m2 has less runtime than m1.  if runtimes
     * are equal it is the natural order comparison of m1 to m2's titles.
     * equal if both runtime and title are the same.  greater than 0
     * if m2's runtime is greater than m1's.
     */
    @Override
    public int compare(Movie m1, Movie m2) {
        int result = m2.getRuntimeMinutes() - m1.getRuntimeMinutes();
        if (result == 0) {
            result = m1.getTitle().compareTo(m2.getTitle());
        }
        else {
            result = m2.getRuntimeMinutes() - m1.getRuntimeMinutes();
        }
        return result;
    }
}
