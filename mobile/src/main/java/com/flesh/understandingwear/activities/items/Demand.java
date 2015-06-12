package com.flesh.understandingwear.activities.items;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.flesh.understandingwear.R;
import com.flesh.understandingwear.broadcastReceivers.DemandIntentReceiver;

public class Demand extends AppCompatActivity {

    Button btn;
    Notification notification;
    NotificationManagerCompat notificationManager;
    int notificationId = 1;
    public static final String ACTION_DEMAND = "com.androidweardocs.ACTION_DEMAND";
    public static final String EXTRA_MESSAGE = "com.androidweardocs.EXTRA_MESSAGE";
    public static final String EXTRA_VOICE_REPLY = "com.androidweardocs.EXTRA_VOICE_REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand);
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

    private NotificationCompat.Builder createWearNotificationBuild(String title, String content, int drawable, NotificationCompat.WearableExtender wearableExtender) {
        return new NotificationCompat.Builder(this)
                .setSmallIcon(drawable)
                .setContentTitle(title)
                .setContentText(content)
                .extend(wearableExtender);//needed to go to wearables.
    }

    private void initNotifications() {


        Intent demandIntent = new Intent(this, DemandIntentReceiver.class)
                .putExtra(EXTRA_MESSAGE, "Reply selected.")
                .setAction(ACTION_DEMAND);
        PendingIntent demandPendingIntent =
                PendingIntent.getBroadcast(this, 0, demandIntent, 0);
        //reply label
        String replyLabel = getResources().getString(R.string.app_name);
        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .build();
        NotificationCompat.Action replyAction =
                new NotificationCompat.Action.Builder(R.drawable.ic_android_white_24dp,
                        "Reply", demandPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        //Create Wearable Extender
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .addAction(replyAction);
        //Create the notification
        notification = createWearNotificationBuild("Hello Android Wear", "First Wearable demand.", R.drawable.powered_by_google_dark, wearableExtender).build();
        //Create notification manager
        notificationManager = NotificationManagerCompat.from(this);
    }

}
