package net.estebanrodriguez.apps.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.estebanrodriguez.apps.popularmovies.model.MovieDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Spoooon on 11/18/2016.
 */

public class LocalMovieDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = LocalMovieDBHelper.class.getSimpleName();

    public static final String DB_NAME = "LocalMovies.db";
    public static final int DB_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";







    public LocalMovieDBHelper(Context context) {
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


        String createMovieTable = "CREATE TABLE " + LocalMovieDBContract.LocalMovieBasicDetailEntries.TABLE_NAME +
                " ( " + LocalMovieDBContract.LocalMovieBasicDetailEntries._ID + " INTEGER PRIMARY KEY, " +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_ADULT + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_BACKDROP_PATH + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_IMAGE_FETCH_URL + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_ORGINAL_TITLE + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_ORIGINAL_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_POPULARITY + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_POSTER_PATH + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_RELEASE_DATE + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_VIDEO + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_IMAGE_FETCH_URL + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_VOTE_AVERAGE + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_VOTE_COUNT + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieBasicDetailEntries.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +" )";

        String createMovieClipTable = "CREATE TABLE " + LocalMovieDBContract.LocalMovieClipEntries.TABLE_NAME + " ( " +
                LocalMovieDBContract.LocalMovieClipEntries._ID + " INTEGER PRIMARY KEY, " +
                LocalMovieDBContract.LocalMovieClipEntries.COLUMN_NAME_CLIP_TYPE + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieClipEntries.COLUMN_NAME_CLIP_URI + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieClipEntries.COLUMN_NAME_KEY + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieClipEntries.COLUMN_NAME_LANGUAGE_ISO639 + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieClipEntries.COLUMN_NAME_LANGUAGE_ISO3166 + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieClipEntries.COLUMN_NAME_MOVIE_ID + "INTEGER, " +
                LocalMovieDBContract.LocalMovieClipEntries.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieClipEntries.COLUMN_NAME_SITE + TEXT_TYPE + COMMA_SEP + " )";

        String createMovieReviewTable = "CREATE TABLE " + LocalMovieDBContract.LocalMovieReviewEntries.TABLE_NAME + " ( " +
                LocalMovieDBContract.LocalMovieReviewEntries._ID + " INTEGER PRIMARY KEY, " +
                LocalMovieDBContract.LocalMovieReviewEntries.COLUMN_NAME_MOVIE_ID + " INTEGER, " +
                LocalMovieDBContract.LocalMovieReviewEntries.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieReviewEntries.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                LocalMovieDBContract.LocalMovieReviewEntries.COLUMN_NAME_REVIEW_URL + TEXT_TYPE + COMMA_SEP + " )";

        stringList.add(createMovieTable);
        stringList.add(createMovieClipTable);
        stringList.add(createMovieReviewTable);

        return stringList;

    }
}
