package com.example.moviehub.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviehub.R;
import com.example.moviehub.model.ApiConfig;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.MovieList;
import com.example.moviehub.model.TVShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ImageView ivBackdrop, ivPoster;
    TextView tvTitle, tvGenre, tvReleaseDate, tvOriginalLanguage, tvAdult, tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivBackdrop = findViewById(R.id.iv_backdrop);
        ivPoster = findViewById(R.id.iv_poster);
        tvTitle = findViewById(R.id.tv_title);
        tvGenre = findViewById(R.id.tv_genre);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvOriginalLanguage = findViewById(R.id.tv_original_language);
        tvAdult = findViewById(R.id.tv_adult);
        tvOverview = findViewById(R.id.tv_overview);

        String id = getIntent().getStringExtra("id");

        System.out.println(id);
        Call<Movie> call = ApiConfig.getApiService().getMovieDetails(Integer.parseInt(id));
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                Movie movie = response.body();

                assert movie != null;
                String title = movie.getTitle();
//                String genre = movie.getGenre();
                String releaseDate = movie.getReleaseDate();
                String originalLanguage = movie.getOriginalLanguage();
                String adult = movie.isAdult() ? "18+" : "All ages";
                String overview = movie.getOverview();
                String poster = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
                String backdrop = "https://image.tmdb.org/t/p/w500" + movie.getBackdropPath();

                Glide.with(DetailActivity.this)
                        .load(poster)
                        .centerCrop()
                        .into(ivPoster);

                Glide.with(DetailActivity.this)
                        .load(backdrop)
                        .centerCrop()
                        .into(ivBackdrop);

                tvTitle.setText(title);
//                tvGenre.setText(genre);
                tvReleaseDate.setText(releaseDate);
                tvOriginalLanguage.setText(originalLanguage);
                tvAdult.setText(adult);
                tvOverview.setText(overview);
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                Toast.makeText(DetailActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}