package com.example.jorgecaro.themoviedbtest.ViewController.ListView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jorgecaro.themoviedbtest.Model.Trailer;
import com.example.jorgecaro.themoviedbtest.R;

import java.util.ArrayList;

/**
 * Created by jorge caro on 11/24/2017.
 */


public class ListViewAdapter extends ArrayAdapter<Trailer> {

    private ArrayList<Trailer> trailers;
    private Context context;

    public ListViewAdapter(@NonNull Context context, ArrayList<Trailer> trailers) {
        super(context, R.layout.list_template);
        this.trailers = trailers;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_template, null);
        TextView trailerName = (TextView) view.findViewById(R.id.trailerName);
        trailerName.setText(trailers.get(position).getName());
        return view;
    }

    @Override
    public int getCount() {
        return trailers.size();
    }
}
