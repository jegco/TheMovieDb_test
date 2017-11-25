package com.example.jorgecaro.themoviedbtest.Model;

import java.util.ArrayList;

/**
 * Created by jorge caro on 11/24/2017.
 */

public class MovieDescription {

    private String overview;
    private String release_date;
    private int budget;
    private ArrayList<Trailer> trailer_url;

    public MovieDescription(String overview, String release_date, int budget) {
        this.overview = overview;
        this.release_date = release_date;
        this.budget = budget;
        trailer_url = new ArrayList<>();
    }

    public MovieDescription() {
        this.budget = 0;
        this.overview = "";
        this.release_date = "";
        trailer_url = new ArrayList<>();
    }

    public ArrayList<Trailer> getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(ArrayList<Trailer> trailer_url) {
        this.trailer_url = trailer_url;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getBudget() {
        return budget;
    }

}
