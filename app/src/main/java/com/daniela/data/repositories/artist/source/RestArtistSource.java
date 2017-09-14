package com.daniela.data.repositories.artist.source;


import com.daniela.data.entities.Artist;
import com.daniela.data.managers.remote.response.artist.ResponseArtists;
import com.daniela.data.managers.remote.response.artist.RestArtist;
import com.daniela.data.managers.remote.restclient.RestClient;
import com.daniela.data.managers.remote.RestManagerPrivateService;
import com.daniela.data.repositories.artist.RepositoryArtistDataSource;

import io.reactivex.Single;
import io.reactivex.functions.Function;


/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/24/17.
 */

public class RestArtistSource implements RepositoryArtistDataSource {

    private static RestArtistSource instance;

    public static RestArtistSource newInstance() {
        if (instance == null) {
            instance = new RestArtistSource();
        }
        return instance;
    }

    @Override
    public Single<Artist> searchArtist(String artistName) {
        return ((RestManagerPrivateService) RestClient.getInstance().getRestClientManager().getService(RestManagerPrivateService.class))
                .searchArtist(artistName)
                .map(new Function<ResponseArtists, Artist>() {
                    @Override
                    public Artist apply(ResponseArtists responseArtists) throws Exception {
                        return transformRestartistToEntity(responseArtists.getArtists().getArtists().get(0));
                    }
                });
    }

    private Artist transformRestartistToEntity(RestArtist artist) {
        return new RestArtist.Builder()
                .withImageUrl(artist.getImages())
                .withName(artist.getName())
                .withPopularity(artist.getPopularity())
                .withId(artist.getId())
                .withTotalFollowers(artist.getFollowers())
                .build()
                .toEntity();
    }

}
