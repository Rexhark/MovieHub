package com.example.moviehub.model;

import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    String apiKey = "67fc5c940f39961930b4e92efd8db693";

//    Movie
    @GET("movie/now_playing?region=ID&api_key=" + apiKey)
    Call<MovieListResponse> getNowPlayingMovies(@Query("page") int page);
    @GET("movie/popular?region=ID&api_key=" + apiKey)
    Call<MovieListResponse> getPopularMovies(@Query("page") int page);
    @GET("movie/top_rated?region=ID&api_key=" + apiKey)
    Call<MovieListResponse> getTopRatedMovies(@Query("page") int page);
    @GET("movie/upcoming?region=ID&api_key=" + apiKey)
    Call<MovieListResponse> getUpcomingMovies(@Query("page") int page);
    @GET("movie/{movie_id}?api_key=" + apiKey)
    Call<Movie> getMovieDetails(@Path("movie_id") int movieId);
    @GET("movie/{movie_id}/credits?api_key=" + apiKey)
    Call<CreditResponse> getMovieCredits(@Path("movie_id") int movieId);


//    TV Show
    @GET("tv/airing_today?api_key=" + apiKey)
    Call<TVShowListResponse> getAiringTodayTVShows(@Query("page") int page);
    @GET("tv/on_the_air?api_key=" + apiKey)
    Call<TVShowListResponse> getOnTheAirTVShows(@Query("page") int page);
    @GET("tv/popular?api_key=" + apiKey)
    Call<TVShowListResponse> getPopularTVShows(@Query("page") int page);
    @GET("tv/top_rated?api_key=" + apiKey)
    Call<TVShowListResponse> getTopRatedTVShows(@Query("page") int page);
    @GET("tv/{series_id}?api_key=" + apiKey)
    Call<TVShow> getTVShowDetails(@Path("series_id") int seriesId);
    @GET("tv/{series_id}/credits?api_key=" + apiKey)
    Call<CreditResponse> getTVShowCredits(@Path("series_id") int seriesId);

}
