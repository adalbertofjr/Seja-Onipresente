package com.example.android.sunshine.util;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;

import com.example.android.sunshine.watchface.SunshineWatchFaceService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

/**
 * SunshineWatchFaceUtil
 * Created by Adalberto Fernandes Júnior on 19/11/17.
 * Copyright © 2016. All rights reserved.
 */

public class SunshineWatchFaceUtil {
    private static String LOG_TAG = SunshineWatchFaceUtil.class.getSimpleName();

    public static final String SUNSHINE_PATH = "/sunshine";
    public static final String IMAGE_PATH = "/image";
    public static final String MAX_KEY = "max";
    public static final String MIN_KEY = "min";
    public static final String IMAGE_KEY = "image";

    public static final int DEFAULT_TEMP = -999;

    /**
     * The {@link DataMap} key for {@link SunshineWatchFaceService} background color name.
     * The color name must be a {@link String} recognized by {@link Color#parseColor}.
     */
    public static final String KEY_BACKGROUND_COLOR = "BACKGROUND_COLOR";

    /**
     * The {@link DataMap} key for {@link SunshineWatchFaceService} hour digits color name.
     * The color name must be a {@link String} recognized by {@link Color#parseColor}.
     */
    public static final String KEY_HOURS_COLOR = "HOURS_COLOR";

    /**
     * The {@link DataMap} key for {@link SunshineWatchFaceService} minute digits color name.
     * The color name must be a {@link String} recognized by {@link Color#parseColor}.
     */
//    public static final String KEY_MINUTES_COLOR = "MINUTES_COLOR";

    /**
     * The {@link DataMap} key for {@link SunshineWatchFaceService} second digits color name.
     * The color name must be a {@link String} recognized by {@link Color#parseColor}.
     */
    public static final String KEY_SECONDS_COLOR = "SECONDS_COLOR";

    /**
     * The path for the {@link DataItem} containing {@link SunshineWatchFaceService} configuration.
     */
    public static final String PATH_WITH_FEATURE = "/watch_face_config/Digital";


    /**
     * Name of the default interactive mode background color and the ambient mode background color.
     */
    public static final String COLOR_NAME_DEFAULT_AND_AMBIENT_BACKGROUND = "Blue";
    public static final int COLOR_VALUE_DEFAULT_AND_AMBIENT_BACKGROUND =
            parseColor(COLOR_NAME_DEFAULT_AND_AMBIENT_BACKGROUND);

    /**
     * Name of the default interactive mode hour digits color and the ambient mode hour digits
     * color.
     */
    public static final String COLOR_NAME_DEFAULT_AND_AMBIENT_HOUR_DIGITS = "White";
    public static final int COLOR_VALUE_DEFAULT_AND_AMBIENT_HOUR_DIGITS =
            parseColor(COLOR_NAME_DEFAULT_AND_AMBIENT_HOUR_DIGITS);

    /**
     * Name of the default interactive mode minute digits color and the ambient mode minute digits
     * color.
     */
    public static final String COLOR_NAME_DEFAULT_AND_AMBIENT_MINUTE_DIGITS = "White";
    public static final int COLOR_VALUE_DEFAULT_AND_AMBIENT_MINUTE_DIGITS =
            parseColor(COLOR_NAME_DEFAULT_AND_AMBIENT_MINUTE_DIGITS);

    /**
     * Name of the default interactive mode second digits color and the ambient mode second digits
     * color.
     */
    public static final String COLOR_NAME_DEFAULT_AND_AMBIENT_SECOND_DIGITS = "Gray";
    public static final int COLOR_VALUE_DEFAULT_AND_AMBIENT_SECOND_DIGITS =
            parseColor(COLOR_NAME_DEFAULT_AND_AMBIENT_SECOND_DIGITS);


    private static int parseColor(String colorName) {
        return Color.parseColor(colorName.toLowerCase());
    }


    public static void fetConfigDataMap(final Context context, final FetchConfigDataMapCallback callback) {
        Task<List<Node>> connectedNodes = Wearable.getNodeClient(context).getConnectedNodes();

        connectedNodes.addOnSuccessListener(new OnSuccessListener<List<Node>>() {
            @Override
            public void onSuccess(List<Node> nodesList) {
                for (Node node : nodesList) {
                    Uri uri = new Uri.Builder()
                            .scheme(PutDataRequest.WEAR_URI_SCHEME)
                            .path(SunshineWatchFaceUtil.SUNSHINE_PATH)
                            .authority(node.getId()) //id which has sent data
                            .build();

                    Task<DataItem> dataItem = Wearable.getDataClient(context).getDataItem(uri);

                    dataItem.addOnSuccessListener(new OnSuccessListener<DataItem>() {
                        @Override
                        public void onSuccess(DataItem dataItem) {
                            if (dataItem != null) {
                                DataMapItem dataMapItem = DataMapItem.fromDataItem(dataItem);
                                DataMap dataMap = dataMapItem.getDataMap();

                                if (dataMap.size() > 0) {
                                    callback.onConfigDataMapFetched(dataMap);
                                }
                            }
                        }
                    });
                }

            }
        });
    }

    public static void fetConfigImageMap(final Context context, final FetchConfigDataMapCallback callback) {
        Task<List<Node>> connectedNodes = Wearable.getNodeClient(context).getConnectedNodes();


        connectedNodes.addOnSuccessListener(new OnSuccessListener<List<Node>>() {
            @Override
            public void onSuccess(List<Node> nodesList) {
                for (Node node : nodesList) {
                    Uri uri = new Uri.Builder()
                            .scheme(PutDataRequest.WEAR_URI_SCHEME)
                            .path(SunshineWatchFaceUtil.IMAGE_PATH)
                            .authority(node.getId()) //id which has sent data
                            .build();

                    Task<DataItem> dataItem = Wearable.getDataClient(context).getDataItem(uri);

                    dataItem.addOnSuccessListener(new OnSuccessListener<DataItem>() {
                        @Override
                        public void onSuccess(DataItem dataItem) {
                            if (dataItem != null) {
                                DataMapItem dataMapItem = DataMapItem.fromDataItem(dataItem);
                                DataMap dataMap = dataMapItem.getDataMap();

                                if (dataMap.size() > 0) {
                                    callback.onConfigDataMapFetched(dataMap);
                                }
                            }
                        }
                    });
                }

            }
        });

    }

    /**
     * Callback interface to perform an action with the current config {@link DataMap} for
     * {@link SunshineWatchFaceService}.
     */
    public interface FetchConfigDataMapCallback {
        /**
         * Callback invoked with the current config {@link DataMap} for
         * {@link SunshineWatchFaceService}.
         */
        void onConfigDataMapFetched(DataMap config);
    }
}
