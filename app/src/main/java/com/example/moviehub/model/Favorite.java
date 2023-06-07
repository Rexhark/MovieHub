package com.example.moviehub.model;

public class Favorite {
    private final int id;
    private final String title;
    private final String type;
    private final String poster_path;

    public Favorite(int id, String title, String type, String poster_path) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.poster_path = poster_path;
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

    public String getPosterPath() {
        return poster_path;
    }
}
