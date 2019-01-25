package com.system.perfect.tugas2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.system.perfect.tugas2.model.Genre;
import com.system.perfect.tugas2.model.Movie;
import com.system.perfect.tugas2.model.ProductionCountry;
import com.system.perfect.tugas2.viewmodel.DetailMovieViewModel;
import com.system.perfect.tugas2.viewmodel.NowPlayingViewModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DetailMovieActivity extends AppCompatActivity {

    String idMovie;
    TextView title, genre, release_date, revenue, vote_average, country, tagline, overview;
    ImageView backdrop_path;
    Toolbar toolbar;
    DetailMovieViewModel viewModel;
    Movie movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Intent get = getIntent();
        idMovie = get.getStringExtra("id_movie");
        viewModel = ViewModelProviders.of(this).get(DetailMovieViewModel.class);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData(){
        viewModel.getDetail(idMovie, BuildConfig.TMDB_API_KEY).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                setData(movie);
                movieData = movie;
            }
        });
    }

    private void setData(final Movie movie){
        Glide.with(this).load(BuildConfig.TMDB_IMAGE_BIG + movie.getBackdropPath()).into(backdrop_path);
        title.setText(movie.getTitle());
        genre.setText(getGenre(movie.getGenres()));
        release_date.setText(getRelease(movie.getReleaseDate()));
        revenue.setText(getRevenueSeparate(movie.getRevenue()));
        String vote = movie.getVoteAverage().toString();
        vote_average.setText(vote);
        country.setText(getCountry(movie.getProductionCountries()));
        tagline.setText(movie.getTagline());
        overview.setText(movie.getOverview());
        toolbar.setTitle(movie.getTitle());
    }

    private String getGenre(List<Genre> genre){
        List<String> genreText = new ArrayList<>();
        for (Genre x: genre){
            genreText.add(x.getName());
        }
        return TextUtils.join(", ", genreText);
    }

    private String getCountry (List<ProductionCountry> country){
        List<String> countryText = new ArrayList<>();
        for (ProductionCountry x: country){
            countryText.add(x.getName());
        }
        return TextUtils.join("",countryText);
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

    private String getRevenueSeparate(long data){
        NumberFormat number = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setCurrencySymbol("$");
        symbols.setGroupingSeparator(',');
        symbols.setMonetaryDecimalSeparator('.');
        ((DecimalFormat) number).setDecimalFormatSymbols(symbols);
        return number.format(data);
    }

    private void initView(){
        title = findViewById(R.id.text_title_detail);
        genre = findViewById(R.id.genre_detail);
        release_date = findViewById(R.id.release_date_detail);
        revenue = findViewById(R.id.revenue);
        vote_average = findViewById(R.id.vote_average);
        country = findViewById(R.id.country);
        tagline = findViewById(R.id.tagline);
        overview = findViewById(R.id.overview);
        backdrop_path = findViewById(R.id.image_detail);
        toolbar = findViewById(R.id.toolbarl);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}
