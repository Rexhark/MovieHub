package com.example.moviehub;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Movie;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);

        MoviesFragment moviesFragment = new MoviesFragment();
        TVShowsFragment tvShowsFragment = new TVShowsFragment();
        FavoritesFragment favoritesFragment = new FavoritesFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, moviesFragment).commit();

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
}