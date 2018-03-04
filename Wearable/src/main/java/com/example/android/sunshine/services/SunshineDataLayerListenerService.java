package com.example.android.sunshine.services;

import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.concurrent.TimeUnit;

/**
 * SunshineDataLayerListenerService
 * Created by Adalberto Fernandes Júnior on 20/01/2018.
 * Copyright © 2018. All rights reserved.
 */

public class SunshineDataLayerListenerService extends WearableListenerService {
    private String LOG_TAG = SunshineDataLayerListenerService.class.getSimpleName();

//    private static final String SUNSHINE_PATH = "/sunshine";
//    private static final String IMAGE_PATH = "/image";
//    private static final String MAX_KEY = "max";
//    private static final String MIN_KEY = "min";

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(LOG_TAG, "onDataChanged: " + dataEvents);

        if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
            ConnectionResult connectionResult = mGoogleApiClient
                    .blockingConnect(30, TimeUnit.SECONDS);
            if (!connectionResult.isSuccess()) {
                Log.e(LOG_TAG, "DataLayerListenerService failed to connect to GoogleApiClient, "
                        + "error code: " + connectionResult.getErrorCode());
                return;
            }
        }
    }
}
