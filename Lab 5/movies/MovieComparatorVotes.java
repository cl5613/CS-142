package movies;

import java.util.Comparator;

/**
 * A comparator that orders Movie's by descending number of votes (in Rating)
 * and then alphabetically by title.
 *
 * @author RIT CS
 * @author Chen Lin
 */
public class MovieComparatorVotes implements Comparator<Movie> {
    /**
     * Compare two Movies by votes.
     *
     * @param m1 the first movie
     * @param m2 the second movie
     * @return less than 0 if m2 has less votes than m1. if number of votes are equal it is the natural
     * order comparison of m1 to m2's titles. equal if both number of votes and title are the same.
     * greater than 0 if m2's votes is greater than m1's.
     */
    @Override
    public int compare(Movie m1, Movie m2) {
        int result = m2.getRating().getNumVotes() - m1.getRating().getNumVotes();
        if (result == 0) {
            result = m2.getTitle().compareTo(m1.getTitle());
        }
        return result;
    }
}
