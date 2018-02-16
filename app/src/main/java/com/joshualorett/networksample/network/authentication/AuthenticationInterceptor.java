package com.joshualorett.networksample.network.authentication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Intercept requests to include an authorization header.
 * Created by Joshua on 12/18/2016.
 */

public class AuthenticationInterceptor implements Interceptor {
    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken)
                .method(original.method(), original.body());

        Request request = builder.build();
        return chain.proceed(request);
    }
}
