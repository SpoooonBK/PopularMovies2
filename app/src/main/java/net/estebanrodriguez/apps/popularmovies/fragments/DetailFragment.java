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
import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.Date;


/**
 * Created by Spoooon on 10/13/2016.
 */
public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);


        Intent intent = getActivity().getIntent();

        MovieItem movieItem = (MovieItem) intent.getExtras().getParcelable(ConstantsVault.MOVIE_ITEM_PARCELABLE);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.detail_recyclerview_clips);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(movieItem.getMovieClips(), getActivity());
        recyclerView.setAdapter(adapter);


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
}
