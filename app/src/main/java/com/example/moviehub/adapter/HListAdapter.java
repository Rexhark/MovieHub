package com.example.moviehub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviehub.R;
import com.example.moviehub.model.Movie;
import com.example.moviehub.model.TVShow;

import java.util.List;

public class HListAdapter extends RecyclerView.Adapter<HListAdapter.ViewHolder>{

    private final List<?> items;
    private final Context context;

    public HListAdapter(Context context, List<?> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public HListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.h_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HListAdapter.ViewHolder holder, int position) {

        String title = "", poster = "";

        if(items.get(position) instanceof Movie) {
            Movie movie = (Movie) items.get(position);
            title = movie.getTitle();
            poster = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        } else if (items.get(position) instanceof TVShow) {
            TVShow tvShow = (TVShow) items.get(position);
            title = tvShow.getName();
            poster = "https://image.tmdb.org/t/p/w500" + tvShow.getPosterPath();
        }

        holder.tvTitle.setText(title);
        Glide.with(context)
                .load(poster)
                .centerCrop()
                .into(holder.ivPoster);


    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
