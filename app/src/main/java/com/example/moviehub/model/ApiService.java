package com.example.moviehub.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    String apiKey = "67fc5c940f39961930b4e92efd8db693";
    @GET("movie/now_playing?api_key=" + apiKey)
    Call<MovieList> getNowPlayingMovies();
    @GET("movie/popular?api_key=" + apiKey)
    Call<MovieList> getPopularMovies();
    @GET("movie/top_rated?api_key=" + apiKey)
    Call<MovieList> getTopRatedMovies();
    @GET("movie/upcoming?api_key=" + apiKey)
    Call<MovieList> getUpcomingMovies();
}
