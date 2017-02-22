package net.estebanrodriguez.apps.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spoooon on 11/18/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    /**
     * The constant DB_NAME.
     */
    public static final String DB_NAME = "LocalMovies.db";
    /**
     * The constant DB_VERSION.
     */
    public static final int DB_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";


    /**
     * Instantiates a new Database helper.
     *
     * @param context the context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        List<String> createStatements = buildCreateStrings();
        for(String statement: createStatements){
            Log.v(LOG_TAG, statement);
            sqLiteDatabase.execSQL(statement);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    private List<String> buildCreateStrings(){

        List<String> stringList= new ArrayList<>();


        String createMovieTable = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.BasicMovieDetailEntries.TABLE_NAME +
                " ( " + DatabaseContract.BasicMovieDetailEntries._ID + " INTEGER PRIMARY KEY, " +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_MOVIE_ID + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ADULT + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_BACKDROP_PATH + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_IMAGE_FETCH_URL + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ORGINAL_TITLE + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ORIGINAL_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_POPULARITY + " REAL " + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_POSTER_PATH + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_RELEASE_DATE + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VIDEO + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VOTE_AVERAGE + " REAL " + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VOTE_COUNT + " REAL " + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_FAVORITED + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_TITLE + TEXT_TYPE + ")";

        String createMovieClipTable = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.MovieClipEntries.TABLE_NAME + " ( " +
                DatabaseContract.MovieClipEntries._ID + " INTEGER PRIMARY KEY, " +
                DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_TYPE + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_URI + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieClipEntries.COLUMN_NAME_URL_KEY + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_SIZE + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieClipEntries.COLUMN_NAME_LANGUAGE_ISO639 + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieClipEntries.COLUMN_NAME_LANGUAGE_ISO3166 + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieClipEntries.COLUMN_NAME_MOVIE_ID + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieClipEntries.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieClipEntries.COLUMN_NAME_SITE + TEXT_TYPE + ")";

        String createMovieReviewTable = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.MovieReviewEntries.TABLE_NAME + " ( " +
                DatabaseContract.MovieReviewEntries._ID + " INTEGER PRIMARY KEY, " +
                DatabaseContract.MovieReviewEntries.COLUMN_NAME_MOVIE_ID + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieReviewEntries.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieReviewEntries.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                DatabaseContract.MovieReviewEntries.COLUMN_NAME_REVIEW_URL + TEXT_TYPE +  ")";

        stringList.add(createMovieTable);
        stringList.add(createMovieClipTable);
        stringList.add(createMovieReviewTable);

        return stringList;

    }
}
