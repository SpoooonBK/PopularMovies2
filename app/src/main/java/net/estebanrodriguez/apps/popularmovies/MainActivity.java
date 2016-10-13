package net.estebanrodriguez.apps.popularmovies;

import android.database.DataSetObserver;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;
import net.estebanrodriguez.apps.popularmovies.data_access.RetrieveMovieDataTask;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        ImageSizer.setDefaultImageSize(width);
        setContentView(R.layout.activity_main);

        //Dynamically set image sizes


    }

    public static class GridViewActivity extends Fragment{

        private MovieItemAdapter<MovieItem> mMovieItemAdapter;

        @Override
        public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
            List<MovieItem> movieItems = MovieDAOImpl.getInstance().getAllMovies();
            View rootView = inflater.inflate(R.layout.fragment_grid_view, container, false);

            GridView gridView = (GridView) rootView.findViewById(R.id.main_gridview);
            Log.v(LOG_TAG, "gridview reference: " + gridView.toString());
            mMovieItemAdapter = new MovieItemAdapter<MovieItem>(getActivity(), movieItems);
            gridView.setAdapter(mMovieItemAdapter);




            return rootView;
        }
    }
}
