package com.flesh.understandingwear.broadcastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by afleshner on 6/11/2015.
 */
public class MessageReceiver extends BroadcastReceiver {

    messagesRecived mController;
    checkReceived mCheckController;

    public interface messagesRecived{
        void onMessageReceived(String message);
    }
    public interface checkReceived {
        void onCheckReceived(String message);
    }

    public void setOnMessagesRecived(messagesRecived listener){
        mController = listener;
    }

    public void setOnCheckRecieved(checkReceived listener){
        mCheckController = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra("message")!=null) {
            String message = intent.getStringExtra("message");
            // Display message in UI
            mController.onMessageReceived(message);
        }else if(intent.getStringExtra("check")!=null){
            mCheckController.onCheckReceived(intent.getStringExtra("check"));
        }
    }
}
