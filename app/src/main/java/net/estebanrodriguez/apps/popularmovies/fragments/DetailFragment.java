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
import android.widget.TextView;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.adapters.DetailRecyclerViewAdapter;
import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;
import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

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

        MovieItem movieItem = intent.getExtras().getParcelable(ConstantsVault.MOVIE_ITEM_PARCELABLE);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.detail_recyclerview_details);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        showDetails(setObservable(movieItem));


        TextView titleTextView = (TextView) rootView.findViewById(R.id.detail_textview_title);
        titleTextView.setText(movieItem.getTitle());


        for(MovieClip movieClip: movieItem.getMovieClips()){
            Log.v(LOG_TAG, movieClip.getName());
        }


        return rootView;

    }


    /**
     * Set observable observable.
     *
     * @param movieItem the movie item
     * @return the observable
     */
    public Observable<MovieItem> setObservable(final MovieItem movieItem){

        Observable<MovieItem> observable = Observable.fromCallable(new Callable<MovieItem>() {
            @Override
            public MovieItem call() throws Exception {

                return MovieDAOImpl.getInstance(getActivity()).completeMovieDetails(movieItem);
            }
        });

        return observable;
    }

    /**
     * Show details.
     *
     * @param observable the observable
     */
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
                        DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(movieItem, getActivity());
                        mRecyclerView.setAdapter(adapter);
                    }
                });
    }

    }

