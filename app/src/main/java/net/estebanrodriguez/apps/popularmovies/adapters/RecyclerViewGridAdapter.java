package net.estebanrodriguez.apps.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.activities.DetailActivity;
import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;
import net.estebanrodriguez.apps.popularmovies.model.MovieDetail;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Spoooon on 10/12/2016.
 */

public class RecyclerViewGridAdapter<MovieItems> extends RecyclerView.Adapter<RecyclerViewGridAdapter.Viewholder> {

    private Context mContext;
    private List<MovieItem> mMovieItems;
    private static final String LOG_TAG = RecyclerViewGridAdapter.class.getSimpleName();


    public RecyclerViewGridAdapter(Context context, List<MovieItem> movieItems) {
        mContext = context;
        mMovieItems = movieItems;

    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;


        public Viewholder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.main_gridview_item_image);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            MovieItem movieItem = getMovieItem(getAdapterPosition());
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(ConstantsVault.MOVIE_ITEM_PARCELABLE, movieItem);
            mContext.startActivity(intent);
        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_grid_movie_item, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewGridAdapter.Viewholder holder, int position) {
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

    public void clear() {

        if (mMovieItems != null) {
            mMovieItems.clear();
        }

    }

    public void addAll(List<MovieItem> movieItems) {

        if (mMovieItems != null) {
            mMovieItems.addAll(movieItems);
        }
    }

    public void swapData(List<MovieItem> movieItems) {
        clear();
        addAll(movieItems);
    }


    public MovieItem getMovieItem(int index) {
        return mMovieItems.get(index);
    }






}


