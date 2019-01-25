package com.system.perfect.tugas2;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.system.perfect.tugas2.adapter.SectionAdapter;
import com.system.perfect.tugas2.fragments.NowPlayingFragment;
import com.system.perfect.tugas2.fragments.PopularFragment;
import com.system.perfect.tugas2.fragments.UpcomingFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private SectionAdapter mSectionAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        mSectionAdapter = new SectionAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setViewPager(mViewPager);

        TabLayout tab = findViewById(R.id.tabs);
        tab.setupWithViewPager(mViewPager);
    }

    private void setViewPager(ViewPager vPager){
        String a = getString(R.string.tab_text_1);
        String b = getString(R.string.tab_text_2);
        String c = getString(R.string.tab_text_3);
        SectionAdapter adapter = new SectionAdapter(getSupportFragmentManager());
        adapter.addFrag(new NowPlayingFragment(),a);
        adapter.addFrag(new UpcomingFragment(),b);
        adapter.addFrag(new PopularFragment(),c);
        vPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
