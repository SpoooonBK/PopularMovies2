package net.estebanrodriguez.apps.popularmovies.data_access;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Spoooon on 10/9/2016.
 */

public class MovieDAOImpl implements MovieDAO {

    private static Boolean isPreferenceChanged = null;
    private boolean mIsCallBackTriggered = false;
    private Context mContext;
    private List<String> mMovieData = new ArrayList<>();
    private final String LOG_TAG = MovieDAOImpl.class.getSimpleName();

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

        List<Map<String, String>> mapList = MovieDataParser.parseJsonMovieDataString(fetchData(getBaseFetchURL()));
        isPreferenceChanged = false;
        mMovieData.clear();
        return MovieItemFactory.buildMovieList(mapList);

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

    private String fetchData(URL url) {
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


    public void getMovieListData() {


        Observable<String> movieDataObservable = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {

                return fetchData(getBaseFetchURL());
            }
        });

        Subscription movieDataSubscription = movieDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.v(LOG_TAG, s);
                        mMovieData.add(s);
                    }
                });

        if (movieDataSubscription != null && !movieDataSubscription.isUnsubscribed()) {
            movieDataSubscription.unsubscribe();
        }

    }

    public void callBack(){

    }

}
