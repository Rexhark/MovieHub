package com.example.moviehub.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviehub.activity.ListActivity;
import com.example.moviehub.activity.MainActivity;
import com.example.moviehub.R;
import com.example.moviehub.adapter.HListAdapter;
import com.example.moviehub.adapter.HListAdapter2;
import com.example.moviehub.adapter.VListAdapter;
import com.example.moviehub.model.Movie;

import java.util.List;

public class MoviesFragment extends Fragment {
    TextView tvShowAllNowPlaying, tvShowAllPopular, tvShowAllTopRated, tvShowAllUpcoming;
    RecyclerView rvNowPlaying, rvPopular, rvTopRated, rvUpcoming;
    Intent intent;
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

        tvShowAllNowPlaying = view.findViewById(R.id.tv_show_all_now_playing);
        tvShowAllPopular = view.findViewById(R.id.tv_show_all_popular);
        tvShowAllTopRated = view.findViewById(R.id.tv_show_all_top_rated);
        tvShowAllUpcoming = view.findViewById(R.id.tv_show_all_upcoming);

        new Handler().postDelayed(() -> {
            rvNowPlaying.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            rvPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rvTopRated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rvUpcoming.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        }, 1000);

        List<Movie> nowPlayingMoviesList = ((MainActivity) requireActivity()).getNowPlayingMoviesList().subList(0,10);
        List<Movie> popularMoviesList = ((MainActivity) requireActivity()).getPopularMoviesList().subList(0,5);
        List<Movie> topRatedMoviesList = ((MainActivity) requireActivity()).getTopRatedMoviesList().subList(0,5);
        List<Movie> upcomingMoviesList = ((MainActivity) requireActivity()).getUpcomingMoviesList().subList(0,5);

        HListAdapter adapter = new HListAdapter(getContext(), nowPlayingMoviesList);
        VListAdapter adapter2 = new VListAdapter(getContext(), popularMoviesList);
        VListAdapter adapter3 = new VListAdapter(getContext(), topRatedMoviesList);
        HListAdapter2 adapter4 = new HListAdapter2(getContext(), upcomingMoviesList);

        rvNowPlaying.setAdapter(adapter);
        rvPopular.setAdapter(adapter2);
        rvTopRated.setAdapter(adapter3);
        rvUpcoming.setAdapter(adapter4);

        intent = new Intent(getContext(), ListActivity.class);
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
}