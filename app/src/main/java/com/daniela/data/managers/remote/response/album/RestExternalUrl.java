package com.daniela.data.managers.remote.response.album;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */


public class RestExternalUrl {

    @Expose
    @SerializedName("spotify")
    private String spotify;

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }
}
