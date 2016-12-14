package com.joshualorett.networksample.network;

import okhttp3.OkHttpClient;

/**
 * Provides a OkHttpClient singleton.
 */
public class HttpClientProvider {
    private static HttpClientProvider ourInstance = new HttpClientProvider();

    public static HttpClientProvider getInstance() {
        return ourInstance;
    }

    private static OkHttpClient httpClient;

    private HttpClientProvider() {
    }

    public OkHttpClient getHttpClient() {
        if(httpClient == null) {
            httpClient = new OkHttpClient();
        }

        return httpClient;
    }
}
