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
import android.widget.Toast;

import com.system.perfect.tugas2.MainActivity;
import com.system.perfect.tugas2.R;

import java.util.Calendar;

import static com.system.perfect.tugas2.MainActivity.CHANNEL_ID;
import static com.system.perfect.tugas2.MainActivity.CHANNEL_NAME;

public class DailyReceiver extends BroadcastReceiver {

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    public static final String TYPE_DAILY = "Daily Reminder";

    private static final int ID_DAILY = 101;

    public DailyReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = context.getString(R.string.daily_reminder_text);
        String title = context.getString(R.string.app_name);
        int notifId = ID_DAILY;

        showAlarmNotification(context, title, message, notifId);
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String ID = CHANNEL_ID;
        String NAME = CHANNEL_NAME;

        NotificationManager notifManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ID)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            builder.setChannelId(ID);
            if (notifManagerCompat != null) {
                notifManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notif = builder.build();
        if (notifManagerCompat != null) {
            notifManagerCompat.notify(notifId, notif);
        }
    }

    public void setRepeatingAlarm(Context context) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(context, DailyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                    pendingIntent);
            //Toast.makeText(context, "New Repeating alarm set up", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

}
