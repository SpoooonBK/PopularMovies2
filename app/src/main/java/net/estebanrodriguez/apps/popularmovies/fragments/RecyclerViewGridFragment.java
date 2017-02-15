package net.estebanrodriguez.apps.popularmovies.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.adapters.RecyclerViewGridAdapter;
import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;
import net.estebanrodriguez.apps.popularmovies.data_access.FavoriteManager;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;
import net.estebanrodriguez.apps.popularmovies.data_access.NetworkChecker;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Spoooon on 10/18/2016.
 */
public class RecyclerViewGridFragment extends Fragment {

    private RecyclerViewGridAdapter<MovieItem> mRecyclerViewGridAdapter;
    private List<MovieItem> mMovieItems;
    private RecyclerView mRecyclerView;
    private MovieDAOImpl mMovieDAO;
    private GridLayoutManager mGridLayoutManager;
    private final String LOG_TAG = RecyclerViewGridFragment.class.getSimpleName();



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mMovieDAO = MovieDAOImpl.getInstance(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_grid_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_recycler_view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        String key = getString(R.string.sort_preference_key);
        String mostPopular = getActivity().getResources().getStringArray(R.array.fetch_movies_list_preference)[0];
        String favorites = getActivity().getResources().getStringArray(R.array.fetch_movies_list_preference)[2];

        if(sharedPreferences.getString(key, mostPopular).equals(favorites)){
            updateMovieData(FavoriteManager.setObservable(getActivity().getApplicationContext()));
        }else{
            updateMovieData(setObservable());
        }

        return rootView;
    }


    public void notifyOnPreferenceChanged() {

        if (NetworkChecker.isNetworkAvailable(getActivity())) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

            String key = getString(R.string.sort_preference_key);
            String mostPopular = getActivity().getResources().getStringArray(R.array.fetch_movies_list_preference)[0];
            String favorites = getActivity().getResources().getStringArray(R.array.fetch_movies_list_preference)[2];

            if(sharedPreferences.getString(key, mostPopular).equals(favorites)){
                updateMovieData(FavoriteManager.setObservable(getActivity().getApplicationContext()));
            }else{
                updateMovieData(setObservable());
            }

        } else {
            Toast toast = Toast.makeText(getActivity(), ConstantsVault.NETWORK_ERROR_MESSAGE, Toast.LENGTH_LONG);
            toast.show();
        }

    }


    public Observable<List<MovieItem>> setObservable() {

        Observable<List<MovieItem>> observable = Observable.fromCallable(new Callable<List<MovieItem>>() {
            @Override
            public List<MovieItem> call() throws Exception {
                return mMovieDAO.getAllMovies();
            }
        });

        return observable;
    }

    /* Method updateMovieData used JavaRX to fire off another thread to fetch the movie data
    and updates the UI when the data is ready.
     */
    public void updateMovieData(final Observable<List<MovieItem>> observable) {


        final Subscription movieListSubscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieItem>>() {
                    @Override
                    public void onCompleted() {
                        Log.v(LOG_TAG, "Data retrieved");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<MovieItem> movieItems) {

                        if (mRecyclerViewGridAdapter == null) {
                            mRecyclerViewGridAdapter = new RecyclerViewGridAdapter<MovieItem>(getActivity(), movieItems);
                            mRecyclerView.setAdapter(mRecyclerViewGridAdapter);

                        } else {
                            mRecyclerViewGridAdapter.swapData(movieItems);
                            mRecyclerViewGridAdapter.notifyDataSetChanged();
                        }
                    }

                });

    }


}



