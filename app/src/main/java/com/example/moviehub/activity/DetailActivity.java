package com.example.moviehub.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviehub.R;
import com.example.moviehub.adapter.CastAdapter;
import com.example.moviehub.model.ApiConfig;
import com.example.moviehub.model.Cast;
import com.example.moviehub.model.CreditResponse;
import com.example.moviehub.model.Genre;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.TVShow;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ImageView ivBackdrop, ivPoster;
    TextView tvTitle, tvGenre, tvAdult, tvOverview, tvDuration;
    RatingBar ratingBar;
    List<Cast> castList;
    RecyclerView rvCast;
    CastAdapter castAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivBackdrop = findViewById(R.id.iv_backdrop);
        ivPoster = findViewById(R.id.iv_poster);
        tvTitle = findViewById(R.id.tv_title);
        tvGenre = findViewById(R.id.tv_genre);
        tvAdult = findViewById(R.id.tv_adult);
        tvOverview = findViewById(R.id.tv_overview);
        tvDuration = findViewById(R.id.tv_duration);
        ratingBar = findViewById(R.id.rating_bar);
        rvCast = findViewById(R.id.rv_cast);

        String id = getIntent().getStringExtra("id");
        String type = getIntent().getStringExtra("type");
        castList = new ArrayList<>();

        if (type.equals("movie")) {
            Call<Movie> call = ApiConfig.getApiService().getMovieDetails(Integer.parseInt(id));
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                    Movie movie = response.body();

                    assert movie != null;
                    String title = movie.getTitle() + " (" + movie.getReleaseDate().substring(0,4) + ")";
                    StringBuilder genreList = new StringBuilder();
                    for (Genre genre : movie.getGenres()) {
                        genreList.append(genre.getName()).append(", ");
                    }
                    String duration = movie.getRuntime() / 60 + "h " + movie.getRuntime() % 60 + "m";
                    genreList.deleteCharAt(genreList.length()-2);
                    String adult = movie.isAdult() ? "18+" : "PG";
                    String overview = movie.getOverview();
                    double rating = movie.getVoteAverage() / 2;
                    String poster = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
                    String backdrop = "https://image.tmdb.org/t/p/w500" + movie.getBackdropPath();
                    if (movie.getBackdropPath() == null) {
                        backdrop = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
                    }

                    Glide.with(DetailActivity.this)
                            .load(poster)
                            .centerCrop()
                            .into(ivPoster);

                    Glide.with(DetailActivity.this)
                            .load(backdrop)
                            .centerCrop()
                            .into(ivBackdrop);

                    ratingBar.setRating((float) rating);
                    tvTitle.setText(title);
                    tvGenre.setText(genreList);
                    tvAdult.setText(adult);
                    tvOverview.setText(overview);
                    tvDuration.setText(duration);
                }

                @Override
                public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                    Toast.makeText(DetailActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
            Call<CreditResponse> callCredit = ApiConfig.getApiService().getMovieCredits(Integer.parseInt(id));
            callCredit.enqueue(new Callback<CreditResponse>() {
                @Override
                public void onResponse(@NonNull Call<CreditResponse> call, @NonNull Response<CreditResponse> response) {
                    CreditResponse credit = response.body();
                    assert credit != null;
                    castList.addAll(credit.getCast());
                    castAdapter = new CastAdapter(DetailActivity.this, castList);
                    rvCast.setAdapter(castAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<CreditResponse> call, @NonNull Throwable t) {
                    Toast.makeText(DetailActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
        else if (type.equals("tvshow")) {
            Call<TVShow> call = ApiConfig.getApiService().getTVShowDetails(Integer.parseInt(id));
            call.enqueue(new Callback<TVShow>() {
                @Override
                public void onResponse(@NonNull Call<TVShow> call, @NonNull Response<TVShow> response) {
                    TVShow tvShow = response.body();

                    assert tvShow != null;
                    String name = tvShow.getName() + " (" + tvShow.getFirstAirDate().substring(0,4) + ")";
                    StringBuilder genreList = new StringBuilder();
                    for (Genre genre : tvShow.getGenres()) {
                        System.out.println(genre.getName());
                        genreList.append(genre.getName()).append(", ");
                    }
                    genreList.deleteCharAt(genreList.length()-2);
                    System.out.println(genreList);
                    String adult = tvShow.isAdult() ? "18+" : "All ages";
                    String overview = tvShow.getOverview();
                    String poster = "https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath();
                    String backdrop = "https://image.tmdb.org/t/p/w500" + tvShow.getBackdropPath();
                    if (tvShow.getBackdropPath() == null) {
                        backdrop = "https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath();
                    }

                    Glide.with(DetailActivity.this)
                            .load(poster)
                            .centerCrop()
                            .into(ivPoster);

                    Glide.with(DetailActivity.this)
                            .load(backdrop)
                            .centerCrop()
                            .into(ivBackdrop);

                    tvTitle.setText(name);
                    tvGenre.setText(genreList);
                    tvAdult.setText(adult);
                    tvOverview.setText(overview);
                }

                @Override
                public void onFailure(@NonNull Call<TVShow> call, @NonNull Throwable t) {
                    Toast.makeText(DetailActivity.this, "Data tidak terload!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }

    }
}