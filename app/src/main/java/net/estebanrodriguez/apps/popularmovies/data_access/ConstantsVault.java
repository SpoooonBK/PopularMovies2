package net.estebanrodriguez.apps.popularmovies.data_access;

/**
 * Created by Spoooon on 10/9/2016.
 */
public final class ConstantsVault {

    //This class stores all string constants

    /**
     * The constant JSON_ARRAY_KEY.
     */
//Array Keys
    public static final String JSON_ARRAY_KEY = "results";

    /**
     * The constant POSTER_PATH.
     */
//movieItem Keys
    public static final String POSTER_PATH = "poster_path";
    /**
     * The constant ADULT.
     */
    public static final String ADULT = "adult";
    /**
     * The constant OVERVIEW.
     */
    public static final String OVERVIEW = "overview";
    /**
     * The constant RELEASE_DATE.
     */
    public static final String RELEASE_DATE = "release_date";
    /**
     * The constant GENRE_IDS.
     */
    public static final String GENRE_IDS = "genre_ids";
    /**
     * The constant ID.
     */
    public static final String ID = "id";
    /**
     * The constant ORIGINAL_TITLE.
     */
    public static final String ORIGINAL_TITLE = "original_title";
    /**
     * The constant ORIGINAL_LANGUAGE.
     */
    public static final String ORIGINAL_LANGUAGE = "original_language";
    /**
     * The constant TITLE.
     */
    public static final String TITLE = "title";
    /**
     * The constant BACKDROP_PATH.
     */
    public static final String BACKDROP_PATH = "backdrop_path";
    /**
     * The constant POPULARITY.
     */
    public static final String POPULARITY = "popularity";
    /**
     * The constant VOTE_COUNT.
     */
    public static final String VOTE_COUNT = "vote_count";
    /**
     * The constant VIDEO.
     */
    public static final String VIDEO = "video";
    /**
     * The constant VOTE_AVERAGE.
     */
    public static final String VOTE_AVERAGE = "vote_average";
    /**
     * The constant FAVORITED.
     */
    public static final String FAVORITED = "favorited";

    /**
     * The constant DETAIL_ID.
     */
//movieDetail Keys
    public static final String DETAIL_ID = "id";
    /**
     * The constant LANGUAGE_CODE_639_1.
     */
    public static final String LANGUAGE_CODE_639_1 = "iso_639_1";
    /**
     * The constant LANGUAGE_CODE_3116_1.
     */
    public static final String LANGUAGE_CODE_3116_1 = "iso_3166_1";
    /**
     * The constant CLIP_KEY.
     */
    public static final String CLIP_KEY = "key";
    /**
     * The constant CLIP_NAME.
     */
    public static final String CLIP_NAME = "name";
    /**
     * The constant CLIP_SITE.
     */
    public static final String CLIP_SITE = "site";
    /**
     * The constant CLIP_SIZE.
     */
    public static final String CLIP_SIZE = "size";
    /**
     * The constant CLIP_TYPE.
     */
    public static final String CLIP_TYPE = "type";
    /**
     * The constant DETAIL_AUTHOR.
     */
    public static final String DETAIL_AUTHOR = "author";
    /**
     * The constant DETAIL_CONTENT.
     */
    public static final String DETAIL_CONTENT = "content";
    /**
     * The constant DETAIL_URL.
     */
    public static final String DETAIL_URL ="url";


    /**
     * The constant IMAGE_SIZE_SMALLEST_W92.
     */
//Image size constants
    public static final String IMAGE_SIZE_SMALLEST_W92 = "w92";
    /**
     * The constant IMAGE_SIZE_SMALLER_W154.
     */
    public static final String IMAGE_SIZE_SMALLER_W154 = "w154";
    /**
     * The constant IMAGE_SIZE_RECOMMENDED_W185.
     */
    public static final String IMAGE_SIZE_RECOMMENDED_W185 = "w154";
    /**
     * The constant IMAGE_SIZE_MEDIUM_W342.
     */
    public static final String IMAGE_SIZE_MEDIUM_W342 = "w342";
    /**
     * The constant IMAGE_SIZE_LARGE_W500.
     */
    public static final String IMAGE_SIZE_LARGE_W500 = "w500";
    /**
     * The constant IMAGE_SIZE_LARGER_W780.
     */
    public static final String IMAGE_SIZE_LARGER_W780 = "w780";
    /**
     * The constant IMAGE_SIZE_ORIGINAL.
     */
    public static final String IMAGE_SIZE_ORIGINAL = "original";

    /**
     * The constant IMAGE_FETCH_BASE_URL.
     */
//IMAGE FETCH URL
    public static final String IMAGE_FETCH_BASE_URL = "http://image.tmdb.org/t/p/";

    /**
     * The constant DB_FETCH_POPULAR_BASE_URL.
     */
//Database Fetch URLs
    public static final String DB_FETCH_POPULAR_BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
    /**
     * The constant DB_FETCH_TOP_RATED_BASE_URL.
     */
    public static final String DB_FETCH_TOP_RATED_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?";
    /**
     * The constant DB_FETCH_BASE_URL.
     */
    public static final String DB_FETCH_BASE_URL = "http://api.themoviedb.org/3/movie/";

    /**
     * The constant NETWORK_ERROR_MESSAGE.
     */
//Error Messages
    public static final String NETWORK_ERROR_MESSAGE = "Usage limited to saved favorites.  To enable full usage, enable internet and restart.";

    /**
     * The constant MOVIE_ITEM_PARCELABLE.
     */
//Parcalables
    public static final String MOVIE_ITEM_PARCELABLE = "Movie Item Parcelable";
    /**
     * The constant MOVIE_CLIP_PARCELABLE.
     */
    public static final String MOVIE_CLIP_PARCELABLE = "Movie Clip Parcelable";

}
