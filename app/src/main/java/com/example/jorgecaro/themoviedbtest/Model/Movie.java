package com.example.jorgecaro.themoviedbtest.Model;

import android.graphics.Bitmap;

/**
 * Created by jorge caro on 11/24/2017.
 */


/*
* Class Movie, part of the app model
* */

public class Movie {
    private int id;
    private String title;
    private Bitmap poster_path;
    private double vote_average;
    private MovieDescription movieDescription;
    private int vote_count;

    public Movie() {

    }

    public Movie(int id, String title, Bitmap poster_path, double vote_average, int vote_count) {
        this.title = title;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.movieDescription = new MovieDescription();
        this.id = id;
        this.vote_count = vote_count;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Bitmap getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public MovieDescription getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(MovieDescription movieDescription) {
        this.movieDescription = movieDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
