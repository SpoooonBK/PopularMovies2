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

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieDetail;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;
import net.estebanrodriguez.apps.popularmovies.model.MovieReview;


import java.util.List;

/**
 * Created by Spoooon on 11/2/2016.
 */

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mMovieDetails;
    private List<MovieClip> mMovieClips;
    private List<MovieReview> mMovieReviews;
    private MovieItem mMovieItem;
    private Context mContext;

    private final int MOVIE_CLIP = 0;
    private final int MOVIE_REVIEW = 1;

    public DetailRecyclerViewAdapter(MovieItem movieItem, Context context) {
        mMovieItem = movieItem;
        mContext = context;
        mMovieDetails = movieItem.getMovieDetails();
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewtype = holder.getItemViewType();

        switch (viewtype){
            case(MOVIE_CLIP):{
                final MovieClip movieClip = (MovieClip) mMovieDetails.get(position);
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
                MovieReview movieReview = (MovieReview)mMovieDetails.get(position);
                ((MovieReviewViewholder) holder).getAuthorTextView().setText(movieReview.getAuthor());
                ((MovieReviewViewholder) holder).getContentTextView().setText(movieReview.getContent());
                ((MovieReviewViewholder) holder).getURLTextView().setText(movieReview.getUrl());
                break;
            }
        }

    }



    @Override
    public int getItemCount() {
        return mMovieDetails.size();
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

        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(mMovieDetails.get(position) instanceof MovieClip){
            return MOVIE_CLIP;
        }
        if (mMovieDetails.get(position) instanceof MovieReview) {
            return MOVIE_REVIEW;
        }
        return -1;
    }

//    @Override
//    public void onBindViewHolder(MovieClipViewholder holder, int position) {
//        final MovieClip movieClip = mMovieClips.get(position);;
//        holder.mMovieClipTitle.setText(movieClip.getName());
//        holder.mPlayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieClip.getClipURI()));
//                mContext.startActivity(intent);
//            }
//        });
//    }

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

        ImageView mImageViewPoster;
        TextView mTextViewReleaseDate;
        TextView mTextViewRating;
        TextView mTextViewPopularity;

        public MovieItemDetailsViewHolder(View itemView) {
            super(itemView);
            mImageViewPoster = (ImageView) itemView.findViewById(R.id.detail_imageview_poster);
            mTextViewReleaseDate = (TextView) itemView.findViewById(R.id.detail_textview_release_date);
        }
    }

}