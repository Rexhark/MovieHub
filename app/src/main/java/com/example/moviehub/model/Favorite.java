package com.example.moviehub.model;

public class Favorite {
    private final int id;
    private final String title;
    private final String type;
    private final int year;
    private final String posterPath;

    public Favorite(int id, String title, String type, int year, String posterPath) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.year = year;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
