package com.example.jorgecaro.themoviedbtest.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jorgecaro.themoviedbtest.MainActivity;
import com.example.jorgecaro.themoviedbtest.Model.MovieDescription;
import com.example.jorgecaro.themoviedbtest.Model.MoviesCollection;
import com.example.jorgecaro.themoviedbtest.Preference.Constants;
import com.example.jorgecaro.themoviedbtest.R;

import org.json.JSONException;
import org.json.JSONObject;

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
    private SharedPreferences preferences;


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
        preferences = getActivity().getSharedPreferences(Constants.PREFERENCE, Context.MODE_APPEND);
        pos = getArguments().getInt("position");
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
            refresh();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("jsonErrorDes", e.getMessage());
        }
    }

    //put movie info on XML components
    public void refresh(){
        posted_picture.setImageBitmap(MoviesCollection.getInstance().getMovies().get(pos).getPoster_path());
        overview.setText("Overview: "+MoviesCollection.getInstance().getMovies().get(pos).getMovieDescription().getOverview());
        budget.setText("Budget: "+MoviesCollection.getInstance().getMovies().get(pos).getMovieDescription().getBudget());
        release_date.setText("Release date: "+MoviesCollection.getInstance().getMovies().get(pos).getMovieDescription().getRelease_date());
        vote_average.setText("Vote average: "+MoviesCollection.getInstance().getMovies().get(pos).getVote_average());
    }


}
