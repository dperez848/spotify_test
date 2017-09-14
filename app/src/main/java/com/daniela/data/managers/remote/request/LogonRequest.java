package com.daniela.data.managers.remote.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/24/17.
 */

public class LogonRequest {

    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("division")
    private String division;

    public LogonRequest(String username, String password, String division) {
        this.username = username;
        this.password = password;
        this.division = division;
    }
}
