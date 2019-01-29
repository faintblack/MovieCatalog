package com.system.perfect.tugas2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.system.perfect.tugas2.model.SpokenLanguage;
import com.system.perfect.tugas2.provider.FavoriteHelper;
import com.system.perfect.tugas2.viewmodel.DetailMovieViewModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    @BindView(R.id.text_title_detail)
    TextView title;
    @BindView(R.id.genre_detail)
    TextView genre;
    @BindView(R.id.release_date_detail)
    TextView release_date;
    @BindView(R.id.revenue_detail)
    TextView revenue;
    @BindView(R.id.vote_average_detail)
    TextView vote_average;
    @BindView(R.id.countries_detail)
    TextView country;
    @BindView(R.id.tagline)
    TextView tagline;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.language_detail)
    TextView language;
    @BindView(R.id.image_detail)
    ImageView backdrop_path;
    @BindView(R.id.poster_detail)
    ImageView poster;
    @BindView(R.id.fab)
    FloatingActionButton fbFavorite;
    @BindView(R.id.toolbarl)
    Toolbar toolbar;

    String idMovie;
    private ArrayList<Movie> listFavorite;

    DetailMovieViewModel viewModel;
    Movie movieData;

    private int position;
    private FavoriteHelper helper;

    public static int RESULT_ADD = 101;
    public static int RESULT_DELETE = 301;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ButterKnife.bind(this);

        Intent get = getIntent();
        idMovie = get.getStringExtra("id_movie");
        viewModel = ViewModelProviders.of(this).get(DetailMovieViewModel.class);

        initView();

        helper = new FavoriteHelper(this);
        helper.open();

        fbFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavorite();
            }
        });
    }

    private void addFavorite(){
        Movie movieFavorite = new Movie();
        movieFavorite.setId(movieData.getId());
        movieFavorite.setTitle(movieData.getTitle());
        movieFavorite.setOverview(movieData.getOverview());
        movieFavorite.setReleaseDate(movieData.getReleaseDate());
        movieFavorite.setPosterPath(movieData.getPosterPath());

        helper.insertDataFavorite(movieFavorite);
        setResult(RESULT_ADD);
        fbFavorite.setImageResource(R.drawable.ic_favorite_24dp);
        Toast.makeText(this, "Favorite Movie Added!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
        new checkFavoriteAsync().execute();
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
        Glide.with(this).load(BuildConfig.TMDB_IMAGE_SMALL + movie.getPosterPath()).into(poster);
        title.setText(movie.getTitle());
        genre.setText(getGenre(movie.getGenres()));
        release_date.setText(getRelease(movie.getReleaseDate()));
        revenue.setText(getRevenueSeparate(movie.getRevenue()));
        String vote = movie.getVoteAverage().toString();
        vote_average.setText(vote);
        country.setText(getCountry(movie.getProductionCountries()));
        language.setText(getLanguage(movie.getSpokenLanguages()));
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
        return TextUtils.join(", ",countryText);
    }

    private String getLanguage(List<SpokenLanguage> lang){
        List<String> langText = new ArrayList<>();
        for (SpokenLanguage x : lang){
            langText.add(x.getIso6391());
        }
        return TextUtils.join(", ",langText).toUpperCase();
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
        listFavorite = new ArrayList<>();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private class checkFavoriteAsync extends AsyncTask<Void, Void, ArrayList<Movie>>{

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return helper.getDataById(idMovie);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            listFavorite.addAll(movies);

            if (listFavorite.size() != 0){
                fbFavorite.setImageResource(R.drawable.ic_favorite_24dp);
            }
        }
    }

}
