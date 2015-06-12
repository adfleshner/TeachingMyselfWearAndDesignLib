package com.flesh.understandingwear;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flesh.understandingwear.broadcastRecievers.MessageReceiver;

public class MainWearActivity extends Activity {

    private TextView mTextView;
    MessageReceiver messageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wear);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                ((Button)stub.findViewById(R.id.btnCheck)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainWearActivity.this,CheckWearActivity.class);
                        startActivity(i);
                    }
                });
                registerMessagesReceiverService();
            }
        });
    }

    private void registerMessagesReceiverService() {
        // Register the local broadcast receiver, defined in step 3.
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
        messageReceiver.setOnMessagesRecived(new MessageReceiver.messagesRecived() {
            @Override
            public void onMessageReceived(String message) {
                mTextView.setText(message);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }
}
