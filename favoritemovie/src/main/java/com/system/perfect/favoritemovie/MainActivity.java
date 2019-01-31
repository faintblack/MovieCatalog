package com.system.perfect.favoritemovie;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.system.perfect.favoritemovie.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerAdapter adapt;
    RecyclerView rv_favorite_module;
    CoordinatorLayout coord;
    ProgressBar pb;
    TextView emptyText;

    private final int LOAD_MOVIES_ID = 117;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.pb_favorite_module);
        emptyText = findViewById(R.id.empty_favorite);
        rv_favorite_module = findViewById(R.id.rv_favorite_module);
        adapt = new RecyclerAdapter(this);
        rv_favorite_module.setLayoutManager(new LinearLayoutManager(this));
        rv_favorite_module.setAdapter(adapt);

        getSupportLoaderManager().initLoader(LOAD_MOVIES_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_MOVIES_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        adapt.setFavourite(cursor);
        pb.setVisibility(View.GONE);
        if (adapt.getItemCount() == 0){
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapt.setFavourite(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
