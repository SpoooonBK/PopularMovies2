package net.estebanrodriguez.apps.popularmovies;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        MovieItem movieItem = (MovieItem) getActivity().getIntent().getExtras().getParcelable(MovieItem.PARCELABLE);

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

        Picasso.with(getActivity()).load(fetchImage).into(posterImageView);
        titleTextView.setText(movieItem.getTitle());
        releaseDateTextView.setText(releaseDate.toString());
        voteAverageTextView.setText(voteAverage.toString());
        popularityTextView.setText(popularity.toString());
        overviewTextView.setText(movieItem.getOverview());

        return rootView;


    }
}
