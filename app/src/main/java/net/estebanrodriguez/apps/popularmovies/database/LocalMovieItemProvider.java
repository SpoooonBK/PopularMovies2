package net.estebanrodriguez.apps.popularmovies.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Spoooon on 11/21/2016.
 */

public class LocalMovieItemProvider extends ContentProvider {

    private LocalMovieDBHelper mLocalMovieDBHelper;
    private SQLiteDatabase db;

    //UriMatcher constants
    private static final UriMatcher URI_MATCHER;
    private static final int MOVIE_ITEM_LIST = 1;
    private static final int MOVIE_ITEM_ID = 2;
    private static final int MOVIE_CLIP_LIST = 3;
    private static final int MOVIE_CLIP_ID = 4;
    private static final int MOVIE_REVIEW_LIST = 5;
    private static final int MOVIE_REVIEW_ID = 6;



    @Override
    public boolean onCreate() {

        mLocalMovieDBHelper = new LocalMovieDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        db = mLocalMovieDBHelper.getReadableDatabase();
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)){

            case MOVIE_ITEM_LIST:
                return LocalMovieDBContract.LocalMovieBasicDetailEntries.CONTENT_TYPE;

            case  MOVIE_ITEM_ID:
                return LocalMovieDBContract.LocalMovieBasicDetailEntries.CONTENT_TYPE_ITEM;

            case MOVIE_CLIP_LIST:
                return LocalMovieDBContract.LocalMovieClipEntries.CONTENT_TYPE;

            case MOVIE_CLIP_ID:
                return LocalMovieDBContract.LocalMovieClipEntries.CONTENT_TYPE_ITEM;

            case MOVIE_REVIEW_LIST:
                return LocalMovieDBContract.LocalMovieReviewEntries.CONTENT_TYPE;

            case MOVIE_REVIEW_ID:
                return LocalMovieDBContract.LocalMovieReviewEntries.CONTENT_TYPE_ITEM;

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        db = mLocalMovieDBHelper.getWritableDatabase();


        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    static {

        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(LocalMovieDBContract.CONTENT_AUTHORITY,
                LocalMovieDBContract.PATH_MOVIE_BASIC_DETAILS,
                MOVIE_ITEM_LIST);

        URI_MATCHER.addURI(LocalMovieDBContract.CONTENT_AUTHORITY,
                LocalMovieDBContract.PATH_MOVIE_BASIC_DETAILS +"/#",
                MOVIE_ITEM_ID);

        URI_MATCHER.addURI(LocalMovieDBContract.CONTENT_AUTHORITY,
                LocalMovieDBContract.PATH_MOVIE_CLIPS,
                MOVIE_CLIP_LIST);

        URI_MATCHER.addURI(LocalMovieDBContract.CONTENT_AUTHORITY,
                LocalMovieDBContract.PATH_MOVIE_CLIPS + "/#",
                MOVIE_CLIP_ID);

        URI_MATCHER.addURI(LocalMovieDBContract.CONTENT_AUTHORITY,
                LocalMovieDBContract.PATH_MOVIE_REVIEWS,
                MOVIE_REVIEW_LIST);

        URI_MATCHER.addURI(LocalMovieDBContract.CONTENT_AUTHORITY,
                LocalMovieDBContract.PATH_MOVIE_REVIEWS + "/#",
                MOVIE_REVIEW_ID);

    }
}
