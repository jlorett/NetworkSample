package com.joshualorett.networksample.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Builds a request with options to set the baseUrl and HttpClientBuilder.
 */

public class RequestBuilder {
    private String baseUrl;

    private OkHttpClient.Builder httpClientBuilder;

    public RequestBuilder(OkHttpClient.Builder httpClientBuilder) {
        this.httpClientBuilder = httpClientBuilder;
    }

    /***
     * Adds a baseUrl to the request.
     * @param baseUrl Request base url.
     */
    public RequestBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /***
     * Build a request with the specified parameters from this class.
     * @param requestClass The request class needed for Retrofit.
     * @param <T> The request class we want to build.
     * @return Request class ready to be called.
     */
    public <T> T build(Class<T> requestClass) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        if(baseUrl != null) {
            retrofitBuilder.baseUrl(baseUrl);
        }
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.client(httpClientBuilder.build());

        return retrofitBuilder.build().create(requestClass);
    }
}
