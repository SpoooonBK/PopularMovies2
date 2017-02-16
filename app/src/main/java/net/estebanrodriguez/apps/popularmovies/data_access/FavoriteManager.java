package net.estebanrodriguez.apps.popularmovies.data_access;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import net.estebanrodriguez.apps.popularmovies.database.DatabaseContract;
import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;
import net.estebanrodriguez.apps.popularmovies.model.MovieReview;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Created by spoooon on 2/15/17.
 */
public class FavoriteManager {

    private FavoriteManager() {
    }

    /**
     * The constant LOG_TAG.
     */
    public static String LOG_TAG = FavoriteManager.class.getName();

    /**
     * Toggle favorite int.
     *
     *
     * @param movieItem the movie item
     * @param context   the context
     * @return the int
     */

    public static int toggleFavorite(MovieItem movieItem, Context context){

        if (isFavorited(movieItem, context)) {
            unfavoriteMovie(movieItem, context);
            movieItem.setFavorited(false);
            return -1;
        } else {
            favoriteMovie(movieItem, context);
            movieItem.setFavorited(true);
            return 0;
        }

    }

    /**
     * Favorite movie.
     *
     * Saves a movieItem and its details into the local database
     *
     * @param movieItem the movie item
     * @param context   the context
     */
    public static void favoriteMovie(MovieItem movieItem, Context context){


        ContentResolver contentResolver = context.getContentResolver();

        List<MovieClip> movieClips = movieItem.getMovieClips();
        List<MovieReview> movieReviews = movieItem.getMovieReviews();


        ContentValues basicDetailsValues = new ContentValues();

        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_MOVIE_ID, movieItem.getID());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ORGINAL_TITLE, movieItem.getOriginalTitle());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_BACKDROP_PATH, movieItem.getBackdropPath());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_IMAGE_FETCH_URL, movieItem.getImageFetchURL());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ORIGINAL_LANGUAGE, movieItem.getOriginalLanguage());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_OVERVIEW, movieItem.getOverview());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_POPULARITY, movieItem.getPopularity());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_POSTER_PATH, movieItem.getPosterPath());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_RELEASE_DATE, movieItem.getFormattedReleaseDate());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_TITLE, movieItem.getTitle());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VIDEO, movieItem.isVideo());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VOTE_AVERAGE, movieItem.getVoteAverage());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VOTE_COUNT, movieItem.getVoteCount());

        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ADULT, movieItem.isAdult());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_FAVORITED, movieItem.isFavorited());


        contentResolver.insert(
                DatabaseContract.BasicMovieDetailEntries.CONTENT_URI,
                basicDetailsValues
        );



        for(MovieClip movieClip: movieClips){
            ContentValues movieClipValues = new ContentValues();
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_URL_KEY, movieClip.getKey());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_TYPE, movieClip.getClipType());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_URI, movieClip.getClipURI());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_LANGUAGE_ISO639, movieClip.getLanguageCodeISO639());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_LANGUAGE_ISO3166, movieClip.getLanguagecodeiso3166());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_MOVIE_ID, movieItem.getID());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_NAME, movieClip.getName());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_SITE, movieClip.getSite());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_SIZE, movieClip.getSize());

            contentResolver.insert(
                    DatabaseContract.MovieClipEntries.CONTENT_URI,
                    movieClipValues
            );



        }

        for(MovieReview movieReview: movieReviews){
            ContentValues reviewValues = new ContentValues();
            reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_AUTHOR, movieReview.getAuthor());
            reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_CONTENT, movieReview.getContent());
            reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_MOVIE_ID, movieItem.getID());
            reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_REVIEW_URL, movieReview.getUrl());

            contentResolver.insert(
                    DatabaseContract.MovieReviewEntries.CONTENT_URI,
                    reviewValues
            );
        }
        showFavorites(context);



    }

    /**
     * Unfavorite movie boolean.
     *
     * @param movieItem the movie item
     * @param context   the context
     * @return the boolean
     */
    public static Boolean unfavoriteMovie(MovieItem movieItem, Context context){

        ContentResolver contentResolver = context.getContentResolver();
        String[] selection = {movieItem.getID()};

        contentResolver.delete(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI,DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_MOVIE_ID, selection);
        contentResolver.delete(DatabaseContract.MovieClipEntries.CONTENT_URI, DatabaseContract.MovieClipEntries.COLUMN_NAME_MOVIE_ID, selection);
        contentResolver.delete(DatabaseContract.MovieReviewEntries.CONTENT_URI,DatabaseContract.MovieReviewEntries.COLUMN_NAME_MOVIE_ID, selection);


        boolean unfavorited = !isFavorited(movieItem, context);
        if(unfavorited){
            Log.v(LOG_TAG, movieItem.getTitle() + " unfavorited.");
        }

        showFavorites(context);
        return !isFavorited(movieItem, context);
    }

    /**
     * Get favorites list.
     *
     * @param context the context
     * @return the list
     */
    public static List<MovieItem> getFavorites(Context context){

        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI, null, null, null, null);
        List<MovieItem> movieItems = MovieItemFactory.buildMovieList(cursor);
        for(MovieItem movieItem : movieItems)
        {
            String id = movieItem.getID();
            cursor = contentResolver.query(DatabaseContract.MovieClipEntries.CONTENT_URI, null, id, null, null);
            List<MovieClip> movieClips = MovieDetailFactory.buildMovieClipList(cursor);
            movieItem.setMovieClips(movieClips);

            cursor = contentResolver.query(DatabaseContract.MovieReviewEntries.CONTENT_URI, null, id, null, null);
            List<MovieReview> movieReviews = MovieDetailFactory.buildMovieReviewList(cursor);
            movieItem.setMovieReviews(movieReviews);
            Log.v(LOG_TAG, movieItem.toString());
        }
        cursor.close();
        return movieItems;
    }

    /**
     * Show favorites.
     *
     * @param context the context
     */
