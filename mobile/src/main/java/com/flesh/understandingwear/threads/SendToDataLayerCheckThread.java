package com.flesh.understandingwear.threads;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by afleshner on 6/11/2015.
 */
public class SendToDataLayerCheckThread extends Thread {

    String path;
    String message;
    GoogleApiClient googleClient;


    // Constructor to send a message to the data layer
    public SendToDataLayerCheckThread(String p, String msg, GoogleApiClient googleClient) {
        path = p;
        message = msg;
        this.googleClient = googleClient;
    }

    public void run() {
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleClient).await();
        for (Node node : nodes.getNodes()) {
            MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleClient, node.getId(), path, message.getBytes()).await();
            if (result.getStatus().isSuccess()) {
                Log.v("CheckTag", "Message: {" + message + "} sent to: " + node.getDisplayName());
            }
            else {
                // Log an error
                Log.v("CheckTag", "ERROR: failed to send Message");
            }
        }
    }

}
