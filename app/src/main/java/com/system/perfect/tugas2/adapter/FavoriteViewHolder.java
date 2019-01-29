package com.system.perfect.tugas2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.perfect.tugas2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class FavoriteViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_favorite)
    public ImageView imgPoster;
    @BindView(R.id.title_favorite)
    public TextView textTitle;
    @BindView(R.id.overview_favorite)
    public TextView textOverview;
    @BindView(R.id.release_favorite)
    public TextView textReleaseDate;

    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }
}
