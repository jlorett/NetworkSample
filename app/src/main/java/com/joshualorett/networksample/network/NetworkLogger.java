package com.joshualorett.networksample.network;

import android.util.Log;

import java.io.IOException;

import retrofit2.Response;

/**
 * Log a network response.
 * Created by Joshua on 12/13/2016.
 */

public class NetworkLogger {
    private static final String LOG_TAG = "Network Manager Log";

    public static void logResponse(Response response) {
        StringBuilder sb = new StringBuilder();

        if(response.raw() != null) {
            sb.append(response.raw().request().toString());
        } else {
            sb.append("Empty response.");
        }

        Log.i(LOG_TAG, sb.toString());
    }

    private void logResponseError(Response response) {
        StringBuilder sb = new StringBuilder();

        if(response.raw() != null) {
            sb.append(response.raw().request().toString());
        } else {
            sb.append("Empty response.");
        }

        if(response.errorBody() != null) {
            try {
                sb.append(new String(response.errorBody().bytes()));
            } catch (IOException e) {
                sb.append("Empty error.");
            }
        } else {
            sb.append("Empty error.");
        }

        Log.e(LOG_TAG, sb.toString());
    }
}
