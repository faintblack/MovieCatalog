package com.system.perfect.tugas2.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.perfect.tugas2.BuildConfig;
import com.system.perfect.tugas2.R;
import com.system.perfect.tugas2.adapter.NowPlayingAdapter;
import com.system.perfect.tugas2.model.Movie;
import com.system.perfect.tugas2.viewmodel.NowPlayingViewModel;

import java.util.List;
import java.util.Objects;

public class NowPlayingFragment extends Fragment {
    private static final String TAG = "NowPlayingFragment";

    private NowPlayingViewModel viewModel;
    private NowPlayingAdapter adapt;
    private RecyclerView rvNowPlaying;
    private List<Movie> movieList;

    public static NowPlayingFragment newInstance() {
        return new NowPlayingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.now_playing_fragment, container, false);
        rvNowPlaying = v.findViewById(R.id.rv_now_playing);
        rvNowPlaying.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvNowPlaying.setHasFixedSize(true);
        adapt = new NowPlayingAdapter(getContext());
        rvNowPlaying.setAdapter(adapt);
        // ItemClickSupport here
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(NowPlayingViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData(){
        viewModel.getDataNowPlaying(BuildConfig.TMDB_API_KEY).observe(Objects.requireNonNull(getActivity()), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapt.setMovieList(movies);
            }
        });
    }
}
