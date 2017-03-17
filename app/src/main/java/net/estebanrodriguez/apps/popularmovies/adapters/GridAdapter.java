package net.estebanrodriguez.apps.popularmovies.adapters;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;
import net.estebanrodriguez.apps.popularmovies.fragments.DetailFragment;
import net.estebanrodriguez.apps.popularmovies.fragments.GridFragment;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;
import net.estebanrodriguez.apps.popularmovies.utility.FragmentStateHolder;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Spoooon on 10/12/2016.
 *
 * @param <MovieItems> the type parameter
 */
public class GridAdapter<MovieItems> extends RecyclerView.Adapter<GridAdapter.Viewholder> {

    private Context mContext;
    private List<MovieItem> mMovieItems;
    private static final String LOG_TAG = GridAdapter.class.getSimpleName();
    private FragmentManager mFragmentManager;


    /**
     * Instantiates a new Recycler view grid adapter.
     *
     * @param context         the context
     * @param movieItems      the movie items
     * @param fragmentManager
     */
    public GridAdapter(Context context, List<MovieItem> movieItems, FragmentManager fragmentManager) {
        mContext = context;
        mMovieItems = movieItems;
        mFragmentManager = fragmentManager;
    }

    public void showDetails(MovieItem movieItem) {

        DetailFragment detailFragment = (DetailFragment) mFragmentManager.findFragmentById(R.id.fragment_detail);
        GridFragment gridFragment = (GridFragment) mFragmentManager.findFragmentById(R.id.fragment_gridview);

        detailFragment.update(movieItem);

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.hide(gridFragment);
        ft.show(detailFragment);
        ft.setBreadCrumbShortTitle(FragmentStateHolder.DETAILS);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * The type Viewholder.
     */
    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * The M image view.
         */
        public ImageView mImageView;


        /**
         * Instantiates a new Viewholder.
         *
         * @param itemView the item view
         */
        public Viewholder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.main_gridview_item_image);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            MovieItem movieItem = getMovieItem(getAdapterPosition());


            Observable<MovieItem> observable = MovieDAOImpl.getInstance(mContext).getMovieDetailsObservable(movieItem);
            Subscription subscription = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<MovieItem>() {
                        @Override
                        public void onCompleted() {


                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(MovieItem movieItem) {

                            showDetails(movieItem);

                        }
                    });


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
    public void onBindViewHolder(GridAdapter.Viewholder holder, int position) {
        MovieItem movieItem = mMovieItems.get(position);
        if(movieItem.isFavorited()){
            holder.mImageView.setBackgroundColor(Color.YELLOW);
        }


        Picasso.with(mContext).load(movieItem.getImageFetchURL())
                .placeholder(R.drawable.happy_popcorn)
                .error(R.drawable.sad_popcorn)
                .into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        return mMovieItems.size();
    }

    /**
     * Clear.
     */
    public void clear() {

        if (mMovieItems != null) {
            mMovieItems.clear();
        }

    }

    /**
     * Add all.
     *
     * @param movieItems the movie items
     */
    public void addAll(List<MovieItem> movieItems) {

        if (mMovieItems != null) {
            mMovieItems.addAll(movieItems);
        }
    }

    /**
     * Swap data.
     *
     * @param movieItems the movie items
     */
    public void swapData(List<MovieItem> movieItems) {
        clear();
        addAll(movieItems);
    }


    /**
     * Gets movie item.
     *
     * @param index the index
     * @return the movie item
     */
    public MovieItem getMovieItem(int index) {
        return mMovieItems.get(index);
    }



}


