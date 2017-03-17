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
import net.estebanrodriguez.apps.popularmovies.local_database.FavoriteManager;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;
import net.estebanrodriguez.apps.popularmovies.utility.NetworkChecker;
import net.estebanrodriguez.apps.popularmovies.interfaces.listeners.FavoritesUpdatedListener;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;
import net.estebanrodriguez.apps.popularmovies.utility.SubscriptionHolder;

import java.util.ArrayList;
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

    private static String MOVIE_ITEM_LIST = "movie_item_list";

    private GridAdapter<MovieItem> mGridAdapter;
    private RecyclerView mRecyclerView;
    private MovieDAOImpl mMovieDAO;
    private GridLayoutManager mGridLayoutManager;
    private final String LOG_TAG = GridFragment.class.getSimpleName();
    private FavoriteManager mFavoriteManager;
    private TextView mHeader;
    private FragmentManager mFragmentManager;
    private List<MovieItem> mMovieItems;


    //Preferences Strings
    private String FAVORITES;
    private String MOST_POPULAR;

    public void setMovieItems(List<MovieItem> movieItems) {
        mMovieItems = movieItems;
    }
    

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Set SortBy Strings
        FAVORITES = getActivity().getResources().getStringArray(R.array.fetch_movies_list_preference)[2];
        MOST_POPULAR = getActivity().getResources().getStringArray(R.array.fetch_movies_list_preference)[0];


        mMovieDAO = MovieDAOImpl.getInstance(getActivity());
        mFavoriteManager = FavoriteManager.getInstance();
        mFragmentManager = getFragmentManager();

        //Favorites updated listner required to display the correct data in gridFragment when a movieItem is favorted or unfavorited
        mFavoriteManager.setFavoritesUpdatedListener(new FavoritesUpdatedListener() {
            @Override
            public void onFavoritesUpdated() {
                updateMovieData();
            }
        });


        View rootView = inflater.inflate(R.layout.fragment_grid_view, container, false);

        mHeader = (TextView) rootView.findViewById(R.id.main_gridview_header_text_view);
        mHeader.setText(getSortByPreference());

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_recycler_view);
        int spanCount = 2;
        mGridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

// Checks to see if there is already movieData saved in Bundle. So that network is not called on orientation change
        if (savedInstanceState != null) {
            mMovieItems = savedInstanceState.getParcelableArrayList(MOVIE_ITEM_LIST);
            updateAdapter(mMovieItems);
        } else {
            updateMovieData();
        }


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
     */
/* Method updateMovieData used JavaRX to subscribe to io thread fetching the movie data
    and updates the UI when the data is ready.
     */
    public void updateMovieData() {

        Observable<List<MovieItem>> observable = null;

        if (getSortByPreference().equals(FAVORITES) || !NetworkChecker.isNetworkAvailable(getActivity())) {
            observable = mFavoriteManager.setObservable(getActivity().getApplicationContext());
        } else {
            observable = MovieDAOImpl.getInstance(getActivity().getApplicationContext()).getMovieItemsObservable();
        }


        final Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MovieItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<MovieItem> movieItems) {

                        setMovieItems(movieItems);

                        updateAdapter(mMovieItems);

                    }

                });
        SubscriptionHolder.holdSubscription(subscription);


    }


    public String getSortByPreference() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String key = getString(R.string.sort_preference_key);
        return sharedPreferences.getString(key, MOST_POPULAR);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mMovieItems != null) {
            ArrayList<MovieItem> parcelableList = new ArrayList<>();
            for (MovieItem movieItem : mMovieItems) {
                parcelableList.add(movieItem);
            }
            outState.putParcelableArrayList(MOVIE_ITEM_LIST, parcelableList);
        }

    }

    public void updateAdapter(List<MovieItem> movieItems) {


        if (mGridAdapter == null) {
            mGridAdapter = new GridAdapter<MovieItem>(getActivity(), movieItems, mFragmentManager);
            mRecyclerView.setAdapter(mGridAdapter);

        } else {
            mGridAdapter.swapData(movieItems);
            mGridAdapter.notifyDataSetChanged();
        }

    }

}








