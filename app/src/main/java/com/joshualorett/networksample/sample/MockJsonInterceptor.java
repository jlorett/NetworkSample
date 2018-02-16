package com.joshualorett.networksample.sample;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Fakes a response with the given content.
 * Created by Joshua on 12/18/2016.
 */

public class MockJsonInterceptor implements Interceptor {
    private String content;

    private int statusCode;

    public MockJsonInterceptor(String content, int statusCode) {
        this.content = content;
        this.statusCode = statusCode;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return new Response.Builder()
                .code(statusCode)
                .message("Mock data.")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), content))
                .build();
    }
}
