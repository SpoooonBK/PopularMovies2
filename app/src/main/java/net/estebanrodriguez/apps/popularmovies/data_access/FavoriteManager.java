package net.estebanrodriguez.apps.popularmovies.data_access;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import net.estebanrodriguez.apps.popularmovies.database.DatabaseContract;
import net.estebanrodriguez.apps.popularmovies.interfaces.listeners.FavoritesUpdatedListener;
import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;
import net.estebanrodriguez.apps.popularmovies.model.MovieReview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Created by spoooon on 2/15/17.
 */
public class FavoriteManager {

    private List<FavoritesUpdatedListener> mFavoritesUpdatedListeners;
    private ContentResolver mContentResolver = null;


    private FavoriteManager() {

    }

    private static class FavoriteManagerHelper {
        private static final FavoriteManager INSTANCE = new FavoriteManager();
    }


    public static FavoriteManager getInstance(ContentResolver contentResolver) {
        FavoriteManager favoriteManager = FavoriteManagerHelper.INSTANCE;
        favoriteManager.setContentResolver(contentResolver);
        return FavoriteManagerHelper.INSTANCE;
    }

    public static FavoriteManager getInstance(){
        return FavoriteManagerHelper.INSTANCE;
    }


    public String LOG_TAG = FavoriteManager.class.getName();

    private void setContentResolver(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    public int toggleFavorite(final MovieItem movieItem) {

                if (isFavorited(movieItem)) {
                    unfavoriteMovie(movieItem);
                    movieItem.setFavorited(false);
                    return -1;
                } else {
                    favoriteMovie(movieItem);
                    movieItem.setFavorited(true);
                    return 0;
                }
    }


    public void favoriteMovie(MovieItem movieItem) {




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


                mContentResolver.insert(
                        DatabaseContract.BasicMovieDetailEntries.CONTENT_URI,
                        basicDetailsValues
                );


                for (MovieClip movieClip : movieClips) {
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

                    mContentResolver.insert(
                            DatabaseContract.MovieClipEntries.CONTENT_URI,
                            movieClipValues
                    );


                }

                for (MovieReview movieReview : movieReviews) {
                    ContentValues reviewValues = new ContentValues();
                    reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_AUTHOR, movieReview.getAuthor());
                    reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_CONTENT, movieReview.getContent());
                    reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_MOVIE_ID, movieItem.getID());
                    reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_REVIEW_URL, movieReview.getUrl());

                    mContentResolver.insert(
                            DatabaseContract.MovieReviewEntries.CONTENT_URI,
                            reviewValues
                    );
                }
                notifyListeners();
                showFavorites();
    }


    public Boolean unfavoriteMovie(MovieItem movieItem) {


        String[] selection = {movieItem.getID()};

        mContentResolver.delete(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI, DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_MOVIE_ID + " = ?", selection);
        mContentResolver.delete(DatabaseContract.MovieClipEntries.CONTENT_URI, DatabaseContract.MovieClipEntries.COLUMN_NAME_MOVIE_ID + " = ?", selection);
        mContentResolver.delete(DatabaseContract.MovieReviewEntries.CONTENT_URI, DatabaseContract.MovieReviewEntries.COLUMN_NAME_MOVIE_ID + " = ?", selection);


        boolean unfavorited = !isFavorited(movieItem);
        if (unfavorited) {
            Log.v(LOG_TAG, movieItem.getTitle() + " unfavorited.");
        }
        notifyListeners();
        showFavorites();
        return !isFavorited(movieItem);
    }


    public List<MovieItem> getFavorites() {



        Cursor cursor = mContentResolver.query(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI, null, null, null, null);
        List<MovieItem> movieItems = MovieItemFactory.buildMovieList(cursor);
        for (MovieItem movieItem : movieItems) {
            String id = movieItem.getID();
            cursor = mContentResolver.query(DatabaseContract.MovieClipEntries.CONTENT_URI, null, id, null, null);
            List<MovieClip> movieClips = MovieDetailFactory.buildMovieClipList(cursor);
            movieItem.setMovieClips(movieClips);

            cursor = mContentResolver.query(DatabaseContract.MovieReviewEntries.CONTENT_URI, null, id, null, null);
            List<MovieReview> movieReviews = MovieDetailFactory.buildMovieReviewList(cursor);
            movieItem.setMovieReviews(movieReviews);
            Log.v(LOG_TAG, movieItem.toString());
        }
        cursor.close();
        showFavorites();
        return movieItems;
    }


//TODO remove after testing
    public void showFavorites() {



        Cursor cursor = null;

        try{
            cursor = mContentResolver.query(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI, null, null, null, null);
            List<MovieItem> movieItems = MovieItemFactory.buildMovieList(cursor);
            for (MovieItem movieItem : movieItems) {
                String id = movieItem.getID();
                cursor = mContentResolver.query(DatabaseContract.MovieClipEntries.CONTENT_URI, null, id, null, null);
                List<MovieClip> movieClips = MovieDetailFactory.buildMovieClipList(cursor);
                movieItem.setMovieClips(movieClips);

                cursor = mContentResolver.query(DatabaseContract.MovieReviewEntries.CONTENT_URI, null, id, null, null);
                List<MovieReview> movieReviews = MovieDetailFactory.buildMovieReviewList(cursor);
                movieItem.setMovieReviews(movieReviews);
                Log.v(LOG_TAG, movieItem.toString());
            }

        }catch (Exception e){

        }finally {
            if(cursor!=null)
            cursor.close();
        }

    }



    public Observable<List<MovieItem>> setObservable(final Context context) {
        Observable<List<MovieItem>> observable = Observable.fromCallable(new Callable<List<MovieItem>>() {
            @Override
            public List<MovieItem> call() throws Exception {
                return getFavorites();
            }
        });
        return observable;
    }

    public boolean isFavorited(MovieItem movieItem) {
        return isFavorited(movieItem.getID());
    }



    public boolean isFavorited(String movieID) {


        Cursor cursor = null;

        String selectionClause = DatabaseContract.MovieReviewEntries.COLUMN_NAME_MOVIE_ID + " = ?";
        String[] selectionArgs = {movieID};
        Boolean favorited = null;

        try {
            cursor = mContentResolver.query(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI, null, selectionClause, selectionArgs, null);

            favorited = cursor.getCount() > 0;

        } catch (Exception e) {

        } finally {
            if (cursor != null  && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return favorited;
    }



    public void setFavoritesUpdatedListener(FavoritesUpdatedListener listener) {
        if (mFavoritesUpdatedListeners == null) {
            mFavoritesUpdatedListeners = new ArrayList<>();
        }
        mFavoritesUpdatedListeners.add(listener);
    }


    public void notifyListeners() {
        if (mFavoritesUpdatedListeners != null) {
            for (FavoritesUpdatedListener listener : mFavoritesUpdatedListeners) {
                listener.onFavoritesUpdated();
            }
        }

    }




}
