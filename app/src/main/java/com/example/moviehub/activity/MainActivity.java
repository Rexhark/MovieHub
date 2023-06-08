package com.example.moviehub.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.example.moviehub.R;
import com.example.moviehub.database.DatabaseHelper;
import com.example.moviehub.fragment.FavoritesFragment;
import com.example.moviehub.fragment.MoviesFragment;
import com.example.moviehub.fragment.TVShowsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    FragmentContainerView fragmentContainer;
    MoviesFragment moviesFragment;
    TVShowsFragment tvShowsFragment;
    FavoritesFragment favoritesFragment;
    ImageView ivRefresh;
    LinearLayout refreshContainer;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesFragment = new MoviesFragment();
        tvShowsFragment = new TVShowsFragment();
        favoritesFragment = new FavoritesFragment();

        bottomNav = findViewById(R.id.bottom_navigation);
        fragmentContainer = findViewById(R.id.fragment_container);
        refreshContainer = findViewById(R.id.refresh_container);
        ivRefresh = findViewById(R.id.iv_refresh);

        dbHelper = new DatabaseHelper(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("MovieHub");

        if (isNetworkConnected()) {

            refreshContainer.setVisibility(View.VISIBLE);
            fragmentContainer.setVisibility(View.GONE);
            bottomNav.setVisibility(View.GONE);

            ivRefresh.setOnClickListener(v -> {
                refreshContainer.setVisibility(View.GONE);
                fragmentContainer.setVisibility(View.GONE);
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            });

        }
        else {

            bottomNav.setVisibility(View.VISIBLE);
            fragmentContainer.setVisibility(View.VISIBLE);
            refreshContainer.setVisibility(View.GONE);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, moviesFragment).commit();

            bottomNav.setOnItemSelectedListener(item -> {
                if (isNetworkConnected()) {
                    fragmentContainer.setVisibility(View.GONE);
                    refreshContainer.setVisibility(View.VISIBLE);
                    bottomNav.setVisibility(View.GONE);

                    ivRefresh.setOnClickListener(v -> {
                        refreshContainer.setVisibility(View.GONE);
                        fragmentContainer.setVisibility(View.GONE);
                        finish();
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    });
                }
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
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo == null || !networkInfo.isConnectedOrConnecting();
    }

    public DatabaseHelper getDbHelper() {
        return dbHelper;
    }
}