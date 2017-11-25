package com.example.jorgecaro.themoviedbtest.Model;

/**
 * Created by jorge caro on 11/24/2017.
 */

public class MovieDescription {

    private String overview;
    private String release_date;
    private int budget;

    public MovieDescription(String overview, String release_date, int budget) {
        this.overview = overview;
        this.release_date = release_date;
        this.budget = budget;
    }

    public MovieDescription() {
        this.budget = 0;
        this.overview = "";
        this.release_date = "";
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
