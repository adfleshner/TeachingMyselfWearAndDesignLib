package com.flesh.understandingwear.activities.items;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat;
import android.app.Notification;
import android.view.View;
import android.widget.Button;

import com.flesh.understandingwear.R;


public class Notify extends AppCompatActivity {

    Button btn;
    Notification notification;
    NotificationManagerCompat notificationManager;
    int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initNotifications();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationManager.notify(notificationId, notification);
            }
        });

    }

    private void initView() {
        btn = (Button) findViewById(R.id.clickMe);
    }

    private Notification createWearNotification(String title, String content, int drawable, NotificationCompat.WearableExtender wearableExtender) {
        return new NotificationCompat.Builder(this)
                .setSmallIcon(drawable)
                .setContentTitle(title)
                .setContentText(content)
                .extend(wearableExtender)//needed to go to wearables.
                .build();
    }

    private void initNotifications() {
        //Create Wearable Extender
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintShowBackgroundOnly(true);
        //Create the notification
        notification = createWearNotification("Hello Android Wear", "First Wearable notification.", R.drawable.powered_by_google_dark, wearableExtender);
        //Create notification manager
        notificationManager = NotificationManagerCompat.from(this);
    }

}