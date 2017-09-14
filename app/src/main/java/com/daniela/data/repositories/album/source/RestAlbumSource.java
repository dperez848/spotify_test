package com.daniela.data.repositories.album.source;


import com.daniela.data.managers.remote.response.artist.ResponseArtistAlbums;
import com.daniela.data.managers.remote.response.album.RestAlbum;
import com.daniela.data.managers.remote.restclient.RestClient;
import com.daniela.data.managers.remote.RestManagerPrivateService;
import com.daniela.data.entities.Album;
import com.daniela.data.repositories.album.RepositoryAlbumDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;


/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/24/17.
 */

public class RestAlbumSource implements RepositoryAlbumDataSource {

    private static RestAlbumSource instance;

    public static RestAlbumSource newInstance() {
        if (instance == null) {
            instance = new RestAlbumSource();
        }
        return instance;
    }

    private List<Album> transformRestAlbumsToEntity(List<RestAlbum> restAlbums) {
        List<Album> albums = new ArrayList<>();
        for (RestAlbum restAlbum : restAlbums) {
            albums.add(new RestAlbum.Builder()
                    .withImageUrl(restAlbum.getImages())
                    .withName(restAlbum.getName())
                    .withExternalUrl(restAlbum.getExternalUrl())
                    .withAvailableMarkets(restAlbum.getAvailableMarkets())
                    .build()
                    .toEntity());
        }
       return albums;
    }

    @Override
    public Single<List<Album>> getArtistAlbums(String artistId) {
        return ((RestManagerPrivateService) RestClient.getInstance().getRestClientManager().getService(RestManagerPrivateService.class))
                .getArtistAlbums(artistId)
                .map(new Function<ResponseArtistAlbums, List<Album>>() {
                    @Override
                    public List<Album> apply(ResponseArtistAlbums responseArtistAlbums) throws Exception {
                        return transformRestAlbumsToEntity(responseArtistAlbums.getAlbums());
                    }
                });
    }

}
