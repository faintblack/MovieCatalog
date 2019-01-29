package com.system.perfect.tugas2.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.widget.Toast;

import com.system.perfect.tugas2.DetailMovieActivity;
import com.system.perfect.tugas2.R;
import com.system.perfect.tugas2.adapter.FavoriteAdapter;
import com.system.perfect.tugas2.model.Movie;
import com.system.perfect.tugas2.provider.FavoriteHelper;
import com.system.perfect.tugas2.support.ItemClickSupport;

import java.util.ArrayList;
import java.util.LinkedList;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel viewModel;
    private FavoriteAdapter adapt;
    private LinkedList<Movie> list;
    private FavoriteHelper helper;

    ProgressBar pb;
    TextView a;
    RecyclerView rv_favorite;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favorite_fragment, container, false);
        //ButterKnife.bind(v);
        a = v.findViewById(R.id.testtt);
        pb = v.findViewById(R.id.pro_bar_favorite);
        rv_favorite = v.findViewById(R.id.rv_favorite);
        rv_favorite.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_favorite.setLayoutManager(layoutManager);

        list = new LinkedList<>();

        adapt = new FavoriteAdapter(getContext());
        adapt.setMovieList(list);
        rv_favorite.setAdapter(adapt);

        helper = new FavoriteHelper(getContext());
        helper.open();

        new LoadFavoriteAsync().execute();

        ItemClickSupport.addTo(rv_favorite).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent x = new Intent(getActivity(), DetailMovieActivity.class);
                x.putExtra("id_movie", adapt.getMovieList().get(position).getId().toString());
                startActivity(x);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
    }

    private class LoadFavoriteAsync extends AsyncTask<Void, Void, ArrayList<Movie>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
            if (list.size() > 0){
                list.clear();
            }
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return helper.getData();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            pb.setVisibility(View.GONE);
            list.addAll(movies);
            adapt.setMovieList(list);

            if (list.size() == 0){
                a.setVisibility(View.VISIBLE);
                //Toast.makeText(getContext(), "Tidak ada data favorite saat ini",Toast.LENGTH_LONG).show();
            }
        }
    }

}
