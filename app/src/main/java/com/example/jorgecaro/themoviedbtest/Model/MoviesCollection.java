package com.example.jorgecaro.themoviedbtest.Model;

import java.util.ArrayList;

/**
 * Created by jorge caro on 11/24/2017.
 */

public class MoviesCollection {

    private static MoviesCollection moviesCollection;
    private ArrayList<Movie> movies;

    private MoviesCollection() {
        this.movies = new ArrayList<>();
    }

    public static MoviesCollection getInstance() {
        if (moviesCollection == null) moviesCollection = new MoviesCollection();
        return moviesCollection;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
