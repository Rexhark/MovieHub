package com.example.moviehub.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviehub.R;
import com.example.moviehub.adapter.VListAdapter;
import com.example.moviehub.database.ApiConfig;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.MovieListResponse;
import com.example.moviehub.model.TVShow;
import com.example.moviehub.model.TVShowListResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class ListActivity extends AppCompatActivity {
    ProgressBar progressBar, progressBar2;
    RecyclerView rvList;
    LinearLayout refreshContainer, mainContainer;
    ImageView ivRefresh;
    VListAdapter vListAdapter;
    String type, type2, title;
    TextInputEditText etSearch;
    TextView tvNoData;
    private List<Movie> nowPlayingMoviesList, popularMoviesList, topRatedMoviesList, upcomingMoviesList;
    private List<TVShow> airingTodayTVShowsList, onTheAirTVShowsList, popularTVShowsList, topRatedTVShowsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        rvList = findViewById(R.id.rv_list);
        progressBar = findViewById(R.id.progress_bar);
        progressBar2 = findViewById(R.id.progress_bar_2);
        refreshContainer = findViewById(R.id.refresh_container);
        ivRefresh = findViewById(R.id.iv_refresh);
        etSearch = findViewById(R.id.et_search);
        tvNoData = findViewById(R.id.tv_no_data);
        mainContainer = findViewById(R.id.main_container);

        type = getIntent().getStringExtra("type");
        type2 = getIntent().getStringExtra("type2");

        if (!isNetworkConnected()) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("MovieHub");
            refreshContainer.setVisibility(View.VISIBLE);
            mainContainer.setVisibility(View.GONE);

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
            refreshContainer.setVisibility(View.GONE);
            mainContainer.setVisibility(View.VISIBLE);

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

                etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Search Movie");
                        String searchText = s.toString();
                        filterMovie(searchText);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (vListAdapter.getItemCount() == 0) {
                            Objects.requireNonNull(getSupportActionBar()).setTitle(title);
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
                    }
                });

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


                etSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Search TV Show");
                        String searchText = s.toString();
                        filterTvShow(searchText);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (vListAdapter.getItemCount() == 0) {
                            Objects.requireNonNull(getSupportActionBar()).setTitle(title);
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
                        }
                    }
                });
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
        for (int page = 1; page <= 3; page++) {
            Call<MovieListResponse> call = ApiConfig.getApiService().getNowPlayingMovies(page);
            call.enqueue(new Callback<MovieListResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                    assert response.body() != null;
                    for (Movie movie : response.body().getMovies()) {
                        movie.setAdult(movie.isAdult());
                        movie.setId(movie.getId());
                        movie.setPosterPath(movie.getPosterPath());
                        movie.setReleaseDate(movie.getReleaseDate());
                        movie.setTitle(movie.getTitle());
                        movie.setVoteAverage(movie.getVoteAverage());
                        nowPlayingMoviesList.add(movie);
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
                        movie.setAdult(movie.isAdult());
                        movie.setId(movie.getId());
                        movie.setPosterPath(movie.getPosterPath());
                        movie.setReleaseDate(movie.getReleaseDate());
                        movie.setTitle(movie.getTitle());
                        movie.setVoteAverage(movie.getVoteAverage());
                        popularMoviesList.add(movie);
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
                        movie.setAdult(movie.isAdult());
                        movie.setId(movie.getId());
                        movie.setPosterPath(movie.getPosterPath());
                        movie.setReleaseDate(movie.getReleaseDate());
                        movie.setTitle(movie.getTitle());
                        movie.setVoteAverage(movie.getVoteAverage());
                        topRatedMoviesList.add(movie);
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
                        movie.setAdult(movie.isAdult());
                        movie.setId(movie.getId());
                        movie.setPosterPath(movie.getPosterPath());
                        movie.setReleaseDate(movie.getReleaseDate());
                        movie.setTitle(movie.getTitle());
                        movie.setVoteAverage(movie.getVoteAverage());
                        upcomingMoviesList.add(movie);
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
                        tvShow.setFirstAirDate(tvShow.getFirstAirDate());
                        tvShow.setId(tvShow.getId());
                        tvShow.setName(tvShow.getName());
                        tvShow.setPosterPath(tvShow.getPosterPath());
                        tvShow.setVoteAverage(tvShow.getVoteAverage());
                        airingTodayTVShowsList.add(tvShow);
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
                        tvShow.setFirstAirDate(tvShow.getFirstAirDate());
                        tvShow.setId(tvShow.getId());
                        tvShow.setName(tvShow.getName());
                        tvShow.setPosterPath(tvShow.getPosterPath());
                        tvShow.setVoteAverage(tvShow.getVoteAverage());
                        onTheAirTVShowsList.add(tvShow);
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
                        tvShow.setFirstAirDate(tvShow.getFirstAirDate());
                        tvShow.setId(tvShow.getId());
                        tvShow.setName(tvShow.getName());
                        tvShow.setPosterPath(tvShow.getPosterPath());
                        tvShow.setVoteAverage(tvShow.getVoteAverage());
                        popularTVShowsList.add(tvShow);
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
                        tvShowItem.setFirstAirDate(tvShow.getFirstAirDate());
                        tvShowItem.setId(tvShow.getId());
                        tvShowItem.setName(tvShow.getName());
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

    public void filterMovie(String searchText){
        List<Movie> filteredMovieList = new ArrayList<>();
        rvList.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);
        progressBar2.setVisibility(View.VISIBLE);
        Call<MovieListResponse> call = ApiConfig.getApiService().searchMovies(searchText);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                assert response.body() != null;
                for (Movie movie : response.body().getMovies()) {
                    movie.setAdult(movie.isAdult());
                    movie.setId(movie.getId());
                    movie.setPosterPath(movie.getPosterPath());
                    movie.setReleaseDate(movie.getReleaseDate());
                    movie.setTitle(movie.getTitle());
                    movie.setVoteAverage(movie.getVoteAverage());
                    filteredMovieList.add(movie);
                }

                vListAdapter = new VListAdapter(ListActivity.this, filteredMovieList);
                rvList.setAdapter(vListAdapter);

                progressBar2.setVisibility(View.GONE);
                if (filteredMovieList.isEmpty()) {
                    tvNoData.setVisibility(View.VISIBLE);
                    rvList.setVisibility(View.GONE);
                } else {
                    tvNoData.setVisibility(View.GONE);
                    rvList.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void filterTvShow(String searchText){
        List<TVShow> filteredTvShowList = new ArrayList<>();
        rvList.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);
        progressBar2.setVisibility(View.VISIBLE);
        Call<TVShowListResponse> call = ApiConfig.getApiService().searchTVShows(searchText);
        call.enqueue(new Callback<TVShowListResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowListResponse> call, @NonNull retrofit2.Response<TVShowListResponse> response) {
                assert response.body() != null;
                for (TVShow tvShow : response.body().getTVShows()) {
                    tvShow.setFirstAirDate(tvShow.getFirstAirDate());
                    tvShow.setId(tvShow.getId());
                    tvShow.setName(tvShow.getName());
                    tvShow.setPosterPath(tvShow.getPosterPath());
                    tvShow.setVoteAverage(tvShow.getVoteAverage());
                    filteredTvShowList.add(tvShow);
                }

                vListAdapter = new VListAdapter(ListActivity.this, filteredTvShowList);
                rvList.setAdapter(vListAdapter);

                progressBar2.setVisibility(View.GONE);
                if (filteredTvShowList.isEmpty()) {
                    tvNoData.setVisibility(View.VISIBLE);
                    rvList.setVisibility(View.GONE);
                } else {
                    tvNoData.setVisibility(View.GONE);
                    rvList.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                Toast.makeText(ListActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}