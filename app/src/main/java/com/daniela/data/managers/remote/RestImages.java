package com.daniela.data.managers.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class RestImages {

    @Expose
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
