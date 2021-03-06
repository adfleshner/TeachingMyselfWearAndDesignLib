package com.flesh.understandingwear.threads;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by afleshner on 6/11/2015.
 */
public class SendToDataLayerThread extends Thread {
    String path;
    DataMap dataMap;
    GoogleApiClient googleClient;

    // Constructor for sending data objects to the data layer
    public SendToDataLayerThread(String p, DataMap data,GoogleApiClient googleClient) {
        path = p;
        dataMap = data;
        this.googleClient = googleClient;
    }

    public void run() {
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleClient).await();
        for (Node node : nodes.getNodes()) {

            // Construct a DataRequest and send over the data layer
            PutDataMapRequest putDMR = PutDataMapRequest.create(path);
            putDMR.getDataMap().putAll(dataMap);
            PutDataRequest request = putDMR.asPutDataRequest();
            DataApi.DataItemResult result = Wearable.DataApi.putDataItem(googleClient,request).await();
            if (result.getStatus().isSuccess()) {
                Log.v("myTag", "DataMap: " + dataMap + " sent to: " + node.getDisplayName());
            } else {
                // Log an error
                Log.v("myTag", "ERROR: failed to send DataMap");
            }
        }
    }
}
