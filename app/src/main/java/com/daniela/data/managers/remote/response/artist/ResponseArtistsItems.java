package com.daniela.data.managers.remote.response.artist;

import com.daniela.data.managers.remote.response.artist.RestArtist;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class ResponseArtistsItems {

    @Expose
    @SerializedName("items")
    private List<RestArtist> artists;

    public List<RestArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<RestArtist> artists) {
        this.artists = artists;
    }
}
