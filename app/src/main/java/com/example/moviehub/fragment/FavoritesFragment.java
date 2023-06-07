package com.example.moviehub.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviehub.R;
import com.example.moviehub.activity.MainActivity;
import com.example.moviehub.adapter.FavoriteAdapter;
import com.example.moviehub.database.DatabaseHelper;
import com.example.moviehub.model.Favorite;
import com.example.moviehub.model.Movie;

import java.util.List;

public class FavoritesFragment extends Fragment {
    RecyclerView rvList;
    TextView tvEmpty;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvList = view.findViewById(R.id.rv_list);
        tvEmpty = view.findViewById(R.id.tv_empty);

        DatabaseHelper dbHelper = ((MainActivity) requireActivity()).getDbHelper();
        List<Favorite> favoriteList = dbHelper.getAllFavorites();

        if (favoriteList.size() == 0) {
            rvList.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
        else {
            rvList.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            FavoriteAdapter adapter = new FavoriteAdapter(getContext(), favoriteList);
            rvList.setAdapter(adapter);
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseHelper dbHelper = ((MainActivity) requireActivity()).getDbHelper();
        List<Favorite> favoriteList = dbHelper.getAllFavorites();

        if (favoriteList.size() == 0) {
            rvList.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
        else {
            rvList.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            FavoriteAdapter adapter = new FavoriteAdapter(getContext(), favoriteList);
            rvList.setAdapter(adapter);
        }
    }
}