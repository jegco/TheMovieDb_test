package com.example.jorgecaro.themoviedbtest.Controller.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jorgecaro.themoviedbtest.Controller.MainActivity;
import com.example.jorgecaro.themoviedbtest.Model.Movie;
import com.example.jorgecaro.themoviedbtest.Model.MoviesCollection;
import com.example.jorgecaro.themoviedbtest.Preference.Constants;
import com.example.jorgecaro.themoviedbtest.R;
import com.example.jorgecaro.themoviedbtest.Views.Recycler.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainFragment extends Fragment{

    private static String urlMovies;
    private View view;
    private RecyclerView recyclerView;
    private SharedPreferences preferences;
    private RecyclerViewAdapter adapter;
    private Button hide, show;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        preferences = getActivity().getSharedPreferences(Constants.PREFERENCE, Context.MODE_APPEND);
        urlMovies = Constants.BASE_URL + preferences.getString(Constants.API_KEY,"API_KEY") + Constants.PARAMETERS_TO_LIST;
        hide = (Button) view.findViewById(R.id.hide);
        show = (Button) view.findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (MoviesCollection.getInstance().getMovies().isEmpty()) {
            getMovies(getActivity());
        }
        return view;
    }

    //refresh recyclerView with data from movies array
    public void refresh(ArrayList<Movie> movies) {
        adapter = new RecyclerViewAdapter(getContext(), movies, getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    //get request to search popular movies on themoviedb.org
    public void getMovies(final Activity activity) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(activity.getResources().getString(R.string.loading));
        progressDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlMovies, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        deserializeJSONArray(response);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(activity, "Error on request", Toast.LENGTH_SHORT).show();
                Log.e("getMoviesError", error.toString());
            }
        });
        requestQueue.add(request);
    }

    //deserialize json obtained by request and add data to array
    public void deserializeJSONArray(JSONObject object) {
        try {

                JSONArray moviesData = object.getJSONArray("results");
                for (int i = 0; i < moviesData.length(); i++) {
                    JSONObject movieData = moviesData.getJSONObject(i);
                    Movie movie = new Movie(movieData.getInt("id"),
                            movieData.getString("title"),
                            loadBitmap(Constants.IMAGE_BASE_PATH + movieData.getString("poster_path")),
                            movieData.getDouble("vote_average"),
                            movieData.getInt("vote_count")
                    );
                    MoviesCollection.getInstance().getMovies().add(movie);
                }

            refresh(MoviesCollection.getInstance().getMovies());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("jsonErrorDes", e.getMessage());
        }
    }

    //load image from url and convert it on Bitmap to be able to show it in ImageView
    public Bitmap loadBitmap(String url) {
        URL newurl = null;
        try {
            newurl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap mIcon_val = null;
        try {
            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mIcon_val;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.home);
        refresh(MoviesCollection.getInstance().getMovies());
    }

    public void hide(){
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < MoviesCollection.getInstance().getMovies().size(); i++){
            if (MoviesCollection.getInstance().getMovies().get(i).getVote_count()>2000)
                movies.add(MoviesCollection.getInstance().getMovies().get(i));
        }
        refresh(movies);
    }

    public void show(){
        refresh(MoviesCollection.getInstance().getMovies());
    }


}
