package net.estebanrodriguez.apps.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMovieItems = MovieDAOImpl.getInstance().getAllMovies();
        View rootView = inflater.inflate(R.layout.fragment_grid_view, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.main_gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(MovieItem.PARCELABLE, mMovieItemAdapter.getMovieItem(position));
                    startActivity(intent);
            }
        });

        mMovieItemAdapter = new MovieItemAdapter<MovieItem>(getActivity(), mMovieItems);
        gridView.setAdapter(mMovieItemAdapter);

        return rootView;
    }
}
