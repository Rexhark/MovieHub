package com.example.moviehub.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviehub.R;
import com.example.moviehub.adapter.VListAdapter;
import com.example.moviehub.model.ApiConfig;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.MovieListResponse;
import com.example.moviehub.model.TVShow;
import com.example.moviehub.model.TVShowListResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class ListActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView rvList;
    VListAdapter vListAdapter;
    String type, type2;
    private List<Movie> nowPlayingMoviesList, popularMoviesList, topRatedMoviesList, upcomingMoviesList;
    private List<TVShow> airingTodayTVShowsList, onTheAirTVShowsList, popularTVShowsList, topRatedTVShowsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rvList = findViewById(R.id.rv_list);
        progressBar = findViewById(R.id.progress_bar);

        type = getIntent().getStringExtra("type");
        type2 = getIntent().getStringExtra("type2");

        rvList.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        Handler handler = new Handler();

        if (Objects.equals(type, "movie")){
            fragmentMoviesApiLoad(type2);
            handler.postDelayed(() -> {
                rvList.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }, 1000);
            if (Objects.equals(type2, "nowPlaying")) {
                vListAdapter = new VListAdapter(this, nowPlayingMoviesList);
            } else if (Objects.equals(type2, "popular")) {
                vListAdapter = new VListAdapter(this, popularMoviesList);
            } else if (Objects.equals(type2, "topRated")) {
                vListAdapter = new VListAdapter(this, topRatedMoviesList);
            } else if (Objects.equals(type2, "upcoming")) {
                vListAdapter = new VListAdapter(this, upcomingMoviesList);
            }
            rvList.setAdapter(vListAdapter);
        }
        else if (Objects.equals(type, "tvshow")){
            fragmentTVShowsApiLoad(type2);
            handler.postDelayed(() -> {
                rvList.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }, 1000);
            if (Objects.equals(type2, "airingToday")) {
                vListAdapter = new VListAdapter(this, airingTodayTVShowsList);
            } else if (Objects.equals(type2, "onTheAir")) {
                vListAdapter = new VListAdapter(this, onTheAirTVShowsList);
            } else if (Objects.equals(type2, "popular")) {
                vListAdapter = new VListAdapter(this, popularTVShowsList);
            } else if (Objects.equals(type2, "topRated")) {
                vListAdapter = new VListAdapter(this, topRatedTVShowsList);
            }
            rvList.setAdapter(vListAdapter);
        }

    }

    public void fragmentMoviesApiLoad(String type){
        Call<MovieListResponse> call;
        //  Now Playing Movies
        if (Objects.equals(type, "nowPlaying")){
            nowPlayingMoviesList = new ArrayList<>();
            call = ApiConfig.getApiService().getNowPlayingMovies();
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                    assert response.body() != null;
                    for (Movie movie : response.body().getMovies()) {
                        Movie movieItem = new Movie();
                        movieItem.setId(movie.getId());
                        movieItem.setTitle(movie.getTitle());
                        movieItem.setPosterPath(movie.getPosterPath());
                        nowPlayingMoviesList.add(movieItem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //  Popular Movies
        else if (Objects.equals(type, "popular")) {
            popularMoviesList = new ArrayList<>();
            call = ApiConfig.getApiService().getPopularMovies();
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                    assert response.body() != null;
                    for (Movie movie : response.body().getMovies()) {
                        Movie movieItem = new Movie();
                        movieItem.setId(movie.getId());
                        movieItem.setTitle(movie.getTitle());
                        movieItem.setPosterPath(movie.getPosterPath());
                        popularMoviesList.add(movieItem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //  Top Rated Movies
        else if (Objects.equals(type, "topRated")) {
            topRatedMoviesList = new ArrayList<>();
            call = ApiConfig.getApiService().getTopRatedMovies();
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                    assert response.body() != null;
                    for (Movie movie : response.body().getMovies()) {
                        Movie movieItem = new Movie();
                        movieItem.setId(movie.getId());
                        movieItem.setTitle(movie.getTitle());
                        movieItem.setPosterPath(movie.getPosterPath());
                        topRatedMoviesList.add(movieItem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //  Upcoming Movies
        else if (Objects.equals(type, "upcoming")) {
            upcomingMoviesList = new ArrayList<>();
            call = ApiConfig.getApiService().getUpcomingMovies();
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                    assert response.body() != null;
                    for (Movie movie : response.body().getMovies()) {
                        Movie movieItem = new Movie();
                        movieItem.setId(movie.getId());
                        movieItem.setTitle(movie.getTitle());
                        movieItem.setPosterPath(movie.getPosterPath());
                        upcomingMoviesList.add(movieItem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void fragmentTVShowsApiLoad(String type) {
        Call<TVShowListResponse> call;
        //  Airing Today TV Shows
        if (Objects.equals(type, "airingToday")){
            airingTodayTVShowsList = new ArrayList<>();
            call = ApiConfig.getApiService().getAiringTodayTVShows();
            call.enqueue(new Callback<TVShowListResponse>() {
                @Override
                public void onResponse(@NonNull Call<TVShowListResponse> call, @NonNull retrofit2.Response<TVShowListResponse> response) {
                    assert response.body() != null;
                    for (TVShow tvShow : response.body().getTVShows()) {
                        TVShow tvShowItem = new TVShow();
                        tvShowItem.setId(tvShow.getId());
                        tvShowItem.setName(tvShow.getName());
                        tvShowItem.setPosterPath(tvShow.getPosterPath());
                        airingTodayTVShowsList.add(tvShowItem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //  On The Air TV Shows
        else if (Objects.equals(type, "onTheAir")) {
            onTheAirTVShowsList = new ArrayList<>();
            call = ApiConfig.getApiService().getOnTheAirTVShows();
            call.enqueue(new Callback<TVShowListResponse>() {
                @Override
                public void onResponse(@NonNull Call<TVShowListResponse> call, @NonNull retrofit2.Response<TVShowListResponse> response) {
                    assert response.body() != null;
                    for (TVShow tvShow : response.body().getTVShows()) {
                        TVShow tvShowItem = new TVShow();
                        tvShowItem.setId(tvShow.getId());
                        tvShowItem.setName(tvShow.getName());
                        tvShowItem.setPosterPath(tvShow.getPosterPath());
                        onTheAirTVShowsList.add(tvShowItem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //  Popular TV Shows
        else if (Objects.equals(type, "popular")) {
            popularTVShowsList = new ArrayList<>();
            call = ApiConfig.getApiService().getPopularTVShows();
            call.enqueue(new Callback<TVShowListResponse>() {
                @Override
                public void onResponse(@NonNull Call<TVShowListResponse> call, @NonNull retrofit2.Response<TVShowListResponse> response) {
                    assert response.body() != null;
                    for (TVShow tvShow : response.body().getTVShows()) {
                        TVShow tvShowItem = new TVShow();
                        tvShowItem.setId(tvShow.getId());
                        tvShowItem.setName(tvShow.getName());
                        tvShowItem.setPosterPath(tvShow.getPosterPath());
                        popularTVShowsList.add(tvShowItem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //  Top Rated TV Shows
        else if (Objects.equals(type, "topRated")) {
            topRatedTVShowsList = new ArrayList<>();
            call = ApiConfig.getApiService().getTopRatedTVShows();
            call.enqueue(new Callback<TVShowListResponse>() {
                @Override
                public void onResponse(@NonNull Call<TVShowListResponse> call, @NonNull retrofit2.Response<TVShowListResponse> response) {
                    assert response.body() != null;
                    for (TVShow tvShow : response.body().getTVShows()) {
                        TVShow tvShowItem = new TVShow();
                        tvShowItem.setId(tvShow.getId());
                        tvShowItem.setName(tvShow.getName());
                        tvShowItem.setPosterPath(tvShow.getPosterPath());
                        topRatedTVShowsList.add(tvShowItem);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}