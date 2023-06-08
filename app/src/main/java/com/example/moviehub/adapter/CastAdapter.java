package com.example.moviehub.adapter;

import android.content.Context;
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
import com.example.moviehub.model.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder>{

    private final List<Cast> items;
    private final Context context;

    public CastAdapter(Context context, List<Cast> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_layout, parent, false);
        return new CastAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.ViewHolder holder, int position) {

        String name, character, portraitPath;

        Cast cast = items.get(position);
        name = cast.getName();
        character = cast.getCharacter();
        portraitPath = "https://image.tmdb.org/t/p/w500" + cast.getProfilePath();

        holder.tvName.setText(name);
        holder.tvCharacter.setText(character);
        if (cast.getProfilePath() != null) {
            Glide.with(context)
                    .load(portraitPath)
                    .centerCrop()
                    .into(holder.ivPortrait);
        } else {
            Glide.with(context)
                    .load(R.drawable.profile_placeholder)
                    .centerCrop()
                    .into(holder.ivPortrait);
        }

    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView container;
        ImageView ivPortrait;
        TextView tvName, tvCharacter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            ivPortrait = itemView.findViewById(R.id.iv_portrait);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCharacter = itemView.findViewById(R.id.tv_character);
        }
    }
}
