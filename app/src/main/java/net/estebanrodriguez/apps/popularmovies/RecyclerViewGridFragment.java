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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mMovieDAO = MovieDAOImpl.getInstance(getActivity());

        //This line gets gets all the movies using the MovieDAOImpl

            mMovieItems = mMovieDAO.getAllMovies();





        View rootView = inflater.inflate(R.layout.fragment_grid_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_recycler_view);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mMovieItemAdapter = new MovieItemAdapter<MovieItem>(getActivity(), mMovieItems);
        mRecyclerView.setAdapter(mMovieItemAdapter);

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
