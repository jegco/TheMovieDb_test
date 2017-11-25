package com.example.jorgecaro.themoviedbtest.Recycler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorgecaro.themoviedbtest.Fragments.DescriptionFragment;
import com.example.jorgecaro.themoviedbtest.MainActivity;
import com.example.jorgecaro.themoviedbtest.Model.Movie;
import com.example.jorgecaro.themoviedbtest.R;

import java.util.ArrayList;

/**
 * Created by jorge caro on 11/24/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Movie> movieList;
    Activity activity;


    public RecyclerViewAdapter(Context context, ArrayList<Movie> movieList, Activity activity) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.movieList = movieList;
        this.activity = activity;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.getMovieName().setText(movieList.get(position).getTitle());
        holder.getVoteAverage().setText(context.getResources().getString(R.string.vote_average) + ": " + movieList.get(position).getVote_average());
        holder.getPostedPicture().setImageBitmap(movieList.get(position).getPoster_path());
        holder.getPostedPicture().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("position", position);
                DescriptionFragment fragment = new DescriptionFragment();
                fragment.setArguments(args);
                ((MainActivity) activity).getSupportFragmentManager().
                        beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
