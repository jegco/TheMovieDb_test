package com.example.jorgecaro.themoviedbtest.Model;

/**
 * Created by jorge caro on 11/24/2017.
 */

public class Trailer {
    private String name;
    private String key;
    private String id;

    public Trailer(String name, String key, String id) {
        this.name = name;
        this.key = key;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }
}
