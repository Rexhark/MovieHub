package com.example.moviehub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowList {
    @SerializedName("results")
    private List<TVShow> tvShows;

    public List<TVShow> getTVShows() {
        return tvShows;
    }
}
