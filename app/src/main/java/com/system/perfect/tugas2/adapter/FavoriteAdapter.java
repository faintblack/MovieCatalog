package com.system.perfect.tugas2.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.system.perfect.tugas2.BuildConfig;
import com.system.perfect.tugas2.DetailMovieActivity;
import com.system.perfect.tugas2.R;
import com.system.perfect.tugas2.model.Movie;
import com.system.perfect.tugas2.support.CustomOnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.system.perfect.tugas2.provider.DatabaseContract.CONTENT_URI;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    Context context;
    Cursor movieList;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public Cursor getMovieList() {
        return movieList;
    }

    public void setMovieList(Cursor movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_favorite, viewGroup, false);
        return new FavoriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int i) {
        final Movie movie = getItem(i);

        favoriteViewHolder.textTitle.setText(movie.getTitle());
        Glide.with(context)
                .load(BuildConfig.TMDB_IMAGE_SMALL + movie.getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(favoriteViewHolder.imgPoster);
        favoriteViewHolder.textOverview.setText(movie.getOverview());
        favoriteViewHolder.textReleaseDate.setText(getRelease(movie.getReleaseDate()));

        favoriteViewHolder.cv_favorite.setOnClickListener(new CustomOnItemClickListener(i,
                new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent x = new Intent(context, DetailMovieActivity.class);
                Uri uri = Uri.parse(CONTENT_URI+"/"+movie.getId());
                x.setData(uri);
                x.putExtra("id_movie", movie.getId().toString());
                context.startActivity(x);
            }
        }));
    }

    private String getRelease(String date){
        String releaseDate = "";
        SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date tgl = formatTanggal.parse(date);
            SimpleDateFormat formatTglBaru = new SimpleDateFormat("dd MMM yyyy");
            releaseDate = formatTglBaru.format(tgl);
        } catch (Exception e){
            e.printStackTrace();
        }
        return releaseDate;
    }

    @Override
    public int getItemCount() {
        return movieList!=null? movieList.getCount() : 0;
    }

    private Movie getItem(int position){
        if (!movieList.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(movieList);
    }
}
