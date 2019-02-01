package com.system.perfect.tugas2.support.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.system.perfect.tugas2.DetailMovieActivity;
import com.system.perfect.tugas2.R;
import com.system.perfect.tugas2.model.Movie;

import java.util.Calendar;

import static com.system.perfect.tugas2.MainActivity.CHANNEL_ID;
import static com.system.perfect.tugas2.MainActivity.CHANNEL_NAME;

public class DailyReleaseReceiver extends BroadcastReceiver {

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    public static final String TYPE_RELEASE = "Release Today Reminder";

    private static final int ID_RELEASE = 102;

    public DailyReleaseReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra("title");
        String title = context.getString(R.string.app_name);
        String id = intent.getStringExtra("id");
        int notifId = ID_RELEASE;

        showAlarmNotification(context, id, title, message, notifId);
    }

    private void showAlarmNotification(Context context, String id,  String title, String message, int notifId) {

        NotificationManager notifManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra("id_movie", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message + " " + context.getString(R.string.release_today_text))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            builder.setChannelId(CHANNEL_ID);
            if (notifManagerCompat != null) {
                notifManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notif = builder.build();
        if (notifManagerCompat != null) {
            notifManagerCompat.notify(notifId, notif);
        }
    }

    public void setRepeatingAlarm(Context context, Movie movie) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        while (calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.HOUR, 2);
        }

        Intent intent = new Intent(context, DailyReleaseReceiver.class);
        intent.putExtra("id", movie.getId().toString());
        intent.putExtra("title", movie.getTitle());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            //Toast.makeText(context, "New Release today reminder set up", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReleaseReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);

        alarmManager.cancel(pendingIntent);
    }


}
