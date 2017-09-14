package com.daniela.data.managers.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */

public class RestArtist {

    public RestArtist(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.popularity = builder.popularity;
        this.followers = builder.followers;
        this.images = builder.images;
    }
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("popularity")
    private int popularity;
    @Expose
    @SerializedName("followers")
    private RestFollowers followers;
    @Expose
    @SerializedName("images")
    private List<RestImages> images;

    ///////////Getters///////////////
    public int getPopularity() {
        return popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public RestFollowers getFollowers() {
        return followers;
    }


    public List<RestImages> getImages() {
        return images;
    }

    /////////////Builder///////////////

    public static class Builder {

        private String id;
        private int popularity;
        private String name;
        private RestFollowers followers;
        private List<RestImages> images;

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

        public Builder withImageUrl(List<RestImages> images) {
            this.images = images;
            return this;
        }

        public Builder withTotalFollowers(RestFollowers followers) {
            this.followers = followers;
            return this;
        }

        public RestArtist build() {
            return new RestArtist(this);
        }
    }

    public Artist toEntity() {
        Artist.Builder artistBuilder = Artist.getBuilder();
        setBasicinfoToBuilder(artistBuilder);
        setFollowersToBuilder(artistBuilder);
        setImageToBuilder(artistBuilder);
        return artistBuilder.build();
    }

    private void setBasicinfoToBuilder(Artist.Builder artistBuilder) {
        artistBuilder
                .withId(getId())
                .withPopularity(getPopularity())
                .withName(getName());
    }

    private void setFollowersToBuilder(Artist.Builder artistBuilder) {
        if (followers.getTotal() > 0) {
            artistBuilder.withTotalFollowers(followers.getTotal());
        }
    }

    private void setImageToBuilder(Artist.Builder artistBuilder) {
        if (!images.isEmpty()) {
            artistBuilder.withImageUrl(images.get(0).getUrl());
        }
    }

}