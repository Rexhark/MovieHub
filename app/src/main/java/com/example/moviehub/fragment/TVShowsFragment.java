package com.example.moviehub.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.moviehub.model.ApiConfig;
import com.example.moviehub.model.TVShow;
import com.example.moviehub.model.TVShowListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TVShowsFragment extends Fragment {
    RecyclerView rvAiringToday, rvOnTheAir, rvPopular, rvTopRated;
    TextView tvShowAllAiringToday, tvShowAllOnTheAir, tvShowAllPopular, tvShowAllTopRated;
    List<TVShow> airingTodayTVShowsList, onTheAirTVShowsList, popularTVShowsList, topRatedTVShowsList;
    HListAdapter hListAdapter;
    HListAdapter2 hListAdapter2;
    VListAdapter vListAdapter;

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

        tvShowAllAiringToday = view.findViewById(R.id.tv_show_all_airing_today);
        tvShowAllOnTheAir = view.findViewById(R.id.tv_show_all_on_the_air);
        tvShowAllPopular = view.findViewById(R.id.tv_show_all_popular);
        tvShowAllTopRated = view.findViewById(R.id.tv_show_all_top_rated);

        airingTodayTVShowsList = new ArrayList<>();
        onTheAirTVShowsList = new ArrayList<>();
        popularTVShowsList = new ArrayList<>();
        topRatedTVShowsList = new ArrayList<>();
        loadApi();

        Intent intent = new Intent(getContext(), ListActivity.class);
        tvShowAllAiringToday.setOnClickListener(v -> {
            intent.putExtra("type", "tvshow");
            intent.putExtra("type2", "airingToday");
            startActivity(intent);
        });
        tvShowAllOnTheAir.setOnClickListener(v -> {
            intent.putExtra("type", "tvshow");
            intent.putExtra("type2", "onTheAir");
            startActivity(intent);
        });
        tvShowAllPopular.setOnClickListener(v -> {
            intent.putExtra("type", "tvshow");
            intent.putExtra("type2", "popular");
            startActivity(intent);
        });
        tvShowAllTopRated.setOnClickListener(v -> {
            intent.putExtra("type", "tvshow");
            intent.putExtra("type2", "topRated");
            startActivity(intent);
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void loadApi() {
        Call<TVShowListResponse> call;
        //      Airing Today TV Shows
        call = ApiConfig.getApiService().getAiringTodayTVShows(1);
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
                hListAdapter = new HListAdapter(getContext(), airingTodayTVShowsList);
                rvAiringToday.setAdapter(hListAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
        //      On The Air TV Shows
        call = ApiConfig.getApiService().getOnTheAirTVShows(1);
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
                vListAdapter = new VListAdapter(getContext(), onTheAirTVShowsList);
                rvOnTheAir.setAdapter(vListAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
        //      Popular TV Shows
        call = ApiConfig.getApiService().getPopularTVShows(1);
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
                hListAdapter2 = new HListAdapter2(getContext(), popularTVShowsList);
                rvPopular.setAdapter(hListAdapter2);
            }

            @Override
            public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
        //      Top Rated TV Shows
        call = ApiConfig.getApiService().getTopRatedTVShows(1);
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
                vListAdapter = new VListAdapter(getContext(), topRatedTVShowsList);
                rvTopRated.setAdapter(vListAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<TVShowListResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}