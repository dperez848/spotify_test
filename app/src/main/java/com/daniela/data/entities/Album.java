package com.daniela.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Locale;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */

public class Album implements Parcelable {

    private String name;
    private List<String> availableMarkets;
    private String imageUrl;
    private String externalUrl;

    private Album(Builder builder) {
        this.availableMarkets = builder.availableMarkets;
        this.name = builder.name;
        this.imageUrl = builder.imageUrl;
        this.externalUrl = builder.externalUrl;
    }

    public static class Builder {

        private String name;
        private String imageUrl;
        private String externalUrl;
        private List<String> availableMarkets;

        public Builder withAvailableMarkets(List<String> availableMarkets) {
            this.availableMarkets = availableMarkets;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder withExternalUrl(String externalUrl) {
            this.externalUrl = externalUrl;
            return this;
        }

        public Album build() {
            return new Album(this);
        }
    }

    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    public void setAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getCountriesMarkets() {
        StringBuilder countriesMarkets = new StringBuilder();
        for (String market : getAvailableMarkets()) {
            Locale l = new Locale("", market);
            countriesMarkets.append(l.getDisplayCountry());
            countriesMarkets.append(", ");
        }
        return countriesMarkets.toString().substring(0, countriesMarkets.toString().length() - 2);
    }
    ////////////////////Parcelabel/////////////////////

    protected Album(Parcel in) {
        name = in.readString();
        availableMarkets = in.createStringArrayList();
        imageUrl = in.readString();
        externalUrl = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeStringList(availableMarkets);
        dest.writeString(imageUrl);
        dest.writeString(externalUrl);
    }

}