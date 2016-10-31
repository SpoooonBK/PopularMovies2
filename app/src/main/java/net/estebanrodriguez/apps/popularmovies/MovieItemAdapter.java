package net.estebanrodriguez.apps.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class MovieItemAdapter<MovieItems> extends RecyclerView.Adapter<MovieItemAdapter.Viewholder> {

    private Context mContext;
    private List<MovieItem> mMovieItems;
    private static final String LOG_TAG = MovieItemAdapter.class.getSimpleName();


    public MovieItemAdapter(Context context, List<MovieItem> movieItems) {
        mContext = context;
        mMovieItems = movieItems;

    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView mImageView;


        public Viewholder(View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.main_gridview_item_image);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(MovieItem.PARCELABLE, getMovieItem(getAdapterPosition()));
            mContext.startActivity(intent);
            getAdapterPosition();
        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_view_movie_item, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(MovieItemAdapter.Viewholder holder, int position) {
        MovieItem movieItem = mMovieItems.get(position);


        Picasso.with(mContext).load(movieItem.getImageFetchURL())
                .placeholder(R.drawable.happy_popcorn)
                .error(R.drawable.sad_popcorn)
                .into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        return mMovieItems.size();
    }

    public void clear(){

        if(mMovieItems != null){
            mMovieItems.clear();
        }

    }

    public void addAll(List<MovieItem> movieItems){

        if(mMovieItems != null){
            mMovieItems.addAll(movieItems);
        }
    }

    public void swapData(List<MovieItem> movieItems){
        clear();
        addAll(movieItems);
    }



    public MovieItem getMovieItem(int index){
        return mMovieItems.get(index);
    }

}
