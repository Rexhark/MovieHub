package com.example.moviehub.model;

import com.google.gson.annotations.SerializedName;

public class TVShow {
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("first_air_date")
    private String first_air_date;
    @SerializedName("genre_ids")
    private int[] genre_ids;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("origin_country")
    private String[] origin_country;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("original_name")
    private String original_name;
    @SerializedName("overview")
    private String overview;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("vote_average")
    private double vote_average;
    @SerializedName("vote_count")
    private int vote_count;
//    Getter & setter

    public String getBackdropPath() {
        return backdrop_path;
    }

    public void setBackdropPath(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getFirstAirDate() {
        return first_air_date;
    }

    public void setFirstAirDate(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public int[] getGenreIds() {
        return genre_ids;
    }

    public void setGenreIds(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getOriginCountry() {
        return origin_country;
    }

    public void setOriginCountry(String[] origin_country) {
        this.origin_country = origin_country;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public void setOriginalLanguage(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginalName() {
        return original_name;
    }

    public void setOriginalName(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public void setVoteAverage(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVoteCount() {
        return vote_count;
    }

    public void setVoteCount(int vote_count) {
        this.vote_count = vote_count;
    }
}