package com.system.perfect.tugas2.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.system.perfect.tugas2.R;
import com.system.perfect.tugas2.adapter.FavoriteAdapter;
import com.system.perfect.tugas2.provider.FavoriteHelper;

import static com.system.perfect.tugas2.provider.DatabaseContract.CONTENT_URI;

public class FavoriteFragment extends Fragment {

    private FavoriteAdapter adapt;
    private Cursor list;
    private FavoriteHelper helper;

    ProgressBar pb;
    TextView empty;
    RecyclerView rv_favorite;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favorite_fragment, container, false);

        empty = v.findViewById(R.id.empty_favorite);
        pb = v.findViewById(R.id.pro_bar_favorite);
        rv_favorite = v.findViewById(R.id.rv_favorite);
        rv_favorite.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_favorite.setLayoutManager(layoutManager);

        adapt = new FavoriteAdapter(getContext());
        rv_favorite.setAdapter(adapt);

        helper = new FavoriteHelper(getContext());
        helper.open();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadFavoriteAsync().execute();
    }

    private class LoadFavoriteAsync extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            //return helper.getData();
            return getActivity().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);
            pb.setVisibility(View.GONE);
            list = movies;
            adapt.setMovieList(list);
            if (adapt.getItemCount() == 0){
                empty.setVisibility(View.VISIBLE);
            } else {
                empty.setVisibility(View.GONE);
            }
        }
    }

}
