package net.estebanrodriguez.apps.popularmovies.data_access;

/**
 * Created by Spoooon on 10/9/2016.
 */

public final class ConstantsVault {

    //This class stores all string constants

    //Array Keys
    public static final String JSON_ARRAY_KEY = "results";

    //Item Keys
    public static final String POSTER_PATH = "poster_path";
    public static final String ADULT = "adult";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String GENRE_IDS = "genre_ids";
    public static final String ID = "id";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String TITLE = "title";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String POPULARITY = "popularity";
    public static final String VOTE_COUNT = "vote_count";
    public static final String VIDEO = "video";
    public static final String VOTE_AVERAGE = "vote_average";

    //Image size constants
    public static final String IMAGE_SIZE_SMALLEST_W92 = "w92";
    public static final String IMAGE_SIZE_SMALLER_W154 = "w154";
    public static final String IMAGE_SIZE_RECOMMENDED_W185 = "w154";
    public static final String IMAGE_SIZE_MEDIUM_W342 = "w342";
    public static final String IMAGE_SIZE_LARGE_W500 = "w500";
    public static final String IMAGE_SIZE_LARGER_W780 = "w780";
    public static final String IMAGE_SIZE_ORIGINAL = "original";

    //IMAGE FETCH URL
    public static final String IMAGE_FETCH_BASE_URL = "http://image.tmdb.org/t/p/";

    //Database Fetch URLs
    public static final String DB_FETCH_POPULAR_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
    public static final String DB_FETCH_TOP_RATED_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?";

    //Error Messages
    public static final String NETWORK_ERROR_MESSAGE = "Internet connectivity is required.  Please check your settings and restart.";

}
