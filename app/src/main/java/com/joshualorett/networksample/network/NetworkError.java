package com.joshualorett.networksample.network;

import retrofit2.Response;

/**
 * A network error object with helpful information.
 */

public class NetworkError {
    int statusCode;

    private String message;

    private Response response;

    public NetworkError() {}

    public NetworkError(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
