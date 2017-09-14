package com.daniela.data.repositories.artist;


import com.daniela.data.managers.remote.Artist;
import com.daniela.data.managers.remote.RestArtist;

import io.reactivex.Single;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/24/17.
 */

public interface RepositoryArtistDataSource {

    Single<Artist> searchArtist(String artistName);


}
