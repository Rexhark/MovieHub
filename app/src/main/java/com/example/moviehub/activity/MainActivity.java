package com.example.moviehub.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.example.moviehub.R;
import com.example.moviehub.fragment.FavoritesFragment;
import com.example.moviehub.fragment.MoviesFragment;
import com.example.moviehub.fragment.TVShowsFragment;
import com.example.moviehub.model.ApiConfig;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.MovieListResponse;
import com.example.moviehub.model.TVShow;
import com.example.moviehub.model.TVShowListResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    ProgressBar progressBar;
    FragmentContainerView fragmentContainer;
    private List<Movie> nowPlayingMoviesList, popularMoviesList, topRatedMoviesList, upcomingMoviesList;
    private List<TVShow> airingTodayTVShowsList, onTheAirTVShowsList, popularTVShowsList, topRatedTVShowsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nowPlayingMoviesList = new ArrayList<>();
        popularMoviesList = new ArrayList<>();
        topRatedMoviesList = new ArrayList<>();
        upcomingMoviesList = new ArrayList<>();

        airingTodayTVShowsList = new ArrayList<>();
        onTheAirTVShowsList = new ArrayList<>();
        popularTVShowsList = new ArrayList<>();
        topRatedTVShowsList = new ArrayList<>();

        bottomNav = findViewById(R.id.bottom_navigation);
        fragmentContainer = findViewById(R.id.fragment_container);
        progressBar = findViewById(R.id.progress_bar);

        MoviesFragment moviesFragment = new MoviesFragment();
        TVShowsFragment tvShowsFragment = new TVShowsFragment();
        FavoritesFragment favoritesFragment = new FavoritesFragment();

        fragmentContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        initialLoad(moviesFragment);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.movies){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, moviesFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.tv_shows){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tvShowsFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.favorites){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, favoritesFragment).commit();
                return true;
            }
            return false;
        });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void initialLoad(MoviesFragment moviesFragment){
        fragmentMoviesApiLoad();
        fragmentTVShowsApiLoad();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, moviesFragment).commit();
    }

    public void fragmentMoviesApiLoad(){
        Call<MovieListResponse> call;
//      Now Playing Movies
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
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
//      Popular Movies
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
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
//      Top Rated Movies
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
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
//      Upcoming Movies
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
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
        fragmentContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public void fragmentTVShowsApiLoad() {
        Call<TVShowListResponse> call;
//      Airing Today TV Shows
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
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
//      On The Air TV Shows
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
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
//      Popular TV Shows
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
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
//      Top Rated TV Shows
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
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
        fragmentContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public List<Movie> getNowPlayingMoviesList() {
        return nowPlayingMoviesList;
    }

    public List<Movie> getPopularMoviesList() {
        return popularMoviesList;
    }

    public List<Movie> getTopRatedMoviesList() {
        return topRatedMoviesList;
    }

    public List<Movie> getUpcomingMoviesList() {
        return upcomingMoviesList;
    }

    public List<TVShow> getAiringTodayTVShowsList() {
        return airingTodayTVShowsList;
    }

    public List<TVShow> getOnTheAirTVShowsList() {
        return onTheAirTVShowsList;
    }

    public List<TVShow> getPopularTVShowsList() {
        return popularTVShowsList;
    }

    public List<TVShow> getTopRatedTVShowsList() {
        return topRatedTVShowsList;
    }
}