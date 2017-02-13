package net.estebanrodriguez.apps.popularmovies.data_access;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import net.estebanrodriguez.apps.popularmovies.database.DatabaseContract;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;

/**
 * Created by spoooon on 2/10/17.
 */

public class FavoriteChecker {

    public static MovieItem getFavoritedMovie(String movieID, Context context){


        String selectionClause = DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_MOVIE_ID;
        String[] selectionArgs = {movieID};


        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor =  contentResolver.query(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI, null, selectionClause, selectionArgs, null);

        if(cursor.getCount()>0){
           List<MovieItem> movieItems = MovieItemFactory.buildMovieList(cursor);
            return movieItems.get(0);
        }

        return null;

    }

    public static boolean isFavorited (String movieID, Context context){

        boolean favorited = false;
        String selectionClause = DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_MOVIE_ID + " = ?";
        String[] selectionArgs = {movieID};


        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor =  contentResolver.query(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI, null, selectionClause, selectionArgs, null);

        if(cursor.getCount() > 0){
            favorited = true;
        }

        return favorited;
    }
}
