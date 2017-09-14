package com.daniela.data.repositories.album;

import android.support.annotation.NonNull;

import com.daniela.data.entities.Album;
import com.daniela.data.repositories.album.source.RestAlbumSource;

import java.util.List;

import io.reactivex.Single;


/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/24/17.
 */

public class RepositoryAlbum implements RepositoryAlbumDataSource {

    private static RepositoryAlbum instance = null;
    private static RestAlbumSource sourceRest;

    @NonNull
    public static RepositoryAlbum newInstance() {
        if (instance == null) {
            instance = new RepositoryAlbum();
        }
        return instance;
    }


    private RestAlbumSource getRestSource() {
        if (sourceRest == null) {
            sourceRest = RestAlbumSource.newInstance();
        }
        return sourceRest;
    }

    @Override
    public Single<List<Album>> getArtistAlbums(String artistId) {
        return getRestSource().getArtistAlbums(artistId);
    }

}
