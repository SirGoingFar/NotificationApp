package com.eemf.sirgoingfar.notificationapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    private static final int REQUEST_CODE = 0;
    private static final int PUSH_NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public NotificationCompat.Builder notificationBuilder(Context context){
        NotificationCompat.Builder notifBuilder =  new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(this, R.color.colorWhite))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(convertIcon(context))
                .setContentTitle("Notification from " + getResources().getString(R.string.app_name))
                .setContentText(getString(R.string.charging_text_body))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.charging_text_body)))
                .setContentIntent(contentIntent(context));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            notifBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);

        return notifBuilder;
    }


    public PendingIntent contentIntent(Context context){
        Intent resultIntent = new Intent(context, ResultActivity.class);

        return PendingIntent.getActivity(
                this,
                REQUEST_CODE,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    public Bitmap convertIcon(Context context){
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background);
    }

    public void pushNotification(View view){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mNotifChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
            );

            assert notificationManager != null;
            notificationManager.createNotificationChannel(mNotifChannel);
        }

        notificationManager.notify(PUSH_NOTIFICATION_ID, notificationBuilder(this).build());
    }

}
