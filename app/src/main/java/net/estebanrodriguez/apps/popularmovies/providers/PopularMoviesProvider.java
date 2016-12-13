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

import net.estebanrodriguez.apps.popularmovies.database.MovieItemDatabaseContract;
import net.estebanrodriguez.apps.popularmovies.database.MovieItemDatabaseHelper;

/**
 * Created by Spoooon on 11/21/2016.
 */

public class PopularMoviesProvider extends ContentProvider {

    private MovieItemDatabaseHelper mLocalMovieDBHelper;
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

        mLocalMovieDBHelper = new MovieItemDatabaseHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        db = mLocalMovieDBHelper.getReadableDatabase();

        try{

            switch (URI_MATCHER.match(uri)){

                case MOVIE_ITEM_LIST:{
                    String query = "SELECT * from " + MovieItemDatabaseContract.BasicMovieDetailEntries.TABLE_NAME;
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
                return MovieItemDatabaseContract.BasicMovieDetailEntries.CONTENT_TYPE;

            case  MOVIE_ITEM_ID:
                return MovieItemDatabaseContract.BasicMovieDetailEntries.CONTENT_TYPE_ITEM;

            case MOVIE_CLIP_LIST:
                return MovieItemDatabaseContract.MovieClipEntries.CONTENT_TYPE;

            case MOVIE_CLIP_ID:
                return MovieItemDatabaseContract.MovieClipEntries.CONTENT_TYPE_ITEM;

            case MOVIE_REVIEW_LIST:
                return MovieItemDatabaseContract.MovieReviewEntries.CONTENT_TYPE;

            case MOVIE_REVIEW_ID:
                return MovieItemDatabaseContract.MovieReviewEntries.CONTENT_TYPE_ITEM;

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
                    long id = db.insert(MovieItemDatabaseContract.BasicMovieDetailEntries.TABLE_NAME, null, contentValues);
                    return (getUriForId(id, uri));
                }

                case MOVIE_CLIP_LIST: {
                    long id = db.insert(MovieItemDatabaseContract.MovieClipEntries.TABLE_NAME, null, contentValues);
                    return (getUriForId(id, uri));
                }

                case MOVIE_REVIEW_LIST: {
                    long id = db.insert(MovieItemDatabaseContract.MovieReviewEntries.TABLE_NAME, null, contentValues);
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
//            if (!isInBatchMode()) {
//                // notify all listeners of changes:
//                getContext().
//                        getContentResolver().
//                        notifyChange(itemUri, null);
//            }
            return itemUri;
        }
        // s.th. went wrong:
        throw new SQLException(
                "Problem while inserting into uri: " + uri);
    }



    static {

        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(MovieItemDatabaseContract.CONTENT_AUTHORITY,
                MovieItemDatabaseContract.PATH_MOVIE_BASIC_DETAILS,
                MOVIE_ITEM_LIST);

        URI_MATCHER.addURI(MovieItemDatabaseContract.CONTENT_AUTHORITY,
                MovieItemDatabaseContract.PATH_MOVIE_BASIC_DETAILS +"/#",
                MOVIE_ITEM_ID);

        URI_MATCHER.addURI(MovieItemDatabaseContract.CONTENT_AUTHORITY,
                MovieItemDatabaseContract.PATH_MOVIE_CLIPS,
                MOVIE_CLIP_LIST);

        URI_MATCHER.addURI(MovieItemDatabaseContract.CONTENT_AUTHORITY,
                MovieItemDatabaseContract.PATH_MOVIE_CLIPS + "/#",
                MOVIE_CLIP_ID);

        URI_MATCHER.addURI(MovieItemDatabaseContract.CONTENT_AUTHORITY,
                MovieItemDatabaseContract.PATH_MOVIE_REVIEWS,
                MOVIE_REVIEW_LIST);

        URI_MATCHER.addURI(MovieItemDatabaseContract.CONTENT_AUTHORITY,
                MovieItemDatabaseContract.PATH_MOVIE_REVIEWS + "/#",
                MOVIE_REVIEW_ID);

    }
}
