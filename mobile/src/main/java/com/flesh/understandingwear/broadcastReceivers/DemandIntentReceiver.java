package com.flesh.understandingwear.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import com.flesh.understandingwear.activities.items.Demand;

public class DemandIntentReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Demand.ACTION_DEMAND)) {String message =
                intent.getStringExtra(Demand.EXTRA_MESSAGE);
            Log.v("MyTag","Extra message from intent = " + message);
            Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
            CharSequence reply = remoteInput.getCharSequence(Demand.EXTRA_VOICE_REPLY);
            Log.v("MyTag", "User reply from wearable: " + reply);
        }
    }
}
