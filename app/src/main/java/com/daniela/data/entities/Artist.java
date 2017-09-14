package com.daniela.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */

public class Artist implements Parcelable {

    private String id;
    private int popularity;
    private String name;
    private int totalFollowers;
    private String imageUrl;

    private Artist(Builder builder) {
        this.id = builder.id;
        this.popularity = builder.popularity;
        this.name = builder.name;
        this.totalFollowers = builder.totalFollowers;
        this.imageUrl = builder.imageUrl;
    }


    public static class Builder {

        private String id;
        private int popularity;
        private String name;
        private int totalFollowers;
        private String imageUrl;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withPopularity(int popularity) {
            this.popularity = popularity;
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

        public Builder withTotalFollowers(int totalFollowers) {
            this.totalFollowers = totalFollowers;
            return this;
        }

        public Artist build() {
            return new Artist(this);
        }
    }

    public int getPopularity() {
        return popularity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalFollowers() {
        return totalFollowers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    ////////////////////Parcelable/////////////////////

    protected Artist(Parcel in) {
        id = in.readString();
        popularity = in.readInt();
        name = in.readString();
        totalFollowers = in.readInt();
        imageUrl = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public static Builder getBuilder() {
        return new Builder();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(popularity);
        dest.writeString(name);
        dest.writeInt(totalFollowers);
        dest.writeString(imageUrl);
    }
}

