package com.example.moviehub;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.example.moviehub.fragment.FavoritesFragment;
import com.example.moviehub.fragment.MoviesFragment;
import com.example.moviehub.fragment.TVShowsFragment;
import com.example.moviehub.model.ApiConfig;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.MovieList;
import com.example.moviehub.model.TVShow;
import com.example.moviehub.model.TVShowList;
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
        fragmentMoviesApiLoad(moviesFragment);
        fragmentTVShowsApiLoad(tvShowsFragment);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            fragmentContainer.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, moviesFragment).commit();
        }, 1000);

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
    }

    public void fragmentTVShowsApiLoad(TVShowsFragment tvShowsFragment) {
        Call<TVShowList> call;
//      Airing Today TV Shows
        call = ApiConfig.getApiService().getAiringTodayTVShows();
        call.enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(@NonNull Call<TVShowList> call, @NonNull retrofit2.Response<TVShowList> response) {
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
            public void onFailure(@NonNull Call<TVShowList> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
//      On The Air TV Shows
        call = ApiConfig.getApiService().getOnTheAirTVShows();
        call.enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(@NonNull Call<TVShowList> call, @NonNull retrofit2.Response<TVShowList> response) {
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
            public void onFailure(@NonNull Call<TVShowList> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
//      Popular TV Shows
        call = ApiConfig.getApiService().getPopularTVShows();
        call.enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(@NonNull Call<TVShowList> call, @NonNull retrofit2.Response<TVShowList> response) {
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
            public void onFailure(@NonNull Call<TVShowList> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
//      Top Rated TV Shows
        call = ApiConfig.getApiService().getTopRatedTVShows();
        call.enqueue(new Callback<TVShowList>() {
            @Override
            public void onResponse(@NonNull Call<TVShowList> call, @NonNull retrofit2.Response<TVShowList> response) {
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
            public void onFailure(@NonNull Call<TVShowList> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
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