package com.flesh.understandingwear.activities.items;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.flesh.understandingwear.R;
import com.flesh.understandingwear.threads.SendToDataLayerCheckThread;
import com.flesh.understandingwear.utils.GooglePlayUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

public class Check extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleClient;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;
    Button btnInfo, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        preferences = getPreferences(MODE_PRIVATE);
        btnInfo = (Button) findViewById(R.id.btn_info);
        // Build a new GoogleApiClient
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit = preferences.edit();
                edit.putString("String", "msg");
                edit.commit();
                googleClient.reconnect();
            }
        });
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit = preferences.edit();
                edit.remove("String");
                edit.commit();
                googleClient.reconnect();
            }
        });
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
        new SendToDataLayerCheckThread("/check", preferences.getString("String", ""), googleClient).start();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
