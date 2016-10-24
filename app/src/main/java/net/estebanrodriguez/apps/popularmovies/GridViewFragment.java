package net.estebanrodriguez.apps.popularmovies;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;

/**
 * Created by Spoooon on 10/18/2016.
 */
public class GridViewFragment extends Fragment {

    private MovieItemAdapter<MovieItem> mMovieItemAdapter;
    private List<MovieItem> mMovieItems;
    private GridView mGridView;
    private MovieDAOImpl mMovieDAO;
    private final String LOG_TAG = GridViewFragment.class.getSimpleName();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mMovieDAO = MovieDAOImpl.getInstance(getActivity());

        //This line gets gets all the movies using the MovieDAOImpl
        mMovieItems = mMovieDAO.getAllMovies();

        View rootView = inflater.inflate(R.layout.fragment_grid_view, container, false);

        mGridView = (GridView) rootView.findViewById(R.id.main_gridview);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(MovieItem.PARCELABLE, mMovieItemAdapter.getMovieItem(position));
                startActivity(intent);
            }
        });

        mMovieItemAdapter = new MovieItemAdapter<MovieItem>(getActivity(), mMovieItems);
        mGridView.setAdapter(mMovieItemAdapter);

        return rootView;
    }

    public void notifyOnPreferenceChanged() {

        mMovieItemAdapter.clear();
        mMovieItems = mMovieDAO.getAllMovies();
        mMovieItemAdapter.addAll(mMovieItems);
        mMovieItemAdapter.notifyDataSetChanged();
    }
}
