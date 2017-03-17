package net.estebanrodriguez.apps.popularmovies.local_database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Spoooon on 11/17/2016.
 */
public final class DatabaseContract {

    /**
     * The constant CONTENT_AUTHORITY.
     */
    public static final String CONTENT_AUTHORITY = "net.estebanrodriguez.apps.popularmovies";
    /**
     * The constant BASE_CONTENT_URI.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * The constant PATH_MOVIE_BASIC_DETAILS.
     */
    public static final String PATH_MOVIE_BASIC_DETAILS = BasicMovieDetailEntries.TABLE_NAME;
    /**
     * The constant PATH_MOVIE_CLIPS.
     */
    public static final String PATH_MOVIE_CLIPS = MovieClipEntries.TABLE_NAME;
    /**
     * The constant PATH_MOVIE_REVIEWS.
     */
    public static final String PATH_MOVIE_REVIEWS = MovieReviewEntries.TABLE_NAME;

    private DatabaseContract() {}

    /**
     * The type Basic movie detail entries.
     */
    public static class BasicMovieDetailEntries implements BaseColumns {

        /**
         * The constant CONTENT_URI.
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_BASIC_DETAILS).build();

        /**
         * The constant TABLE_NAME.
         */
        public static final String TABLE_NAME = "movies";


        /**
         * The constant COLUMN_NAME_ID.
         */
        public static final String COLUMN_NAME_ID = "_ID";
        /**
         * The constant COLUMN_NAME_MOVIE_ID.
         */
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        /**
         * The constant COLUMN_NAME_ADULT.
         */
        public static final String COLUMN_NAME_ADULT= "adult";
        /**
         * The constant COLUMN_NAME_OVERVIEW.
         */
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        /**
         * The constant COLUMN_NAME_POSTER_PATH.
         */
        public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        /**
         * The constant COLUMN_NAME_RELEASE_DATE.
         */
        public static final String COLUMN_NAME_RELEASE_DATE ="release_date";
        /**
         * The constant COLUMN_NAME_ORGINAL_TITLE.
         */
        public static final String COLUMN_NAME_ORGINAL_TITLE ="original_title";
        /**
         * The constant COLUMN_NAME_ORIGINAL_LANGUAGE.
         */
        public static final String COLUMN_NAME_ORIGINAL_LANGUAGE = "original_language";
        /**
         * The constant COLUMN_NAME_BACKDROP_PATH.
         */
        public static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        /**
         * The constant COLUMN_NAME_POPULARITY.
         */
        public static final String COLUMN_NAME_POPULARITY = "popularity";
        /**
         * The constant COLUMN_NAME_VOTE_COUNT.
         */
        public static final String COLUMN_NAME_VOTE_COUNT = "vote_count";
        /**
         * The constant COLUMN_NAME_VIDEO.
         */
        public static final String COLUMN_NAME_VIDEO = "video";
        /**
         * The constant COLUMN_NAME_VOTE_AVERAGE.
         */
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        /**
         * The constant COLUMN_NAME_IMAGE_FETCH_URL.
         */
        public static final String COLUMN_NAME_IMAGE_FETCH_URL = "image_fetch_url";
        /**
         * The constant COLUMN_NAME_TITLE.
         */
        public static final String COLUMN_NAME_TITLE ="title";
        /**
         * The constant COLUMN_NAME_FAVORITED.
         */
        public static final String COLUMN_NAME_FAVORITED ="favorited";

        /**
         * The constant CONTENT_TYPE.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/net.estebanrodriguez.apps.popularmovies.movies";

        /**
         * The constant CONTENT_TYPE_ITEM.
         */
        public static final String CONTENT_TYPE_ITEM =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/net.estebanrodriguez.apps.popularmovies.movies";

        /**
         * Build get movie details uri uri.
         *
         * @param movie_id the movie id
         * @return the uri
         */
        public static Uri buildGetMovieDetailsURI(Integer movie_id){
            return CONTENT_URI.buildUpon().appendPath(movie_id.toString()).build();
        }

    }

    /**
     * The type Movie clip entries.
     */
    public static class MovieClipEntries implements BaseColumns {

        /**
         * The constant CONTENT_URI.
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_CLIPS).build();

        /**
         * The constant TABLE_NAME.
         */
        public static final String TABLE_NAME ="clips";


        /**
         * The constant COLUMN_NAME_MOVIE_ID.
         */
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        /**
         * The constant COLUMN_NAME_LANGUAGE_ISO639.
         */
        public static final String COLUMN_NAME_LANGUAGE_ISO639 = "iso_639";
        /**
         * The constant COLUMN_NAME_LANGUAGE_ISO3166.
         */
        public static final String COLUMN_NAME_LANGUAGE_ISO3166 = "iso_3166";
        /**
         * The constant COLUMN_NAME_URL_KEY.
         */
        public static final String COLUMN_NAME_URL_KEY = "url_key";
        /**
         * The constant COLUMN_NAME_NAME.
         */
        public static final String COLUMN_NAME_NAME = "name";
        /**
         * The constant COLUMN_NAME_SITE.
         */
        public static final String COLUMN_NAME_SITE = "site";
        /**
         * The constant COLUMN_NAME_CLIP_TYPE.
         */
        public static final String COLUMN_NAME_CLIP_TYPE = "clip_type";
        /**
         * The constant COLUMN_NAME_CLIP_URI.
         */
        public static final String COLUMN_NAME_CLIP_URI = "clip_uri";
        /**
         * The constant COLUMN_NAME_CLIP_SIZE.
         */
        public static final String COLUMN_NAME_CLIP_SIZE = "clip_size";

        /**
         * The constant CONTENT_TYPE.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/net.estebanrodriguez.apps.popularmovies.clips";

        /**
         * The constant CONTENT_TYPE_ITEM.
         */
        public static final String CONTENT_TYPE_ITEM =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/net.estebanrodriguez.apps.popularmovies.clips";

        /**
         * Build get movie clips uri uri.
         *
         * @param movie_id the movie id
         * @return the uri
         */
        public static Uri buildGetMovieClipsURI (Integer movie_id){
            return CONTENT_URI.buildUpon().appendPath(movie_id.toString()).build();
        }
    }

    /**
     * The type Movie review entries.
     */
    public static class MovieReviewEntries implements BaseColumns {

        /**
         * The constant CONTENT_URI.
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_REVIEWS).build();

        /**
         * The constant TABLE_NAME.
         */
        public static final String TABLE_NAME ="reviews";


        /**
         * The constant COLUMN_NAME_MOVIE_ID.
         */
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        /**
         * The constant COLUMN_NAME_AUTHOR.
         */
        public static final String COLUMN_NAME_AUTHOR = "author";
        /**
         * The constant COLUMN_NAME_CONTENT.
         */
        public static final String COLUMN_NAME_CONTENT = "content";
        /**
         * The constant COLUMN_NAME_REVIEW_URL.
         */
        public static final String COLUMN_NAME_REVIEW_URL = "review_url";

        /**
         * The constant CONTENT_TYPE.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/net.estebanrodriguez.apps.popularmovies.reviews";

        /**
         * The constant CONTENT_TYPE_ITEM.
         */
        public static final String CONTENT_TYPE_ITEM =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/net.estebanrodriguez.apps.popularmovies.reviews";

        /**
         * Build get reviews uri uri.
         *
         * @param movie_id the movie id
         * @return the uri
         */
        public static Uri buildGetReviewsURI(Integer movie_id){
         return   CONTENT_URI.buildUpon().appendPath(movie_id.toString()).build();
        }
    }


}
