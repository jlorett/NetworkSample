package com.joshualorett.networksample.network;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by Joshua on 12/13/2016.
 */

public interface NetworkManager {
   <T> void execute(Call<T> call, NetworkListener listener) throws IOException;

    <T> void enqueue(Call<T> call, NetworkListener listener);
}
