package com.joshualorett.networksample.sample;

import com.joshualorett.networksample.network.NetworkError;
import com.joshualorett.networksample.network.NetworkListener;
import com.joshualorett.networksample.network.NetworkManager;
import com.joshualorett.networksample.network.RequestBuilder;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by Joshua on 12/13/2016.
 */

public class NetworkCharacterGetter implements CharacterGetter, NetworkListener {
    private static final String HOST = "";

    private NetworkManager networkManager;

    private RequestBuilder requestBuilder;

    private CharacterGetterListener listener;

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

        networkManager.enqueue(service.getCharacters(), this);
    }

    // Network callbacks
    @Override
    public void onResponse(Response response) {
        listener.onGetCharacter((Character[]) response.body());
    }

    @Override
    public void onNetworkError(NetworkError error) {
        listener.onGetCharacterError();
    }

    // Network request
    interface CharacterService {
        @GET("characters")
        Call<Character[]> getCharacters();
    }
}
