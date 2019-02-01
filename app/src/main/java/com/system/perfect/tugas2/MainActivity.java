package com.system.perfect.tugas2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.system.perfect.tugas2.adapter.SectionAdapter;
import com.system.perfect.tugas2.fragments.FavoriteFragment;
import com.system.perfect.tugas2.fragments.NowPlayingFragment;
import com.system.perfect.tugas2.fragments.PopularFragment;
import com.system.perfect.tugas2.fragments.UpcomingFragment;
import com.system.perfect.tugas2.model.Movie;
import com.system.perfect.tugas2.support.SharedPreference;
import com.system.perfect.tugas2.support.notification.DailyReceiver;
import com.system.perfect.tugas2.support.notification.DailyReleaseReceiver;
import com.system.perfect.tugas2.viewmodel.NowPlayingViewModel;
import com.system.perfect.tugas2.viewmodel.UpcomingViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private SectionAdapter mSectionAdapter;
    private ViewPager mViewPager;
    private DailyReceiver receiver;
    private DailyReleaseReceiver releaseReceiver;
    private SharedPreference session;
    private UpcomingViewModel viewModel;

    public static String CHANNEL_NAME = "Movie Catalog channel";
    public static String CHANNEL_ID = "Channel_16";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        receiver = new DailyReceiver();
        releaseReceiver = new DailyReleaseReceiver();
        viewModel = ViewModelProviders.of(this).get(UpcomingViewModel.class);

        mSectionAdapter = new SectionAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        setViewPager(mViewPager);
        TabLayout tab = findViewById(R.id.tabs);
        tab.setupWithViewPager(mViewPager);

        session = new SharedPreference(this);

        if (session.getFirstRun()){
            receiver.setRepeatingAlarm(this);
            session.setFirstRun(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Date date = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final String current = date_format.format(date);

        viewModel.getDataUpcoming(BuildConfig.TMDB_API_KEY).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                for (Movie film : movies){
                    if (film.getReleaseDate().equals(current)){
                        releaseReceiver.setRepeatingAlarm(MainActivity.this, film);
                    }
                }
            }
        });
    }

    private void setViewPager(ViewPager vPager){
        String a = getString(R.string.tab_text_1);
        String b = getString(R.string.tab_text_2);
        String c = getString(R.string.tab_text_4);
        String d = getString(R.string.tab_text_3);
        SectionAdapter adapter = new SectionAdapter(getSupportFragmentManager());
        adapter.addFrag(new NowPlayingFragment(),a);
        adapter.addFrag(new UpcomingFragment(),b);
        adapter.addFrag(new FavoriteFragment(),c);
        adapter.addFrag(new PopularFragment(),d);
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
