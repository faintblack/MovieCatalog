package com.system.perfect.tugas2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.system.perfect.tugas2.support.notification.DailyReleaseReceiver;
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

import static com.system.perfect.tugas2.provider.DatabaseContract.CONTENT_URI;
import static com.system.perfect.tugas2.provider.DatabaseHelper.DESCRIPTION;
import static com.system.perfect.tugas2.provider.DatabaseHelper.ID;
import static com.system.perfect.tugas2.provider.DatabaseHelper.POSTER;
import static com.system.perfect.tugas2.provider.DatabaseHelper.RELEASE_DATE;
import static com.system.perfect.tugas2.provider.DatabaseHelper.TITLE;

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

    private DailyReleaseReceiver releaseReceiver;
    DetailMovieViewModel viewModel;
    Movie movieData;

    private FavoriteHelper helper;

    public static int RESULT_ADD = 101;
    public static int RESULT_DELETE = 301;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ButterKnife.bind(this);
        releaseReceiver = new DailyReleaseReceiver();

        Intent get = getIntent();
        idMovie = get.getStringExtra("id_movie");
        viewModel = ViewModelProviders.of(this).get(DetailMovieViewModel.class);

        Uri uri = getIntent().getData();
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()) movieData = new Movie(cursor);
                cursor.close();
            }
        }

        initView();

        helper = new FavoriteHelper(this);
        helper.open();

        fbFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listFavorite.size() == 0){
                    addFavorite();
                } else {
                    removeFavorite(idMovie);
                }
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

        ContentValues values = new ContentValues();
        values.put(ID,movieData.getId());
        values.put(TITLE,movieData.getTitle());
        values.put(DESCRIPTION, movieData.getOverview());
        values.put(RELEASE_DATE, movieData.getReleaseDate());
        values.put(POSTER, movieData.getPosterPath());

        getContentResolver().insert(CONTENT_URI, values);
        setResult(RESULT_ADD);
        fbFavorite.setImageResource(R.drawable.ic_favorite_24dp);
        new checkFavoriteAsync().execute();
        Toast.makeText(this, R.string.toast_add_favorite,Toast.LENGTH_LONG).show();
    }

    private void removeFavorite(String id){
        getContentResolver().delete(getIntent().getData(),id,null);
        setResult(RESULT_DELETE, null);
        listFavorite.clear();
        fbFavorite.setImageResource(R.drawable.ic_favorite_border_24dp);
        Toast.makeText(this, R.string.toast_remove_favorite,Toast.LENGTH_LONG).show();
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
            } else {
                fbFavorite.setImageResource(R.drawable.ic_favorite_border_24dp);
            }
        }
    }

}
