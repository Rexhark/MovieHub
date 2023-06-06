package com.example.moviehub.model;

import com.google.gson.annotations.SerializedName;

public class Person {
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("also_known_as")
    private String also_known_as;
    @SerializedName("biography")
    private String biography;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("deathday")
    private String deathday;
    @SerializedName("gender")
    private int gender;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("id")
    private int id;
    @SerializedName("imdb_id")
    private String imdb_id;
    @SerializedName("known_for_department")
    private String known_for_department;
    @SerializedName("name")
    private String name;
    @SerializedName("place_of_birth")
    private String place_of_birth;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("profile_path")
    private String profile_path;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(String also_known_as) {
        this.also_known_as = also_known_as;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
