package com.example.moviehub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private List<Cast> cast;

    // Getter & setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

}