//TODO remove after testing
    public static void showFavorites(Context context){

        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI, null, null, null, null);
        List<MovieItem> movieItems = MovieItemFactory.buildMovieList(cursor);
        for(MovieItem movieItem : movieItems)
        {
            String id = movieItem.getID();
            cursor = contentResolver.query(DatabaseContract.MovieClipEntries.CONTENT_URI, null, id, null, null);
            List<MovieClip> movieClips = MovieDetailFactory.buildMovieClipList(cursor);
            movieItem.setMovieClips(movieClips);

            cursor = contentResolver.query(DatabaseContract.MovieReviewEntries.CONTENT_URI, null, id, null, null);
            List<MovieReview> movieReviews = MovieDetailFactory.buildMovieReviewList(cursor);
            movieItem.setMovieReviews(movieReviews);
            Log.v(LOG_TAG, movieItem.toString());
        }
        cursor.close();

    }

    /**
     * Set observable observable.
     *
     * @param context the context
     * @return the observable
     */
    public static Observable<List<MovieItem>> setObservable(final Context context){
        Observable<List<MovieItem>> observable = Observable.fromCallable(new Callable<List<MovieItem>>() {
            @Override
            public List<MovieItem> call() throws Exception {
                return getFavorites(context);
            }
        });
        return observable;
    }

    /**
     * Set observable observable.
     *
     * @param movieItem the movie item
     * @param context   the context
     * @return the observable
     */
    public static Observable<Boolean> setObservable(final MovieItem movieItem, final Context context){
        Observable<Boolean> observable = Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return unfavoriteMovie(movieItem, context);
            }
        });
        return observable;
    }

    /**
     * Is favorited boolean.
     *
     * @param movieItem the movie item
     * @param context   the context
     * @return the boolean
     */
    public static boolean isFavorited(MovieItem movieItem, Context context){
        Cursor cursor = null;
        ContentResolver contentResolver = context.getContentResolver();
        String selectionClause = DatabaseContract.MovieReviewEntries.COLUMN_NAME_MOVIE_ID + " = ?";
        String[] selectionArgs = {movieItem.getID()};
        Boolean favorited = null;

        try{
            cursor =contentResolver.query(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI,null,selectionClause, selectionArgs, null);

            if(cursor.getCount() > 0){
                favorited = true;
            }else favorited = false;

        }catch (Exception e){

        }finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return favorited;
    }
}
