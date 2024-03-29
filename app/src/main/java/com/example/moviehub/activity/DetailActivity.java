package com.example.moviehub.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviehub.R;
import com.example.moviehub.adapter.CastAdapter;
import com.example.moviehub.database.ApiConfig;
import com.example.moviehub.database.DatabaseHelper;
import com.example.moviehub.model.Cast;
import com.example.moviehub.model.CreditResponse;
import com.example.moviehub.model.Favorite;
import com.example.moviehub.model.Genre;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.TVShow;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    LinearLayout container;
    ProgressBar progressBar;
    LinearLayout refreshContainer;
    ImageView ivBackdrop, ivPoster, ivRefresh;
    TextView tvTitle, tvGenre, tvAdult, tvOverview, tvDuration;
    RatingBar ratingBar;
    RecyclerView rvCast;
    CastAdapter castAdapter;
    DatabaseHelper dbHelper;
    int id_, year_;
    String title_, type_, posterPath_;

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

        container = findViewById(R.id.container);
        progressBar = findViewById(R.id.progress_bar);
        refreshContainer = findViewById(R.id.refresh_container);
        ivRefresh = findViewById(R.id.iv_refresh);

        dbHelper = new DatabaseHelper(this);

        id_ = 0;
        title_ = "";
        type_ = "";
        year_ = 0;
        posterPath_ = "";
        ActionBar actionBar = getSupportActionBar();

        if (!isNetworkConnected()) {

            refreshContainer.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            container.setVisibility(View.GONE);
            assert actionBar != null;
            actionBar.setTitle("MovieHub");

            ivRefresh.setOnClickListener(v -> {
                refreshContainer.setVisibility(View.GONE);
                container.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            });

        } else {

            progressBar.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);
            refreshContainer.setVisibility(View.GONE);

            String id = getIntent().getStringExtra("id");
            String type = getIntent().getStringExtra("type");

            List<Cast> castList = new ArrayList<>();

            System.out.println("id: " + id);

            if (type.equals("movie")) {
                assert actionBar != null;
                actionBar.setTitle("Movie Detail");
                loadMovieDetail(Integer.parseInt(id), castList);
            }
            else if (type.equals("tvshow")) {
                assert actionBar != null;
                actionBar.setTitle("TV Show Detail");
                loadTvShowDetail(Integer.parseInt(id), castList);
            }

            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void loadMovieDetail(int id, List<Cast> castList){
        Call<Movie> call = ApiConfig.getApiService().getMovieDetails(id);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                Movie movie = response.body();

                assert movie != null;

                // Cek title
                String title;
                if (movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty()){
                    title = movie.getTitle() + " (" + movie.getReleaseDate().substring(0,4) + ")";
                }
                else {
                    title = movie.getTitle();
                }

                // Cek genre
                StringBuilder genreList = new StringBuilder();
                if (movie.getGenres() != null && movie.getGenres().size() > 0) {
                    for (Genre genre : movie.getGenres()) {
                        genreList.append(genre.getName()).append(", ");
                    }
                    genreList.deleteCharAt(genreList.length()-2);
                }
                else {
                    genreList.append("-");
                }

                String duration = movie.getRuntime() / 60 + "h " + movie.getRuntime() % 60 + "m";
                String adult = movie.isAdult() ? "18+" : "PG";

                // Cek overview
                String overview;
                if (movie.getOverview() != null && !movie.getOverview().isEmpty()) {
                    overview = movie.getOverview();
                }
                else {
                    overview = "-";
                }

                double rating = movie.getVoteAverage() / 2;

                String poster = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();

                // Cek backdrop
                String backdrop;
                if (movie.getBackdropPath() == null) {
                    backdrop = poster;
                }
                else {
                    backdrop = "https://image.tmdb.org/t/p/original" + movie.getBackdropPath();
                }

                // Cek poster
                if(!isDestroyed()) {
                    if (movie.getPosterPath() != null) {
                        Glide.with(DetailActivity.this)
                                .load(poster)
                                .centerCrop()
                                .into(ivPoster);
                        Glide.with(DetailActivity.this)
                                .load(backdrop)
                                .centerCrop()
                                .into(ivBackdrop);
                    }
                }

                ratingBar.setRating((float) rating);
                tvTitle.setText(title);
                tvGenre.setText(genreList);
                tvAdult.setText(adult);
                tvOverview.setText(overview);
                tvDuration.setText(duration);

                id_ = movie.getId();
                title_ = movie.getTitle();
                type_ = "movie";
                if (movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty()){
                    year_ = Integer.parseInt(movie.getReleaseDate().substring(0,4));
                } else {
                    year_ = 0;
                }
                posterPath_ = poster;
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                Toast.makeText(DetailActivity.this, "Please check your connnection", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
        Call<CreditResponse> callCredit = ApiConfig.getApiService().getMovieCredits(id);
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
                Toast.makeText(DetailActivity.this, "Please check your connnection", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
        container.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        refreshContainer.setVisibility(View.GONE);
    }

    public void loadTvShowDetail(int id, List<Cast> castList){
        Call<TVShow> call = ApiConfig.getApiService().getTVShowDetails(id);
        call.enqueue(new Callback<TVShow>() {
            @Override
            public void onResponse(@NonNull Call<TVShow> call, @NonNull Response<TVShow> response) {
                TVShow tvShow = response.body();

                assert tvShow != null;

                // Cek name
                String name;
                if (tvShow.getFirstAirDate() != null && !tvShow.getFirstAirDate().isEmpty()){
                    name = tvShow.getName() + " (" + tvShow.getFirstAirDate().substring(0,4) + ")";
                }
                else {
                    name = tvShow.getName();
                }

                // Cek genre
                StringBuilder genreList = new StringBuilder();
                if (tvShow.getGenres() != null && tvShow.getGenres().size() > 0) {
                    for (Genre genre : tvShow.getGenres()) {
                        genreList.append(genre.getName()).append(", ");
                    }
                    genreList.deleteCharAt(genreList.length()-2);
                }
                else {
                    genreList.append("-");
                }

                String episode = tvShow.getNumberOfEpisodes() + " Episodes";
                String adult = tvShow.isAdult() ? "18+" : "PG";

                // Cek overview
                String overview;
                if (tvShow.getOverview() != null && !tvShow.getOverview().isEmpty()) {
                    overview = tvShow.getOverview();
                }
                else {
                    overview = "-";
                }

                double rating = tvShow.getVoteAverage() / 2;

                String poster = "https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath();

                // Cek backdrop
                String backdrop;
                if (tvShow.getBackdropPath() == null) {
                    backdrop = poster;
                }
                else {
                    backdrop = "https://image.tmdb.org/t/p/original" + tvShow.getBackdropPath();
                }

                // Cek poster
                if (!isDestroyed()) {
                    if (tvShow.getPosterPath() != null){
                        Glide.with(DetailActivity.this)
                                .load(poster)
                                .centerCrop()
                                .into(ivPoster);

                        Glide.with(DetailActivity.this)
                                .load(backdrop)
                                .centerCrop()
                                .into(ivBackdrop);
                    }
                }

                ratingBar.setRating((float) rating);
                tvTitle.setText(name);
                tvDuration.setText(episode);
                tvGenre.setText(genreList);
                tvAdult.setText(adult);
                tvOverview.setText(overview);

                id_ = tvShow.getId();
                title_ = tvShow.getName();
                type_ = "tvshow";
                if (tvShow.getFirstAirDate() != null && !tvShow.getFirstAirDate().isEmpty()){
                    year_ = Integer.parseInt(tvShow.getFirstAirDate().substring(0,4));
                }
                else {
                    year_ = 0;
                }
                posterPath_ = poster;
            }

            @Override
            public void onFailure(@NonNull Call<TVShow> call, @NonNull Throwable t) {
                Toast.makeText(DetailActivity.this, "Please check your connnection", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
        Call<CreditResponse> callCredit = ApiConfig.getApiService().getTVShowCredits(id);
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
                Toast.makeText(DetailActivity.this, "Please check your connnection", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
        container.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        refreshContainer.setVisibility(View.GONE);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        MenuItem btnFavorite = menu.findItem(R.id.btn_favorite);
        String id = getIntent().getStringExtra("id");

        if (dbHelper.isFavorite(Integer.parseInt(id))){
            btnFavorite.setIcon(R.drawable.favorite_red);
        } else {
            btnFavorite.setIcon(R.drawable.favorite);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        } else if (item.getItemId()==R.id.btn_favorite){
            if (dbHelper.isFavorite(id_)){
                dbHelper.deleteFavorite(id_);
                item.setIcon(R.drawable.favorite);
                Toast.makeText(this, "Removed from favorite", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.addFavorite(new Favorite(id_, title_, type_, year_, posterPath_));
                item.setIcon(R.drawable.favorite_red);
                Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}