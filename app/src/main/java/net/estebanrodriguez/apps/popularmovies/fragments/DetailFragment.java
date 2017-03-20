package net.estebanrodriguez.apps.popularmovies.fragments;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.adapters.DetailAdapter;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;


/**
 * Created by Spoooon on 10/13/2016.
 */
public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    public static final String MOVIE_ITEM = "movie_item";
    private RecyclerView mRecyclerView;
    private DetailAdapter mDetailAdapter;
    private LinearLayout mLinearLayout;
    private TextView mHeader;
    private MovieItem mMovieItem;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mDetailAdapter = new DetailAdapter(getActivity());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.detail_recyclerview_details);
        mRecyclerView.setAdapter(mDetailAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.detail_layout);
        mHeader = (TextView) rootView.findViewById(R.id.detail_textview_title);
        if(savedInstanceState!=null){
            MovieItem movieItem = savedInstanceState.getParcelable(MOVIE_ITEM);
            if(movieItem != null){
                update(movieItem);
            }
        }


        return rootView;
    }

    public void update(MovieItem movieItem){


        mMovieItem = movieItem;
        mDetailAdapter.setDetails(movieItem);
        mDetailAdapter.notifyDataSetChanged();
        mHeader.setText(movieItem.getTitle());
        mLinearLayout.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mMovieItem != null){
            outState.putParcelable(MOVIE_ITEM, mMovieItem);
            Log.e(LOG_TAG, "OnSavedInstanceState called.  Bundling movieItem: " + mMovieItem.getTitle());
        }
    }



}

