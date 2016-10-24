package net.estebanrodriguez.apps.popularmovies.data_access;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Spoooon on 10/9/2016.
 */

public class MovieDAOImpl implements MovieDAO {

    private static Boolean isPreferenceChanged = null;
    private Context mContext;


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

    public void setContext(Context context) {
        mContext = context;
    }

/*
* METHOD getBaseFetchURL():
*  gets the proper base fetch url as defined in ConstantsVault by checking the shared preferences.
*
* */
    private String getBaseFetchURL() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String sortKey = mContext.getString(R.string.sort_preference_key);
        String mostPopular = mContext.getResources().getStringArray(R.array.fetch_movies_list_preference)[0];
        String topRated = mContext.getResources().getStringArray(R.array.fetch_movies_list_preference)[1];
        String currentPref = preferences.getString(sortKey, mostPopular);


        if (currentPref.equals(mostPopular)) {
            return ConstantsVault.DB_FETCH_POPULAR_BASE_URL;
        }

        if (currentPref.equals(topRated)) {
            return ConstantsVault.DB_FETCH_TOP_RATED_BASE_URL;
        }

        return null;
    }
}
