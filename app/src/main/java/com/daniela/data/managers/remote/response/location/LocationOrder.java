package com.daniela.data.managers.remote.response.location;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 6/9/17.
 */

public class LocationOrder implements Parcelable {

    public static final String STATUS_CONFIRMED = "confirmed";
    public static final String STATUS_VIEWED = "viewed";

    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("note")
    private String note;


    public LocationOrder() {
    }

    protected LocationOrder(Parcel in) {
        status = in.readString();
        note = in.readString();
    }

    public static final Creator<LocationOrder> CREATOR = new Creator<LocationOrder>() {
        @Override
        public LocationOrder createFromParcel(Parcel in) {
            return new LocationOrder(in);
        }

        @Override
        public LocationOrder[] newArray(int size) {
            return new LocationOrder[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(note);
    }
}
