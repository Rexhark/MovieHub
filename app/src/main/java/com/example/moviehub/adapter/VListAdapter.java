package com.example.moviehub.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviehub.R;
import com.example.moviehub.activity.DetailActivity;
import com.example.moviehub.model.Genre;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.TVShow;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class VListAdapter extends RecyclerView.Adapter<VListAdapter.ViewHolder> {

    private final List<?> items;
    private final Context context;

    public VListAdapter(Context context, List<?> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VListAdapter.ViewHolder holder, int position) {

        if(items.get(position) instanceof Movie) {
            Movie movie = (Movie) items.get(position);

            String title = movie.getTitle() + " (" + movie.getReleaseDate().substring(0,4) + ")";
            String adult = movie.isAdult() ? "18+" : "PG";
            double rating = movie.getVoteAverage() / 2;
            String poster = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();

            if (poster.contains("null")) {
                Glide.with(context)
                        .load(R.drawable.image_placeholder)
                        .centerCrop()
                        .into(holder.ivPoster);
            } else {
                Glide.with(context)
                        .load(poster)
                        .centerCrop()
                        .into(holder.ivPoster);
            }

            holder.tvTitle.setText(title);
            holder.tvAdult.setText(adult);
            holder.ratingBar.setRating((float) rating);

            holder.container.setOnClickListener(v -> {
                String id = String.valueOf(movie.getId());
                String type = "movie";
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type", type);
                context.startActivity(intent);
            });
        }
        else if (items.get(position) instanceof TVShow) {
            TVShow tvShow = (TVShow) items.get(position);

            String title = tvShow.getName() + " (" + tvShow.getFirstAirDate().substring(0,4) + ")";
            String adult = tvShow.isAdult() ? "18+" : "PG";
            double rating = tvShow.getVoteAverage() / 2;
            String poster = "https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath();

            if (poster.contains("null")) {
                holder.ivPoster.setImageResource(R.drawable.image_placeholder);
            } else {
                Glide.with(context)
                        .load(poster)
                        .centerCrop()
                        .into(holder.ivPoster);
            }
            holder.tvTitle.setText(title);
            holder.tvAdult.setText(adult);
            holder.ratingBar.setRating((float) rating);

            holder.container.setOnClickListener(v -> {
                String id = String.valueOf(tvShow.getId());
                String type = "tvshow";
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type", type);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView container;
        ImageView ivPoster;
        TextView tvTitle, tvAdult;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAdult = itemView.findViewById(R.id.tv_adult);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }
}
