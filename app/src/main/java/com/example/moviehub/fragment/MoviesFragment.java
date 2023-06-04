package com.example.moviehub.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviehub.MainActivity;
import com.example.moviehub.R;
import com.example.moviehub.adapter.HListAdapter;
import com.example.moviehub.adapter.HListAdapter2;
import com.example.moviehub.adapter.VListAdapter;
import com.example.moviehub.model.Movie;

import java.util.List;

public class MoviesFragment extends Fragment {
    private List<Movie> nowPlayingMoviesList, popularMoviesList, topRatedMoviesList, upcomingMoviesList;
    RecyclerView rvNowPlaying, rvPopular, rvTopRated, rvUpcoming;
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

        nowPlayingMoviesList = ((MainActivity) requireActivity()).getNowPlayingMoviesList();
        popularMoviesList = ((MainActivity) requireActivity()).getPopularMoviesList();
        topRatedMoviesList = ((MainActivity) requireActivity()).getTopRatedMoviesList();
        upcomingMoviesList = ((MainActivity) requireActivity()).getUpcomingMoviesList();

        HListAdapter adapter = new HListAdapter(getContext(),nowPlayingMoviesList);
        VListAdapter adapter2 = new VListAdapter(getContext(),popularMoviesList);
        VListAdapter adapter3 = new VListAdapter(getContext(),topRatedMoviesList);
        HListAdapter2 adapter4 = new HListAdapter2(getContext(),upcomingMoviesList);

        rvNowPlaying.setAdapter(adapter);
        rvPopular.setAdapter(adapter2);
        rvTopRated.setAdapter(adapter3);
        rvUpcoming.setAdapter(adapter4);

        // Inflate the layout for this fragment
        return view;
    }
}