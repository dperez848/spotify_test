package com.daniela.data;


import com.daniela.data.repositories.album.RepositoryAlbum;
import com.daniela.data.repositories.artist.RepositoryArtist;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/26/17.
 */

public class Injection {

    public static RepositoryArtist getArtistRepository() {
        return RepositoryArtist.newInstance();
    }

    public static RepositoryAlbum getAlbumRepository() {
        return RepositoryAlbum.newInstance();
    }

}
