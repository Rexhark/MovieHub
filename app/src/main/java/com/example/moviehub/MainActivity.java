package com.example.moviehub;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviehub.fragment.FavoritesFragment;
import com.example.moviehub.fragment.MoviesFragment;
import com.example.moviehub.fragment.TVShowsFragment;
import com.example.moviehub.model.ApiConfig;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.MovieList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    private List<Movie> nowPlayingMoviesList, popularMoviesList, topRatedMoviesList, upcomingMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nowPlayingMoviesList = new ArrayList<>();
        popularMoviesList = new ArrayList<>();
        topRatedMoviesList = new ArrayList<>();
        upcomingMoviesList = new ArrayList<>();

        bottomNav = findViewById(R.id.bottom_navigation);

        MoviesFragment moviesFragment = new MoviesFragment();
        TVShowsFragment tvShowsFragment = new TVShowsFragment();
        FavoritesFragment favoritesFragment = new FavoritesFragment();

        try {
            fragmentMoviesApiLoad(moviesFragment);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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

    public void fragmentMoviesApiLoad(MoviesFragment moviesFragment){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Call<MovieList> call;
//      Now Playing Movies
            call = ApiConfig.getApiService().getNowPlayingMovies();
            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(@NonNull Call<MovieList> call, @NonNull retrofit2.Response<MovieList> response) {
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
                public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
//      Popular Movies
            call = ApiConfig.getApiService().getPopularMovies();
            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(@NonNull Call<MovieList> call, @NonNull retrofit2.Response<MovieList> response) {
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
                public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
//      Top Rated Movies
            call = ApiConfig.getApiService().getTopRatedMovies();
            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(@NonNull Call<MovieList> call, @NonNull retrofit2.Response<MovieList> response) {
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
                public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
//      Upcoming Movies
            call = ApiConfig.getApiService().getUpcomingMovies();
            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(@NonNull Call<MovieList> call, @NonNull retrofit2.Response<MovieList> response) {
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
                public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                }
            });
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, moviesFragment).commit();
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
}