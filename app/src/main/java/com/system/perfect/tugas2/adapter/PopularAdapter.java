package com.system.perfect.tugas2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.system.perfect.tugas2.BuildConfig;
import com.system.perfect.tugas2.R;
import com.system.perfect.tugas2.model.Genre;
import com.system.perfect.tugas2.model.Movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularViewHolder> {

    Context context;
    List<Movie> movieList;

    public PopularAdapter(Context context) {
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
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_popular, viewGroup, false);
        return new PopularViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder popularViewHolder, int i) {
        popularViewHolder.textTitle.setText(movieList.get(i).getTitle());
        Glide.with(context)
                .load(BuildConfig.TMDB_IMAGE_SMALL + getMovieList().get(i).getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(popularViewHolder.imgPoster);

        String description = movieList.get(i).getOverview();
        if (description.length() > 180){
            description = description.substring(0, 180) + "...";
        }

        popularViewHolder.textOverview.setText(description);
        popularViewHolder.textReleaseDate.setText(getRelease(movieList.get(i).getReleaseDate()));
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
