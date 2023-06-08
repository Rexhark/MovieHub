package com.example.moviehub.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviehub.R;
import com.example.moviehub.activity.DetailActivity;
import com.example.moviehub.model.Favorite;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    private final List<Favorite> items;
    private final Context context;

    public FavoriteAdapter(Context context, List<Favorite> items) {
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        Favorite favorite = items.get(position);
        String title;
        if (favorite.getYear() == 0) {
            title = favorite.getTitle();
            holder.tvTitle.setText(title);
        } else {
            title = favorite.getTitle() + " (" + favorite.getYear() + ")";
            holder.tvTitle.setText(title);
        }

        if (favorite.getPosterPath().contains("null")) {
            Glide.with(context)
                    .load(R.drawable.image_placeholder)
                    .centerCrop()
                    .into(holder.ivPoster);
        } else {
            Glide.with(context)
                    .load(favorite.getPosterPath())
                    .centerCrop()
                    .into(holder.ivPoster);
        }

        if (favorite.getType().equals("movie")) {
            holder.ivType.setImageResource(R.drawable.movie);
        } else if (favorite.getType().equals("tvshow")) {
            holder.ivType.setImageResource(R.drawable.tv_show);
        }

        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", String.valueOf(favorite.getId()));
            intent.putExtra("type", favorite.getType());
            System.out.println(favorite.getId());
            System.out.println(favorite.getType());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView container;
        ImageView ivPoster, ivType;
        TextView tvTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            ivType = itemView.findViewById(R.id.iv_type);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
