package com.flesh.understandingwear.activities.items;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flesh.understandingwear.R;
import com.flesh.understandingwear.threads.SendToDataLayerMessageThread;
import com.flesh.understandingwear.threads.SendToDataLayerThread;
import com.flesh.understandingwear.utils.GooglePlayUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Wearable;

import java.util.Date;

public class Handheld extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handheld);

        // Build a new GoogleApiClient
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GooglePlayUtils.checkGooglePlayServices(this);
    }


    // Connect to the data layer when the Activity starts
    @Override
    protected void onStart() {
        super.onStart();
        googleClient.connect();

    }

    // Disconnect from the data layer when the Activity stops
    @Override
    protected void onStop() {
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }
        super.onStop();
    }


    @Override
    public void onConnected(Bundle bundle) {
        //DATA
        String WEARABLE_DATA_PATH = "/wearable_data";
        // Create a DataMap object and send it to the data layer
        DataMap dataMap = new DataMap();
        dataMap.putLong("time", new Date().getTime());
        dataMap.putString("hole", "1");
        dataMap.putString("front", "250");
        dataMap.putString("middle", "260");
        dataMap.putString("back", "270");
        //Requires a new thread to avoid blocking the UI
        new SendToDataLayerThread(WEARABLE_DATA_PATH, dataMap, googleClient).start();
        //MESSAGE
        String message = "Hello wearable\n Via the data layer";
        //Requires a new thread to avoid blocking the UI
        new SendToDataLayerMessageThread("/message_path", message,googleClient).start();
    }

    @Override
    public void onConnectionSuspended(int cause) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
