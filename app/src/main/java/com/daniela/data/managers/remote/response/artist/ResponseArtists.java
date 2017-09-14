package com.daniela.data.managers.remote.response.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class ResponseArtists {

    @Expose
    @SerializedName("artists")
    private ResponseArtistsItems artists;

    public ResponseArtistsItems getArtists() {
        return artists;
    }

    public void setArtists(ResponseArtistsItems artists) {
        this.artists = artists;
    }
}
