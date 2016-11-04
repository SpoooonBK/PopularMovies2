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


import java.util.List;

/**
 * Created by Spoooon on 11/2/2016.
 */

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.Viewholder> {

    private List<MovieClip> mMovieClips;
    private Context mContext;

    public DetailRecyclerViewAdapter(List<MovieClip> movieClips, Context context) {
        mMovieClips = movieClips;
        mContext = context;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public ImageButton mPlayButton;
        public TextView mMovieClipTitle;

        public Viewholder(View itemView) {
            super(itemView);
            mPlayButton = (ImageButton) itemView.findViewById(R.id.button_play);
            mMovieClipTitle = (TextView) itemView.findViewById(R.id.text_view_movie_clip_title);
        }

    }


    @Override
    public void onBindViewHolder(DetailRecyclerViewAdapter.Viewholder holder, int position) {
        final MovieClip movieClip = mMovieClips.get(position);;
        holder.mMovieClipTitle.setText(movieClip.getName());
        holder.mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieClip.getClipURI()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieClips.size();
    }



    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_movie_clip_item, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }



}