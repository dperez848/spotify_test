package com.daniela.data.managers.remote;

import com.daniela.data.repositories.album.Album;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/13/17.
 */


public class RestAlbum {

    public RestAlbum(Builder builder) {
        this.name = builder.name;
        this.availableMarkets = builder.availableMarkets;
        this.images = builder.images;
        this.externalUrl = builder.externalUrl;
    }

    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("available_markets")
    private List<String> availableMarkets;
    @Expose
    @SerializedName("images")
    private List<RestImages> images;
    @Expose
    @SerializedName("external_urls")
    private RestExternalUrl externalUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImages(List<RestImages> images) {
        this.images = images;
    }

    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    public void setAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
    }

    public RestExternalUrl getExternalUrl() {
        return externalUrl;
    }

    public List<RestImages> getImages() {
        return images;
    }


    public Album toEntity() {
        Album.Builder albumBuilder = Album.getBuilder();
        setBasicinfoToBuilder(albumBuilder);
        setImageToBuilder(albumBuilder);
        setMarketsToBuilder(albumBuilder);
        setExternalUrl(albumBuilder);
        return albumBuilder.build();
    }

    private void setMarketsToBuilder(Album.Builder albumBuilder) {
        if (!availableMarkets.isEmpty()) {
            albumBuilder.withAvailableMarkets(availableMarkets);
        }
    }

    private void setBasicinfoToBuilder(Album.Builder artistBuilder) {
        artistBuilder
                .withName(getName());
    }


    private void setImageToBuilder(Album.Builder artistBuilder) {
        if (!images.isEmpty()) {
            artistBuilder.withImageUrl(images.get(0).getUrl());
        }
    }

    private void setExternalUrl(Album.Builder artistBuilder) {
        if (externalUrl != null) {
            artistBuilder.withExternalUrl(externalUrl.getSpotify());
        }else{
            artistBuilder.withExternalUrl("");


        }
    }

    /////////////Builder///////////////

    public static class Builder {

        private String name;
        private List<String> availableMarkets;
        private List<RestImages> images;
        private RestExternalUrl externalUrl;


        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withImageUrl(List<RestImages> images) {
            this.images = images;
            return this;
        }

        public Builder withAvailableMarkets(List<String> availableMarkets) {
            this.availableMarkets = availableMarkets;
            return this;
        }
        public Builder withExternalUrl(RestExternalUrl externalUrl) {
            this.externalUrl = externalUrl;
            return this;
        }


        public RestAlbum build() {
            return new RestAlbum(this);
        }
    }

}