package com.joshualorett.networksample.network;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Send out request synchronously with execute or asynchronously with enqueue.
 */

public interface NetworkManager {
    /***
     * Network callback.
     */
    interface NetworkListener {
        void onResponse(Response response);

        void onNetworkError(NetworkError error);
    }

   <T> void execute(Call<T> call, NetworkListener listener) throws IOException;

    <T> void enqueue(Call<T> call, NetworkListener listener);
}
