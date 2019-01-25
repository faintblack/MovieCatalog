package com.system.perfect.tugas2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.perfect.tugas2.R;

class PopularViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgPoster;
    public TextView textTitle, textOverview, textReleaseDate;

    public PopularViewHolder(@NonNull View itemView) {
        super(itemView);
        imgPoster = itemView.findViewById(R.id.img_popular);
        textTitle = itemView.findViewById(R.id.title_all_movies);
        textOverview = itemView.findViewById(R.id.overview_all_movies);
        textReleaseDate = itemView.findViewById(R.id.release_all_movies);
    }
}
