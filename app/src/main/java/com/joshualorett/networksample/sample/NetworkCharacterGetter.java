package com.joshualorett.networksample.sample;

import android.content.Context;

import com.joshualorett.networksample.R;
import com.joshualorett.networksample.common.AssetLoader;
import com.joshualorett.networksample.network.BaseNetworkManager;
import com.joshualorett.networksample.common.Cancellable;
import com.joshualorett.networksample.network.HttpClientProvider;
import com.joshualorett.networksample.network.NetworkError;
import com.joshualorett.networksample.network.NetworkManager;
import com.joshualorett.networksample.network.RequestBuilder;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Get characters from a network.
 */

public class NetworkCharacterGetter implements CharacterGetter, NetworkManager.NetworkListener, Cancellable {
    private static final String HOST = "http://localhost.com";

    private NetworkManager networkManager;

    private RequestBuilder requestBuilder;

    private CharacterGetterListener listener;

    private Call call;

    /**
     * Get characters from a network request.
     * @param networkManager makes network request.
     * @param requestBuilder request builder with maybe some fields set.
     */
    public NetworkCharacterGetter(NetworkManager networkManager, RequestBuilder requestBuilder) {
        this.networkManager = networkManager;
        this.requestBuilder = requestBuilder;
    }

    @Override
    public void get(CharacterGetterListener listener) {
        this.listener = listener;

        CharacterService service = requestBuilder.host(HOST).build(CharacterService.class);

        call = service.getCharacters();

        networkManager.enqueue(call, this);
    }

    // Network callbacks
    @Override
    public void onResponse(Response response) {
        listener.onGetCharacter((Character[]) response.body());
    }

    @Override
    public void onNetworkError(NetworkError error) {
        listener.onGetCharacterError(error.getStatusCode());
    }

    @Override
    public void cancel() {
        if(call != null && !call.isExecuted() && !call.isCanceled()) {
            call.cancel();
        }
    }

    // Network request
    interface CharacterService {
        @GET("characters")
        Call<Character[]> getCharacters();
    }

    // Create a NetworkCharacterGetter instance.
    public static class Factory {
        public static NetworkCharacterGetter create() throws IOException {
            OkHttpClient httpClient = HttpClientProvider.getInstance().getHttpClient();

            NetworkManager networkManager = new BaseNetworkManager();

            RequestBuilder requestBuilder = new RequestBuilder(httpClient);

            return new NetworkCharacterGetter(networkManager, requestBuilder);
        }

        public static NetworkCharacterGetter createWithMock(Context context) throws IOException {
            OkHttpClient httpClient = HttpClientProvider.getInstance().getHttpClient();

            NetworkManager networkManager = new BaseNetworkManager();

            // Use mock data for request.
            RequestBuilder requestBuilder = new RequestBuilder(httpClient)
                    .addInterceptor(new MockJsonInterceptor(AssetLoader.load(context, R.raw.sample), 200));

            return new NetworkCharacterGetter(networkManager, requestBuilder);
        }
    }
}
