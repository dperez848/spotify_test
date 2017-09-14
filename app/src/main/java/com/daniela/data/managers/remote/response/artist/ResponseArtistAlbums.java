package com.daniela.data.managers.remote.response.artist;

import com.daniela.data.managers.remote.response.album.RestAlbum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class ResponseArtistAlbums {

    @Expose
    @SerializedName("items")
    private List<RestAlbum> albums;

    public List<RestAlbum> getAlbums() {
        return albums;
    }

}
