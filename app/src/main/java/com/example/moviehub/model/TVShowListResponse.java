package com.example.moviehub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowListResponse {
    @SerializedName("results")
    private List<TVShow> tvShows;

    public List<TVShow> getTVShows() {
        return tvShows;
    }
}
