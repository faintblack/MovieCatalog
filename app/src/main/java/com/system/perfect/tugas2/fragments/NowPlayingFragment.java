package com.system.perfect.tugas2.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.perfect.tugas2.R;
import com.system.perfect.tugas2.viewmodel.NowPlayingViewModel;

public class NowPlayingFragment extends Fragment {
    private static final String TAG = "NowPlayingFragment";

    private NowPlayingViewModel mViewModel;

    public static NowPlayingFragment newInstance() {
        return new NowPlayingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.now_playing_fragment, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NowPlayingViewModel.class);
        // TODO: Use the ViewModel
    }

}
