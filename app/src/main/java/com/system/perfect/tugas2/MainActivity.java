package com.system.perfect.tugas2;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.system.perfect.tugas2.adapter.SectionAdapter;
import com.system.perfect.tugas2.fragments.NowPlayingFragment;
import com.system.perfect.tugas2.fragments.PopularFragment;
import com.system.perfect.tugas2.fragments.UpcomingFragment;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private SectionAdapter mSectionAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate : Starting...");

        mSectionAdapter = new SectionAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setViewPager(mViewPager);

        TabLayout tab = findViewById(R.id.tabs);
        tab.setupWithViewPager(mViewPager);

    }

    private void setViewPager(ViewPager vPager){
        SectionAdapter adapter = new SectionAdapter(getSupportFragmentManager());
        adapter.addFrag(new NowPlayingFragment(),"Now Playing");
        adapter.addFrag(new UpcomingFragment(),"Upcoming");
        adapter.addFrag(new PopularFragment(),"Search");
        vPager.setAdapter(adapter);
    }

}
