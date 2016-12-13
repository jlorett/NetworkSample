package com.joshualorett.networksample.network;

import retrofit2.Response;

/***
 * Network callback.
 */
public interface NetworkListener {
    void onResponse(Response response);

    void onNetworkError(NetworkError error);
}
