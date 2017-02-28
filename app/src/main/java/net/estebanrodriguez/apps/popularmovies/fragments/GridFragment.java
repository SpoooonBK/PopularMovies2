package net.estebanrodriguez.apps.popularmovies.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.adapters.GridAdapter;
import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;
import net.estebanrodriguez.apps.popularmovies.data_access.FavoriteManager;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;
import net.estebanrodriguez.apps.popularmovies.data_access.NetworkChecker;
import net.estebanrodriguez.apps.popularmovies.interfaces.listeners.FavoritesUpdatedListener;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The type Recycler view grid fragment.
 */
public class GridFragment extends Fragment {

    private GridAdapter<MovieItem> mGridAdapter;
    private RecyclerView mRecyclerView;
    private MovieDAOImpl mMovieDAO;
    private GridLayoutManager mGridLayoutManager;
    private final String LOG_TAG = GridFragment.class.getSimpleName();
    private FavoriteManager mFavoriteManager;
    private TextView mHeader;
    private FragmentManager mFragmentManager;


    //Preferences Strings
    private String FAVORITES;
    private String MOST_POPULAR;



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mMovieDAO = MovieDAOImpl.getInstance(getActivity());
        mFavoriteManager = FavoriteManager.getInstance();
        mFragmentManager = getFragmentManager();

        View rootView = inflater.inflate(R.layout.fragment_grid_view, container, false);




        mHeader = (TextView) rootView.findViewById(R.id.main_gridview_header_text_view);
        mHeader.setText(getSortByPreference());


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_recycler_view);
        int spanCount = 2;
        mGridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        updateMovieData();


        //Set favorites updated listener
        mFavoriteManager.setFavoritesUpdatedListener(new FavoritesUpdatedListener() {
            @Override
            public void onFavoritesUpdated() {
                updateMovieData();
            }
        });


        //Set SortBy Constants
        FAVORITES = getActivity().getResources().getStringArray(R.array.fetch_movies_list_preference)[2];
        MOST_POPULAR = getActivity().getResources().getStringArray(R.array.fetch_movies_list_preference)[0];

        return rootView;
    }


    /**
     * Notify on preference changed. Switches to the correct movieData set
     */
    public void notifyOnPreferenceChanged() {

        if (NetworkChecker.isNetworkAvailable(getActivity())) {


            String sortByPreference = getSortByPreference();


            mHeader.setText(sortByPreference);

            updateMovieData();

        } else {
            Toast toast = Toast.makeText(getActivity(), ConstantsVault.NETWORK_ERROR_MESSAGE, Toast.LENGTH_LONG);
            toast.show();
        }

    }


    /**
     * Sets observable for movie fetches.
     *
     * @return the observable
     */
    public Observable<List<MovieItem>> setObservable() {

        Observable<List<MovieItem>> observable = Observable.fromCallable(new Callable<List<MovieItem>>() {
            @Override
            public List<MovieItem> call() throws Exception {
                return mMovieDAO.getAllMovies();
            }
        });

        return observable;
    }

    /**
     * Update movie data.
     *
     *
     */
/* Method updateMovieData used JavaRX to subscribe to io thread fetching the movie data
    and updates the UI when the data is ready.
     */
    public void updateMovieData() {

        Observable<List<MovieItem>> observable = null;

        if (getSortByPreference().equals(FAVORITES)) {
            observable = mFavoriteManager.setObservable(getActivity().getApplicationContext());
        } else{
            observable = MovieDAOImpl.getInstance(getActivity().getApplicationContext()).getMovieItemsObservable();
        }





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


                        if (mGridAdapter == null) {
                            mGridAdapter = new GridAdapter<MovieItem>(getActivity(), movieItems,mFragmentManager );
                            mRecyclerView.setAdapter(mGridAdapter);

                        } else {
                            mGridAdapter.swapData(movieItems);
                            mGridAdapter.notifyDataSetChanged();
                        }

                    }

                });

    }


    public String getSortByPreference(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String key = getString(R.string.sort_preference_key);
        return sharedPreferences.getString(key, MOST_POPULAR);
    }


}



