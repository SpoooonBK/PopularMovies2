package net.estebanrodriguez.apps.popularmovies.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.adapters.DetailRecyclerViewAdapter;
import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;
import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieDetail;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Spoooon on 10/13/2016.
 */
public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);


        Intent intent = getActivity().getIntent();

        MovieItem movieItem = (MovieItem) intent.getExtras().getParcelable(ConstantsVault.MOVIE_ITEM_PARCELABLE);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.detail_recyclerview_clips);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        showDetails(setObservable(movieItem));


        String title = movieItem.getTitle();
        Date releaseDate = movieItem.getReleaseDate();
        String overview = movieItem.getOverview();
        Double voteAverage = movieItem.getVoteAverage();
        Double popularity = movieItem.getPopularity();
        String fetchImage = movieItem.getImageFetchURL();

        ImageView posterImageView = (ImageView) rootView.findViewById(R.id.detail_imageview_poster);
        TextView titleTextView = (TextView) rootView.findViewById(R.id.detail_textview_title);
        TextView releaseDateTextView = (TextView) rootView.findViewById(R.id.detail_textview_release_date);
        TextView voteAverageTextView = (TextView) rootView.findViewById(R.id.detail_textview_vote_average);
        TextView popularityTextView = (TextView) rootView.findViewById(R.id.detail_textview_popularity);
        TextView overviewTextView = (TextView) rootView.findViewById(R.id.detail_textview_overview);

        Picasso.with(getActivity()).load(fetchImage)
                .placeholder(R.drawable.happy_popcorn)
                .error(R.drawable.sad_popcorn)
                .into(posterImageView);
        titleTextView.setText(title);
        releaseDateTextView.setText(releaseDate.toString());
        voteAverageTextView.setText(voteAverage.toString());
        popularityTextView.setText(popularity.toString());
        overviewTextView.setText(overview);




        for(MovieClip movieClip: movieItem.getMovieClips()){
            Log.v(LOG_TAG, movieClip.getName());
        }


        return rootView;

    }


    public Observable<MovieItem> setObservable(final MovieItem movieItem){

        Observable<MovieItem> observable = Observable.fromCallable(new Callable<MovieItem>() {
            @Override
            public MovieItem call() throws Exception {

                return MovieDAOImpl.getInstance(getActivity()).completeMovieDetails(movieItem);
            }
        });

        return observable;
    }

    public void showDetails(Observable<MovieItem> observable) {
        Subscription movieDetailSubscription = observable.subscribeOn(Schedulers.io())
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
                        List<MovieDetail> movieDetails = movieItem.getMovieDetails();
                        DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(movieDetails, getActivity());
                        mRecyclerView.setAdapter(adapter);

                    }
                });
    }
    }

