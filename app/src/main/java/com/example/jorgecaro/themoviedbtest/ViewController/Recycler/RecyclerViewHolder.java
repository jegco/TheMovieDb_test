package com.example.jorgecaro.themoviedbtest.ViewController.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorgecaro.themoviedbtest.R;

/**
 * Created by jorge caro on 8/19/2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder{

    private TextView movieName, voteAverage;
    private ImageView postedPicture;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        movieName = (TextView) itemView.findViewById(R.id.movieName);
        voteAverage = (TextView) itemView.findViewById(R.id.movieVoteAverage);
        postedPicture = (ImageView) itemView.findViewById(R.id.posterImage);
    }

    public TextView getMovieName() {
        return movieName;
    }

    public TextView getVoteAverage() {
        return voteAverage;
    }

    public ImageView getPostedPicture() {
        return postedPicture;
    }
}
