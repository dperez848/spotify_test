package com.daniela.data.managers.remote.response.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class RestFollowers {

    @Expose
    @SerializedName("total")
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
