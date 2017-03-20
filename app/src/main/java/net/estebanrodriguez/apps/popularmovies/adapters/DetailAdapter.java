package net.estebanrodriguez.apps.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.local_database.FavoriteManager;
import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;
import net.estebanrodriguez.apps.popularmovies.model.MovieReview;
import net.estebanrodriguez.apps.popularmovies.utility.SubscriptionHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Spoooon on 11/2/2016.
 */
public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mMovieDetailsList;
    private MovieItem mMovieItem;
    private List<MovieReview> mMovieReviews;
    private List<MovieClip> mMovieClips;

    private Context mContext;

    private final int MOVIE_CLIP = 0;
    private final int MOVIE_REVIEW = 1;
    private final int MOVIE_ITEM = 2;
    private final int MOVIE_CLIP_HEADER = 4;
    private final int MOVIE_REVIEW_HEADER = 5;

    /**
     * The constant LOG_TAG.
     */
    public static String LOG_TAG = DetailAdapter.class.getName();

    /**
     * Instantiates a new Detail recycler view adapter.
     * @param context   the context
     */
    public DetailAdapter(Context context) {
        mMovieDetailsList = new ArrayList<>();
        mContext = context;
    }


    public void setDetails(MovieItem movieItem) {

        mMovieDetailsList.clear();

        mMovieItem = movieItem;
        mMovieClips = movieItem.getMovieClips();
        mMovieReviews = movieItem.getMovieReviews();

        mMovieDetailsList.add(movieItem);
        for(MovieClip movieClip: mMovieClips){
            mMovieDetailsList.add(movieClip);
        }

        for(MovieReview movieReview: mMovieReviews) {
            mMovieDetailsList.add(movieReview);
        }
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




                final ToggleButton starButton = ((MovieItemDetailsViewHolder) holder).getStarButton();


                starButton.setChecked(mMovieItem.isFavorited());


                //Set starButton to toggle(insert, delete) from local db using RxJava
                starButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        final Observable<Integer> togglerObservable = Observable.fromCallable(new Callable<Integer>() {
                            @Override
                            public Integer call() throws Exception {
                                return FavoriteManager.getInstance().toggleFavorite(mMovieItem);
                            }
                        });

                        final Subscription subscription = togglerObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Integer>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Integer integer) {

                                        starButton.setChecked(mMovieItem.isFavorited());

                                        String notification;
                                        if(mMovieItem.isFavorited()){
                                            notification = mContext.getString(R.string.favorited);
                                        }else notification = mContext.getString(R.string.unfavorited);
                                        Toast toast = Toast.makeText(mContext,mMovieItem.getTitle() + " " + notification,Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });
                        SubscriptionHolder.holdSubscription(subscription);

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






    /**
     * The type Movie clip viewholder.
     */
    public class MovieClipViewholder extends RecyclerView.ViewHolder {
        private ImageButton mPlayButton;
        private TextView mMovieClipTitle;

        /**
         * Instantiates a new Movie clip viewholder.
         *
         * @param itemView the item view
         */
        public MovieClipViewholder(View itemView) {
            super(itemView);
            mPlayButton = (ImageButton) itemView.findViewById(R.id.button_play);
            mMovieClipTitle = (TextView) itemView.findViewById(R.id.text_view_movie_clip_title);
        }

        /**
         * Gets play button.
         *
         * @return the play button
         */
        public ImageButton getPlayButton() {
            return mPlayButton;
        }

        /**
         * Sets play button.
         *
         * @param playButton the play button
         */
        public void setPlayButton(ImageButton playButton) {
            mPlayButton = playButton;
        }

        /**
         * Gets movie clip title.
         *
         * @return the movie clip title
         */
        public TextView getMovieClipTitle() {
            return mMovieClipTitle;
        }

        /**
         * Sets movie clip title.
         *
         * @param movieClipTitle the movie clip title
         */
        public void setMovieClipTitle(TextView movieClipTitle) {
            mMovieClipTitle = movieClipTitle;
        }
    }

    /**
     * The type Movie review viewholder.
     */
    public class MovieReviewViewholder extends RecyclerView.ViewHolder{

        /**
         * The M author.
         */
        TextView mAuthor;
        /**
         * The M content.
         */
        TextView mContent;
        /**
         * The M url.
         */
        TextView mURL;


        /**
         * Instantiates a new Movie review viewholder.
         *
         * @param itemView the item view
         */
        public MovieReviewViewholder(View itemView) {
            super(itemView);

            mAuthor = (TextView)itemView.findViewById(R.id.review_text_view_author_name);
            mContent = (TextView)itemView.findViewById(R.id.review_text_view_content);
            mURL = (TextView)itemView.findViewById(R.id.review_text_view_url);
            
        }

        /**
         * Gets author text view.
         *
         * @return the author text view
         */
        public TextView getAuthorTextView() {
            return mAuthor;
        }

        /**
         * Sets author.
         *
         * @param author the author
         */
        public void setAuthor(TextView author) {
            mAuthor = author;
        }

        /**
         * Gets content text view.
         *
         * @return the content text view
         */
        public TextView getContentTextView() {
            return mContent;
        }

        /**
         * Sets content.
         *
         * @param content the content
         */
        public void setContent(TextView content) {
            mContent = content;
        }

        /**
         * Gets url text view.
         *
         * @return the url text view
         */
        public TextView getURLTextView() {
            return mURL;
        }

        /**
         * Sets url.
         *
         * @param URL the url
         */
        public void setURL(TextView URL) {
            mURL = URL;
        }
    }

    /**
     * The type Movie item details view holder.
     */
    public class MovieItemDetailsViewHolder  extends RecyclerView.ViewHolder{

        private ImageView mImageViewPoster;
        private TextView mTextViewReleaseDate;
        private TextView mTextViewRating;
        private TextView mTextViewPopularity;
        private TextView mTextViewOverview;
        private ToggleButton mStarButton;

        /**
         * Instantiates a new Movie item details view holder.
         *
         * @param itemView the item view
         */
        public MovieItemDetailsViewHolder(View itemView) {
            super(itemView);
            mImageViewPoster = (ImageView) itemView.findViewById(R.id.detail_imageview_poster);
            mTextViewReleaseDate = (TextView) itemView.findViewById(R.id.detail_textview_release_date);
            mTextViewRating =(TextView)itemView.findViewById(R.id.detail_textview_vote_average);
            mTextViewPopularity =(TextView)itemView.findViewById(R.id.detail_textview_popularity);
            mTextViewOverview = (TextView)itemView.findViewById(R.id.detail_textview_overview);
            mStarButton = (ToggleButton)itemView.findViewById(R.id.detail_togglebutton_save);
        }

        /**
         * Gets image view poster.
         *
         * @return the image view poster
         */
        public ImageView getImageViewPoster() {
            return mImageViewPoster;
        }

        /**
         * Sets image view poster.
         *
         * @param imageViewPoster the image view poster
         */
        public void setImageViewPoster(ImageView imageViewPoster) {
            mImageViewPoster = imageViewPoster;
        }

        /**
         * Gets text view release date.
         *
         * @return the text view release date
         */
        public TextView getTextViewReleaseDate() {
            return mTextViewReleaseDate;
        }

        /**
         * Sets text view release date.
         *
         * @param textViewReleaseDate the text view release date
         */
        public void setTextViewReleaseDate(TextView textViewReleaseDate) {
            mTextViewReleaseDate = textViewReleaseDate;
        }

        /**
         * Gets text view rating.
         *
         * @return the text view rating
         */
        public TextView getTextViewRating() {
            return mTextViewRating;
        }

        /**
         * Sets text view rating.
         *
         * @param textViewRating the text view rating
         */
        public void setTextViewRating(TextView textViewRating) {
            mTextViewRating = textViewRating;
        }

        /**
         * Gets text view popularity.
         *
         * @return the text view popularity
         */
        public TextView getTextViewPopularity() {
            return mTextViewPopularity;
        }

        /**
         * Sets text view popularity.
         *
         * @param textViewPopularity the text view popularity
         */
        public void setTextViewPopularity(TextView textViewPopularity) {
            mTextViewPopularity = textViewPopularity;
        }

        /**
         * Gets text view overview.
         *
         * @return the text view overview
         */
        public TextView getTextViewOverview() {
            return mTextViewOverview;
        }

        /**
         * Sets text view overview.
         *
         * @param textViewOverview the text view overview
         */
        public void setTextViewOverview(TextView textViewOverview) {
            mTextViewOverview = textViewOverview;
        }

        /**
         * Gets star button.
         *
         * @return the star button
         */
        public ToggleButton getStarButton() {
            return mStarButton;
        }

        /**
         * Sets star button.
         *
         * @param starButton the star button
         */
        public void setStarButton(ToggleButton starButton) {
            mStarButton = starButton;
        }
    }

    /**
     * The type Movie details header view holder.
     */
    public class MovieDetailsHeaderViewHolder extends RecyclerView.ViewHolder{

        private TextView mHeader;

        /**
         * Instantiates a new Movie details header view holder.
         *
         * @param itemView the item view
         */
        public MovieDetailsHeaderViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * Gets header.
         *
         * @return the header
         */
        public TextView getHeader() {
            return mHeader;
        }

        /**
         * Sets header.
         *
         * @param header the header
         */
        public void setHeader(TextView header) {
            mHeader = header;
        }
    }





}