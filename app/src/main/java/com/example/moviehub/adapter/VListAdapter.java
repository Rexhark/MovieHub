package com.example.moviehub.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviehub.R;
import com.example.moviehub.activity.DetailActivity;
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

        String id = "", title = "", posterPath = "", type="";

        if(items.get(position) instanceof Movie) {
            Movie movie = (Movie) items.get(position);
            type = "movie";
            id = String.valueOf(movie.getId());
            title = movie.getTitle();
            posterPath = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        } else if (items.get(position) instanceof TVShow) {
            TVShow tvShow = (TVShow) items.get(position);
            type = "tvshow";
            id = String.valueOf(tvShow.getId());
            title = tvShow.getName();
            posterPath = "https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath();
        }

        holder.tvTitle.setText(title);
        Glide.with(context)
                .load(posterPath)
                .centerCrop()
                .into(holder.ivPoster);

        String finalId = id;
        String finalType = type;
        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", finalId);
            intent.putExtra("type", finalType);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout container;
        ImageView ivPoster;
        TextView tvTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
