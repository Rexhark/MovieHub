package com.example.moviehub.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    String apiKey = "67fc5c940f39961930b4e92efd8db693";

//    Movie
    @GET("movie/now_playing?api_key=" + apiKey)
    Call<MovieListResponse> getNowPlayingMovies();
    @GET("movie/popular?api_key=" + apiKey)
    Call<MovieListResponse> getPopularMovies();
    @GET("movie/top_rated?api_key=" + apiKey)
    Call<MovieListResponse> getTopRatedMovies();
    @GET("movie/upcoming?api_key=" + apiKey)
    Call<MovieListResponse> getUpcomingMovies();
    @GET("movie/{movie_id}?api_key=" + apiKey)
    Call<Movie> getMovieDetails(@Path("movie_id") int movieId);
    @GET("movie/{movie_id}/credits?api_key=" + apiKey)
    Call<CreditResponse> getMovieCredits(@Path("movie_id") int movieId);


//    TV Show
    @GET("tv/airing_today?api_key=" + apiKey)
    Call<TVShowListResponse> getAiringTodayTVShows();
    @GET("tv/on_the_air?api_key=" + apiKey)
    Call<TVShowListResponse> getOnTheAirTVShows();
    @GET("tv/popular?api_key=" + apiKey)
    Call<TVShowListResponse> getPopularTVShows();
    @GET("tv/top_rated?api_key=" + apiKey)
    Call<TVShowListResponse> getTopRatedTVShows();
    @GET("tv/{series_id}?api_key=" + apiKey)
    Call<TVShow> getTVShowDetails(@Path("series_id") int seriesId);

}
