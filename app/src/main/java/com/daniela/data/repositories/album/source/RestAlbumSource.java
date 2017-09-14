package com.daniela.data.repositories.album.source;


import com.daniela.data.managers.remote.Artist;
import com.daniela.data.managers.remote.ResponseArtistAlbums;
import com.daniela.data.managers.remote.ResponseArtists;
import com.daniela.data.managers.remote.RestAlbum;
import com.daniela.data.managers.remote.RestArtist;
import com.daniela.data.managers.remote.RestClient;
import com.daniela.data.managers.remote.RestManagerPrivateService;
import com.daniela.data.repositories.album.Album;
import com.daniela.data.repositories.album.RepositoryAlbumDataSource;
import com.daniela.data.repositories.artist.RepositoryArtistDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

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
