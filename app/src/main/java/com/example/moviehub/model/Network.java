package com.example.moviehub.model;

import com.google.gson.annotations.SerializedName;

public class Network {
    @SerializedName("headquarters")
    private String headquarters;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("id")
    private int id;
    @SerializedName("logo_path")
    private String logo_path;
    @SerializedName("name")
    private String name;
    @SerializedName("origin_country")
    private String origin_country;
//    Getter & Setter

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
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

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }
}
