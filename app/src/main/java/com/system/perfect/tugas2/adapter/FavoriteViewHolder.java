package com.system.perfect.tugas2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.perfect.tugas2.R;

class FavoriteViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgPoster;
    public TextView textTitle;
    public TextView textOverview;
    public TextView textReleaseDate;
    public CardView cv_favorite;

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        imgPoster = itemView.findViewById(R.id.img_favorite);
        textTitle = itemView.findViewById(R.id.title_favorite);
        textOverview = itemView.findViewById(R.id.overview_favorite);
        textReleaseDate = itemView.findViewById(R.id.release_favorite);
        cv_favorite = itemView.findViewById(R.id.cv_favorite);
    }
}
