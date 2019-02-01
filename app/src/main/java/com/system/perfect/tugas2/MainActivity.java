package com.system.perfect.tugas2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
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
import com.system.perfect.tugas2.support.SharedPreference;
import com.system.perfect.tugas2.support.notification.DailyReceiver;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private SectionAdapter mSectionAdapter;
    private ViewPager mViewPager;
    private DailyReceiver receiver;
    SharedPreference session;

    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifManager;

    public static final int NOTIFICATION_ID = 1;
    public static String CHANNEL_NAME = "Movie Catalog channel";
    public static String CHANNEL_ID = "Channel_16";

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

        session = new SharedPreference(this);

        if (session.getFirstRun()){
            receiver = new DailyReceiver();
            receiver.setRepeatingAlarm(this);
            session.setFirstRun(false);
        }
        /*else {
            receiver = new DailyReceiver();
            receiver.cancelAlarm(this);
            session.setFirstRun(false);
        }*/

    }

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            mNotifManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    };

    public void sendNotification(View view) {
        Intent x = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, x, 0);

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_white_24dp))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.app_name))
                .setContentIntent(pendingIntent)
                .setSubText(getResources().getString(R.string.subtext_notif))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel cnl = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notifManager != null) {
                notifManager.createNotificationChannel(cnl);
            }
        }

        Notification notification = builder.build();

        if (notifManager != null) {
            notifManager.notify(NOTIFICATION_ID, notification);
        }

        new Handler().postDelayed(timeRunnable, 6000);
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
