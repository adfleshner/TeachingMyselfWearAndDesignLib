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
import android.widget.Toast;

import com.flesh.understandingwear.broadcastRecievers.MessageReceiver;

public class CheckWearActivity extends Activity {

    TextView mTextView;
    MessageReceiver messageReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                registerCheckReceiverService();
            }
        });
    }

    private void registerCheckReceiverService() {
        // Register the local broadcast receiver, defined in step 3.
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
        messageReceiver.setOnCheckRecieved(new MessageReceiver.checkReceived() {
            @Override
            public void onCheckReceived(String message) {
                if (message.isEmpty()) {
                    Toast.makeText(CheckWearActivity.this, "Go To app", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    mTextView.setText(message);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }

}
