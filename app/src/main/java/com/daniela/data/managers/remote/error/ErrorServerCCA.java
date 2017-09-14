package com.daniela.data.managers.remote.error;

import com.google.gson.annotations.SerializedName;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/24/17.
 */

public class ErrorServerCCA {

    @SerializedName("message")
    private String message;

    public String getMessageError() {
        return message;
    }

    public void setMessageError(String message) {
        this.message = message;
    }
}