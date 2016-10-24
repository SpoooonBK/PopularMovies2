package net.estebanrodriguez.apps.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;

/**
 * Created by Spoooon on 10/12/2016.
 */

public class MovieItemAdapter<MovieItems> extends ArrayAdapter<MovieItem> {

    private Context mContext;
    private List<MovieItem> mMovieItems;
    private static final String LOG_TAG = MovieItemAdapter.class.getSimpleName();


    public MovieItemAdapter(Context context, List<MovieItem> movieItems) {
        super(context, 0, movieItems);
        mContext = context;
        mMovieItems = movieItems;

    }

    public MovieItem getMovieItem(int index){
        return mMovieItems.get(index);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_view_movie_item, parent, false);
        }

        ImageView imageViewMovieImage = (ImageView) convertView.findViewById(R.id.main_gridview_item_image);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.main_gridview_item_title);
        MovieItem movieItem = mMovieItems.get(position);

        textViewTitle.setText(movieItem.getTitle());
        Picasso.with(mContext).load(movieItem.getImageFetchURL()).into(imageViewMovieImage);

        return convertView;
    }


}
