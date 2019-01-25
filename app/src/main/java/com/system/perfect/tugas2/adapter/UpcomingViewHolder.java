package com.system.perfect.tugas2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.perfect.tugas2.R;

class UpcomingViewHolder extends RecyclerView.ViewHolder {

    public ImageView imgPoster;
    public TextView textTitle;

    public UpcomingViewHolder(@NonNull View itemView) {
        super(itemView);
        imgPoster = itemView.findViewById(R.id.img_upcoming);
        textTitle = itemView.findViewById(R.id.title_upcoming);
    }
}
