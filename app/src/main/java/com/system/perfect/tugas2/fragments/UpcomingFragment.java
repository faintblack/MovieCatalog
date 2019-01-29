package com.system.perfect.tugas2.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.system.perfect.tugas2.BuildConfig;
import com.system.perfect.tugas2.DetailMovieActivity;
import com.system.perfect.tugas2.R;
import com.system.perfect.tugas2.adapter.UpcomingAdapter;
import com.system.perfect.tugas2.model.Movie;
import com.system.perfect.tugas2.support.ItemClickSupport;
import com.system.perfect.tugas2.viewmodel.UpcomingViewModel;

import java.util.List;
import java.util.Objects;

import static com.system.perfect.tugas2.provider.DatabaseContract.CONTENT_URI;

public class UpcomingFragment extends Fragment {
    private static final String TAG = "UpcomingFragment";

    private UpcomingViewModel viewModel;
    private UpcomingAdapter adapt;
    ProgressBar pb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.upcoming_fragment, container, false);
        pb = v.findViewById(R.id.pro_bar_upcoming);
        RecyclerView rvUpcoming = v.findViewById(R.id.rv_upcoming);
        rvUpcoming.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        rvUpcoming.setLayoutManager(layoutManager);
        adapt = new UpcomingAdapter(getContext());
        rvUpcoming.setAdapter(adapt);

        // ItemClickSupport here
        ItemClickSupport.addTo(rvUpcoming).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent x = new Intent(getActivity(), DetailMovieActivity.class);
                Uri uri = Uri.parse(CONTENT_URI+"/"+adapt.getMovieList().get(position).getId().toString());
                x.setData(uri);
                x.putExtra("id_movie", adapt.getMovieList().get(position).getId().toString());
                startActivity(x);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(UpcomingViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData(){
        viewModel.getDataUpcoming(BuildConfig.TMDB_API_KEY).observe(Objects.requireNonNull(getActivity()), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapt.setMovieList(movies);
                pb.setVisibility(View.GONE);
            }
        });
    }
}
