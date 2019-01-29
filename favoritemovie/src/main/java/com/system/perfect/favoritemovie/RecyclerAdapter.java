package com.system.perfect.favoritemovie;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.system.perfect.favoritemovie.DatabaseContract.DESCRIPTION;
import static com.system.perfect.favoritemovie.DatabaseContract.POSTER;
import static com.system.perfect.favoritemovie.DatabaseContract.RELEASE_DATE;
import static com.system.perfect.favoritemovie.DatabaseContract.TITLE;
import static com.system.perfect.favoritemovie.DatabaseContract.getColumnString;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    Cursor cursor;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (cursor.moveToPosition(i)){
            viewHolder.textTitle.setText(DatabaseContract.getColumnString(cursor,TITLE));
            viewHolder.textRelease.setText(DatabaseContract.getColumnString(cursor,RELEASE_DATE));
            viewHolder.textDescription.setText(DatabaseContract.getColumnString(cursor,DESCRIPTION));
            Glide.with(context)
                    .load(BuildConfig.TMDB_IMAGE_SMALL + getColumnString(cursor,POSTER))
                    .apply(new RequestOptions().override(350, 550))
                    .into(viewHolder.imgPoster);
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0 ;
    }

    void setFavourite (Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textDescription, textRelease;
        ImageView imgPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.title_favorite_module);
            textDescription = itemView.findViewById(R.id.overview_favorite_module);
            textRelease = itemView.findViewById(R.id.release_favorite_module);
            imgPoster = itemView.findViewById(R.id.img_favorite_module);
        }
    }
}
