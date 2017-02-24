package net.estebanrodriguez.apps.popularmovies.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.adapters.DetailAdapter;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;
import net.estebanrodriguez.apps.popularmovies.model.MovieItemHolder;

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
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.detail_recyclerview_details);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
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
                        DetailAdapter adapter = new DetailAdapter(getActivity());
                        mRecyclerView.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(MovieItemHolder.hasMovieItem()){
            MovieItem movieItem = MovieItemHolder.getMovieItem();
            showDetails(setObservable(movieItem));
        }

    }
}

