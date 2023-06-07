package com.example.moviehub.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    LinearLayout refreshContainer;
    ImageView ivRefresh;
    VListAdapter vListAdapter;
    String type, type2, title;
    private List<Movie> nowPlayingMoviesList, popularMoviesList, topRatedMoviesList, upcomingMoviesList;
    private List<TVShow> airingTodayTVShowsList, onTheAirTVShowsList, popularTVShowsList, topRatedTVShowsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rvList = findViewById(R.id.rv_list);
        progressBar = findViewById(R.id.progress_bar);
        refreshContainer = findViewById(R.id.refresh_container);
        ivRefresh = findViewById(R.id.iv_refresh);

        type = getIntent().getStringExtra("type");
        type2 = getIntent().getStringExtra("type2");

        if (!isNetworkConnected()) {
            refreshContainer.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            rvList.setVisibility(View.GONE);

            ivRefresh.setOnClickListener(v -> {
                refreshContainer.setVisibility(View.GONE);
                rvList.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            });
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            refreshContainer.setVisibility(View.GONE);
            rvList.setVisibility(View.GONE);

            if (Objects.equals(type, "movie")){
                if (Objects.equals(type2, "nowPlaying")) {
                    loadNowplaying();
                    title = "Now Playing";
                } else if (Objects.equals(type2, "popular")) {
                    loadPopularMovies();
                    title = "Popular";
                } else if (Objects.equals(type2, "topRated")) {
                    loadTopRatedMovies();
                    title = "Top Rated";
                } else if (Objects.equals(type2, "upcoming")) {
                    loadUpcomingMovies();
                    title = "Upcoming";
                }
            }
            else if (Objects.equals(type, "tvshow")){
                if (Objects.equals(type2, "airingToday")) {
                    loadAiringToday();
                    title = "Airing Today";
                } else if (Objects.equals(type2, "onTheAir")) {
                    loadOnTheAir();
                    title = "On The Air";
                } else if (Objects.equals(type2, "popular")) {
                    loadPopularTvShow();
                    title = "Popular";
                } else if (Objects.equals(type2, "topRated")) {
                    loadTopRatedTvShow();
                    title = "Top Rated";
                }
                rvList.setAdapter(vListAdapter);
            }

            Objects.requireNonNull(getSupportActionBar()).setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadNowplaying(){
        nowPlayingMoviesList = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            Call<MovieListResponse> call = ApiConfig.getApiService().getNowPlayingMovies(page);
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                    assert response.body() != null;
                    for (Movie movie : response.body().getMovies()) {
                        Movie movieItem = new Movie();
                        movieItem.setAdult(movie.isAdult());
                        movieItem.setBackdropPath(movie.getBackdropPath());
                        movieItem.setId(movie.getId());
                        movieItem.setOverview(movie.getOverview());
                        movieItem.setPosterPath(movie.getPosterPath());
                        movieItem.setReleaseDate(movie.getReleaseDate());
                        movieItem.setTitle(movie.getTitle());
                        movieItem.setVoteAverage(movie.getVoteAverage());
                        nowPlayingMoviesList.add(movieItem);
                    }
                    vListAdapter = new VListAdapter(ListActivity.this, nowPlayingMoviesList);
                    rvList.setAdapter(vListAdapter);
                    rvList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void loadPopularMovies(){
        popularMoviesList = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            Call<MovieListResponse> call = ApiConfig.getApiService().getPopularMovies(page);
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                    assert response.body() != null;
                    for (Movie movie : response.body().getMovies()) {
                        Movie movieItem = new Movie();
                        movieItem.setAdult(movie.isAdult());
                        movieItem.setBackdropPath(movie.getBackdropPath());
                        movieItem.setId(movie.getId());
                        movieItem.setOverview(movie.getOverview());
                        movieItem.setPosterPath(movie.getPosterPath());
                        movieItem.setReleaseDate(movie.getReleaseDate());
                        movieItem.setTitle(movie.getTitle());
                        movieItem.setVoteAverage(movie.getVoteAverage());
                        popularMoviesList.add(movieItem);
                    }
                    vListAdapter = new VListAdapter(ListActivity.this, popularMoviesList);
                    rvList.setAdapter(vListAdapter);
                    rvList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void loadTopRatedMovies(){
        topRatedMoviesList = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            Call<MovieListResponse> call = ApiConfig.getApiService().getTopRatedMovies(page);
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                    assert response.body() != null;
                    for (Movie movie : response.body().getMovies()) {
                        Movie movieItem = new Movie();
                        movieItem.setAdult(movie.isAdult());
                        movieItem.setBackdropPath(movie.getBackdropPath());
                        movieItem.setId(movie.getId());
                        movieItem.setOverview(movie.getOverview());
                        movieItem.setPosterPath(movie.getPosterPath());
                        movieItem.setReleaseDate(movie.getReleaseDate());
                        movieItem.setTitle(movie.getTitle());
                        movieItem.setVoteAverage(movie.getVoteAverage());
                        topRatedMoviesList.add(movieItem);
                    }
                    vListAdapter = new VListAdapter(ListActivity.this, topRatedMoviesList);
                    rvList.setAdapter(vListAdapter);
                    rvList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void loadUpcomingMovies(){
        upcomingMoviesList = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            Call<MovieListResponse> call = ApiConfig.getApiService().getUpcomingMovies(page);
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                    assert response.body() != null;
                    for (Movie movie : response.body().getMovies()) {
                        Movie movieItem = new Movie();
                        movieItem.setAdult(movie.isAdult());
                        movieItem.setBackdropPath(movie.getBackdropPath());
                        movieItem.setId(movie.getId());
                        movieItem.setOverview(movie.getOverview());
                        movieItem.setPosterPath(movie.getPosterPath());
                        movieItem.setReleaseDate(movie.getReleaseDate());
                        movieItem.setTitle(movie.getTitle());
                        movieItem.setVoteAverage(movie.getVoteAverage());
                        upcomingMoviesList.add(movieItem);
                    }
                    vListAdapter = new VListAdapter(ListActivity.this, upcomingMoviesList);
                    rvList.setAdapter(vListAdapter);
                    rvList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void loadAiringToday(){
        airingTodayTVShowsList = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            Call<TVShowListResponse> call = ApiConfig.getApiService().getAiringTodayTVShows(page);
            call.enqueue(new Callback<TVShowListResponse>() {
                @Override
                public void onResponse(@NonNull Call<TVShowListResponse> call, @NonNull retrofit2.Response<TVShowListResponse> response) {
                    assert response.body() != null;
                    for (TVShow tvShow : response.body().getTVShows()) {
                        TVShow tvShowItem = new TVShow();
                        tvShowItem.setBackdropPath(tvShow.getBackdropPath());
                        tvShowItem.setFirstAirDate(tvShow.getFirstAirDate());
                        tvShowItem.setId(tvShow.getId());
                        tvShowItem.setName(tvShow.getName());
                        tvShowItem.setOverview(tvShow.getOverview());
                        tvShowItem.setPosterPath(tvShow.getPosterPath());
                        tvShowItem.setVoteAverage(tvShow.getVoteAverage());
                        airingTodayTVShowsList.add(tvShowItem);
                    }
                    vListAdapter = new VListAdapter(ListActivity.this, airingTodayTVShowsList);
                    rvList.setAdapter(vListAdapter);
                    rvList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void loadOnTheAir(){
        onTheAirTVShowsList = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            Call<TVShowListResponse> call = ApiConfig.getApiService().getOnTheAirTVShows(page);
            call.enqueue(new Callback<TVShowListResponse>() {
                @Override
                public void onResponse(@NonNull Call<TVShowListResponse> call, @NonNull retrofit2.Response<TVShowListResponse> response) {
                    assert response.body() != null;
                    for (TVShow tvShow : response.body().getTVShows()) {
                        TVShow tvShowItem = new TVShow();
                        tvShowItem.setBackdropPath(tvShow.getBackdropPath());
                        tvShowItem.setFirstAirDate(tvShow.getFirstAirDate());
                        tvShowItem.setId(tvShow.getId());
                        tvShowItem.setName(tvShow.getName());
                        tvShowItem.setOverview(tvShow.getOverview());
                        tvShowItem.setPosterPath(tvShow.getPosterPath());
                        tvShowItem.setVoteAverage(tvShow.getVoteAverage());
                        onTheAirTVShowsList.add(tvShowItem);
                    }
                    vListAdapter = new VListAdapter(ListActivity.this, onTheAirTVShowsList);
                    rvList.setAdapter(vListAdapter);
                    rvList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void loadPopularTvShow(){
        popularTVShowsList = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            Call<TVShowListResponse> call = ApiConfig.getApiService().getPopularTVShows(page);
            call.enqueue(new Callback<TVShowListResponse>() {
                @Override
                public void onResponse(@NonNull Call<TVShowListResponse> call, @NonNull retrofit2.Response<TVShowListResponse> response) {
                    assert response.body() != null;
                    for (TVShow tvShow : response.body().getTVShows()) {
                        TVShow tvShowItem = new TVShow();
                        tvShowItem.setBackdropPath(tvShow.getBackdropPath());
                        tvShowItem.setFirstAirDate(tvShow.getFirstAirDate());
                        tvShowItem.setId(tvShow.getId());
                        tvShowItem.setName(tvShow.getName());
                        tvShowItem.setOverview(tvShow.getOverview());
                        tvShowItem.setPosterPath(tvShow.getPosterPath());
                        tvShowItem.setVoteAverage(tvShow.getVoteAverage());
                        popularTVShowsList.add(tvShowItem);
                    }
                    vListAdapter = new VListAdapter(ListActivity.this, popularTVShowsList);
                    rvList.setAdapter(vListAdapter);
                    rvList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void loadTopRatedTvShow(){
        topRatedTVShowsList = new ArrayList<>();
        for (int page = 1; page <= 5; page++) {
            Call<TVShowListResponse> call = ApiConfig.getApiService().getTopRatedTVShows(page);
            call.enqueue(new Callback<TVShowListResponse>() {
                @Override
                public void onResponse(@NonNull Call<TVShowListResponse> call, @NonNull retrofit2.Response<TVShowListResponse> response) {
                    assert response.body() != null;
                    for (TVShow tvShow : response.body().getTVShows()) {
                        TVShow tvShowItem = new TVShow();
                        tvShowItem.setBackdropPath(tvShow.getBackdropPath());
                        tvShowItem.setFirstAirDate(tvShow.getFirstAirDate());
                        tvShowItem.setId(tvShow.getId());
                        tvShowItem.setName(tvShow.getName());
                        tvShowItem.setOverview(tvShow.getOverview());
                        tvShowItem.setPosterPath(tvShow.getPosterPath());
                        tvShowItem.setVoteAverage(tvShow.getVoteAverage());
                        topRatedTVShowsList.add(tvShowItem);
                    }
                    vListAdapter = new VListAdapter(ListActivity.this, topRatedTVShowsList);
                    rvList.setAdapter(vListAdapter);
                    rvList.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                    Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}