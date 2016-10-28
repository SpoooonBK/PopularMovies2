package net.estebanrodriguez.apps.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;
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

    private MovieItemAdapter<MovieItem> mMovieItemAdapter;
    private List<MovieItem> mMovieItems;
    private RecyclerView mRecyclerView;
    private MovieDAOImpl mMovieDAO;
    private GridLayoutManager mGridLayoutManager;
    private final String LOG_TAG = RecyclerViewGridFragment.class.getSimpleName();




    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = null;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mMovieDAO = MovieDAOImpl.getInstance(getActivity());

        //This line gets gets all the movies using the MovieDAOImpl

        View rootView = inflater.inflate(R.layout.fragment_grid_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_recycler_view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        Observable <List<MovieItem>> movieListObservable = Observable.fromCallable(new Callable<List<MovieItem>>() {
            @Override
            public List<MovieItem> call() throws Exception {
                return mMovieDAO.getAllMovies();
            }
        });

        Subscription movieListSubsctition = movieListObservable.subscribeOn(Schedulers.io())
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
                        mMovieItemAdapter = new MovieItemAdapter<MovieItem>(getActivity(), movieItems);
                        mRecyclerView.setAdapter(mMovieItemAdapter);
                    }
                });






        return rootView;
    }



    public void notifyOnPreferenceChanged() {

        if(NetworkChecker.isNetworkAvailable(getActivity())){
            mMovieItems = mMovieDAO.getAllMovies();
            mMovieItemAdapter.swapData(mMovieItems);
            mMovieItemAdapter.notifyDataSetChanged();
        }
        else{
            Toast toast = Toast.makeText(getActivity(), ConstantsVault.NETWORK_ERROR_MESSAGE, Toast.LENGTH_LONG);
            toast.show();
        }

    }







}
