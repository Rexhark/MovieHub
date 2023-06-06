package com.example.moviehub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieListResponse {
    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}

