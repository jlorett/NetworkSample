package com.joshualorett.networksample.network;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Joshua on 12/13/2016.
 */

public class BaseNetworkManager implements NetworkManager {
    public BaseNetworkManager() {}

    /***
     * Execute a request synchronously.
     * @param call Network request call.
     * @param listener Network callback.
     * @param <T> Expected response object.
     * @throws IOException if a problem occurred talking to the server.
     * @throws RuntimeException (and subclasses) if an unexpected error occurs creating the request
     */
    @Override
    public <T> void execute(Call<T> call, NetworkListener listener) throws IOException {
        Response<T> response = call.execute();

        if(response.isSuccessful()) {
            listener.onResponse(response);

            NetworkLogger.logResponse(response);
        } else {
            listener.onNetworkError(new NetworkError());
        }
    }

    /***
     * Execute a request asynchronously.
     * @param call Network request call.
     * @param listener Network callback.
     * @param <T> Expected response object.
     */
    @Override
    public <T> void enqueue(Call<T> call, final NetworkListener listener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(response.isSuccessful()) {
                    listener.onResponse(response);

                    NetworkLogger.logResponse(response);
                } else {
                    listener.onNetworkError(new NetworkError());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                listener.onNetworkError(new NetworkError());
            }
        });
    }
}
