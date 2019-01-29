package com.system.perfect.tugas2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.system.perfect.tugas2.BuildConfig;
import com.system.perfect.tugas2.R;
import com.system.perfect.tugas2.model.Movie;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    Context context;
    List<Movie> movieList;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
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
        favoriteViewHolder.textTitle.setText(movieList.get(i).getTitle());
        Glide.with(context)
                .load(BuildConfig.TMDB_IMAGE_SMALL + getMovieList().get(i).getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(favoriteViewHolder.imgPoster);
        favoriteViewHolder.textOverview.setText(movieList.get(i).getOverview());
        favoriteViewHolder.textReleaseDate.setText(getRelease(movieList.get(i).getReleaseDate()));
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
        return movieList!=null? movieList.size() : 0;
    }
}
