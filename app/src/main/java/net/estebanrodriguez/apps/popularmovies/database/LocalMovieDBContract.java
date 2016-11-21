package net.estebanrodriguez.apps.popularmovies.database;

import android.os.Parcelable;
import android.provider.BaseColumns;

/**
 * Created by Spoooon on 11/17/2016.
 */

public final class LocalMovieDBContract {

    private LocalMovieDBContract() {}

    public static class LocalMovieBasicDetailEntries implements BaseColumns {

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_NAME_ID= "id";
        public static final String COLUMN_NAME_ADULT= "adult";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        public static final String COLUMN_NAME_RELEASE_DATE ="release_date";
        public static final String COLUMN_NAME_ORGINAL_TITLE ="original_title";
        public static final String COLUMN_NAME_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_NAME_POPULARITY = "popularity";
        public static final String COLUMN_NAME_VOTE_COUNT = "vote_count";
        public static final String COLUMN_NAME_VIDEO = "video";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_NAME_IMAGE_FETCH_URL = "image_fetch_url";
        public static final String COLUMN_NAME_TITLE ="title";

    }

    public static class LocalMovieClipEntries implements BaseColumns {

        public static final String TABLE_NAME ="clips";

        public static final String COLUMN_NAME_CLIP_ID ="id";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME_LANGUAGE_ISO639 = "iso_639";
        public static final String COLUMN_NAME_LANGUAGE_ISO3166 = "iso_3166";
        public static final String COLUMN_NAME_KEY = "key";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_SITE = "site";
        public static final String COLUMN_NAME_CLIP_TYPE = "clip_type";
        public static final String COLUMN_NAME_CLIP_URI = "clip_uri";
    }

    public static class LocalMovieReviewEntries implements BaseColumns {

        public static final String TABLE_NAME ="reviews";

        public static final String COLUMN_NAME_REVIEW_ID ="id";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_REVIEW_URL = "review_url";
    }
}
