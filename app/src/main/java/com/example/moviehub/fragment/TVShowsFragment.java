package com.example.moviehub.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviehub.activity.MainActivity;
import com.example.moviehub.R;
import com.example.moviehub.adapter.HListAdapter;
import com.example.moviehub.adapter.HListAdapter2;
import com.example.moviehub.adapter.VListAdapter;
import com.example.moviehub.model.TVShow;

import java.util.List;

public class TVShowsFragment extends Fragment {
    RecyclerView rvAiringToday, rvOnTheAir, rvPopular, rvTopRated;

    public TVShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_shows, container, false);

        rvAiringToday = view.findViewById(R.id.rv_airing_today);
        rvOnTheAir = view.findViewById(R.id.rv_on_the_air);
        rvPopular = view.findViewById(R.id.rv_popular);
        rvTopRated = view.findViewById(R.id.rv_top_rated);

        List<TVShow> airingTodayTVShowsList = ((MainActivity) requireActivity()).getAiringTodayTVShowsList();
        List<TVShow> onTheAirTVShowsList = ((MainActivity) requireActivity()).getOnTheAirTVShowsList();
        List<TVShow> popularTVShowsList = ((MainActivity) requireActivity()).getPopularTVShowsList();
        List<TVShow> topRatedTVShowsList = ((MainActivity) requireActivity()).getTopRatedTVShowsList();

        HListAdapter adapter = new HListAdapter(getContext(), airingTodayTVShowsList);
        HListAdapter2 adapter2 = new HListAdapter2(getContext(), onTheAirTVShowsList);
        VListAdapter adapter3 = new VListAdapter(getContext(), popularTVShowsList);
        VListAdapter adapter4 = new VListAdapter(getContext(), topRatedTVShowsList);

        rvAiringToday.setAdapter(adapter);
        rvOnTheAir.setAdapter(adapter2);
        rvPopular.setAdapter(adapter3);
        rvTopRated.setAdapter(adapter4);

        // Inflate the layout for this fragment
        return view;
    }
}