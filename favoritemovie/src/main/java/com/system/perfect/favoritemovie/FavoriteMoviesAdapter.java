package com.system.perfect.favoritemovie;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.system.perfect.favoritemovie.DatabaseContract.DESCRIPTION;
import static com.system.perfect.favoritemovie.DatabaseContract.POSTER;
import static com.system.perfect.favoritemovie.DatabaseContract.RELEASE_DATE;
import static com.system.perfect.favoritemovie.DatabaseContract.TITLE;
import static com.system.perfect.favoritemovie.DatabaseContract.getColumnString;

public class FavoriteMoviesAdapter extends CursorAdapter {

    Context context;

    public FavoriteMoviesAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textTitle = view.findViewById(R.id.title_favorite_module);
        TextView textDescription = view.findViewById(R.id.overview_favorite_module);
        TextView textReleaseDate = view.findViewById(R.id.release_favorite_module);
        ImageView imgPoster = view.findViewById(R.id.img_favorite_module);

        textTitle.setText(getColumnString(cursor,TITLE));
        textDescription.setText(getColumnString(cursor,DESCRIPTION));
        textReleaseDate.setText(getRelease(getColumnString(cursor,RELEASE_DATE)));
        Glide.with(context)
                .load(BuildConfig.TMDB_IMAGE_SMALL + getColumnString(cursor,POSTER))
                .apply(new RequestOptions().override(350, 550))
                .into(imgPoster);
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

}
