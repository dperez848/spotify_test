package com.daniela.data.repositories.album;


import java.util.List;

import io.reactivex.Single;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/24/17.
 */

public interface RepositoryAlbumDataSource {

    Single<List<Album>> getArtistAlbums(String artistId);

}
