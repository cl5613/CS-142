package cs;

import java.util.AbstractMap;
import java.util.Map;

/**
 * A public class that contains maps to get the genre (GENRES) or
 * movie type (TITLE_TYPE) using a string key.  For example:<br>
 * <br>
 * <tt>MovieMaps.GENRES.get("Action");   // returns Genre.ACTION</tt><br>
 * <tt>MovieMaps.TITLE_TYPES.get("movie");   // returns TitleType.MOVIE</tt><br>
 */
public final class MovieMaps {
    /** the genres map */
    public final static Map<String, Genre> GENRES = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Action", Genre.ACTION),
            new AbstractMap.SimpleEntry<>("Adult", Genre.ADULT),
            new AbstractMap.SimpleEntry<>("Adventure", Genre.ADVENTURE),
            new AbstractMap.SimpleEntry<>("Animation", Genre.ANIMATION),
            new AbstractMap.SimpleEntry<>("Biography", Genre.BIOGRAPHY),
            new AbstractMap.SimpleEntry<>("Comedy", Genre.COMEDY),
            new AbstractMap.SimpleEntry<>("Crime", Genre.CRIME),
            new AbstractMap.SimpleEntry<>("Documentary", Genre.DOCUMENTARY),
            new AbstractMap.SimpleEntry<>("Drama", Genre.DRAMA),
            new AbstractMap.SimpleEntry<>("Family", Genre.FAMILY),
            new AbstractMap.SimpleEntry<>("Fantasy", Genre.FANTASY),
            new AbstractMap.SimpleEntry<>("Film-Noir", Genre.FILM_NOIR),
            new AbstractMap.SimpleEntry<>("Game-Show", Genre.GAME_SHOW),
            new AbstractMap.SimpleEntry<>("History", Genre.HISTORY),
            new AbstractMap.SimpleEntry<>("Horror", Genre.HORROR),
            new AbstractMap.SimpleEntry<>("Music", Genre.MUSIC),
            new AbstractMap.SimpleEntry<>("Musical", Genre.MUSICAL),
            new AbstractMap.SimpleEntry<>("Mystery", Genre.MYSTERY),
            new AbstractMap.SimpleEntry<>("News", Genre.NEWS),
            new AbstractMap.SimpleEntry<>("None", Genre.NONE),
            new AbstractMap.SimpleEntry<>("Reality-TV", Genre.REALITY_TV),
            new AbstractMap.SimpleEntry<>("Romance", Genre.ROMANCE),
            new AbstractMap.SimpleEntry<>("Sci-Fi", Genre.SCI_FI),
            new AbstractMap.SimpleEntry<>("Short", Genre.SHORT),
            new AbstractMap.SimpleEntry<>("Short-Film", Genre.SHORT_FILM),
            new AbstractMap.SimpleEntry<>("Sport", Genre.SPORT),
            new AbstractMap.SimpleEntry<>("Superhero", Genre.SUPERHERO),
            new AbstractMap.SimpleEntry<>("Talk-Show", Genre.TALK_SHOW),
            new AbstractMap.SimpleEntry<>("Thriller", Genre.THRILLER),
            new AbstractMap.SimpleEntry<>("War", Genre.WAR),
            new AbstractMap.SimpleEntry<>("Western", Genre.WESTERN)
    );

    /** the title types map */
    public final static Map<String, TitleType> TITLE_TYPES = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("movie", TitleType.MOVIE),
            new AbstractMap.SimpleEntry<>("short", TitleType.SHORT),
            new AbstractMap.SimpleEntry<>("tvEpisode", TitleType.TV_EPISODE),
            new AbstractMap.SimpleEntry<>("tvMiniSeries", TitleType.TV_MINI_SERIES),
            new AbstractMap.SimpleEntry<>("tvSeries", TitleType.TV_SERIES),
            new AbstractMap.SimpleEntry<>("tvSpecial", TitleType.TV_SPECIAL),
            new AbstractMap.SimpleEntry<>("video", TitleType.VIDEO),
            new AbstractMap.SimpleEntry<>("videoGame", TitleType.VIDEO_GAME)
    );
}
