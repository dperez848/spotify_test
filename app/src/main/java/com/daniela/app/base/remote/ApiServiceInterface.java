package com.daniela.app.base.remote;



import com.daniela.data.managers.remote.response.artist.ResponseArtistAlbums;
import com.daniela.data.managers.remote.response.artist.ResponseArtists;
import com.daniela.data.managers.remote.response.artist.RestArtist;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 5/24/17.
 */

public interface ApiServiceInterface {

    @GET("search")
    Single<ResponseArtists> searchArtist(@Query("q") String query, @Query("type") String type);

    @GET("artists/{id}/albums")
    Single<ResponseArtistAlbums> getArtistAlbums(@Path("id") String artistId);

    @GET("albums/{id}")
    Single<RestArtist> getAlbum(@Path("id") String albumId);

}
