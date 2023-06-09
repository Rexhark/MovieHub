package com.example.moviehub.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviehub.R;
import com.example.moviehub.activity.ListActivity;
import com.example.moviehub.adapter.HListAdapter;
import com.example.moviehub.adapter.HListAdapter2;
import com.example.moviehub.adapter.VListAdapter;
import com.example.moviehub.database.ApiConfig;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.MovieListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MoviesFragment extends Fragment {
    LinearLayout containerMain;
    ProgressBar progressBar;
    TextView tvShowAllNowPlaying, tvShowAllPopular, tvShowAllTopRated, tvShowAllUpcoming, tvNoConnection;
    RecyclerView rvNowPlaying, rvPopular, rvTopRated, rvUpcoming;
    List<Movie> nowPlayingMoviesList, popularMoviesList, topRatedMoviesList, upcomingMoviesList;
    HListAdapter hListAdapter;
    HListAdapter2 hListAdapter2;
    VListAdapter vListAdapter;

    public MoviesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        rvNowPlaying = view.findViewById(R.id.rv_now_playing);
        rvPopular = view.findViewById(R.id.rv_popular);
        rvTopRated = view.findViewById(R.id.rv_top_rated);
        rvUpcoming = view.findViewById(R.id.rv_upcoming);

        containerMain = view.findViewById(R.id.container_main);
        progressBar = view.findViewById(R.id.progress_bar);
        tvNoConnection = view.findViewById(R.id.tv_no_connection);

        tvShowAllNowPlaying = view.findViewById(R.id.tv_show_all_now_playing);
        tvShowAllPopular = view.findViewById(R.id.tv_show_all_popular);
        tvShowAllTopRated = view.findViewById(R.id.tv_show_all_top_rated);
        tvShowAllUpcoming = view.findViewById(R.id.tv_show_all_upcoming);

        nowPlayingMoviesList = new ArrayList<>();
        popularMoviesList = new ArrayList<>();
        topRatedMoviesList = new ArrayList<>();
        upcomingMoviesList = new ArrayList<>();

        loadApi();

        Intent intent = new Intent(getContext(), ListActivity.class);
        tvShowAllNowPlaying.setOnClickListener(v -> {
            intent.putExtra("type", "movie");
            intent.putExtra("type2", "nowPlaying");
            startActivity(intent);
        });
        tvShowAllPopular.setOnClickListener(v -> {
            intent.putExtra("type", "movie");
            intent.putExtra("type2", "popular");
            startActivity(intent);
        });
        tvShowAllTopRated.setOnClickListener(v -> {
            intent.putExtra("type", "movie");
            intent.putExtra("type2", "topRated");
            startActivity(intent);
        });
        tvShowAllUpcoming.setOnClickListener(v -> {
            intent.putExtra("type", "movie");
            intent.putExtra("type2", "upcoming");
            startActivity(intent);
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void loadApi(){
        Call<MovieListResponse> call;
        //      Now Playing Movies
        call = ApiConfig.getApiService().getNowPlayingMovies(1);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                assert response.body() != null;
                progressBar.setVisibility(View.VISIBLE);
                containerMain.setVisibility(View.GONE);
                for (Movie movie : response.body().getMovies()) {
                    if ((movie.getTitle() != null && !movie.getTitle().isEmpty())) {
                        nowPlayingMoviesList.add(movie);
                    }
                }
                containerMain.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                hListAdapter = new HListAdapter(getContext(), nowPlayingMoviesList);
                rvNowPlaying.setAdapter(hListAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                Context context = getContext();
                if (context != null) {
                    containerMain.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    tvNoConnection.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //      Popular Movies
        call = ApiConfig.getApiService().getPopularMovies(1);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                assert response.body() != null;
                containerMain.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                for (Movie movie : response.body().getMovies()) {
                    if ((movie.getTitle() != null && !movie.getTitle().isEmpty())) {
                        popularMoviesList.add(movie);
                    }
                }
                vListAdapter = new VListAdapter(getContext(), popularMoviesList);
                rvPopular.setAdapter(vListAdapter);
                containerMain.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                Context context = getContext();
                if (context != null) {
                    containerMain.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    tvNoConnection.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //      Top Rated Movies
        call = ApiConfig.getApiService().getTopRatedMovies(1);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                assert response.body() != null;
                containerMain.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                for (Movie movie : response.body().getMovies()) {
                    if ((movie.getTitle() != null && !movie.getTitle().isEmpty())) {
                        topRatedMoviesList.add(movie);
                    }
                }
                containerMain.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                hListAdapter2 = new HListAdapter2(getContext(), topRatedMoviesList);
                rvTopRated.setAdapter(hListAdapter2);
            }

            @Override
            public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                Context context = getContext();
                if (context != null) {
                    containerMain.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    tvNoConnection.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //      Upcoming Movies
        call = ApiConfig.getApiService().getUpcomingMovies(1);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieListResponse> call, @NonNull retrofit2.Response<MovieListResponse> response) {
                assert response.body() != null;
                containerMain.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                for (Movie movie : response.body().getMovies()) {
                    if ((movie.getTitle() != null && !movie.getTitle().isEmpty())) {
                        upcomingMoviesList.add(movie);
                    }
                }
                containerMain.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                vListAdapter = new VListAdapter(getContext(), upcomingMoviesList);
                rvUpcoming.setAdapter(vListAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<MovieListResponse> call, @NonNull Throwable t) {
                Context context = getContext();
                if (context != null) {
                    containerMain.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    tvNoConnection.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}