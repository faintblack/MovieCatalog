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
import com.system.perfect.tugas2.viewmodel.UpcomingViewModel;

public class UpcomingFragment extends Fragment {
    private static final String TAG = "UpcomingFragment";

    private UpcomingViewModel mViewModel;

    public static UpcomingFragment newInstance() {
        return new UpcomingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.upcoming_fragment, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UpcomingViewModel.class);
        // TODO: Use the ViewModel
    }

}
