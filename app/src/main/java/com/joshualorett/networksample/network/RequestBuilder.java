package com.joshualorett.networksample.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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

    private List<Interceptor> interceptors;

    private OkHttpClient httpClient;

    public RequestBuilder(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /***
     * Adds an authenticator to the request.
     * @param authenticator Authenticator to add.
     */
    public RequestBuilder authenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    /***
     * Adds a header with key and value to request.
     * @param requestHeader Request header to add.
     */
    public RequestBuilder addHeader(RequestHeader requestHeader) {
        if(requestHeaders == null) {
            requestHeaders = new ArrayList<>();
        }
        requestHeaders.add(requestHeader);

        return this;
    }

    /***
     * Adds an interceptor to request.
     * @param interceptor Interceptor to add to request.
     */
    public RequestBuilder addInterceptor(Interceptor interceptor) {
        if(interceptors == null) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(interceptor);

        return this;
    }

    /***
     * Adds a host to the request.
     * @param host Request base url.
     */
    public RequestBuilder host(String host) {
        this.host = host;
        return this;
    }

    /***
     * Build a request with the specified parameters from this class.
     * @param requestClass The request class needed for Retrofit.
     * @param <T> The request class we want to build.
     * @return Request class ready to be called.
     */
    public <T> T build(Class<T> requestClass) {
        OkHttpClient.Builder httpClientBuilder = httpClient.newBuilder();

        httpClientBuilder.interceptors().clear();

        if(requestHeaders != null) {
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

        if(interceptors != null) {
            for(Interceptor interceptor : interceptors) {
                httpClientBuilder.interceptors().add(interceptor);
            }
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
