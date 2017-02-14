package net.estebanrodriguez.apps.popularmovies.adapters;

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
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDetailFactory;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieItemFactory;
import net.estebanrodriguez.apps.popularmovies.database.DatabaseContract;
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

    private final int MOVIE_CLIP = 0;
    private final int MOVIE_REVIEW = 1;
    private final int MOVIE_ITEM = 2;
    private final int MOVIE_CLIP_HEADER = 4;
    private final int MOVIE_REVIEW_HEADER = 5;

    public static String LOG_TAG = DetailRecyclerViewAdapter.class.getName();

    public DetailRecyclerViewAdapter(MovieItem movieItem, Context context) {
        mMovieItem = movieItem;
        mContext = context;
        mMovieDetailsList = movieItem.getMovieDetails();
        setHasMovieClip();
        setHasMovieReview();
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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

//                final MovieItem mMovieItem = (MovieItem) mMovieDetailsList.get(position);
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




                ImageButton starButton = ((MovieItemDetailsViewHolder) holder).getStarButton();
                if(mMovieItem.isFavorited()){
                    starButton.setSelected(true);
                }

                starButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!mMovieItem.isFavorited()){
                            mMovieItem.setFavorited(true);
                            favoriteMovie();
                            showFavorites();
                        }else
                        {
                            mMovieItem.setFavorited(false);
                        }



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

    public void favoriteMovie(){

        Log.v(LOG_TAG, "Favorited: " + mMovieItem.toString());

        ContentResolver contentResolver = mContext.getContentResolver();

        List<MovieClip> movieClips = mMovieItem.getMovieClips();
        List<MovieReview> movieReviews = mMovieItem.getMovieReviews();


        ContentValues basicDetailsValues = new ContentValues();

        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_MOVIE_ID, mMovieItem.getID());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ORGINAL_TITLE, mMovieItem.getOriginalTitle());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_BACKDROP_PATH, mMovieItem.getBackdropPath());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_IMAGE_FETCH_URL, mMovieItem.getImageFetchURL());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ORIGINAL_LANGUAGE, mMovieItem.getOriginalLanguage());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_OVERVIEW, mMovieItem.getOverview());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_POPULARITY, mMovieItem.getPopularity());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_POSTER_PATH, mMovieItem.getPosterPath());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_RELEASE_DATE, mMovieItem.getFormattedReleaseDate());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_TITLE, mMovieItem.getTitle());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VIDEO, mMovieItem.isVideo());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VOTE_AVERAGE, mMovieItem.getVoteAverage());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VOTE_COUNT, mMovieItem.getVoteCount());

        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ADULT, mMovieItem.isAdult());
        basicDetailsValues.put(DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_FAVORITED, mMovieItem.isFavorited());


        contentResolver.insert(
                DatabaseContract.BasicMovieDetailEntries.CONTENT_URI,
                basicDetailsValues
        );



        for(MovieClip movieClip: movieClips){
            ContentValues movieClipValues = new ContentValues();
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_URL_KEY, movieClip.getKey());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_TYPE, movieClip.getClipType());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_URI, movieClip.getClipURI());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_LANGUAGE_ISO639, movieClip.getLanguageCodeISO639());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_LANGUAGE_ISO3166, movieClip.getLanguagecodeiso3166());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_MOVIE_ID, mMovieItem.getID());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_NAME, movieClip.getName());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_SITE, movieClip.getSite());
            movieClipValues.put(DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_SIZE, movieClip.getSize());

            contentResolver.insert(
                    DatabaseContract.MovieClipEntries.CONTENT_URI,
                    movieClipValues
            );


        }

        for(MovieReview movieReview: movieReviews){
            ContentValues reviewValues = new ContentValues();
            reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_AUTHOR, movieReview.getAuthor());
            reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_CONTENT, movieReview.getContent());
            reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_MOVIE_ID, mMovieItem.getID());
            reviewValues.put(DatabaseContract.MovieReviewEntries.COLUMN_NAME_REVIEW_URL, movieReview.getUrl());

            contentResolver.insert(
                    DatabaseContract.MovieReviewEntries.CONTENT_URI,
                    reviewValues
            );
        }



    }

    //TODO remove after testing
    public void showFavorites(){

        ContentResolver contentResolver = mContext.getContentResolver();

        Cursor cursor = contentResolver.query(DatabaseContract.BasicMovieDetailEntries.CONTENT_URI, null, null, null, null);
        List<MovieItem> movieItems = MovieItemFactory.buildMovieList(cursor);
        for(MovieItem movieItem : movieItems)
        {
            String id = movieItem.getID();
            cursor = contentResolver.query(DatabaseContract.MovieClipEntries.CONTENT_URI, null, id, null, null);
            List<MovieClip> movieClips = MovieDetailFactory.buildMovieClipList(cursor);

            cursor = contentResolver.query(DatabaseContract.MovieReviewEntries.CONTENT_URI, null, id, null, null);
            List<MovieReview> movieReviews = MovieDetailFactory.buildMovieReviewList(cursor);
            Log.v(LOG_TAG, movieItem.toString());
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