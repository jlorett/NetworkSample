package com.joshualorett.networksample.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Builds a request with options to set the host, authenticator, and request headers.
 */

public class RequestBuilder {
    private String host;

    private Authenticator authenticator;

    private List<RequestHeader> requestHeaders;

    private OkHttpClient httpClient;

    public RequestBuilder(OkHttpClient httpClient) {
        this.httpClient = httpClient;
        requestHeaders = new ArrayList<>();
    }

    public RequestBuilder authenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    public RequestBuilder addHeader(RequestHeader requestHeader) {
        requestHeaders.add(requestHeader);
        return this;
    }

    public RequestBuilder host(String host) {
        this.host = host;
        return this;
    }

    public <T> T build(Class<T> requestClass) {
        OkHttpClient.Builder httpClientBuilder = httpClient.newBuilder();

        if(requestHeaders != null) {
            httpClientBuilder.interceptors().clear();

            httpClientBuilder.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request prevRequest = chain.request();

                    if(requestHeaders != null) {
                        Request.Builder modifiedRequestBuilder = prevRequest.newBuilder();
                        // Add all headers
                        for(int i = 0; i < requestHeaders.size(); i++) {
                            RequestHeader requestHeader = requestHeaders.get(i);
                            modifiedRequestBuilder.addHeader(requestHeader.getKey(), requestHeader.getValue());
                        }
                        modifiedRequestBuilder.method(prevRequest.method(), prevRequest.body());
                        return chain.proceed(modifiedRequestBuilder.build());
                    }

                    return chain.proceed(prevRequest);
                }
            });
        }

        if(authenticator != null) {
            httpClientBuilder.authenticator(authenticator);
        }

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        if(host != null) {
            retrofitBuilder.baseUrl(host);
        }
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.client(httpClientBuilder.build());

        return retrofitBuilder.build().create(requestClass);
    }
}
