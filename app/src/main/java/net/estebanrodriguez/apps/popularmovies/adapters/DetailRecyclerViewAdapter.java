package net.estebanrodriguez.apps.popularmovies.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.data_access.FavoriteManager;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDetailFactory;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieItemFactory;
import net.estebanrodriguez.apps.popularmovies.database.DatabaseContract;
import net.estebanrodriguez.apps.popularmovies.fragments.DetailFragment;
import net.estebanrodriguez.apps.popularmovies.fragments.RecyclerViewGridFragment;
import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;
import net.estebanrodriguez.apps.popularmovies.model.MovieReview;


import java.util.List;

/**
 * Created by Spoooon on 11/2/2016.
 */

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mMovieDetailsList;
    private List<MovieClip> mMovieClips;
    private List<MovieReview> mMovieReviews;
    private MovieItem mMovieItem;
    private Context mContext;
    private boolean mHasMovieClip;
    private boolean mHasMovieReview;
    private DetailFragment mFragment;

    private final int MOVIE_CLIP = 0;
    private final int MOVIE_REVIEW = 1;
    private final int MOVIE_ITEM = 2;
    private final int MOVIE_CLIP_HEADER = 4;
    private final int MOVIE_REVIEW_HEADER = 5;

    public static String LOG_TAG = DetailRecyclerViewAdapter.class.getName();

    public DetailRecyclerViewAdapter(MovieItem movieItem, Context context, DetailFragment fragment) {
        mMovieItem = movieItem;
        mContext = context;
        mMovieDetailsList = movieItem.getMovieDetails();
        mFragment = fragment;
        setHasMovieClip();
        setHasMovieReview();
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewtype = holder.getItemViewType();

        switch (viewtype){
            case(MOVIE_CLIP):{
                final MovieClip movieClip = (MovieClip) mMovieDetailsList.get(position);
                ((MovieClipViewholder) holder).getMovieClipTitle().setText(movieClip.getName());
                ((MovieClipViewholder) holder).getPlayButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieClip.getClipURI()));
                        mContext.startActivity(intent);
                    }
                });
                break;
            }

            case (MOVIE_REVIEW): {
                MovieReview movieReview = (MovieReview) mMovieDetailsList.get(position);
                ((MovieReviewViewholder) holder).getAuthorTextView().setText(movieReview.getAuthor());
                ((MovieReviewViewholder) holder).getContentTextView().setText(movieReview.getContent());
                ((MovieReviewViewholder) holder).getURLTextView().setText(movieReview.getUrl());
                break;
            }

            case (MOVIE_ITEM): {


                String releaseDate = mMovieItem.getFormattedReleaseDate();
                String overview = mMovieItem.getOverview();
                Double rating = mMovieItem.getVoteAverage();
                Double popularity = mMovieItem.getPopularity();
                String fetchImage = mMovieItem.getImageFetchURL();



                ((MovieItemDetailsViewHolder) holder).getTextViewPopularity().setText(popularity.toString());
                ((MovieItemDetailsViewHolder) holder).getTextViewRating().setText(rating.toString());
                ((MovieItemDetailsViewHolder) holder).getTextViewReleaseDate().setText(releaseDate);
                ((MovieItemDetailsViewHolder) holder).getTextViewOverview().setText(overview);

                ImageView imageView = ((MovieItemDetailsViewHolder) holder).getImageViewPoster();
                Picasso.with(mContext).load(fetchImage)
                        .placeholder(R.drawable.happy_popcorn)
                        .error(R.drawable.sad_popcorn)
                        .into(imageView);




                final ImageButton starButton = ((MovieItemDetailsViewHolder) holder).getStarButton();
                starButton.setPressed(mMovieItem.isFavorited());

                starButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FavoriteManager favoriteManager = FavoriteManager.getInstance();
                        favoriteManager.toggleFavorite(mMovieItem, mContext);
                    }
                });

                break;

            }
            case (MOVIE_REVIEW_HEADER): {
                ((MovieDetailsHeaderViewHolder) holder).getHeader().setText(R.string.reviews);
                break;
            }

            case (MOVIE_CLIP_HEADER): {
                ((MovieDetailsHeaderViewHolder) holder).getHeader().setText(R.string.trailers);
                break;
            }
        }

    }







    @Override
    public int getItemCount() {

        return (mMovieDetailsList.size());
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case MOVIE_CLIP:
            {
                View movieClipView = inflater.inflate(R.layout.recycler_view_movie_clip_item, parent, false);
                viewHolder = new MovieClipViewholder(movieClipView);}
                break;

            case MOVIE_REVIEW:
            {
                View movieArticleView = inflater.inflate(R.layout.recycler_view_movie_review_item, parent, false);
                viewHolder = new MovieReviewViewholder(movieArticleView);
                break;
            }

            case MOVIE_ITEM:
            {
                View detailView = inflater.inflate(R.layout.recycler_view_basic_details, parent, false);
                viewHolder = new MovieItemDetailsViewHolder(detailView);
                break;
            }

        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {


        if(mMovieDetailsList.get(position) instanceof MovieClip){
            return MOVIE_CLIP;
        }
        if (mMovieDetailsList.get(position) instanceof MovieReview) {
            return MOVIE_REVIEW;
        }
        if (mMovieDetailsList.get(position) instanceof MovieItem) {
            return MOVIE_ITEM;
        }
        return -1;
    }

    private void setHasMovieReview(){
        for(Object movieDetail: mMovieDetailsList){
            mHasMovieReview = movieDetail instanceof MovieReview;
        }

    }

    private void setHasMovieClip(){
        for(Object movieDetail: mMovieDetailsList){
            if(movieDetail instanceof MovieClip){
                mHasMovieClip = true;
            }
            else{
                mHasMovieReview = false;
            }
        }
    }

    public boolean hasMovieClip() {
        return mHasMovieClip;
    }

    public boolean hasMovieReview() {
        return mHasMovieReview;
    }



    public class MovieClipViewholder extends RecyclerView.ViewHolder {
        private ImageButton mPlayButton;
        private TextView mMovieClipTitle;

        public MovieClipViewholder(View itemView) {
            super(itemView);
            mPlayButton = (ImageButton) itemView.findViewById(R.id.button_play);
            mMovieClipTitle = (TextView) itemView.findViewById(R.id.text_view_movie_clip_title);
        }

        public ImageButton getPlayButton() {
            return mPlayButton;
        }

        public void setPlayButton(ImageButton playButton) {
            mPlayButton = playButton;
        }

        public TextView getMovieClipTitle() {
            return mMovieClipTitle;
        }

        public void setMovieClipTitle(TextView movieClipTitle) {
            mMovieClipTitle = movieClipTitle;
        }
    }

    public class MovieReviewViewholder extends RecyclerView.ViewHolder{

        TextView mAuthor;
        TextView mContent;
        TextView mURL;


        public MovieReviewViewholder(View itemView) {
            super(itemView);

            mAuthor = (TextView)itemView.findViewById(R.id.review_text_view_author_name);
            mContent = (TextView)itemView.findViewById(R.id.review_text_view_content);
            mURL = (TextView)itemView.findViewById(R.id.review_text_view_url);
            
        }

        public TextView getAuthorTextView() {
            return mAuthor;
        }

        public void setAuthor(TextView author) {
            mAuthor = author;
        }

        public TextView getContentTextView() {
            return mContent;
        }

        public void setContent(TextView content) {
            mContent = content;
        }

        public TextView getURLTextView() {
            return mURL;
        }

        public void setURL(TextView URL) {
            mURL = URL;
        }
    }

    public class MovieItemDetailsViewHolder  extends RecyclerView.ViewHolder{

        private ImageView mImageViewPoster;
        private TextView mTextViewReleaseDate;
        private TextView mTextViewRating;
        private TextView mTextViewPopularity;
        private TextView mTextViewOverview;
        private ImageButton mStarButton;

        public MovieItemDetailsViewHolder(View itemView) {
            super(itemView);
            mImageViewPoster = (ImageView) itemView.findViewById(R.id.detail_imageview_poster);
            mTextViewReleaseDate = (TextView) itemView.findViewById(R.id.detail_textview_release_date);
            mTextViewRating =(TextView)itemView.findViewById(R.id.detail_textview_vote_average);
            mTextViewPopularity =(TextView)itemView.findViewById(R.id.detail_textview_popularity);
            mTextViewOverview = (TextView)itemView.findViewById(R.id.detail_textview_overview);
            mStarButton = (ImageButton)itemView.findViewById(R.id.detail_imagebutton_save);
        }

        public ImageView getImageViewPoster() {
            return mImageViewPoster;
        }

        public void setImageViewPoster(ImageView imageViewPoster) {
            mImageViewPoster = imageViewPoster;
        }

        public TextView getTextViewReleaseDate() {
            return mTextViewReleaseDate;
        }

        public void setTextViewReleaseDate(TextView textViewReleaseDate) {
            mTextViewReleaseDate = textViewReleaseDate;
        }

        public TextView getTextViewRating() {
            return mTextViewRating;
        }

        public void setTextViewRating(TextView textViewRating) {
            mTextViewRating = textViewRating;
        }

        public TextView getTextViewPopularity() {
            return mTextViewPopularity;
        }

        public void setTextViewPopularity(TextView textViewPopularity) {
            mTextViewPopularity = textViewPopularity;
        }

        public TextView getTextViewOverview() {
            return mTextViewOverview;
        }

        public void setTextViewOverview(TextView textViewOverview) {
            mTextViewOverview = textViewOverview;
        }

        public ImageButton getStarButton() {
            return mStarButton;
        }

        public void setStarButton(ImageButton starButton) {
            mStarButton = starButton;
        }
    }

    public class MovieDetailsHeaderViewHolder extends RecyclerView.ViewHolder{

        private TextView mHeader;

        public MovieDetailsHeaderViewHolder(View itemView) {
            super(itemView);
            mHeader = (TextView) itemView.findViewById(R.id.detail_recyclerview_header);
        }

        public TextView getHeader() {
            return mHeader;
        }

        public void setHeader(TextView header) {
            mHeader = header;
        }
    }

}