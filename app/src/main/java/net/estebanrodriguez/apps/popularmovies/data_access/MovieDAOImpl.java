package net.estebanrodriguez.apps.popularmovies.data_access;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;

import net.estebanrodriguez.apps.popularmovies.BuildConfig;
import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Spoooon on 10/9/2016.
 */

public class MovieDAOImpl implements MovieDAO {

    private static Boolean isPreferenceChanged = null;
    private Context mContext;
    private Observable mMovieCache = null;


    private MovieDAOImpl() {

    }

    private static class MovieDAOHelper {
        private static final MovieDAOImpl INSTANCE = new MovieDAOImpl();
    }

    public static MovieDAOImpl getInstance(Context context) {
        MovieDAOImpl movieDAO = MovieDAOHelper.INSTANCE;
        movieDAO.setContext(context);
        return movieDAO;
    }

    @Override
    public List<MovieItem> getAllMovies() {


        RetrieveMovieDataTask task = new RetrieveMovieDataTask(getBaseFetchURL());
        task.execute();



        //Validation for task
        try {
            if (task.get() != null) {

                List<Map<String, String>> mapList = MovieDataParser.parseJsonMovieDataString(task.get());
                return MovieItemFactory.buildMovieList(mapList);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            isPreferenceChanged = false;
        }

        return null;
    }

    public static void NotifyPreferenceChange() {
        isPreferenceChanged = true;
    }

    public static Boolean getIsPreferenceChanged() {
        return isPreferenceChanged;
    }

    private void setContext(Context context) {
        mContext = context;
    }

/*
* METHOD getBaseFetchURL():
*  gets the proper base fetch url as defined in ConstantsVault by checking the shared preferences.
*
* */
    private URL getBaseFetchURL() {

        String baseURL = null;
        URL url = null;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sortKey = mContext.getString(R.string.sort_preference_key);
        String mostPopular = mContext.getResources().getStringArray(R.array.fetch_movies_list_preference)[0];
        String topRated = mContext.getResources().getStringArray(R.array.fetch_movies_list_preference)[1];
        String currentPref = preferences.getString(sortKey, mostPopular);


        if (currentPref.equals(mostPopular)) {
            baseURL = ConstantsVault.DB_FETCH_POPULAR_BASE_URL;
        }

        if (currentPref.equals(topRated)) {
            baseURL = ConstantsVault.DB_FETCH_TOP_RATED_BASE_URL;
        }

        //Build URL
        final String API_PARAM = "api_key";


        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter(API_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private String fetchData(URL url){
        String movieData = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));


            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            movieData = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return movieData;
    }







    public Observable<String> getMovieListData(String url){

        if(mMovieCache == null) {
            mMovieCache = Observable.create(new Observable.OnSubscribe<Object>() {

                @Override
                public void call(Subscriber<? super Object> subscriber) {

                    try {
                        String data = fetchData(getBaseFetchURL());
                        subscriber.onNext(data);
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }

                }

            }
            }

        return mMovieCache;
    }

}
