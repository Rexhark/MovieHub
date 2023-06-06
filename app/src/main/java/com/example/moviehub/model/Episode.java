package com.example.moviehub.model;

import com.google.gson.annotations.SerializedName;

public class Episode {
    @SerializedName("air_date")
    private String air_date;
    @SerializedName("crew")
    private String[] crew;
    @SerializedName("episode_number")
    private int episode_number;
    @SerializedName("guest_stars")
    private String[] guest_stars;
    @SerializedName("name")
    private String name;
    @SerializedName("overview")
    private String overview;
    @SerializedName("id")
    private int id;
    @SerializedName("production_code")
    private String production_code;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("season_number")
    private int season_number;
    @SerializedName("still_path")
    private String still_path;
    @SerializedName("vote_average")
    private double vote_average;
    @SerializedName("vote_count")
    private int vote_count;

    // Getter & setter

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String[] getCrew() {
        return crew;
    }

    public void setCrew(String[] crew) {
        this.crew = crew;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }

    public String[] getGuest_stars() {
        return guest_stars;
    }

    public void setGuest_stars(String[] guest_stars) {
        this.guest_stars = guest_stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduction_code() {
        return production_code;
    }

    public void setProduction_code(String production_code) {
        this.production_code = production_code;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public String getStill_path() {
        return still_path;
    }

    public void setStill_path(String still_path) {
        this.still_path = still_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
