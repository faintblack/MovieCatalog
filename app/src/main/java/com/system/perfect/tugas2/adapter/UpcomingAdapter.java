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

import java.util.List;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingViewHolder> {

    Context context;
    List<Movie> movieList;

    public UpcomingAdapter(Context context) {
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
    public UpcomingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_upcoming, viewGroup, false);
        return new UpcomingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingViewHolder upcomingViewHolder, int i) {
        upcomingViewHolder.textTitle.setText(movieList.get(i).getTitle());
        Glide.with(context)
                .load(BuildConfig.TMDB_IMAGE_MEDIUM + getMovieList().get(i).getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(upcomingViewHolder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return movieList!=null? movieList.size() : 0;
    }
}
