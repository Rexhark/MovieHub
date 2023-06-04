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

import java.util.List;

public class VListAdapter extends RecyclerView.Adapter<VListAdapter.ViewHolder> {

    private final List<Movie> items;
    private final Context context;

    public VListAdapter(Context context, List<Movie> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_list_layout, parent, false);
        return new VListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VListAdapter.ViewHolder holder, int position) {

        String title = items.get(position).getTitle();
        String poster = "https://image.tmdb.org/t/p/w500" + items.get(position).getPosterPath();

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
