package net.estebanrodriguez.apps.popularmovies.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import net.estebanrodriguez.apps.popularmovies.database.DatabaseContract;
import net.estebanrodriguez.apps.popularmovies.database.DatabaseHelper;

/**
 * Created by Spoooon on 11/21/2016.
 */

public class PopularMoviesProvider extends ContentProvider {

    private DatabaseHelper mLocalMovieDBHelper;
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

        mLocalMovieDBHelper = new DatabaseHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        db = mLocalMovieDBHelper.getReadableDatabase();

        try{

            switch (URI_MATCHER.match(uri)){

                case MOVIE_ITEM_LIST:{
                    String query = "SELECT * from " + DatabaseContract.BasicMovieDetailEntries.TABLE_NAME;
                    if(db != null){
                        return db.rawQuery(query, null);
                    }
                }

                case MOVIE_CLIP_LIST:{
                    String query = "SELECT * from " + DatabaseContract.MovieClipEntries.TABLE_NAME
                            + " WHERE " + DatabaseContract.MovieClipEntries.COLUMN_NAME_MOVIE_ID
                            + " = " + selection;
                    if(db != null){
                        return db.rawQuery(query, null);
                    }
                }

                case MOVIE_REVIEW_LIST:{
                    String query = "SELECT * from " + DatabaseContract.MovieReviewEntries.TABLE_NAME
                            + " WHERE " + DatabaseContract.MovieReviewEntries.COLUMN_NAME_MOVIE_ID
                            + " = " + selection;
                    if(db != null){
                        return db.rawQuery(query, null);
                    }
                }

            }

            } catch (SQLException e){

        }
//        finally {
//            if(db != null){
//                db.close();
//            }
//        }




        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)){

            case MOVIE_ITEM_LIST:
                return DatabaseContract.BasicMovieDetailEntries.CONTENT_TYPE;

            case  MOVIE_ITEM_ID:
                return DatabaseContract.BasicMovieDetailEntries.CONTENT_TYPE_ITEM;

            case MOVIE_CLIP_LIST:
                return DatabaseContract.MovieClipEntries.CONTENT_TYPE;

            case MOVIE_CLIP_ID:
                return DatabaseContract.MovieClipEntries.CONTENT_TYPE_ITEM;

            case MOVIE_REVIEW_LIST:
                return DatabaseContract.MovieReviewEntries.CONTENT_TYPE;

            case MOVIE_REVIEW_ID:
                return DatabaseContract.MovieReviewEntries.CONTENT_TYPE_ITEM;

            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if(URI_MATCHER.match(uri) != MOVIE_ITEM_LIST &&
                URI_MATCHER.match(uri) != MOVIE_CLIP_LIST &&
                URI_MATCHER.match(uri) != MOVIE_REVIEW_LIST
                ){
            throw new IllegalArgumentException("Unsupported uri for insertion: " + uri);
        }


        try {


            db = mLocalMovieDBHelper.getWritableDatabase();

            switch (URI_MATCHER.match(uri)) {

                case MOVIE_ITEM_LIST: {
                    long id = db.insert(DatabaseContract.BasicMovieDetailEntries.TABLE_NAME, null, contentValues);
                    return (getUriForId(id, uri));
                }

                case MOVIE_CLIP_LIST: {
                    long id = db.insert(DatabaseContract.MovieClipEntries.TABLE_NAME, null, contentValues);
                    return (getUriForId(id, uri));
                }

                case MOVIE_REVIEW_LIST: {
                    long id = db.insert(DatabaseContract.MovieReviewEntries.TABLE_NAME, null, contentValues);
                    return (getUriForId(id, uri));
                }

            }
        } catch (SQLException e){
        }
        finally {
            if(db != null){
                db.close();
            }
        }
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

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            return itemUri;
        }
        // s.th. went wrong:
        throw new SQLException(
                "Problem while inserting into uri: " + uri);
    }



    static {

        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.PATH_MOVIE_BASIC_DETAILS,
                MOVIE_ITEM_LIST);

        URI_MATCHER.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.PATH_MOVIE_BASIC_DETAILS +"/#",
                MOVIE_ITEM_ID);

        URI_MATCHER.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.PATH_MOVIE_CLIPS,
                MOVIE_CLIP_LIST);

        URI_MATCHER.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.PATH_MOVIE_CLIPS + "/#",
                MOVIE_CLIP_ID);

        URI_MATCHER.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.PATH_MOVIE_REVIEWS,
                MOVIE_REVIEW_LIST);

        URI_MATCHER.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.PATH_MOVIE_REVIEWS + "/#",
                MOVIE_REVIEW_ID);

    }
}
