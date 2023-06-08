package com.example.moviehub.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviehub.R;
import com.example.moviehub.activity.MainActivity;
import com.example.moviehub.adapter.FavoriteAdapter;
import com.example.moviehub.database.DatabaseHelper;
import com.example.moviehub.model.Favorite;

import java.util.Collections;
import java.util.List;

public class FavoritesFragment extends Fragment {
    RecyclerView rvList;
    TextView tvEmpty;
    DatabaseHelper dbHelper;
    FavoriteAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvList = view.findViewById(R.id.rv_list);
        tvEmpty = view.findViewById(R.id.tv_empty);

        dbHelper = ((MainActivity) requireActivity()).getDbHelper();
        List<Favorite> favoriteList = dbHelper.getAllFavorites();

        if (favoriteList.size() == 0) {
            rvList.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
        else {
            rvList.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            adapter = new FavoriteAdapter(getContext(), favoriteList);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.favorite_action_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_sort) {
            showSortMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(), requireView().getRootView().findViewById(R.id.btn_sort));
        popupMenu.getMenuInflater().inflate(R.menu.sort_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.by_title) {
                adapter = new FavoriteAdapter(getContext(), dbHelper.sortFavorites("title"));
                rvList.setAdapter(adapter);
                return true;
            } else if (id == R.id.by_year) {
                List<Favorite> list = dbHelper.sortFavorites("year");
                Collections.reverse(list);
                adapter = new FavoriteAdapter(getContext(), list);
                rvList.setAdapter(adapter);
                return true;
            }

            return false;
        });

        popupMenu.show();
    }

}