package com.example.jorgecaro.themoviedbtest.ViewController.Fragments;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jorgecaro.themoviedbtest.ViewController.ListView.ListViewAdapter;
import com.example.jorgecaro.themoviedbtest.MainActivity;
import com.example.jorgecaro.themoviedbtest.Model.MovieDescription;
import com.example.jorgecaro.themoviedbtest.Model.MoviesCollection;
import com.example.jorgecaro.themoviedbtest.Model.Trailer;
import com.example.jorgecaro.themoviedbtest.ConfigConstant.Constants;
import com.example.jorgecaro.themoviedbtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
* Fragment to show selected movie description, this class do a getRequest to search Movie complete data
* but not all data obtained is displayed here, just what this test required.
* */


public class DescriptionFragment extends Fragment {

    private static String urlMovie;
    private View view;
    private int pos = 0;
    private TextView overview, vote_average, budget, release_date;
    private ImageView posted_picture;
    private ListView listViewTrailers;
    private SharedPreferences preferences;
    private ArrayList<Trailer> trailers;

    public static void watchYoutubeVideo(Context context, String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_description, container, false);
        overview = (TextView) view.findViewById(R.id.overview);
        vote_average = (TextView) view.findViewById(R.id.movieVoteAverage);
        budget = (TextView) view.findViewById(R.id.budget);
        release_date = (TextView) view.findViewById(R.id.release_date);
        posted_picture = (ImageView) view.findViewById(R.id.posterImage);
        listViewTrailers = (ListView) view.findViewById(R.id.trailers);
        listViewTrailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                watchYoutubeVideo(getContext(),
                        MoviesCollection.getInstance().getMovies().get(pos).
                                getMovieDescription().getTrailer_url().get(position).getKey());
            }
        });
        preferences = getActivity().getSharedPreferences(Constants.PREFERENCE, Context.MODE_APPEND);
        pos = getArguments().getInt("position");
        trailers = new ArrayList<>();
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(MoviesCollection.getInstance().getMovies().get(pos).getTitle());
        getMovieDescription();
        return view;
    }

    //get request to search a specific movie on themoviedb.org
    public void getMovieDescription() {
        urlMovie = Constants.BASE_MOVIE_URL + MoviesCollection.getInstance().getMovies().get(pos).getId() + "?api_key=" + preferences.getString(Constants.API_KEY, "Api_key") + Constants.PARAMETERS_TO_LIST;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getResources().getString(R.string.loading));
        progressDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlMovie, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        deserializeJSONObject(response);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error on request", Toast.LENGTH_SHORT).show();
                Log.e("getMoviesError", error.toString());
            }
        });
        requestQueue.add(request);
    }

    //deserialize json object and add it to model
    public void deserializeJSONObject(JSONObject object) {
        try {
            MoviesCollection.getInstance().getMovies().get(pos).setMovieDescription(
                    new MovieDescription(object.getString("overview"),
                            object.getString("release_date"),
                            object.getInt("budget")));
            JSONObject videoResults = object.getJSONObject("videos");
            JSONArray results = videoResults.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                trailers.add(new Trailer(
                        results.getJSONObject(i).getString("name"),
                        results.getJSONObject(i).getString("key"),
                        results.getJSONObject(i).getString("id")
                ));
            }
            MoviesCollection.getInstance().getMovies().get(pos).getMovieDescription().setTrailer_url(trailers);
            refresh();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("jsonErrorDes", e.getMessage());
        }
    }

    //put movie info on XML components
    public void refresh() {
        posted_picture.setImageBitmap(MoviesCollection.getInstance().getMovies().get(pos).getPoster_path());
        overview.setText("Overview: " + MoviesCollection.getInstance().getMovies().get(pos).getMovieDescription().getOverview());
        budget.setText("Budget: " + MoviesCollection.getInstance().getMovies().get(pos).getMovieDescription().getBudget());
        release_date.setText("Release date: " + MoviesCollection.getInstance().getMovies().get(pos).getMovieDescription().getRelease_date());
        vote_average.setText("Vote average: " + MoviesCollection.getInstance().getMovies().get(pos).getVote_average());
        listViewTrailers.setAdapter(
                new ListViewAdapter(getContext(),
                        MoviesCollection.getInstance().getMovies().get(pos).getMovieDescription().getTrailer_url()));
    }


}
